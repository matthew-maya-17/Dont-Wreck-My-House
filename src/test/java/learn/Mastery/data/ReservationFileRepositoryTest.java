package learn.Mastery.data;

import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservations-seed.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";
    static Host host;

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

        host = new Host();
        host.setHost_id("9d469342-ad0b-4f5a-8d28-e81e690ba29a");
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
        assertEquals(new BigDecimal(1000), reservation1.getTotal());
        assertEquals("9d469342-ad0b-4f5a-8d28-e81e690ba29a", host.getHost_id());
    }

    @Test
    void shouldUpdateReservation() throws DataException {
        List<Reservation> all = repository.findByHostId(host);
        Reservation index0 = all.get(0);
        index0.setTotal(new BigDecimal(1));
        index0.setStart_date(LocalDate.of(1999, 12, 31));

        boolean result = repository.updateReservation(index0);

        assertTrue(result);
        assertEquals(LocalDate.of(1999, 12, 31), all.get(0).getStart_date());
        assertEquals(new BigDecimal(1), all.get(0).getTotal());
        assertEquals(1, all.get(0).getReservation_id());
    }

    @Test
    void shouldNotUpdateReservation() throws DataException {
        List<Reservation> all = repository.findByHostId(host);
        Reservation index0 = all.get(0);
        index0.setReservation_id(100);
        index0.setTotal(new BigDecimal(1));
        index0.setStart_date(LocalDate.of(1999, 12, 31));

        boolean result = repository.updateReservation(index0);

        assertFalse(result); //Cannot find ID 100
    }

    @Test
    void shouldDeleteReservation() throws DataException {
        List<Reservation> all = repository.findByHostId(host);
        int count = all.size();
        Reservation reservation = all.get(1);
        Boolean result = repository.deleteReservation(reservation);

        assertTrue(result);
        assertEquals(count - 1 , repository.findByHostId(host).size());
    }

    @Test
    public void shouldNotDeleteUnknownId() throws DataException{
        Reservation r = new Reservation();

        Host host = new Host();
        host.setHost_id("123"); //NonExisting HostID therefore Reservation file doesn't exist.

        r.setHost(host);
        boolean result = repository.deleteReservation(r);
        assertFalse(result);
    }
}