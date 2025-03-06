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
    void shouldFindExistingEmail() throws DataException {
        List<Guest> guests = guestService.findByEmail("mattewmatt2009@hotmail.com");
        assertNotNull(guests);
    }

}