package ua.university;

import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

public class GenericRepositoryTest {

    @Test
    public void testPersonRepoAddFindRemove() throws InvalidDataException {
        GenericRepository<Person> repo = new GenericRepository<>(Person::getEmail);

        Person p1 = new Person("Alice", 20, "alice@example.com");
        Person p2 = new Person("Bob", 22, "bob@example.com");

        repo.add(p1);
        repo.add(p2);

        assertEquals(repo.getAll().size(), 2);

        assertTrue(repo.findByIdentity("alice@example.com").isPresent());
        assertEquals(repo.findByIdentity("bob@example.com").get().getName(), "Bob");

        boolean removed = repo.removeByIdentity("alice@example.com");
        assertTrue(removed);
        assertFalse(repo.findByIdentity("alice@example.com").isPresent());
    }

    @Test
    public void testCourseRepositoryAddFindRemove() {
        GenericRepository<Course> repo = new GenericRepository<>(Course::getTitle);
        Course c = new Course("Java", "desc", 3, LocalDate.now(), CourseLevel.BEGINNER);
        repo.add(c);
        assertEquals(repo.getAll().size(), 1);
        assertTrue(repo.findByIdentity("Java").isPresent());
        boolean removed = repo.removeByIdentity("Java");
        assertTrue(removed);
        assertTrue(repo.getAll().isEmpty());
    }

    @Test
    public void testCourseRepoDuplicateIdentity() {
        GenericRepository<Course> repo = new GenericRepository<>(Course::getTitle);
        Course c1 = new Course("Java", "desc", 3, LocalDate.now(), CourseLevel.BEGINNER);
        Course c2 = new Course("Java", "desc2", 4, LocalDate.now(), CourseLevel.INTERMEDIATE);

        repo.add(c1);
        repo.add(c2);

        List<Course> all = repo.getAll();
        assertEquals(all.size(), 2);

        assertTrue(repo.findByIdentity("Java").isPresent());

        boolean removed = repo.removeByIdentity("Java");
        assertTrue(removed);
        assertEquals(repo.getAll().size(), 1);
    }

    @Test
    public void testStudentRepository() {
        GenericRepository<Student> repo = new GenericRepository<>(Student::email);
        Student s1 = new Student("A","B","a@b.com", LocalDate.now());
        Student s2 = new Student("C","D","c@d.com", LocalDate.now());
        repo.add(s1);
        repo.add(s2);
        List<Student> all = repo.getAll();
        assertEquals(all.size(), 2);
        assertTrue(repo.findByIdentity("a@b.com").isPresent());
    }
}
