package ua.university;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Starting Lab 6 Demo - Stream API");

        StudentRepository studentRepo = new StudentRepository();
        studentRepo.add(new Student("John", "Doe", "john.doe@university.com", LocalDate.of(2024, 9, 1)));
        studentRepo.add(new Student("Alice", "Smith", "alice.smith@university.com", LocalDate.of(2024, 8, 15)));
        studentRepo.add(new Student("Bob", "Johnson", "bob.johnson@university.com", LocalDate.of(2024, 9, 10)));
        studentRepo.add(new Student("Emma", "Doe", "emma.doe@university.com", LocalDate.of(2024, 7, 20)));
        studentRepo.add(new Student("Charlie", "Brown", "charlie.brown@university.com", LocalDate.of(2024, 10, 5)));

        logger.info("=== Student Search Examples ===");
        
        studentRepo.findByEmail("john.doe@university.com")
                .ifPresent(s -> logger.info("Found by email: " + s));

        logger.info("--- Students with lastName 'Doe' ---");
        studentRepo.findByLastName("Doe").forEach(s -> logger.info(s.toString()));

        logger.info("--- Students enrolled after 2024-08-20 ---");
        studentRepo.findEnrolledAfter(LocalDate.of(2024, 8, 20))
                .forEach(s -> logger.info(s.toString()));

        logger.info("--- Students enrolled between 2024-08-01 and 2024-09-05 ---");
        studentRepo.findEnrolledBetween(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 9, 5))
                .forEach(s -> logger.info(s.toString()));

        logger.info("--- Terminal operations: collect, forEach, reduce ---");
        studentRepo.printAllEmails();
        studentRepo.concatenateLastNames();
        studentRepo.countStudents();

        CourseRepository courseRepo = new CourseRepository();
        courseRepo.add(new Course("Java Programming", "Intro to Java", 3, LocalDate.of(2025, 1, 15), CourseLevel.BEGINNER));
        courseRepo.add(new Course("Data Structures", "Advanced DS", 4, LocalDate.of(2025, 2, 1), CourseLevel.INTERMEDIATE));
        courseRepo.add(new Course("Algorithms", "Algorithm design", 5, LocalDate.of(2025, 3, 1), CourseLevel.ADVANCED));
        courseRepo.add(new Course("Java Advanced", "Spring Boot", 4, LocalDate.of(2025, 4, 1), CourseLevel.ADVANCED));
        courseRepo.add(new Course("Database Systems", "SQL and NoSQL", 3, LocalDate.of(2025, 1, 20), CourseLevel.INTERMEDIATE));

        logger.info("=== Course Search Examples ===");

        courseRepo.findByTitle("Java Programming")
                .ifPresent(c -> logger.info("Found by title: " + c));

        logger.info("--- Courses with level ADVANCED ---");
        courseRepo.findByLevel(CourseLevel.ADVANCED).forEach(c -> logger.info(c.toString()));

        logger.info("--- Courses with 3-4 credits ---");
        courseRepo.findByCreditsRange(3, 4).forEach(c -> logger.info(c.toString()));

        logger.info("--- Courses starting after 2025-02-01 ---");
        courseRepo.findStartingAfter(LocalDate.of(2025, 2, 1))
                .forEach(c -> logger.info(c.toString()));

        logger.info("--- Courses with 'Java' in title ---");
        courseRepo.findByTitleContains("Java").forEach(c -> logger.info(c.toString()));

        logger.info("--- Terminal operations: map, reduce ---");
        courseRepo.getTotalCredits();
        courseRepo.getAverageCredits();
        courseRepo.printAllTitles();
        courseRepo.concatenateTitles();

        logger.info("=== Performance Comparison: stream vs parallelStream ===");
        performanceTest(studentRepo, courseRepo);

        logger.info("Lab 6 Demo completed");
    }

    private static void performanceTest(StudentRepository studentRepo, CourseRepository courseRepo) {
        for (int i = 0; i < 10000; i++) {
            studentRepo.add(new Student("Student" + i, "LastName" + i, 
                    "student" + i + "@university.com", LocalDate.of(2024, 1, 1).plusDays(i % 365)));
        }

        long start1 = System.nanoTime();
        List<Student> result1 = studentRepo.getAll().stream()
                .filter(s -> s.enrollmentDate().isAfter(LocalDate.of(2024, 6, 1)))
                .toList();
        long end1 = System.nanoTime();
        long duration1 = (end1 - start1) / 1_000_000;
        logger.info(() -> "Sequential stream: found " + result1.size() + " students in " + duration1 + " ms");

        long start2 = System.nanoTime();
        List<Student> result2 = studentRepo.getAll().parallelStream()
                .filter(s -> s.enrollmentDate().isAfter(LocalDate.of(2024, 6, 1)))
                .toList();
        long end2 = System.nanoTime();
        long duration2 = (end2 - start2) / 1_000_000;
        logger.info(() -> "Parallel stream: found " + result2.size() + " students in " + duration2 + " ms");
    }
}
