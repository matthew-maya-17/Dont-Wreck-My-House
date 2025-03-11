package learn.Mastery.data;

import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import learn.Mastery.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationFileRepository implements ReservationRepository{

    private static final String DELIMITER = ",";
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(@Value("${dataReservationDirectory}") String directory) {
        this.directory = directory;
    }


    @Override
    public List<Reservation> findByHostId(String hostId) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))){
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()){
                String[] fields = line.split(DELIMITER, -1);

                if (fields.length == 5){
                    result.add(deserialize(fields, hostId));
                }
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex){
            // don't throw on read
            throw new DataException("Error reading reservations file: " + directory, ex);
        }
        return result;
    }

    @Override
    public Reservation addReservation(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHost().getHost_id());
        reservation.setReservation_id(getNextId(all));
        all.add(reservation);
        writeAll(all, reservation.getHost().getHost_id());
        return reservation;
    }

    @Override
    public Boolean updateReservation(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHost().getHost_id());
        for (int i = 0; i < all.size(); i++){
            if(all.get(i).getReservation_id() == reservation.getReservation_id()){
                all.set(i, reservation);
                writeAll(all, reservation.getHost().getHost_id());
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean deleteReservation(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHost().getHost_id());
        for (int i = 0; i < all.size(); i++){
            if(all.get(i).getReservation_id() == reservation.getReservation_id()){
                all.remove(i);
                writeAll(all, reservation.getHost().getHost_id());
                return true;
            }
        }
        return false;
    }

    private String getFilePath(String host_id){
        return Paths.get(directory,host_id + ".csv").toString();
    }

    private int getNextId(List<Reservation> allReservations) {
        int nextId = 0;
        for (Reservation r : allReservations) {
            nextId = Math.max(nextId, r.getReservation_id());
        }
        return nextId + 1;
    }

    private void writeAll(List<Reservation> reservations, String host_id) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host_id))) {

            writer.println(HEADER);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    //Reservation to String
    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getReservation_id(),
                reservation.getStart_date(),
                reservation.getEnd_date(),
                reservation.getGuest().getGuest_Id(),
                reservation.getTotal());
    }

    //String to Reservation
    private Reservation deserialize(String[] fields, String host_Id) {
        Reservation result = new Reservation();
        result.setReservation_id(Integer.parseInt(fields[0]));
        result.setStart_date(LocalDate.parse(fields[1]));
        result.setEnd_date(LocalDate.parse(fields[2]));

        Guest guest = new Guest();
        int guestId = Integer.parseInt(fields[3]);
        guest.setGuest_Id(guestId);

        Host host = new Host();
        host.setHost_id(host_Id);

        result.setHost(host);
        result.setGuest(guest);
        result.setTotal(new BigDecimal(fields[4]));
        return result;
    }
}
