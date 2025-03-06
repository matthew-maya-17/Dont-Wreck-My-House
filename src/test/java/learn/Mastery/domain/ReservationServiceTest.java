package learn.Mastery.domain;

import learn.Mastery.data.*;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
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
        List<Reservation> reservations = new ArrayList<>();

        reservationService.findByHostId()
    }
}