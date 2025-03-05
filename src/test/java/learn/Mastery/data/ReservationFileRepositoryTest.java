package learn.Mastery.data;

import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservations-seed.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";

    static final Host host = new Host("9d469342-ad0b-4f5a-8d28-e81e690ba29a", "Maya", "matthewmatt2009@hotmail.com", "(631) 3539689",
            "200 Glade Road", "South Miami", "FL","34479",new BigDecimal(477),new BigDecimal("596.25"));

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindExistingHostId() throws DataException {
        List<Reservation> all = repository.findByHostId(host);
        assertNotNull(all);
        assertEquals(3, all.size());
    }

    @Test
    void shouldNotFindNonExistingHostId() throws DataException {
        Host host = new Host();
        host.setHost_id("1234567890");
        List<Reservation> all = repository.findByHostId(host);
        assertEquals(0, all.size());
    }

    @Test
    void shouldAddValidReservation() throws DataException {
        Reservation reservation1 = new Reservation();
        reservation1.setGuest(new Guest(6,"Kenn","Curson","kcurson5@youku.com","(941) 9618942","FL"));
        reservation1.setStart_date(LocalDate.of(2025,3,7));
        reservation1.setEnd_date(LocalDate.of(2025,3,19));
        reservation1.setHost(host);
        reservation1.setTotal(new BigDecimal(1000));

        Reservation result = repository.addReservation(reservation1);
        assertNotNull(result);
        assertEquals(4, result.getReservation_id());
    }

//    @Test
//    void shouldNotAddNonValidReservation() throws DataException {
//        Reservation reservation1 = new Reservation();
//        reservation1.setGuest(new Guest(6,"Kenn","Curson","kcurson5@youku.com","(941) 9618942","FL"));
//        reservation1.setStart_date(LocalDate.of(2025,3,7));
//        reservation1.setEnd_date(LocalDate.of(2025,3,19));
//        Host host = new Host();
//        reservation1.setHost(host);
//        reservation1.setTotal(new BigDecimal(1000));
//
//        Reservation result = repository.addReservation(reservation1);
//        assertNull(result);
////        assertEquals(1, result.getReservation_id());
//    }

    @Test
    void shouldUpdateReservation() throws DataException {
        Reservation reservation1 = new Reservation();
        reservation1.setGuest(new Guest(23,"Nika","Khvan","Google.coms","(941) 9618942","TX"));
        reservation1.setStart_date(LocalDate.of(2025,9,7));
        reservation1.setEnd_date(LocalDate.of(2025,9,19));
        Host host = new Host();
        host.setHost_id("ANNOYING1234567890");
        reservation1.setHost(host);
        reservation1.setTotal(new BigDecimal(1500));

        repository.addReservation(reservation1);

        reservation1.setGuest(new Guest(6,"Kenn","Curson","kcurson5@youku.com","(941) 9618942","FL"));
        reservation1.setStart_date(LocalDate.of(2026,3,7));
        reservation1.setEnd_date(LocalDate.of(2026,3,19));
        reservation1.setHost(host);
        reservation1.setTotal(new BigDecimal(1000));

        Boolean updated = repository.updateReservation(reservation1);
        assertTrue(updated);
        assertEquals(LocalDate.of(2026,3,7), reservation1.getStart_date());
        assertEquals(LocalDate.of(2026,3,19), reservation1.getEnd_date());
    }
}