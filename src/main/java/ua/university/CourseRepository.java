package ua.university;

import java.util.logging.Logger;

public class CourseRepository extends GenericRepository<Course> {
    private static final Logger logger = Logger.getLogger(CourseRepository.class.getName());

    public CourseRepository() {
        super(Course::getTitle);
    }

    public void sortByTitle(String order) {
        sortByIdentity(order);
        logger.info(() -> "Courses sorted by title " + order);
    }

    public void sortByCredits() {
        sortBy(Course.byCredits());
        logger.info("Courses sorted by credits");
    }

    public void sortByStartDate() {
        sortBy(Course.byStartDate());
        logger.info("Courses sorted by start date");
    }

    public void sortByLevel() {
        sortBy(Course.byLevel());
        logger.info("Courses sorted by level");
    }
}
