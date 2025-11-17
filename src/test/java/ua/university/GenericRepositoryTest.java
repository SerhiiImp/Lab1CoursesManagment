package ua.university;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class GenericRepositoryTest {

    private StudentRepository studentRepo;
    private CourseRepository courseRepo;

    @BeforeMethod
    public void setUp() {
        studentRepo = new StudentRepository();
        courseRepo = new CourseRepository();
    }

    @Test
    public void testStudentFindByEmail() {
        studentRepo.add(new Student("John", "Doe", "john@test.com", LocalDate.now()));
        studentRepo.add(new Student("Jane", "Smith", "jane@test.com", LocalDate.now()));

        Optional<Student> found = studentRepo.findByEmail("john@test.com");
        assertTrue(found.isPresent());
        assertEquals(found.get().firstName(), "John");

        Optional<Student> notFound = studentRepo.findByEmail("nonexistent@test.com");
        assertFalse(notFound.isPresent());
    }

    @Test
    public void testStudentFindByLastName() {
        studentRepo.add(new Student("John", "Doe", "john@test.com", LocalDate.now()));
        studentRepo.add(new Student("Jane", "Doe", "jane@test.com", LocalDate.now()));
        studentRepo.add(new Student("Bob", "Smith", "bob@test.com", LocalDate.now()));

        List<Student> results = studentRepo.findByLastName("Doe");
        assertEquals(results.size(), 2);
        assertTrue(results.stream().allMatch(s -> s.lastName().equalsIgnoreCase("Doe")));
    }

    @Test
    public void testStudentFindByFirstName() {
        studentRepo.add(new Student("John", "Doe", "john1@test.com", LocalDate.now()));
        studentRepo.add(new Student("John", "Smith", "john2@test.com", LocalDate.now()));
        studentRepo.add(new Student("Jane", "Brown", "jane@test.com", LocalDate.now()));

        List<Student> results = studentRepo.findByFirstName("John");
        assertEquals(results.size(), 2);
        assertTrue(results.stream().allMatch(s -> s.firstName().equalsIgnoreCase("John")));
    }

    @Test
    public void testStudentFindEnrolledAfter() {
        LocalDate cutoff = LocalDate.of(2024, 6, 1);
        studentRepo.add(new Student("A", "A", "a@t.com", LocalDate.of(2024, 5, 1)));
        studentRepo.add(new Student("B", "B", "b@t.com", LocalDate.of(2024, 7, 1)));
        studentRepo.add(new Student("C", "C", "c@t.com", LocalDate.of(2024, 8, 1)));

        List<Student> results = studentRepo.findEnrolledAfter(cutoff);
        assertEquals(results.size(), 2);
        assertTrue(results.stream().allMatch(s -> s.enrollmentDate().isAfter(cutoff)));
    }

    @Test
    public void testStudentFindEnrolledBefore() {
        LocalDate cutoff = LocalDate.of(2024, 6, 1);
        studentRepo.add(new Student("A", "A", "a@t.com", LocalDate.of(2024, 5, 1)));
        studentRepo.add(new Student("B", "B", "b@t.com", LocalDate.of(2024, 7, 1)));
        studentRepo.add(new Student("C", "C", "c@t.com", LocalDate.of(2024, 4, 1)));

        List<Student> results = studentRepo.findEnrolledBefore(cutoff);
        assertEquals(results.size(), 2);
        assertTrue(results.stream().allMatch(s -> s.enrollmentDate().isBefore(cutoff)));
    }

    @Test
    public void testStudentFindEnrolledBetween() {
        LocalDate start = LocalDate.of(2024, 5, 1);
        LocalDate end = LocalDate.of(2024, 7, 31);
        studentRepo.add(new Student("A", "A", "a@t.com", LocalDate.of(2024, 4, 15)));
        studentRepo.add(new Student("B", "B", "b@t.com", LocalDate.of(2024, 6, 15)));
        studentRepo.add(new Student("C", "C", "c@t.com", LocalDate.of(2024, 8, 15)));

        List<Student> results = studentRepo.findEnrolledBetween(start, end);
        assertEquals(results.size(), 1);
        assertEquals(results.get(0).email(), "b@t.com");
    }

    @Test
    public void testStudentCountStudents() {
        studentRepo.add(new Student("A", "A", "a@t.com", LocalDate.now()));
        studentRepo.add(new Student("B", "B", "b@t.com", LocalDate.now()));
        studentRepo.add(new Student("C", "C", "c@t.com", LocalDate.now()));

        long count = studentRepo.countStudents();
        assertEquals(count, 3);
    }

    @Test
    public void testCourseFindByTitle() {
        courseRepo.add(new Course("Java", "desc", 3, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("Python", "desc", 4, LocalDate.now(), CourseLevel.INTERMEDIATE));

        Optional<Course> found = courseRepo.findByTitle("Java");
        assertTrue(found.isPresent());
        assertEquals(found.get().getCredits(), 3);

        Optional<Course> notFound = courseRepo.findByTitle("C++");
        assertFalse(notFound.isPresent());
    }

    @Test
    public void testCourseFindByLevel() {
        courseRepo.add(new Course("C1", "d", 3, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C2", "d", 4, LocalDate.now(), CourseLevel.ADVANCED));
        courseRepo.add(new Course("C3", "d", 5, LocalDate.now(), CourseLevel.ADVANCED));

        List<Course> results = courseRepo.findByLevel(CourseLevel.ADVANCED);
        assertEquals(results.size(), 2);
        assertTrue(results.stream().allMatch(c -> c.getLevel() == CourseLevel.ADVANCED));
    }

    @Test
    public void testCourseFindByCreditsRange() {
        courseRepo.add(new Course("C1", "d", 2, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C2", "d", 3, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C3", "d", 4, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C4", "d", 5, LocalDate.now(), CourseLevel.BEGINNER));

        List<Course> results = courseRepo.findByCreditsRange(3, 4);
        assertEquals(results.size(), 2);
        assertTrue(results.stream().allMatch(c -> c.getCredits() >= 3 && c.getCredits() <= 4));
    }

    @Test
    public void testCourseFindStartingAfter() {
        LocalDate cutoff = LocalDate.of(2025, 2, 1);
        courseRepo.add(new Course("C1", "d", 3, LocalDate.of(2025, 1, 1), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C2", "d", 3, LocalDate.of(2025, 3, 1), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C3", "d", 3, LocalDate.of(2025, 4, 1), CourseLevel.BEGINNER));

        List<Course> results = courseRepo.findStartingAfter(cutoff);
        assertEquals(results.size(), 2);
        assertTrue(results.stream().allMatch(c -> c.getStartDate().isAfter(cutoff)));
    }

    @Test
    public void testCourseFindStartingBefore() {
        LocalDate cutoff = LocalDate.of(2025, 3, 1);
        courseRepo.add(new Course("C1", "d", 3, LocalDate.of(2025, 1, 1), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C2", "d", 3, LocalDate.of(2025, 2, 1), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C3", "d", 3, LocalDate.of(2025, 4, 1), CourseLevel.BEGINNER));

        List<Course> results = courseRepo.findStartingBefore(cutoff);
        assertEquals(results.size(), 2);
        assertTrue(results.stream().allMatch(c -> c.getStartDate().isBefore(cutoff)));
    }

    @Test
    public void testCourseFindByTitleContains() {
        courseRepo.add(new Course("Java Basics", "d", 3, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("Advanced Java", "d", 4, LocalDate.now(), CourseLevel.ADVANCED));
        courseRepo.add(new Course("Python", "d", 3, LocalDate.now(), CourseLevel.BEGINNER));

        List<Course> results = courseRepo.findByTitleContains("Java");
        assertEquals(results.size(), 2);
        assertTrue(results.stream().allMatch(c -> c.getTitle().toLowerCase().contains("java")));
    }

    @Test
    public void testCourseGetTotalCredits() {
        courseRepo.add(new Course("C1", "d", 3, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C2", "d", 4, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C3", "d", 5, LocalDate.now(), CourseLevel.BEGINNER));

        int total = courseRepo.getTotalCredits();
        assertEquals(total, 12);
    }

    @Test
    public void testCourseGetAverageCredits() {
        courseRepo.add(new Course("C1", "d", 3, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C2", "d", 4, LocalDate.now(), CourseLevel.BEGINNER));
        courseRepo.add(new Course("C3", "d", 5, LocalDate.now(), CourseLevel.BEGINNER));

        double avg = courseRepo.getAverageCredits();
        assertEquals(avg, 4.0, 0.01);
    }
}
