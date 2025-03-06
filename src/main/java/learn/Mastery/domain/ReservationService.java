package learn.Mastery.domain;

import learn.Mastery.data.DataException;
import learn.Mastery.data.GuestRepository;
import learn.Mastery.data.HostRepository;
import learn.Mastery.data.ReservationRepository;
import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;

import java.io.FileNotFoundException;
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

    public List<Reservation> findByHostId(Host host) throws DataException, FileNotFoundException {
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getHost_id(), i -> i));
        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getGuest_Id(), i -> i));

        List<Reservation> result = reservationRepository.findByHostId(host);

        for (Reservation reservation : result) {
            reservation.setHost(hostMap.get(reservation.getHost().getHost_id()));
            reservation.setGuest(guestMap.get(reservation.getGuest().getGuest_Id()));
        }
        return result;
    }

    public ReservationResult<Reservation> addReservation(Reservation reservation) throws DataException {
        ReservationResult<Reservation> result = validate(reservation);

        if (!result.isSuccess()){
            return result;
        }

        result.setPayload(reservationRepository.addReservation(reservation));

        return result;
    }

    public ReservationResult<Reservation> updateReservation(Reservation reservation) throws DataException {
        ReservationResult<Reservation> result = validate(reservation);

        if (!result.isSuccess()){
            return result;
        }

        boolean updated = reservationRepository.updateReservation(reservation);

        if (!updated){
            result.addErrorMessage(String.format("Error with reservation id: %s", reservation.getReservation_id()));
        }

        return result;
    }

    public ReservationResult<Reservation> deleteReservation(Reservation reservation) throws DataException {
        ReservationResult<Reservation> result = new ReservationResult<>();

        if (!reservationRepository.deleteReservation(reservation)){
            result.addErrorMessage("Reservation with does not exist");
            return result;
        }
        return result;
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
            result.addErrorMessage("Nothing to save.");
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

        for(Reservation r : reservationRepository.findByHostId(reservation.getHost())){
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
            result.addErrorMessage("guest does not exist.");
        }
    }
}
