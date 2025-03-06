package learn.Mastery.data;

import learn.Mastery.models.Guest;
import learn.Mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestRepositoryDouble implements GuestRepository{

    private ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        Guest guest1 = new Guest(1, "Matthew", "Maya", "matthewmatt2009@hotmail.com",
                "(631) 3539689", "NY");
        Guest guest2 = new Guest(2, "Santiago", "Maya", "santiagomaya456@gmail.com",
                "(631) 3249639", "NY");
        Guest guest3 = new Guest(3, "Nika", "Khvan", "nikakhvan@gmail.com",
                "(305) 7738921", "FL");
        Guest guest4 = new Guest(4, "Sandra", "Martinez", "spmartinez2004@hotmail.com",
                "(631) 8342250", "NY");
        Guest guest5 = new Guest(5, "Topher", "Cullens", "tophCullens@aol.com",
                "(901) 4983249", "PA");

        guests.add(guest1);
        guests.add(guest2);
        guests.add(guest3);
        guests.add(guest4);
        guests.add(guest5);
    }


    @Override
    public List<Guest> findAll() {
        return new ArrayList<>(guests);
    }

    @Override
    public Guest findByEmail(String email) throws DataException {
        return guests.stream()
                .filter(guest -> guest.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
