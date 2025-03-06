package learn.Mastery.domain;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private ArrayList<String> errors = new ArrayList<>();

    public boolean isSuccess() {
        return errors.size() == 0;
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(errors);
    }

    public void addErrorMessage(String error) {
        errors.add(error);
    }
}