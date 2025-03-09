package learn.Mastery;

import learn.Mastery.data.GuestFileRepository;
import learn.Mastery.data.HostFileRepository;
import learn.Mastery.data.ReservationFileRepository;
import learn.Mastery.domain.GuestService;
import learn.Mastery.domain.HostService;
import learn.Mastery.domain.ReservationService;
import learn.Mastery.ui.Controller;
import learn.Mastery.ui.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        configureWithXMLAndRun();
    }

    private static void configureWithXMLAndRun(){
        ApplicationContext container = new ClassPathXmlApplicationContext("dependency-configuration.xml");

        Controller controller = container.getBean(Controller.class);
        // Run the app!
        controller.run();
    }

    private static void configureManuallyAndRun(){
        View view = new View();

        HostFileRepository hostFileRepository = new HostFileRepository("./data/hosts.csv");
        GuestFileRepository guestFileRepository = new GuestFileRepository("./data/guests.csv");
        ReservationFileRepository reservationFileRepository = new ReservationFileRepository("./data/reservations");

        HostService hostService = new HostService(hostFileRepository);
        GuestService guestService = new GuestService(guestFileRepository);
        ReservationService reservationService = new ReservationService(reservationFileRepository, guestFileRepository, hostFileRepository);

        Controller controller = new Controller(hostService, guestService, reservationService, view);
        controller.run();
    }
}