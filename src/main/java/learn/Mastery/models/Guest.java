package learn.Mastery.models;

import java.util.Objects;

public class Guest {
    private int guest_Id;
    private String first_name;
    private String last_name;
    private String email;
    private String phoneNumber;
    private String state;

    public Guest() {
    }

    public Guest(int guest_Id, String first_name, String last_name, String email,String phoneNumber, String state) {
        this.guest_Id = guest_Id;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.last_name = last_name;
        this.first_name = first_name;
    }

    public int getGuest_Id() {
        return guest_Id;
    }

    public void setGuest_Id(int guest_Id) {
        this.guest_Id = guest_Id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return guest_Id == guest.guest_Id && Objects.equals(first_name, guest.first_name) && Objects.equals(last_name, guest.last_name) && Objects.equals(email, guest.email) && Objects.equals(phoneNumber, guest.phoneNumber) && Objects.equals(state, guest.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guest_Id, first_name, last_name, email, phoneNumber, state);
    }
}