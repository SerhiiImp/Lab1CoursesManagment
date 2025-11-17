package ua.university;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StudentRepository extends GenericRepository<Student> {
    private static final Logger logger = Logger.getLogger(StudentRepository.class.getName());

    public StudentRepository() {
        super(Student::email);
    }

    public Optional<Student> findByEmail(String email) {
        logger.info(() -> "Searching student by email: " + email);
        return storage.stream()
                .filter(s -> s.email().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<Student> findByLastName(String lastName) {
        logger.info(() -> "Searching students by lastName: " + lastName);
        return storage.stream()
                .filter(s -> s.lastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public List<Student> findByFirstName(String firstName) {
        logger.info(() -> "Searching students by firstName: " + firstName);
        return storage.stream()
                .filter(s -> s.firstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    public List<Student> findEnrolledAfter(LocalDate date) {
        logger.info(() -> "Searching students enrolled after: " + date);
        return storage.stream()
                .filter(s -> s.enrollmentDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Student> findEnrolledBefore(LocalDate date) {
        logger.info(() -> "Searching students enrolled before: " + date);
        return storage.stream()
                .filter(s -> s.enrollmentDate().isBefore(date))
                .collect(Collectors.toList());
    }

    public List<Student> findEnrolledBetween(LocalDate start, LocalDate end) {
        logger.info(() -> "Searching students enrolled between: " + start + " and " + end);
        return storage.stream()
                .filter(s -> !s.enrollmentDate().isBefore(start) && !s.enrollmentDate().isAfter(end))
                .collect(Collectors.toList());
    }

    public long countStudents() {
        long count = storage.stream().count();
        logger.info(() -> "Total students: " + count);
        return count;
    }

    public void printAllEmails() {
        logger.info("Printing all student emails:");
        storage.stream()
                .map(Student::email)
                .forEach(email -> logger.info(() -> "  " + email));
    }

    public String concatenateLastNames() {
        String result = storage.stream()
                .map(Student::lastName)
                .reduce("", (acc, name) -> acc.isEmpty() ? name : acc + ", " + name);
        logger.info(() -> "Concatenated lastNames: " + result);
        return result;
    }
}
