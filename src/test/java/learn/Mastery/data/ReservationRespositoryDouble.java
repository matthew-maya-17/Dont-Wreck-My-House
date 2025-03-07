package learn.Mastery.data;

import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRespositoryDouble implements ReservationRepository{

    static ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRespositoryDouble(){
        Reservation reservation1 = new Reservation();
        reservation1.setReservation_id(1);
        reservation1.setStart_date(LocalDate.of(2027, 8,12));
        reservation1.setEnd_date(LocalDate.of(2027, 8,19));
        reservation1.setTotal(new BigDecimal(1000));
        reservation1.setHost(new Host("74f32f01-9c6d-4e87-b2d9-d389af693b44","Gery",
                "ngery2v@ow.ly","(203) 6385708","09 Bashford Trail",
                "New Haven","CT","06505",new BigDecimal(464),new BigDecimal(580)));
        reservation1.setGuest(new Guest(5, "Topher", "Cullens", "tophCullens@aol.com",
                "(901) 4983249", "PA"));
        reservations.add(reservation1);
    }

    @Override
    public List<Reservation> findByHostId(String hostId) throws DataException {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation addReservation(Reservation reservation) throws DataException {
        return reservation;
    }

    @Override
    public Boolean updateReservation(Reservation reservation) throws DataException {
        return findByHostId(reservation.getHost().getHost_id()) != null;
    }

    @Override
    public Boolean deleteReservation(Reservation reservation) throws DataException {
        return findByHostId(reservation.getHost().getHost_id()) != null ||
                (reservation.getStart_date().isBefore(LocalDate.now()) && reservation.getEnd_date().isBefore(LocalDate.now()));
    }
}
