package learn.Mastery.data;

import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    private static final String SEED_FILE_PATH = "./data/hosts-seed.csv";
    private static final String TEST_FILE_PATH = "./data/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataException {
        List<Host> all = repository.findAll();
        assertEquals(10, all.size());
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", all.get(0).getHost_id());
        assertEquals("eyearnes0@sfgate.com", all.get(0).getEmail());
        assertEquals("(806) 1783815", all.get(0).getPhoneNumber());
    }

    @Test
    void shouldFindExistingEmail() throws DataException {
        Host host1 = repository.findByEmail("eyearnes0@sfgate.com");
        assertNotNull(host1);
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", host1.getHost_id());
        assertEquals("(806) 1783815", host1.getPhoneNumber());

        Host host2 = repository.findByEmail("mmcgrady6@odnoklassniki.ru");
        assertNotNull(host2);
        assertEquals("6c4fd66e-4a24-4299-8905-198d90520b76", host2.getHost_id());
        assertEquals("(210) 3037049", host2.getPhoneNumber());

        Host host3 = repository.findByEmail("charley4@apple.com");
        assertNotNull(host3);
        assertEquals("b6ddb844-b990-471a-8c0a-519d0777eb9b", host3.getHost_id());
        assertEquals("(954) 7895760", host3.getPhoneNumber());
    }

    @Test
    void shouldNotFindNonExistingEmail() throws DataException {
        Host host1 = repository.findByEmail("matthewmatt2009@hotmail.com");
        assertNull(host1);

        Host host2 = repository.findByEmail("martymcfly123@aol.com");
        assertNull(host2);
    }
}