package ua.university;

import java.time.LocalDate;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Lab5 demo started");

        StudentRepository studentRepo = new StudentRepository();
        studentRepo.add(new Student("John", "Doe", "john@example.com", LocalDate.of(2024, 9, 1)));
        studentRepo.add(new Student("Alice", "Smith", "alice@example.com", LocalDate.of(2024, 8, 15)));
        studentRepo.add(new Student("Bob", "Johnson", "bob@example.com", LocalDate.of(2024, 9, 10)));

        logger.info("Students before sorting: " + studentRepo.getAll());

        studentRepo.sortByLastName("asc");
        logger.info("Students sorted by lastName asc: " + studentRepo.getAll());

        studentRepo.sortByEmail();
        logger.info("Students sorted by email: " + studentRepo.getAll());

        studentRepo.sortByEnrollmentDate();
        logger.info("Students sorted by enrollmentDate: " + studentRepo.getAll());

        CourseRepository courseRepo = new CourseRepository();
        courseRepo.add(new Course("Java Basics", "Learn Java", 3, LocalDate.of(2025, 1, 10), CourseLevel.BEGINNER));
        courseRepo.add(new Course("Advanced Algorithms", "Deep algorithms", 5, LocalDate.of(2025, 2, 1), CourseLevel.ADVANCED));
        courseRepo.add(new Course("Data Structures", "Core structures", 4, LocalDate.of(2025, 1, 20), CourseLevel.INTERMEDIATE));

        logger.info("Courses before sorting: " + courseRepo.getAll());

        courseRepo.sortByTitle("desc");
        logger.info("Courses sorted by title desc: " + courseRepo.getAll());

        courseRepo.sortByCredits();
        logger.info("Courses sorted by credits: " + courseRepo.getAll());

        courseRepo.sortByStartDate();
        logger.info("Courses sorted by start date: " + courseRepo.getAll());

        courseRepo.sortByLevel();
        logger.info("Courses sorted by level: " + courseRepo.getAll());

        logger.info("Lab5 demo finished");
    }
}
