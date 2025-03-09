package learn.Mastery.ui;

import learn.Mastery.data.DataException;
import learn.Mastery.domain.GuestService;
import learn.Mastery.domain.HostService;
import learn.Mastery.domain.ReservationResult;
import learn.Mastery.domain.ReservationService;
import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
                System.out.println("Host not found. Please enter a registered email.");
            }
        } while (guest == null);

        do {
            hostEmail = view.getEmail("Host");
            hostLocation = hostService.findByEmail(hostEmail);
            if (hostLocation == null) {
                System.out.println("Host not found. Please enter a registered email.");
            }
        } while (hostLocation == null);

        List<Reservation> reservations = reservationService.findByHostId(hostLocation.getHost_id());
        view.displayAllReservationByHostId(reservations);
        LocalDate start;
        LocalDate end;
        boolean isValid;

        do {
            isValid = true;

            start = view.getDate("Start");
            end = view.getDate("End");

            if (start.isAfter(end)) {
                System.out.println("Reservation start date cannot be after the reservation end date.");
                isValid = false;
            }

            if (start.isBefore(LocalDate.now())) {
                System.out.println("Reservation start & end date cannot be in the past. They must both be in the future.");
                isValid = false;
            }

            for (Reservation r : reservations) {
                if (!(end.isBefore(r.getStart_date()) || start.isAfter(r.getEnd_date()))) {
                    System.out.println("Reservation overlaps with another of the host's existing reservations.");
                    isValid = false;
                }
            }

        } while (!isValid);
        reservationToAdd.setHost(hostLocation);
        reservationToAdd.setGuest(guest);
        reservationToAdd.setStart_date(start);
        reservationToAdd.setEnd_date(end);

        view.displayHeader("Summary");
        view.displayDate(start, "Start");
        view.displayDate(end, "End");
        System.out.println("Total: " + reservationService.calculateReservationTotal(reservationToAdd));
        boolean answer = view.readBooleanSummary();
        if (answer){
            ReservationResult<Reservation> result = reservationService.addReservation(reservationToAdd);
            if (result.isSuccess()){
                String successMessage = String.format("Reservation %s created.", result.getPayload().getReservation_id());
                view.displayStatus(true, successMessage);
            }
            else {
                view.displayStatus(false, result.getErrorMessages());
            }
        }
        else {
            System.out.println("No worries, goodbye");
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
                System.out.println("Host not found. Please enter a registered email.");
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
        List<Reservation> reservations = reservationService.findByHostId(hostLocation.getHost_id());
        view.displayAllFutureGuestReservationByHostId(reservations);


        int reservationId = view.readInt("Reservation ID: ");
        Reservation reservationToBeUpdated = null;

        for (Reservation r : reservations){
            if(r.getReservation_id() == reservationId){
                reservationToBeUpdated = r;
            }
        }

        view.displayHeader(String.format("Editing Reservation %s", reservationId));
        LocalDate start;
        LocalDate end;
        boolean isValid;

        do {
            isValid = true;

            start = view.readLocalDate("Start (" + reservationToBeUpdated.getStart_date().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "): ");
            end = view.readLocalDate("End (" + reservationToBeUpdated.getEnd_date().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "): ");

            if (start.isAfter(end)) {
                System.out.println("Reservation start date cannot be after the reservation end date.");
                isValid = false;
            }

            if (start.isBefore(LocalDate.now())) {
                System.out.println("Reservation start & end date cannot be in the past. They must both be in the future.");
                isValid = false;
            }

            for (Reservation r : reservations) {
                if (r.getReservation_id() == reservationToBeUpdated.getReservation_id()) {
                    continue;
                }
                if (!(end.isBefore(r.getStart_date()) || start.isAfter(r.getEnd_date()))) {
                    System.out.println("Reservation overlaps with another of the host's existing reservations.");
                    isValid = false;
                }
            }
        } while (!isValid);

        reservationToBeUpdated.setStart_date(start);
        reservationToBeUpdated.setEnd_date(end);
        BigDecimal newTotal = reservationService.calculateReservationTotal(reservationToBeUpdated);
        reservationToBeUpdated.setTotal(newTotal);
        view.displayHeader("Summary");
        view.displayDate(start, "Start");
        view.displayDate(end, "End");
        view.println("Total: " + newTotal);
        boolean answer = view.readBooleanSummary();
        if (answer){
            ReservationResult<Reservation> result = reservationService.updateReservation(reservationToBeUpdated);
            if (result.isSuccess()){
                String successMessage = String.format("Reservation %s updated.", result.getPayload().getReservation_id());
                view.displayStatus(true, successMessage);
            }
            else {
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
                System.out.println("Host not found. Please enter a registered email.");
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
        List<Reservation> reservations = reservationService.findByHostId(hostLocation.getHost_id());
        view.displayAllFutureGuestReservationByHostId(reservations);


        int reservationId = view.readInt("Reservation ID: ");
        Reservation reservationToBeDeleted = new Reservation();

        for (Reservation r : reservations){
            if(r.getReservation_id() == reservationId){
                reservationToBeDeleted = r;
            }
        }

        ReservationResult<Reservation> result = reservationService.deleteReservation(reservationToBeDeleted);
        if (result.isSuccess()){
            String successMessage = String.format("Reservation %s deleted.", result.getPayload().getReservation_id());
            view.displayStatus(true, successMessage);
        }
        else {
            view.displayStatus(false, result.getErrorMessages());
        }
    }
}
