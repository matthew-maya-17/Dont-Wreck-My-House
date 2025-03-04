package learn.Mastery.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int reservation_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private int guest_id;
    private BigDecimal total;
    private Guest guest;
    private Host host;

    public Reservation(int reservation_id, BigDecimal total, int guest_id, LocalDate end_date, LocalDate start_date) {
        this.reservation_id = reservation_id;
        this.total = total;
        this.guest_id = guest_id;
        this.end_date = end_date;
        this.start_date = start_date;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
}
