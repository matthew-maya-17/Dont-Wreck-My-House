package learn.Mastery.ui;

import learn.Mastery.data.DataException;
import learn.Mastery.domain.GuestService;
import learn.Mastery.domain.HostService;
import learn.Mastery.domain.ReservationService;
import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;

import java.io.FileNotFoundException;
import java.time.LocalDate;
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

//    public void run() {
//        view.displayHeader("Welcome to Mastery Project: Don't Wreck My House");
//        try {
//            runAppLoop();
//        } catch (DataException ex) {
//            view.displayException(ex);
//        }
//        view.displayHeader("Goodbye.");
//    }
//
//    private void runAppLoop() throws DataException {
//        MainMenuOption option;
//        do {
//            option = view.selectMainMenuOption();
//            switch (option) {
//                case VIEW_RESERVATIONS:
//                    viewReservations();
//                    break;
//                case ADD_RESERVATION:
//                    addReservation();
//                    break;
//                case EDIT_RESERVATION:
//                    editReservation();
//                    break;
//                case DELETE_RESERVATION:
//                    deleteReservation();
//                    break;
//                case GENERATE:
//                    generate();
//                    break;
//            }
//        } while (option != MainMenuOption.EXIT);
//    }

    // top level menu
    private void viewReservations() {
    }

    private void addReservation() throws DataException, FileNotFoundException {
        view.displayHeader(MainMenuOption.ADD_RESERVATION.getMessage());
        String guestEmail = view.getEmail("Guest");
        String hostEmail = view.getEmail("Host");
        Host hostLocation = hostService.findByEmail(hostEmail);
        List<Reservation> reservations = reservationService.findByHostId(hostLocation.getHost_id());
        for (Reservation r: reservations){
            System.out.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s",
                    r.getReservation_id(),
                    r.getStart_date(),
                    r.getEnd_date(),
                    r.getGuest().getLast_name(),
                    r.getGuest().getFirst_name(),
                    r.getGuest().getEmail());
        }

    }

    private void editReservation() {

    }

    private void deleteReservation() throws DataException {

    }

    // support methods
//    private Host getHost() {
//        String lastNamePrefix = view.getForagerNamePrefix();
//        List<Host> foragers = foragerService.findByLastName(lastNamePrefix);
//        return view.chooseForager(foragers);
//    }
//
//    private Guest getGuest() {
//        Category category = view.getItemCategory();
//        List<Guest> items = itemService.findByCategory(category);
//        return view.chooseItem(items);
//    }
}
