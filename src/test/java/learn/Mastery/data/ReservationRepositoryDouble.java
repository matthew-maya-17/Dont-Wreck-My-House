package learn.Mastery.data;

import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository{

    static Host host = new Host("74f32f01-9c6d-4e87-b2d9-d389af693b44","Gery",
            "ngery2v@ow.ly","(203) 6385708","09 Bashford Trail",
            "New Haven","CT","06505",new BigDecimal(464),new BigDecimal(580));

    static ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble(){
        Reservation reservation1 = new Reservation();
        reservation1.setReservation_id(1);
        reservation1.setStart_date(LocalDate.of(2027, 8,12));
        reservation1.setEnd_date(LocalDate.of(2027, 8,19));
        reservation1.setTotal(new BigDecimal(2450));
        reservation1.setHost(new Host("74f32f01-9c6d-4e87-b2d9-d389af693b44","Gery",
                "ngery2v@ow.ly","(203) 6385708","09 Bashford Trail",
                "New Haven","CT","06505",new BigDecimal(464),new BigDecimal(580)));
        reservation1.setGuest(new Guest(5, "Topher", "Cullens", "tophCullens@aol.com",
                "(901) 4983249", "PA"));
        reservations.add(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setReservation_id(2);
        reservation2.setStart_date(LocalDate.of(2027, 7,29));
        reservation2.setEnd_date(LocalDate.of(2027, 8,6));
        reservation2.setTotal(new BigDecimal(100));
        reservation2.setHost(new Host("74f32f01-9c6d-4e87-b2d9-d389af693b44","Gery",
                "ngery2v@ow.ly","(203) 6385708","09 Bashford Trail",
                "New Haven","CT","06505",new BigDecimal(464),new BigDecimal(280)));
        reservation2.setGuest(new Guest(2, "Santiago", "Maya", "santiagomaya456@gmail.com",
                "(631) 3249639", "NY"));
        reservations.add(reservation2);

        Reservation reservation3 = new Reservation();
        reservation3.setReservation_id(3);
        reservation3.setStart_date(LocalDate.of(2027, 8,12));
        reservation3.setEnd_date(LocalDate.of(2027, 8,19));
        reservation3.setTotal(new BigDecimal(1900));
        reservation3.setHost(new Host("74f32f01-9c6d-4e87-b2d9-d389af693b44","Gery",
                "ngery2v@ow.ly","(203) 6385708","09 Bashford Trail",
                "New Haven","CT","06505",new BigDecimal(464),new BigDecimal(880)));
        reservation3.setGuest(new Guest(4, "Sandra", "Martinez", "spmartinez2004@hotmail.com",
                "(631) 8342250", "NY"));
        reservations.add(reservation3);

        Reservation reservation4 = new Reservation();
        reservation4.setReservation_id(4);
        reservation4.setStart_date(LocalDate.of(2027, 8,21));
        reservation4.setEnd_date(LocalDate.of(2027, 8,24));
        reservation4.setTotal(new BigDecimal(1900));
        reservation4.setHost(new Host("48dc8768-4e33-4257-a0b9-676e7b08cb7f","Charon",
                        "bcharon56@storify.com","(813) 9880586",
                        "65 Saint Paul Plaza","Tampa","FL","33647",
                        new BigDecimal(380),new BigDecimal(475)));
        reservation4.setGuest(new Guest(17, "Xavier", "McCormick", "XGoneGiveItToU@gmail.com",
                "(917) 3406250", "TX"));
        reservations.add(reservation4);
    }

    @Override
    public List<Reservation> findByHostId(String hostId) throws DataException {
        return reservations.stream()
                .filter(r -> r.getHost().getHost_id().equals(hostId))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation addReservation(Reservation reservation) throws DataException {
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public Boolean updateReservation(Reservation reservation) throws DataException {
        if(reservation == null){
            return false;
        }

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservation_id() == reservation.getReservation_id()) {
                reservations.set(i, reservation);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean deleteReservation(Reservation reservation) throws DataException {
        if(reservation == null){
            return false;
        }

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservation_id() == reservation.getReservation_id()) {
                reservations.remove(i);
                return true;
            }
        }
        return false;
    }
}
