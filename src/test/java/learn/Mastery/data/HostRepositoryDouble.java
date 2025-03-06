package learn.Mastery.data;

import learn.Mastery.models.Host;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository{

    Host host = new Host("74f32f01-9c6d-4e87-b2d9-d389af693b44","Gery",
            "ngery2v@ow.ly","(203) 6385708","09 Bashford Trail",
            "New Haven","CT","06505",new BigDecimal(464),new BigDecimal(580));

    private ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble(){

    }

    @Override
    public List<Host> findAll() throws FileNotFoundException, DataException {
        return null;
    }

    @Override
    public Host findByEmail(String email) throws DataException {
        return null;
    }
}
