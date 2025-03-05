package learn.Mastery.data;

import learn.Mastery.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/guests-seed.csv";
    static final String TEST_FILE_PATH = "./data/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataException {
        List<Guest> all = repository.findAll();
        assertEquals(10, all.size());
        assertEquals("Sullivan", all.get(0).getFirst_name());
        assertEquals("Lomas", all.get(0).getLast_name());
        assertEquals("slomas0@mediafire.com", all.get(0).getEmail());
    }

    @Test
    void shouldFindExistingEmail() throws DataException {
        Guest guest1 = repository.findByEmail("slomas0@mediafire.com");
        assertNotNull(guest1);
        assertEquals(1, guest1.getGuest_Id());
        assertEquals("(702) 7768761", guest1.getPhoneNumber());

        Guest guest2 = repository.findByEmail("jhulson8@auda.org.au");
        assertNotNull(guest2);
        assertEquals(9, guest2.getGuest_Id());
        assertEquals("(202) 4440620", guest2.getPhoneNumber());

        Guest guest3 = repository.findByEmail("iganter9@privacy.gov.au");
        assertNotNull(guest3);
        assertEquals(10, guest3.getGuest_Id());
        assertEquals("(915) 5895326", guest3.getPhoneNumber());
    }

    @Test
    void shouldNotFindNonExistingEmail() throws DataException {
        Guest guest1 = repository.findByEmail("matthewmatt2009@hotmail.com");
        assertNull(guest1);

        Guest guest2 = repository.findByEmail("martymcfly123@aol.com");
        assertNull(guest2);
    }
}