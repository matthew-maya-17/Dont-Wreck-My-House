package learn.Mastery.models;

import java.math.BigDecimal;

public class Host {
    private String host_id;
    private String last_name;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private int postal_code;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;

    public Host(BigDecimal weekendRate, BigDecimal standardRate, String state, int postal_code, String city, String address, String phoneNumber, String email, String last_name, String host_id) {
        this.weekendRate = weekendRate;
        this.standardRate = standardRate;
        this.state = state;
        this.postal_code = postal_code;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.last_name = last_name;
        this.host_id = host_id;
    }

    public String getHost_id() {
        return host_id;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public String getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getLast_name() {
        return last_name;
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
