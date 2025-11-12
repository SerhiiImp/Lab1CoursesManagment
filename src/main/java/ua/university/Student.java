package ua.university;

import java.time.LocalDate;

public record Student(String firstName, String lastName, String email, LocalDate enrollmentDate) {
    @Override
    public String toString() {
        return firstName + " " + lastName + " <" + email + "> enrolled=" + enrollmentDate;
    }
}
