package learn.Mastery.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private int reservation_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private BigDecimal total;

    private Host host;
    private Guest guest;

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

    public BigDecimal setReservationTotal() {
        LocalDate startDate = getStart_date();
        LocalDate endDate = getEnd_date();

        if (startDate == null || endDate == null) {
            return BigDecimal.ZERO;
        }

        while(!startDate.isAfter(endDate)){
            if(!(startDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)) || !(startDate.getDayOfWeek().equals(DayOfWeek.SATURDAY))){
                total = total.add(host.getStandardRate());
            } else {
                total = total.add(host.getStandardRate());
            }
            startDate = startDate.plusDays(1);
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservation_id == that.reservation_id && Objects.equals(start_date, that.start_date) && Objects.equals(end_date, that.end_date) && Objects.equals(total, that.total) && Objects.equals(guest, that.guest) && Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation_id, start_date, end_date, total, guest, host);
    }
}
