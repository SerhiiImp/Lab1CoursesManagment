package ua.university;

import com.fasterxml.jackson.core.type.TypeReference;
import ua.university.config.ConfigManager;
import ua.university.exception.DataSerializationException;
import ua.university.service.SerializationService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Starting Lab 7 - Serialization and File Handling");

        try {
            ConfigManager config = new ConfigManager("src/main/resources/config.properties");
            logger.info("Configuration loaded successfully");

            String studentsJsonPath = config.getProperty("students.json.path");
            String studentsYamlPath = config.getProperty("students.yaml.path");
            String coursesJsonPath = config.getProperty("courses.json.path");
            String coursesYamlPath = config.getProperty("courses.yaml.path");
            int testObjectsCount = config.getIntProperty("test.objects.count", 5);

            logger.info("Creating test data with " + testObjectsCount + " objects");

            StudentRepository originalStudentRepo = new StudentRepository();
            for (int i = 1; i <= testObjectsCount; i++) {
                originalStudentRepo.add(new Student(
                        "Student" + i,
                        "LastName" + i,
                        "student" + i + "@university.com",
                        LocalDate.of(2024, 9, i)
                ));
            }

            CourseRepository originalCourseRepo = new CourseRepository();
            originalCourseRepo.add(new Course("Java Programming", "Introduction to Java", 3, LocalDate.of(2025, 1, 15), CourseLevel.BEGINNER));
            originalCourseRepo.add(new Course("Data Structures", "Advanced Data Structures", 4, LocalDate.of(2025, 2, 1), CourseLevel.INTERMEDIATE));
            originalCourseRepo.add(new Course("Algorithms", "Algorithm Design and Analysis", 5, LocalDate.of(2025, 3, 1), CourseLevel.ADVANCED));
            originalCourseRepo.add(new Course("Spring Boot", "Enterprise Java Development", 4, LocalDate.of(2025, 4, 1), CourseLevel.ADVANCED));
            originalCourseRepo.add(new Course("Database Systems", "SQL and NoSQL Databases", 3, LocalDate.of(2025, 1, 20), CourseLevel.INTERMEDIATE));

            SerializationService serializationService = new SerializationService();

            logger.info("=== Saving data to JSON ===");
            serializationService.saveToJson(originalStudentRepo.getAll(), studentsJsonPath);
            serializationService.saveToJson(originalCourseRepo.getAll(), coursesJsonPath);

            logger.info("=== Saving data to YAML ===");
            serializationService.saveToYaml(originalStudentRepo.getAll(), studentsYamlPath);
            serializationService.saveToYaml(originalCourseRepo.getAll(), coursesYamlPath);

            logger.info("=== Loading data from JSON ===");
            List<Student> studentsFromJson = serializationService.loadFromJson(
                    studentsJsonPath,
                    new TypeReference<List<Student>>() {}
            );
            List<Course> coursesFromJson = serializationService.loadFromJson(
                    coursesJsonPath,
                    new TypeReference<List<Course>>() {}
            );

            logger.info("=== Loading data from YAML ===");
            List<Student> studentsFromYaml = serializationService.loadFromYaml(
                    studentsYamlPath,
                    new TypeReference<List<Student>>() {}
            );
            List<Course> coursesFromYaml = serializationService.loadFromYaml(
                    coursesYamlPath,
                    new TypeReference<List<Course>>() {}
            );

            logger.info("=== Comparing original and restored data ===");
            boolean studentsJsonMatch = compareStudents(originalStudentRepo.getAll(), studentsFromJson);
            boolean studentsYamlMatch = compareStudents(originalStudentRepo.getAll(), studentsFromYaml);
            boolean coursesJsonMatch = compareCourses(originalCourseRepo.getAll(), coursesFromJson);
            boolean coursesYamlMatch = compareCourses(originalCourseRepo.getAll(), coursesFromYaml);

            logger.info("Students JSON match: " + studentsJsonMatch);
            logger.info("Students YAML match: " + studentsYamlMatch);
            logger.info("Courses JSON match: " + coursesJsonMatch);
            logger.info("Courses YAML match: " + coursesYamlMatch);

            if (studentsJsonMatch && studentsYamlMatch && coursesJsonMatch && coursesYamlMatch) {
                logger.info("All data restored successfully and matches original data");
            }

            logger.info("=== Demonstrating exception handling ===");
            try {
                serializationService.loadFromJson("nonexistent.json", new TypeReference<List<Student>>() {});
            } catch (DataSerializationException e) {
                logger.warning("Expected exception caught: " + e.getMessage());
            }

            logger.info("Lab 7 completed successfully");

        } catch (IOException e) {
            logger.severe("Failed to load configuration: " + e.getMessage());
        } catch (DataSerializationException e) {
            logger.severe("Serialization error: " + e.getMessage());
        }
    }

    private static boolean compareStudents(List<Student> original, List<Student> restored) {
        if (original.size() != restored.size()) {
            return false;
        }
        for (int i = 0; i < original.size(); i++) {
            Student o = original.get(i);
            Student r = restored.get(i);
            if (!o.firstName().equals(r.firstName()) ||
                !o.lastName().equals(r.lastName()) ||
                !o.email().equals(r.email()) ||
                !o.enrollmentDate().equals(r.enrollmentDate())) {
                return false;
            }
        }
        return true;
    }

    private static boolean compareCourses(List<Course> original, List<Course> restored) {
        if (original.size() != restored.size()) {
            return false;
        }
        for (int i = 0; i < original.size(); i++) {
            Course o = original.get(i);
            Course r = restored.get(i);
            if (!o.getTitle().equals(r.getTitle()) ||
                !o.getDescription().equals(r.getDescription()) ||
                o.getCredits() != r.getCredits() ||
                !o.getStartDate().equals(r.getStartDate()) ||
                o.getLevel() != r.getLevel()) {
                return false;
            }
        }
        return true;
    }
}
