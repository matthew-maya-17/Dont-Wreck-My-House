package learn.Mastery.domain;

import learn.Mastery.data.DataException;
import learn.Mastery.data.GuestRepository;
import learn.Mastery.models.Guest;

import java.util.List;
import java.util.stream.Collectors;

public class GuestService {

    GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> findAll() throws DataException {
        return guestRepository.findAll();
    }

    public Guest findByEmail(String email) throws DataException {
        return guestRepository.findAll().stream()
                .filter(guest -> guest.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

}
