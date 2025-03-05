package learn.Mastery.data;

import learn.Mastery.models.Host;

import java.io.FileNotFoundException;
import java.util.List;

public interface HostRepository {

    List<Host> findAll() throws FileNotFoundException, DataException;

    Host findByEmail(String email) throws DataException;
}
