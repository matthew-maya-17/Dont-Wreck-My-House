package learn.Mastery.domain;

import learn.Mastery.data.DataException;
import learn.Mastery.data.GuestRepositoryDouble;
import learn.Mastery.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService guestService;

    @BeforeEach
    void setup() {
        GuestRepositoryDouble repository = new GuestRepositoryDouble();
        guestService = new GuestService(repository);
    }

    @Test
    void shouldFindAll() throws DataException {
        List<Guest> guests = guestService.findAll();
        assertNotNull(guests);
        assertEquals(5, guests.size());
        assertEquals("Sandra", guests.get(3).getFirst_name());
        assertEquals("Martinez", guests.get(3).getLast_name());
    }

    @Test
    void shouldFindExistingEmail() throws DataException {
        Guest guest1 = guestService.findByEmail("matthewmatt2009@hotmail.com");
        assertNotNull(guest1);
        assertEquals("NY", guest1.getState());
        assertEquals("(631) 3539689", guest1.getPhoneNumber());

        Guest guest2 = guestService.findByEmail("nikakhvan@gmail.com");
        assertNotNull(guest2);
        assertEquals("FL", guest2.getState());
        assertEquals("(305) 7738921", guest2.getPhoneNumber());
    }

    @Test
    void shouldNotFindNullEmail() throws DataException {
        Guest guest1 = guestService.findByEmail(null);
        assertNull(guest1);
    }

    @Test
    void shouldNotFindNonExistingEmail() throws DataException {
        Guest guest1 = guestService.findByEmail("TestNotInDOUBLE@AOL.com");
        assertNull(guest1);
    }

}