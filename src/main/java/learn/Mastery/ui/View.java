package learn.Mastery.ui;

import learn.Mastery.models.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public String getEmail(String emailOwner){
        return io.readRequiredString(String.format("%s Email: ", emailOwner));
    }

    public void reservationSummary(){
        LocalDate start = io.readLocalDate("Start Date: ");
        LocalDate end = io.readLocalDate("End Date: ");
        displayHeader("Summary");
        io.println("Start: " + start.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).toString());
        io.println("End: " + start.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).toString());

    }


    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }
}
