package learn.Mastery.domain;

import learn.Mastery.data.*;
import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService reservationService;

    @BeforeEach
    void setup(){
        HostRepository hostRepository = new HostRepositoryDouble();
        GuestRepository guestRepository = new GuestRepositoryDouble();
        ReservationRepository reservationRepository = new ReservationRespositoryDouble();
        reservationService = new ReservationService(reservationRepository,guestRepository, hostRepository);
    }

    @Test
    void shouldFindByHostId() throws DataException, FileNotFoundException {
        List<Reservation> reservations = reservationService.findByHostId("74f32f01-9c6d-4e87-b2d9-d389af693b44");
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
    }

    @Test
    void shouldAddValidReservation() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2025, 4, 1));
        test.setEnd_date(LocalDate.of(2025, 4, 8));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(2, result.getPayload().getReservation_id());
    }

    @Test
    void shouldNotAddNullReservation() throws DataException {
        Reservation test = null;
        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Nothing to save. Reservation cannot be Null!"));

    }

    @Test
    void shouldNotAddReservationIfMissingHost() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2025, 4, 1));
        test.setEnd_date(LocalDate.of(2025, 4, 8));
        test.setTotal(new BigDecimal(5));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation must have a host!"));
    }

    @Test
    void shouldNotAddReservationIfHostDoesntExist() throws DataException {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setHost(new Host("000","Mark","emix8m@youtu.be","(818) 8310814",
                "80 Mcbride Circle","Burbank","CA","91505",
                new BigDecimal(132), new BigDecimal(165))); // Fake forager
        reservation.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL")); // Assume valid item]
        reservation.setStart_date(LocalDate.of(2027, 8,20));
        reservation.setEnd_date(LocalDate.of(2027, 8, 25));

        // Act
        ReservationResult<Reservation> result = reservationService.addReservation(reservation);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().contains("Host does not exist."));
    }

    @Test
    void shouldNotAddReservationIfMissingGuest() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2025, 4, 1));
        test.setEnd_date(LocalDate.of(2025, 4, 8));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation must have a guest!"));
    }

    @Test
    void shouldNotAddReservationIfGuestDoesntExist() throws DataException {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setHost(new Host("74f32f01-9c6d-4e87-b2d9-d389af693b44","Gery",
                "ngery2v@ow.ly","(203) 6385708","09 Bashford Trail",
                "New Haven","CT","06505",new BigDecimal(464),new BigDecimal(580))); // Fake forager
        reservation.setGuest(new Guest(100, "Pat", "Smith", "pSmith@gmail.com",
                "(305) 0379821", "NV")); // Assume valid item]
        reservation.setStart_date(LocalDate.of(2027, 8,20));
        reservation.setEnd_date(LocalDate.of(2027, 8, 25));

        // Act
        ReservationResult<Reservation> result = reservationService.addReservation(reservation);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().contains("Guest does not exist."));
    }

    @Test
    void shouldNotAddReservationIfMissingStartDate() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setEnd_date(LocalDate.of(2025, 4, 8));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation must have a start date"));
    }

    @Test
    void shouldNotAddReservationIfMissingEndDate() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2025, 4, 1));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation must have a end date"));
    }

    @Test
    void shouldNotAddReservationIfStartIsAfterEnd() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2025, 4, 10));
        test.setEnd_date(LocalDate.of(2025, 4, 8));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation start date cannot be after the reservation end date."));
    }

    @Test
    void shouldNotAddReservationIfStartIsInPast() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2025, 2, 1));
        test.setEnd_date(LocalDate.of(2025, 2, 8));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation start & end date cannot be in the past. They must both be in the future."));
    }

    @Test
    void shouldNotAddReservationThatSurroundsExistingReservation() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2027, 8,1));
        test.setEnd_date(LocalDate.of(2027, 8, 29));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation overlaps with another of the hosts existing reservations."));
    }

    @Test
    void shouldNotAddReservationInsideExistingReservation() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2027, 8,14));
        test.setEnd_date(LocalDate.of(2027, 8, 16));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation overlaps with another of the hosts existing reservations."));
    }

    @Test
    void shouldNotAddReservationThatOverlapsStartOfExistingRegistration() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2027, 8,11));
        test.setEnd_date(LocalDate.of(2027, 8, 15));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation overlaps with another of the hosts existing reservations."));
    }

    @Test
    void shouldNotAddReservationThatOverlapsEndOfExistingRegistration() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2027, 8,15));
        test.setEnd_date(LocalDate.of(2027, 8, 25));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation overlaps with another of the hosts existing reservations."));
    }

    @Test
    void shouldNotAddReservationThatEndsOnTheStartOfExistingRegistration() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2027, 8,1));
        test.setEnd_date(LocalDate.of(2027, 8, 12));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation overlaps with another of the hosts existing reservations."));
    }

    @Test
    void shouldNotAddReservationThatStartsOnTheEndOfExistingRegistration() throws DataException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2027, 8,19));
        test.setEnd_date(LocalDate.of(2027, 8, 21));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));

        ReservationResult<Reservation> result = reservationService.addReservation(test);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation overlaps with another of the hosts existing reservations."));
    }

    @Test
    void shouldUpdateReservation() throws DataException, FileNotFoundException {
        Reservation updated = reservationService.findByHostId("74f32f01-9c6d-4e87-b2d9-d389af693b44").get(0);
        updated.setStart_date(LocalDate.of(2027, 8,21));
        updated.setEnd_date(LocalDate.of(2027, 8,25));

        ReservationResult<Reservation> result = reservationService.updateReservation(updated);
        assertNotNull(result);
        assertEquals(result.getErrorMessages(), result.getErrorMessages().size()); //no error messages
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldDeleteReservation() throws DataException, FileNotFoundException {
        Reservation reservation = reservationService.findByHostId("74f32f01-9c6d-4e87-b2d9-d389af693b44").get(0);
        ReservationResult<Reservation> result = reservationService.deleteReservation(reservation);

        assertTrue(result.isSuccess());
        assertEquals(0, result.getErrorMessages().size());
        assertEquals(1, reservation.getReservation_id());
    }

    @Test
    void shouldNotDeleteNonExistingReservation() throws DataException, FileNotFoundException {
        Reservation test = new Reservation();
        test.setReservation_id(2);
        test.setStart_date(LocalDate.of(2027, 8,15));
        test.setEnd_date(LocalDate.of(2027, 8, 25));
        test.setTotal(new BigDecimal(5));
        test.setHost(new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75")));
        test.setGuest(new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL"));
        ReservationResult<Reservation> result = reservationService.deleteReservation(test);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertTrue(result.getErrorMessages().get(0).contains("Reservation does not exist"));
    }
}