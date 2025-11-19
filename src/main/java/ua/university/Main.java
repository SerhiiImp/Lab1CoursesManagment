package ua.university;

import ua.university.exception.InvalidDataException;

import java.time.LocalDate;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("=== Starting Lab 8: Data Validation and Object Creation ===\n");

        StudentRepository studentRepo = new StudentRepository();
        CourseRepository courseRepo = new CourseRepository();

        demonstrateValidObjects(studentRepo, courseRepo);
        System.out.println("\n" + "=".repeat(80) + "\n");
        demonstrateInvalidObjects();
        System.out.println("\n" + "=".repeat(80) + "\n");
        demonstrateRepositoryValidation(studentRepo, courseRepo);

        logger.info("\n=== Lab 8 completed successfully ===");
    }

    private static void demonstrateValidObjects(StudentRepository studentRepo, CourseRepository courseRepo) {
        logger.info("=== Demonstration 1: Creating Valid Objects ===\n");

        try {
            logger.info("Creating valid student 1...");
            Student student1 = new Student(
                "Ivan",
                "Petrov",
                "ivan.petrov@university.ua",
                LocalDate.of(2024, 9, 1)
            );
            studentRepo.add(student1);
            System.out.println("✓ Created: " + student1);

            logger.info("\nCreating valid student 2...");
            Student student2 = new Student(
                "Maria",
                "Kovalenko",
                "maria.k@university.ua",
                LocalDate.of(2024, 9, 15)
            );
            studentRepo.add(student2);
            System.out.println("✓ Created: " + student2);

            logger.info("\nCreating valid student 3...");
            Student student3 = new Student(
                "Oleksandr",
                "Shevchenko",
                "alex.shev@university.ua",
                LocalDate.of(2024, 8, 20)
            );
            studentRepo.add(student3);
            System.out.println("✓ Created: " + student3);

            logger.info("\n\nCreating valid course 1...");
            Course course1 = new Course(
                "Java Programming",
                "Introduction to Java programming language",
                5,
                LocalDate.of(2025, 2, 1),
                CourseLevel.BEGINNER
            );
            courseRepo.add(course1);
            System.out.println("✓ Created: " + course1);

            logger.info("\nCreating valid course 2...");
            Course course2 = new Course(
                "Data Structures",
                "Advanced data structures and algorithms",
                6,
                LocalDate.of(2025, 3, 1),
                CourseLevel.INTERMEDIATE
            );
            courseRepo.add(course2);
            System.out.println("✓ Created: " + course2);

            logger.info("\n\n✓ All valid objects created successfully!");
            logger.info(() -> "Total students in repository: " + studentRepo.countStudents());
            logger.info(() -> "Total courses in repository: " + courseRepo.getAll().size());

        } catch (InvalidDataException e) {
            logger.severe("Unexpected validation error: " + e.getMessage());
        }
    }

    private static void demonstrateInvalidObjects() {
        logger.info("=== Demonstration 2: Attempting to Create Invalid Objects ===\n");

        logger.info("Test 1: Student with empty first name");
        try {
            new Student("", "Ivanov", "test@university.ua", LocalDate.now());
            logger.severe("ERROR: Should have thrown InvalidDataException!");
        } catch (InvalidDataException e) {
            System.out.println("✗ Validation failed as expected:");
            System.out.println("  " + e.getMessage());
            displayErrors(e);
        }

        System.out.println();
        logger.info("Test 2: Student with invalid email format");
        try {
            new Student("Petro", "Ivanov", "not-an-email", LocalDate.now());
            logger.severe("ERROR: Should have thrown InvalidDataException!");
        } catch (InvalidDataException e) {
            System.out.println("✗ Validation failed as expected:");
            System.out.println("  " + e.getMessage());
            displayErrors(e);
        }

        System.out.println();
        logger.info("Test 3: Student with future enrollment date");
        try {
            new Student("Anna", "Petrenko", "anna@university.ua", LocalDate.now().plusDays(10));
            logger.severe("ERROR: Should have thrown InvalidDataException!");
        } catch (InvalidDataException e) {
            System.out.println("✗ Validation failed as expected:");
            System.out.println("  " + e.getMessage());
            displayErrors(e);
        }

        System.out.println();
        logger.info("Test 4: Student with MULTIPLE validation errors");
        try {
            new Student("", null, "bad-email", LocalDate.now().plusYears(1));
            logger.severe("ERROR: Should have thrown InvalidDataException!");
        } catch (InvalidDataException e) {
            System.out.println("✗ Validation failed as expected (multiple errors):");
            System.out.println("  " + e.getMessage());
            displayErrors(e);
        }

        System.out.println();
        logger.info("Test 5: Course with invalid credits (too low)");
        try {
            new Course("Math", "Mathematics", 0, LocalDate.of(2025, 1, 1), CourseLevel.BEGINNER);
            logger.severe("ERROR: Should have thrown InvalidDataException!");
        } catch (InvalidDataException e) {
            System.out.println("✗ Validation failed as expected:");
            System.out.println("  " + e.getMessage());
            displayErrors(e);
        }

        System.out.println();
        logger.info("Test 6: Course with invalid credits (too high)");
        try {
            new Course("Physics", "Physics course", 15, LocalDate.of(2025, 1, 1), CourseLevel.ADVANCED);
            logger.severe("ERROR: Should have thrown InvalidDataException!");
        } catch (InvalidDataException e) {
            System.out.println("✗ Validation failed as expected:");
            System.out.println("  " + e.getMessage());
            displayErrors(e);
        }

        System.out.println();
        logger.info("Test 7: Course with null level");
        try {
            new Course("Chemistry", "Chemistry basics", 5, LocalDate.of(2025, 1, 1), null);
            logger.severe("ERROR: Should have thrown InvalidDataException!");
        } catch (InvalidDataException e) {
            System.out.println("✗ Validation failed as expected:");
            System.out.println("  " + e.getMessage());
            displayErrors(e);
        }

        System.out.println();
        logger.info("Test 8: Course with MULTIPLE validation errors");
        try {
            new Course("", null, 0, null, null);
            logger.severe("ERROR: Should have thrown InvalidDataException!");
        } catch (InvalidDataException e) {
            System.out.println("✗ Validation failed as expected (multiple errors):");
            System.out.println("  " + e.getMessage());
            displayErrors(e);
        }

        logger.info("\n✓ All invalid object tests completed successfully!");
    }

    private static void demonstrateRepositoryValidation(StudentRepository studentRepo, CourseRepository courseRepo) {
        logger.info("=== Demonstration 3: Repository Validation ===\n");

        logger.info("Attempting to add invalid student to repository...");
        try {
            studentRepo.add(new Student("", "Test", "bad-email", LocalDate.now().plusDays(1)));
            logger.severe("ERROR: Repository should have prevented invalid object!");
        } catch (InvalidDataException e) {
            System.out.println("✓ Repository correctly rejected invalid student:");
            System.out.println("  " + e.getMessage());
        }

        System.out.println();
        logger.info("Attempting to add invalid course to repository...");
        try {
            courseRepo.add(new Course("", null, 0, null, null));
            logger.severe("ERROR: Repository should have prevented invalid object!");
        } catch (InvalidDataException e) {
            System.out.println("✓ Repository correctly rejected invalid course:");
            System.out.println("  " + e.getMessage());
        }

        System.out.println();
        logger.info("Final repository state:");
        logger.info(() -> "  Students: " + studentRepo.countStudents());
        logger.info(() -> "  Courses: " + courseRepo.getAll().size());

        System.out.println("\nStudents in repository:");
        studentRepo.getAll().forEach(s -> System.out.println("  - " + s));

        System.out.println("\nCourses in repository:");
        courseRepo.getAll().forEach(c -> System.out.println("  - " + c));
    }

    private static void displayErrors(InvalidDataException e) {
        System.out.println("  Detailed errors:");
        e.getErrors().forEach(error -> System.out.println("    • " + error));
    }
}
