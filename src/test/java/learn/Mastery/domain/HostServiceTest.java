package learn.Mastery.domain;

import learn.Mastery.data.DataException;
import learn.Mastery.data.HostRepository;
import learn.Mastery.data.HostRepositoryDouble;
import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService hostService;

    @BeforeEach
    void setup(){
        HostRepository hostRepository = new HostRepositoryDouble();
        hostService = new HostService(hostRepository);
    }

    @Test
    void shouldFindAll() throws DataException, FileNotFoundException {
        List<Host> all = hostService.findAll();
        assertNotNull(all);
        assertEquals(3, all.size());
    }

    @Test
    void shouldFindExistingGuestByEmail() throws DataException, FileNotFoundException {
        Host host = hostService.findByEmail("hrany71@mit.edu");
        assertNotNull(host);
        assertEquals("80170dba-ceda-4295-982d-766ba6b2aec0", host.getHost_id());
        assertEquals("PA", host.getState());
        assertEquals("Pittsburgh", host.getCity());
        assertEquals("674 Sundown Lane", host.getAddress());
    }

    @Test
    void shouldNotFindNullEmail() throws DataException, FileNotFoundException {
        Host host1 = hostService.findByEmail(null);
        assertNull(host1);
    }

    @Test
    void shouldNotFindNonExistingEmail() throws DataException, FileNotFoundException {
        Host host1 = hostService.findByEmail("TestNotInDOUBLE@AOL.com");
        assertNull(host1);
    }
}