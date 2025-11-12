package ua.university;

import java.time.LocalDate;
import java.util.logging.Logger;

public class MainLab4 {
    private static final Logger logger = Logger.getLogger(MainLab4.class.getName());

    public static void main(String[] args) {
        logger.info("Lab4 demo started");
        GenericRepository<Course> courseRepo = new GenericRepository<>(Course::getTitle);

        Course c1 = new Course("Intro to Java", "Basics of Java", 3, LocalDate.now().plusDays(7), CourseLevel.BEGINNER);
        Course c2 = new Course("Advanced Java", "Deep topics", 5, LocalDate.now().plusDays(30), CourseLevel.ADVANCED);
        Course cDup = new Course("Intro to Java", "Duplicate title", 2, LocalDate.now(), CourseLevel.INTERMEDIATE);

        courseRepo.add(c1);
        courseRepo.add(c2);
        courseRepo.add(cDup);

        logger.info("All courses: " + courseRepo.getAll());

        logger.info("Find by title 'Intro to Java': " + courseRepo.findByIdentity("Intro to Java").orElse(null));

        boolean removed = courseRepo.removeByIdentity("Intro to Java");
        logger.info("Removed one by identity result: " + removed);
        logger.info("Remaining courses: " + courseRepo.getAll());

        GenericRepository<Student> studentRepo = new GenericRepository<>(Student::email);
        Student s1 = new Student("Ivan", "Petrenko", "ivan@example.com", LocalDate.of(2025,9,1));
        Student s2 = new Student("Olena", "Koval", "olena@example.com", LocalDate.of(2025,9,10));

        studentRepo.add(s1);
        studentRepo.add(s2);
        logger.info("Find student by email: " + studentRepo.findByIdentity("ivan@example.com").orElse(null));

        logger.info("Lab4 demo finished");
    }
}
