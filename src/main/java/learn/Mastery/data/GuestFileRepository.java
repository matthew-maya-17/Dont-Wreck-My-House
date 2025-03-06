package learn.Mastery.data;

import learn.Mastery.models.Guest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository{

    private static final String DELIMETER = ",";
    private final String filePath;

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() throws DataException {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            reader.readLine(); //Reads + Skips the header

            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                String[] fields = line.split(DELIMETER, -1);
                if (fields.length == 6){
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException e) {
            throw new DataException("Failed to read guest data from file: " + filePath, e);
        }
        return result;
    }

    @Override
    public Guest findByEmail(String email) throws DataException {
        return findAll().stream()
                .filter(guest -> guest.getEmail().equals(email))
                .findFirst()
                .orElse(null);

    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setGuest_Id(Integer.parseInt(fields[0]));
        result.setFirst_name(fields[1]);
        result.setLast_name(fields[2]);
        result.setEmail(fields[3]);
        result.setPhoneNumber(fields[4]);
        result.setState(fields[5]);
        return result;
    }
}
