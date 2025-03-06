package learn.Mastery.data;

import learn.Mastery.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository{

    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble(){
        Host host1 = new Host("74f32f01-9c6d-4e87-b2d9-d389af693b44","Gery",
                "ngery2v@ow.ly","(203) 6385708","09 Bashford Trail",
                "New Haven","CT","06505",new BigDecimal(464),new BigDecimal(580));
        Host host2 = new Host("48dc8768-4e33-4257-a0b9-676e7b08cb7f","Charon",
                "bcharon56@storify.com","(813) 9880586",
                "65 Saint Paul Plaza","Tampa","FL","33647",
                new BigDecimal(380),new BigDecimal(475));
        Host host3 = new Host("80170dba-ceda-4295-982d-766ba6b2aec0","Rany","hrany71@mit.edu",
                "(412) 9327874", "674 Sundown Lane","Pittsburgh","PA",
                "15210",new BigDecimal(411),new BigDecimal("513.75"));

        hosts.add(host1);
        hosts.add(host2);
        hosts.add(host3);
    }

    @Override
    public List<Host> findAll() {
        return new ArrayList<>(hosts);
    }

    @Override
    public Host findByEmail(String email) throws DataException {
        return hosts.stream()
                .filter(guest -> guest.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
