package ua.university;

import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

public class GenericRepositoryTest {

    @Test
    public void testStudentRepositorySortByLastName() {
        StudentRepository repo = new StudentRepository();
        repo.add(new Student("John", "Doe", "j@e.com", LocalDate.of(2024, 9, 1)));
        repo.add(new Student("Alice", "Smith", "a@e.com", LocalDate.of(2024, 8, 1)));
        repo.add(new Student("Bob", "Johnson", "b@e.com", LocalDate.of(2024, 7, 1)));

        repo.sortByLastName("asc");
        List<Student> sorted = repo.getAll();
        assertEquals(sorted.get(0).lastName(), "Doe");
        assertEquals(sorted.get(1).lastName(), "Johnson");
        assertEquals(sorted.get(2).lastName(), "Smith");
    }

    @Test
    public void testStudentRepositorySortByEmail() {
        StudentRepository repo = new StudentRepository();
        repo.add(new Student("Z", "Z", "z@e.com", LocalDate.now()));
        repo.add(new Student("A", "A", "a@e.com", LocalDate.now()));
        repo.add(new Student("M", "M", "m@e.com", LocalDate.now()));

        repo.sortByEmail();
        List<Student> sorted = repo.getAll();
        assertEquals(sorted.get(0).email(), "a@e.com");
        assertEquals(sorted.get(1).email(), "m@e.com");
        assertEquals(sorted.get(2).email(), "z@e.com");
    }

    @Test
    public void testStudentRepositorySortByEnrollmentDate() {
        StudentRepository repo = new StudentRepository();
        repo.add(new Student("A", "A", "a@e.com", LocalDate.of(2024, 10, 1)));
        repo.add(new Student("B", "B", "b@e.com", LocalDate.of(2024, 8, 1)));
        repo.add(new Student("C", "C", "c@e.com", LocalDate.of(2024, 9, 1)));

        repo.sortByEnrollmentDate();
        List<Student> sorted = repo.getAll();
        assertEquals(sorted.get(0).enrollmentDate(), LocalDate.of(2024, 8, 1));
        assertEquals(sorted.get(1).enrollmentDate(), LocalDate.of(2024, 9, 1));
        assertEquals(sorted.get(2).enrollmentDate(), LocalDate.of(2024, 10, 1));
    }

    @Test
    public void testCourseRepositorySortByTitle() {
        CourseRepository repo = new CourseRepository();
        repo.add(new Course("Zebra", "desc", 3, LocalDate.now(), CourseLevel.BEGINNER));
        repo.add(new Course("Alpha", "desc", 4, LocalDate.now(), CourseLevel.INTERMEDIATE));
        repo.add(new Course("Beta", "desc", 5, LocalDate.now(), CourseLevel.ADVANCED));

        repo.sortByTitle("asc");
        List<Course> sorted = repo.getAll();
        assertEquals(sorted.get(0).getTitle(), "Alpha");
        assertEquals(sorted.get(1).getTitle(), "Beta");
        assertEquals(sorted.get(2).getTitle(), "Zebra");
    }

    @Test
    public void testCourseRepositorySortByCredits() {
        CourseRepository repo = new CourseRepository();
        repo.add(new Course("C1", "d", 5, LocalDate.now(), CourseLevel.BEGINNER));
        repo.add(new Course("C2", "d", 3, LocalDate.now(), CourseLevel.BEGINNER));
        repo.add(new Course("C3", "d", 4, LocalDate.now(), CourseLevel.BEGINNER));

        repo.sortByCredits();
        List<Course> sorted = repo.getAll();
        assertEquals(sorted.get(0).getCredits(), 3);
        assertEquals(sorted.get(1).getCredits(), 4);
        assertEquals(sorted.get(2).getCredits(), 5);
    }

    @Test
    public void testCourseRepositorySortByStartDate() {
        CourseRepository repo = new CourseRepository();
        repo.add(new Course("C1", "d", 3, LocalDate.of(2025, 3, 1), CourseLevel.BEGINNER));
        repo.add(new Course("C2", "d", 3, LocalDate.of(2025, 1, 1), CourseLevel.BEGINNER));
        repo.add(new Course("C3", "d", 3, LocalDate.of(2025, 2, 1), CourseLevel.BEGINNER));

        repo.sortByStartDate();
        List<Course> sorted = repo.getAll();
        assertEquals(sorted.get(0).getStartDate(), LocalDate.of(2025, 1, 1));
        assertEquals(sorted.get(1).getStartDate(), LocalDate.of(2025, 2, 1));
        assertEquals(sorted.get(2).getStartDate(), LocalDate.of(2025, 3, 1));
    }

    @Test
    public void testCourseRepositorySortByLevel() {
        CourseRepository repo = new CourseRepository();
        repo.add(new Course("C1", "d", 3, LocalDate.now(), CourseLevel.ADVANCED));
        repo.add(new Course("C2", "d", 3, LocalDate.now(), CourseLevel.BEGINNER));
        repo.add(new Course("C3", "d", 3, LocalDate.now(), CourseLevel.INTERMEDIATE));

        repo.sortByLevel();
        List<Course> sorted = repo.getAll();
        assertEquals(sorted.get(0).getLevel(), CourseLevel.BEGINNER);
        assertEquals(sorted.get(1).getLevel(), CourseLevel.INTERMEDIATE);
        assertEquals(sorted.get(2).getLevel(), CourseLevel.ADVANCED);
    }
}
