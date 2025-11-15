package ua.university;

import java.time.LocalDate;
import java.util.Comparator;

public record Student(String firstName, String lastName, String email, LocalDate enrollmentDate) implements Comparable<Student> {

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
