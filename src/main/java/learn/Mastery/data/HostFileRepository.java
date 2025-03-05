package learn.Mastery.data;

import learn.Mastery.models.Host;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepository implements HostRepository{

    private static final String DELIMETER = ",";
    private final String filePath;

    public HostFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Host> findAll() throws DataException {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            reader.readLine(); //Reads + Skips the header

            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                String[] fields = line.split(DELIMETER, -1);

                if (fields.length == 10){
                    result.add(deserialize(fields));
                }
            }

        } catch (FileNotFoundException ex) {

        } catch (IOException e) {
            //Don't throw on a read
            throw new DataException("Failed to read host data from file: " + filePath, e);
        }
        return result;
    }

    @Override
    public Host findByEmail(String email) throws DataException {
        return findAll().stream()
                .filter(host -> host.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setHost_id(fields[0]);
        result.setLast_name(fields[1]);
        result.setEmail(fields[2]);
        result.setPhoneNumber(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostal_code((fields[7]));
        result.setStandardRate(new BigDecimal(fields[8]));
        result.setWeekendRate(new BigDecimal(fields[9]));
        return result;
    }
}
