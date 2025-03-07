package learn.Mastery.data;

import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findByHostId(String hostId) throws DataException;

    Reservation addReservation(Reservation reservation) throws DataException;

    Boolean updateReservation(Reservation reservation) throws DataException;

    Boolean deleteReservation(Reservation reservation) throws DataException;

}
