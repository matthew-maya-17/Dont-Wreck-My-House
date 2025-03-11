package learn.Mastery.ui;

import learn.Mastery.data.DataException;
import learn.Mastery.domain.GuestService;
import learn.Mastery.domain.HostService;
import learn.Mastery.domain.ReservationResult;
import learn.Mastery.domain.ReservationService;
import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Controller {

    private final HostService hostService;
    private final GuestService guestService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(HostService hostService, GuestService guestService, ReservationService reservationService, View view) {
        this.hostService = hostService;
        this.guestService = guestService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Mastery Project: Don't Wreck My House");
        try {
            runAppLoop();
        } catch (DataException | FileNotFoundException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException, FileNotFoundException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS:
                    viewReservations();
                    break;
                case ADD_RESERVATION:
                    addReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
                case DELETE_RESERVATION:
                    deleteReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    // top level menu
    private void viewReservations() throws DataException, FileNotFoundException {
        view.displayHeader("View Reservations for Host");
        String hostEmail = view.getEmail("Host");
        Host hostLocation = hostService.findByEmail(hostEmail);
        while (hostLocation == null) {
            view.displayStatus(false, "[INVALID] Enter a valid Host email.");
            hostEmail = view.getEmail("Host");
            hostLocation = hostService.findByEmail(hostEmail);
        }
        List<Reservation> reservations = reservationService.findByHostId(hostLocation.getHost_id());
        view.displayAllReservationByHostId(reservations);
    }

    private void addReservation() throws DataException, FileNotFoundException {
        Reservation reservationToAdd = new Reservation();
        view.displayHeader(MainMenuOption.ADD_RESERVATION.getMessage());
        String guestEmail;
        Guest guest;
        String hostEmail;
        Host hostLocation;

        do {
            guestEmail = view.getEmail("Guest");
            guest = guestService.findByEmail(guestEmail);
            if (guest == null) {
                System.out.println("Guest not found. Please enter a registered email.");
            }
        } while (guest == null);

        do {
            hostEmail = view.getEmail("Host");
            hostLocation = hostService.findByEmail(hostEmail);
            if (hostLocation == null) {
                System.out.println("Host not found. Please enter a registered email.");
            }
        } while (hostLocation == null);
        reservationToAdd.setHost(hostLocation);
        reservationToAdd.setGuest(guest);

        List<Reservation> reservations = reservationService.findByHostId(hostLocation.getHost_id());
        view.displayAllReservationByHostId(reservations);
        boolean canDisplaySummary = view.setStartAndEndDates(reservationToAdd, reservations);

        if(canDisplaySummary) {
            view.displayHeader("Summary");
            view.displayDate(reservationToAdd.getStart_date(), "Start");
            view.displayDate(reservationToAdd.getEnd_date(), "End");
            System.out.println("Total: " + reservationService.calculateReservationTotal(reservationToAdd));
            boolean answer = view.readBooleanSummary();
        }
        ReservationResult<Reservation> result = reservationService.addReservation(reservationToAdd);
        if (result.isSuccess()){
            String successMessage = String.format("Reservation %s created.", result.getPayload().getReservation_id());
            view.displayStatus(true, successMessage);
        }
        else {
            view.displayStatus(false, result.getErrorMessages());
        }
    }

    private void editReservation() throws DataException, FileNotFoundException {
        view.displayHeader(MainMenuOption.EDIT_RESERVATION.getMessage()); //Displays Header
        String guestEmail;
        Guest guest;
        String hostEmail;
        Host hostLocation;

        //Fetches guest email and sees if it's in our Database
        do {
            guestEmail = view.getEmail("Guest");
            guest = guestService.findByEmail(guestEmail);
            if (guest == null) {
                System.out.println("Guest not found. Please enter a registered email.");
            }
        } while (guest == null);

        //Fetches host email and sees if it's in our Database
        do {
            hostEmail = view.getEmail("Host");
            hostLocation = hostService.findByEmail(hostEmail);
            if (hostLocation == null) {
                System.out.println("Host not found. Please enter a registered email.");
            }
        } while (hostLocation == null);

        //Displays all future reservations for guest with host
        view.displayHeader(String.format("%s: %s,%s", hostLocation.getLast_name(),hostLocation.getCity(),hostLocation.getState()));
        List<Reservation> reservations = reservationService.findByHostId(hostLocation.getHost_id()); //All Reservation by Host ID
        List<Reservation> futureReservations = reservations.stream() //Future Reservation by Host ID
                .filter(reservation -> reservation.getStart_date().isAfter(LocalDate.now()))
                .sorted((a,b) -> a.getStart_date().compareTo(b.getStart_date()))
                .toList();
        view.displayAllReservationByHostId(futureReservations);

        if (!(futureReservations.isEmpty())) {
            Reservation reservationToBeUpdated = view.readReservationId(futureReservations);

            view.displayHeader(String.format("Editing Reservation %s", reservationToBeUpdated.getReservation_id()));

            Boolean canDisplaySummary = view.setStartAndEndDates(reservationToBeUpdated, futureReservations);

            BigDecimal newTotal = reservationService.calculateReservationTotal(reservationToBeUpdated);
            reservationToBeUpdated.setTotal(newTotal);

            if(canDisplaySummary) {
                view.displayHeader("Summary");
                view.displayDate(reservationToBeUpdated.getStart_date(), "Start");
                view.displayDate(reservationToBeUpdated.getEnd_date(), "End");
                view.println("Total: " + newTotal);
                view.readBooleanSummary();
            }

            ReservationResult<Reservation> result = reservationService.updateReservation(reservationToBeUpdated);
            if (result.isSuccess()) {
                String successMessage = String.format("Reservation %s updated.", result.getPayload().getReservation_id());
                view.displayStatus(true, successMessage);
            } else {
                view.displayStatus(false, result.getErrorMessages());
            }
        }
    }

    private void deleteReservation() throws DataException, FileNotFoundException {
        view.displayHeader(MainMenuOption.EDIT_RESERVATION.getMessage()); //Displays Header
        String guestEmail;
        Guest guest;
        String hostEmail;
        Host hostLocation;

        //Fetches guest email and sees if it's in our Database
        do {
            guestEmail = view.getEmail("Guest");
            guest = guestService.findByEmail(guestEmail);
            if (guest == null) {
                System.out.println("Guest not found. Please enter a registered email.");
            }
        } while (guest == null);

        //Fetches host email and sees if it's in our Database
        do {
            hostEmail = view.getEmail("Host");
            hostLocation = hostService.findByEmail(hostEmail);
            if (hostLocation == null) {
                System.out.println("Host not found. Please enter a registered email.");
            }
        } while (hostLocation == null);

        //Displays all future reservations for guest with host
        view.displayHeader(String.format("%s: %s,%s", hostLocation.getLast_name(), hostLocation.getCity(), hostLocation.getState()));
        List<Reservation> reservations = reservationService.findByHostId(hostLocation.getHost_id());
        List<Reservation> futureReservations = reservations.stream() //Future Reservation by Host ID
                .filter(reservation -> reservation.getStart_date().isAfter(LocalDate.now()))
                .sorted((a,b) -> a.getStart_date().compareTo(b.getStart_date()))
                .toList();
        view.displayAllReservationByHostId(futureReservations);

        if (!(futureReservations.isEmpty())) {
            Reservation reservationToDelete = view.readReservationId(futureReservations);

            for (Reservation r : reservations) {
                if (r.getReservation_id() == reservationToDelete.getReservation_id()) {
                    reservationToDelete = r;
                }
            }

            ReservationResult<Reservation> result = reservationService.deleteReservation(reservationToDelete);

            if (result.isSuccess()) {
                String successMessage = String.format("Reservation %s deleted.", result.getPayload().getReservation_id());
                view.displayStatus(true, successMessage);
            } else {
                view.displayStatus(false, result.getErrorMessages());
            }
        }
    }
}