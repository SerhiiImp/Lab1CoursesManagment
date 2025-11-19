package ua.university.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvalidDataException extends RuntimeException {
    private final List<String> errors;

    public InvalidDataException(List<String> errors) {
        super(buildMessage(errors));
        this.errors = new ArrayList<>(errors);
    }

    public InvalidDataException(String singleError) {
        super(singleError);
        this.errors = Collections.singletonList(singleError);
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    private static String buildMessage(List<String> errors) {
        if (errors == null || errors.isEmpty()) {
            return "Invalid data";
        }
        return "Validation failed: " + String.join("; ", errors);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
