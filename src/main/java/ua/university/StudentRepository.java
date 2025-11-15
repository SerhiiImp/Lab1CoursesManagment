package ua.university;

import java.util.logging.Logger;

public class StudentRepository extends GenericRepository<Student> {
    private static final Logger logger = Logger.getLogger(StudentRepository.class.getName());

    public StudentRepository() {
        super(Student::email);
    }

    public void sortByLastName(String order) {
        sortByIdentity(order);
        logger.info(() -> "Students sorted by lastName " + order);
    }

    public void sortByEmail() {
        sortBy(Student.byEmail());
        logger.info("Students sorted by email");
    }

    public void sortByEnrollmentDate() {
        sortBy(Student.byEnrollmentDate());
        logger.info("Students sorted by enrollment date");
    }
}
