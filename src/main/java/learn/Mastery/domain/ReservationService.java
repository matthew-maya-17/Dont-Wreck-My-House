package learn.Mastery.domain;

import learn.Mastery.data.DataException;
import learn.Mastery.data.GuestRepository;
import learn.Mastery.data.HostRepository;
import learn.Mastery.data.ReservationRepository;
import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationService {

    HostRepository hostRepository;
    GuestRepository guestRepository;
    ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public List<Reservation> findByHostId(String hostId) throws DataException, FileNotFoundException {
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getHost_id(), i -> i));
        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getGuest_Id(), i -> i));

        List<Reservation> result = reservationRepository.findByHostId(hostId);

        for (Reservation reservation : result) {
            reservation.setHost(hostMap.get(reservation.getHost().getHost_id()));
            reservation.setGuest(guestMap.get(reservation.getGuest().getGuest_Id()));
        }
        return result;
    }

    public ReservationResult<Reservation> addReservation(Reservation addedReservation) throws DataException {
        ReservationResult<Reservation> result = validate(addedReservation);

        if (!result.isSuccess()){
            return result;
        }

        BigDecimal newTotal = calculateReservationTotal(addedReservation);
        addedReservation.setTotal(newTotal);

        result.setPayload(reservationRepository.addReservation(addedReservation));

        return result;
    }

    public ReservationResult<Reservation> updateReservation(Reservation updatedReservation) throws DataException {
        ReservationResult<Reservation> result = validate(updatedReservation);

        if (!result.isSuccess()){
            return result;
        }

        List<Reservation> reservations = reservationRepository.findByHostId(updatedReservation.getHost().getHost_id());
        Reservation existingReservation = reservations.stream()
                .filter(r -> r.getReservation_id() == updatedReservation.getReservation_id())
                .findFirst()
                .orElse(null);

        if (existingReservation == null) {
            result.addErrorMessage("Reservation not found.");
            return result;
        }

        if (!existingReservation.getGuest().equals(updatedReservation.getGuest()) ||
                !existingReservation.getHost().equals(updatedReservation.getHost())) {
            result.addErrorMessage("Only start and end dates can be changed.");
            return result;
        }

        BigDecimal newTotal = calculateReservationTotal(updatedReservation);
        updatedReservation.setTotal(newTotal);

        boolean updated = reservationRepository.updateReservation(updatedReservation);

        if (!updated){
            result.addErrorMessage(String.format("Failed to update reservation with id: %s.", updatedReservation.getReservation_id()));
        } else {
            result.setPayload(updatedReservation);
        }

        return result;
    }

    public ReservationResult<Reservation> deleteReservation(Reservation deletedReservation) throws DataException {
        ReservationResult<Reservation> result = new ReservationResult<>();

        Reservation existingReservations = findExistingReservation(deletedReservation);

        if (existingReservations == null) {
            result.addErrorMessage("Reservation not found.");
            return result;
        }

        if(existingReservations.getStart_date().isBefore(LocalDate.now())){
            result.addErrorMessage("You can only delete a reservation in the future!");
            return result;
        }

        if (!reservationRepository.deleteReservation(existingReservations)){
            result.addErrorMessage("Reservation deletion failed for some reason.");
            return result;
        }

        result.setPayload(existingReservations);
        return result;
    }

    private Reservation findExistingReservation(Reservation reservation) throws DataException {
        List<Reservation> reservations = reservationRepository.findByHostId(reservation.getHost().getHost_id());

        return reservations.stream()
                .filter(r -> r.getReservation_id() == reservation.getReservation_id())
                .findFirst()
                .orElse(null);
    }

    public BigDecimal calculateReservationTotal(Reservation reservation) {
        BigDecimal total = BigDecimal.ZERO;
        LocalDate start = reservation.getStart_date();
        LocalDate end = reservation.getEnd_date();
        Host host = reservation.getHost();

        if (start == null || end == null) {
            return BigDecimal.ZERO;
        }

        while(!start.isAfter(end)){
            if(start.getDayOfWeek().equals(DayOfWeek.FRIDAY) || start.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                total = total.add(host.getWeekendRate());
            } else {
                total = total.add(host.getStandardRate());
            }
            start = start.plusDays(1);
        }
        return total;
    }

    private ReservationResult<Reservation> validate(Reservation reservation) throws DataException {
        ReservationResult<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        validateFields(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateChildrenExist(reservation, result);

        return result;
    }

    private ReservationResult<Reservation> validateNulls(Reservation reservation) {
        ReservationResult<Reservation> result = new ReservationResult<>();

        if (reservation == null) {
            result.addErrorMessage("Nothing to save. Reservation cannot be Null!");
            return result;
        }

        if (reservation.getHost() == null) {
            result.addErrorMessage("Reservation must have a host!");
        }

        if (reservation.getGuest() == null) {
            result.addErrorMessage("Reservation must have a guest!");
        }

        if (reservation.getStart_date() == null) {
            result.addErrorMessage("Reservation must have a start date");
        }

        if (reservation.getEnd_date() == null) {
            result.addErrorMessage("Reservation must have a end date");
        }
        return result;
    }

    private void validateFields(Reservation reservation, ReservationResult<Reservation> result) throws DataException {
        if(reservation.getStart_date().isAfter(reservation.getEnd_date())){
            result.addErrorMessage("Reservation start date cannot be after the reservation end date.");
        }

        // No past dates for reservations.
        if (reservation.getStart_date().isBefore(LocalDate.now())) {
            result.addErrorMessage("Reservation start & end date cannot be in the past. They must both be in the future.");
        }

        for(Reservation r : reservationRepository.findByHostId(reservation.getHost().getHost_id())){
            if(!(reservation.getEnd_date().isBefore(r.getStart_date()) ||
                    reservation.getStart_date().isAfter(r.getEnd_date()))){
                result.addErrorMessage("Reservation overlaps with another of the hosts existing reservations.");
            }
        }
    }

    private void validateChildrenExist(Reservation reservation, ReservationResult<Reservation> result) throws DataException {
        if (reservation.getHost().getHost_id() == null
                || hostRepository.findByEmail(reservation.getHost().getEmail()) == null) {
            result.addErrorMessage("Host does not exist.");
        }

        if (reservation.getHost().getHost_id() == null
                || guestRepository.findByEmail(reservation.getGuest().getEmail()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }
    }
}
