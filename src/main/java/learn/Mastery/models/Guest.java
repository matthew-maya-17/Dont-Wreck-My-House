package learn.Mastery.models;

public class Guest {
    private int guest_Id;
    private String first_name;
    private String last_name;
    private String email;
    private String phoneNumber;
    private String state;

    public Guest(int guest_Id, String state, String phoneNumber, String email, String last_name, String first_name) {
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

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getState() {
        return state;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
