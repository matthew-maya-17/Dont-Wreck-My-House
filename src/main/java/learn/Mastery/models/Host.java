package learn.Mastery.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Host {
    private String host_id;
    private String last_name;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String postal_code;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;

    public Host(){}

    public Host(String host_id, String last_name,String email, String phoneNumber, String address, String city, String state, String postal_code, BigDecimal standardRate,BigDecimal weekendRate) {
        this.host_id = host_id;
        this.last_name = last_name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.standardRate = standardRate;
        this.weekendRate = weekendRate;
    }

    public String getHost_id() {
        return host_id;
    }

    public void setHost_id(String host_id) {
        this.host_id = host_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return Objects.equals(host_id, host.host_id) && Objects.equals(last_name, host.last_name) && Objects.equals(email, host.email) && Objects.equals(phoneNumber, host.phoneNumber) && Objects.equals(address, host.address) && Objects.equals(city, host.city) && Objects.equals(state, host.state) && Objects.equals(postal_code, host.postal_code) && Objects.equals(standardRate, host.standardRate) && Objects.equals(weekendRate, host.weekendRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host_id, last_name, email, phoneNumber, address, city, state, postal_code, standardRate, weekendRate);
    }
}
