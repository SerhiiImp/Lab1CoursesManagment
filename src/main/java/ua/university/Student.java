package ua.university;

import ua.university.exception.InvalidDataException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public record Student(String firstName, String lastName, String email, LocalDate enrollmentDate) implements Comparable<Student> {
    private static final Logger logger = Logger.getLogger(Student.class.getName());

    public Student {
        List<String> errors = new ArrayList<>();

        String tempFirstName = firstName;
        String tempLastName = lastName;
        String tempEmail = email;

        if (tempFirstName == null || tempFirstName.isBlank()) {
            errors.add("firstName: cannot be null or empty");
        } else {
            tempFirstName = tempFirstName.trim();
        }

        if (tempLastName == null || tempLastName.isBlank()) {
            errors.add("lastName: cannot be null or empty");
        } else {
            tempLastName = tempLastName.trim();
        }

        if (tempEmail == null || tempEmail.isBlank()) {
            errors.add("email: cannot be null or empty");
        } else {
            tempEmail = tempEmail.trim().toLowerCase();
            if (!tempEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                errors.add("email: invalid format");
            }
        }

        if (enrollmentDate == null) {
            errors.add("enrollmentDate: cannot be null");
        } else if (enrollmentDate.isAfter(LocalDate.now())) {
            errors.add("enrollmentDate: cannot be in the future");
        }

        if (!errors.isEmpty()) {
            logger.warning(() -> "Validation failed for Student: " + errors);
            throw new InvalidDataException(errors);
        }

        firstName = tempFirstName;
        lastName = tempLastName;
        email = tempEmail;

        String logFirstName = firstName;
        String logLastName = lastName;
        logger.info(() -> "Student created successfully: " + logFirstName + " " + logLastName);
    }

    @Override
    public int compareTo(Student other) {
        return this.lastName.compareTo(other.lastName);
    }

    public static Comparator<Student> byEmail() {
        return Comparator.comparing(Student::email);
    }

    public static Comparator<Student> byEnrollmentDate() {
        return Comparator.comparing(Student::enrollmentDate);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " <" + email + "> enrolled=" + enrollmentDate;
    }
}
