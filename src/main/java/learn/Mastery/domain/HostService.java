package learn.Mastery.domain;

import learn.Mastery.data.DataException;
import learn.Mastery.data.HostRepository;
import learn.Mastery.models.Host;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class HostService {

    HostRepository hostRepository;

    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    public List<Host> findByEmail(String email) throws DataException, FileNotFoundException {
        return hostRepository.findAll().stream()
                .filter(guest -> guest.getEmail().equals(email))
                .collect(Collectors.toList());
    }
}
