package ua.util;

public class ValidationHelper {

    public static void requireNonEmpty(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    public static void requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }

    public static void requireEmail(String email) {
        requireNonEmpty(email, "Email");
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email must be valid");
        }
    }
}
