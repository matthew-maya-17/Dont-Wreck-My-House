package learn.Mastery.data;

import learn.Mastery.models.Guest;

import java.util.List;

public interface GuestRepository {

    List<Guest> findAll() throws DataException;

    Guest findByEmail(String email) throws DataException;
}
