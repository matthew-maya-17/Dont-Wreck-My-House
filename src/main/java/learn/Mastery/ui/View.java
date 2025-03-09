package learn.Mastery.ui;

import learn.Mastery.data.DataException;
import learn.Mastery.data.ReservationRepository;
import learn.Mastery.models.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class View {

    private static final String INVALID_NUMBER
            = "[INVALID] Enter a valid number.";
    private static final String NUMBER_OUT_OF_RANGE
            = "[INVALID] Enter a number between %s and %s.";
    private static final String INVALID_GUEST_EMAIL
            = "[INVALID] Enter a valid Guest email.";
    private static final String INVALID_HOST_EMAIL
            = "[INVALID] Enter a valid Host email.";
    private static final String REQUIRED
            = "[INVALID] Value is required.";
    private static final String INVALID_DATE
            = "[INVALID] Enter a date in MM/dd/yyyy format.";

    private final Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public View() {}

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            if (!option.isHidden()) {
                printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(readInt(message, min, max));
    }

    public String getEmail(String emailOwner){
        return readRequiredString(String.format("%s Email: ", emailOwner));
    }

    public LocalDate getDate(String startOrEnd){
        return readLocalDate(String.format("%s Date [MM/dd/yyyy]: ", startOrEnd));
    }

    public void displayDate(LocalDate date, String startOrEnd){
        println(startOrEnd + ": " + date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    }
    public void reservationSummary(){
        LocalDate start = readLocalDate("Start Date: ");
        LocalDate end = readLocalDate("End Date: ");
        displayHeader("Summary");
        println("Start [MM/dd/yyyy]: " + start.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        println("End [MM/dd/yyyy]: " + start.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        println("Total: " );
    }

    public void displayAllReservationByHostId(List<Reservation> reservations){
        if (reservations == null || reservations.isEmpty()){
            println("No reservations found.");
            return;
        }
        println("");
        reservations.stream()
                .sorted((a,b) -> a.getStart_date().compareTo(b.getStart_date()))
                .map(r -> String.format(
                        "ID: %d, %s - %s, Guest: %s %s, Email: %s",
                        r.getReservation_id(),
                        r.getStart_date(),
                        r.getEnd_date(),
                        r.getGuest().getLast_name(),
                        r.getGuest().getFirst_name(),
                        r.getGuest().getEmail()
                ))
                .forEach(System.out::println);
    }

//    public void editReservationDates(){
//        readLocalDate("Start (" + + ")");
//        readLocalDate("End (" + + ")");
//
//    }

    public void displayAllFutureGuestReservationByHostId(List<Reservation> reservations){
        if (reservations == null || reservations.isEmpty()){
            println("No reservations found.");
            return;
        }
        println("");
        reservations.stream()
                .filter(reservation -> reservation.getStart_date().isAfter(LocalDate.now()))
                .sorted((a,b) -> a.getStart_date().compareTo(b.getStart_date()))
                .map(r -> String.format(
                        "ID: %d, %s - %s, Guest: %s %s, Email: %s",
                        r.getReservation_id(),
                        r.getStart_date(),
                        r.getEnd_date(),
                        r.getGuest().getLast_name(),
                        r.getGuest().getFirst_name(),
                        r.getGuest().getEmail()
                ))
                .forEach(System.out::println);
    }

    public void displayHeader(String message) {
        println("");
        println(message);
        println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            println(message);
        }
    }

//    public LocalDate updateLocalDate(String prompt){
//        while (true) {
//            print(prompt);
//            String input = scanner.nextLine();
//            if (input.trim().length() > 0) {
//                m.setFrom(input);
//            }
//            try {
//                return LocalDate.parse(input, formatter);
//            } catch (DateTimeParseException ex) {
//                println(INVALID_DATE);
//            }
//        }
//    }

    public boolean readBooleanSummary(){
        return readBoolean("Is this okay? [y/n]: ");
    }

    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    public String readRequiredString(String prompt) {
        while (true) {
            String result = readString(prompt);
            if (!result.isEmpty()) {
                return result;
            }
            println(REQUIRED);
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int result = readInt(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public LocalDate readLocalDate(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                println(INVALID_DATE);
            }
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readRequiredString(prompt).toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            println("[INVALID] Please enter 'y' or 'n'.");
        }
    }
}
