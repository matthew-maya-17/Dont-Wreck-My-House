package learn.Mastery.ui;

import learn.Mastery.data.DataException;
import learn.Mastery.domain.GuestService;
import learn.Mastery.domain.HostService;
import learn.Mastery.domain.ReservationService;

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

    public void run() {
        view.displayHeader("Welcome to Sustainable Foraging");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
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
                case GENERATE:
                    generate();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    // top level menu
    private void viewReservations() {
    }

    private void addReservation() {

    }

    private void editReservation() {

    }

    private void deleteReservation() throws DataException {

    }

    // support methods
    private Forager getForager() {
        String lastNamePrefix = view.getForagerNamePrefix();
        List<Forager> foragers = foragerService.findByLastName(lastNamePrefix);
        return view.chooseForager(foragers);
    }

    private Item getItem() {
        Category category = view.getItemCategory();
        List<Item> items = itemService.findByCategory(category);
        return view.chooseItem(items);
    }
}
