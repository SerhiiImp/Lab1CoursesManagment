package ua.university;

public record Instructor(String firstName, String lastName, String expertise) {
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + expertise + ")";
    }
}
