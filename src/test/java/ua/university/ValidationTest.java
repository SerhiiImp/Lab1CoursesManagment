package ua.university;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.university.exception.InvalidDataException;

import java.time.LocalDate;

public class ValidationTest {

    @Test
    public void testValidStudentCreation() {
        Student student = new Student(
            "John",
            "Doe",
            "john.doe@example.com",
            LocalDate.of(2024, 1, 15)
        );
        Assert.assertNotNull(student);
        Assert.assertEquals(student.firstName(), "John");
        Assert.assertEquals(student.lastName(), "Doe");
        Assert.assertEquals(student.email(), "john.doe@example.com");
    }

    @Test
    public void testStudentWithEmptyFirstName() {
        try {
            new Student("", "Doe", "john@example.com", LocalDate.now());
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("firstName"));
        }
    }

    @Test
    public void testStudentWithNullLastName() {
        try {
            new Student("John", null, "john@example.com", LocalDate.now());
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("lastName"));
        }
    }

    @Test
    public void testStudentWithInvalidEmail() {
        try {
            new Student("John", "Doe", "invalid-email", LocalDate.now());
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("email"));
        }
    }

    @Test
    public void testStudentWithFutureEnrollmentDate() {
        try {
            new Student("John", "Doe", "john@example.com", LocalDate.now().plusDays(1));
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("enrollmentDate"));
        }
    }

    @Test
    public void testStudentWithMultipleErrors() {
        try {
            new Student("", null, "bad-email", LocalDate.now().plusYears(1));
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("firstName"));
            Assert.assertTrue(e.getMessage().contains("lastName"));
            Assert.assertTrue(e.getMessage().contains("email"));
            Assert.assertTrue(e.getMessage().contains("enrollmentDate"));
            Assert.assertEquals(e.getErrors().size(), 4);
        }
    }

    @Test
    public void testValidCourseCreation() {
        Course course = new Course(
            "Java Programming",
            "Introduction to Java",
            3,
            LocalDate.of(2025, 1, 15),
            CourseLevel.BEGINNER
        );
        Assert.assertNotNull(course);
        Assert.assertEquals(course.getTitle(), "Java Programming");
        Assert.assertEquals(course.getCredits(), 3);
    }

    @Test
    public void testCourseWithEmptyTitle() {
        try {
            new Course("", "Description", 3, LocalDate.now(), CourseLevel.BEGINNER);
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("title"));
        }
    }

    @Test
    public void testCourseWithNullDescription() {
        try {
            new Course("Java", null, 3, LocalDate.now(), CourseLevel.BEGINNER);
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("description"));
        }
    }

    @Test
    public void testCourseWithInvalidCredits() {
        try {
            new Course("Java", "Description", 0, LocalDate.now(), CourseLevel.BEGINNER);
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("credits"));
        }
    }

    @Test
    public void testCourseWithTooManyCredits() {
        try {
            new Course("Java", "Description", 15, LocalDate.now(), CourseLevel.BEGINNER);
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("credits"));
        }
    }

    @Test
    public void testCourseWithNullStartDate() {
        try {
            new Course("Java", "Description", 3, null, CourseLevel.BEGINNER);
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("startDate"));
        }
    }

    @Test
    public void testCourseWithNullLevel() {
        try {
            new Course("Java", "Description", 3, LocalDate.now(), null);
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getMessage().contains("level"));
        }
    }

    @Test
    public void testCourseWithMultipleErrors() {
        try {
            new Course("", null, 0, null, null);
            Assert.fail("Expected InvalidDataException");
        } catch (InvalidDataException e) {
            Assert.assertTrue(e.getErrors().size() >= 4);
            Assert.assertTrue(e.getMessage().contains("title"));
            Assert.assertTrue(e.getMessage().contains("description"));
            Assert.assertTrue(e.getMessage().contains("credits"));
            Assert.assertTrue(e.getMessage().contains("startDate"));
        }
    }

    @Test
    public void testStudentRepositoryWithValidData() {
        StudentRepository repo = new StudentRepository();
        Student student = new Student("Jane", "Smith", "jane@example.com", LocalDate.now());
        repo.add(student);
        Assert.assertEquals(repo.getAll().size(), 1);
    }

    @Test
    public void testCourseRepositoryWithValidData() {
        CourseRepository repo = new CourseRepository();
        Course course = new Course("Python", "Python basics", 4, LocalDate.now(), CourseLevel.INTERMEDIATE);
        repo.add(course);
        Assert.assertEquals(repo.getAll().size(), 1);
    }

    @Test
    public void testStudentEmailNormalization() {
        Student student = new Student("John", "Doe", "JOHN.DOE@EXAMPLE.COM", LocalDate.now());
        Assert.assertEquals(student.email(), "john.doe@example.com");
    }

    @Test
    public void testStudentNameTrimming() {
        Student student = new Student("  John  ", "  Doe  ", "john@example.com", LocalDate.now());
        Assert.assertEquals(student.firstName(), "John");
        Assert.assertEquals(student.lastName(), "Doe");
    }

    @Test
    public void testCourseTitleTrimming() {
        Course course = new Course("  Java Programming  ", "Description", 3, LocalDate.now(), CourseLevel.BEGINNER);
        Assert.assertEquals(course.getTitle(), "Java Programming");
    }
}
