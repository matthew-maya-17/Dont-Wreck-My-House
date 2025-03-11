package learn.Mastery.domain;

import learn.Mastery.data.DataException;
import learn.Mastery.data.HostRepository;
import learn.Mastery.models.Host;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class HostService {

    HostRepository hostRepository;

    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    public List<Host> findAll() throws DataException, FileNotFoundException {
        return hostRepository.findAll();
    }

    public Host findByEmail(String email) throws DataException, FileNotFoundException {
        return hostRepository.findAll().stream()
                .filter(guest -> guest.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
