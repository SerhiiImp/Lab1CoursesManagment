package ua.university;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CourseRepository extends GenericRepository<Course> {
    private static final Logger logger = Logger.getLogger(CourseRepository.class.getName());

    public CourseRepository() {
        super(Course::getTitle);
    }

    public Optional<Course> findByTitle(String title) {
        logger.info(() -> "Searching course by title: " + title);
        return storage.stream()
                .filter(c -> c.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public List<Course> findByLevel(CourseLevel level) {
        logger.info(() -> "Searching courses by level: " + level);
        return storage.stream()
                .filter(c -> c.getLevel() == level)
                .collect(Collectors.toList());
    }

    public List<Course> findByCreditsRange(int minCredits, int maxCredits) {
        logger.info(() -> "Searching courses with credits between: " + minCredits + " and " + maxCredits);
        return storage.stream()
                .filter(c -> c.getCredits() >= minCredits && c.getCredits() <= maxCredits)
                .collect(Collectors.toList());
    }

    public List<Course> findStartingAfter(LocalDate date) {
        logger.info(() -> "Searching courses starting after: " + date);
        return storage.stream()
                .filter(c -> c.getStartDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Course> findStartingBefore(LocalDate date) {
        logger.info(() -> "Searching courses starting before: " + date);
        return storage.stream()
                .filter(c -> c.getStartDate().isBefore(date))
                .collect(Collectors.toList());
    }

    public List<Course> findByTitleContains(String keyword) {
        logger.info(() -> "Searching courses with title containing: " + keyword);
        return storage.stream()
                .filter(c -> c.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public int getTotalCredits() {
        int total = storage.stream()
                .mapToInt(Course::getCredits)
                .sum();
        logger.info(() -> "Total credits: " + total);
        return total;
    }

    public double getAverageCredits() {
        double avg = storage.stream()
                .mapToInt(Course::getCredits)
                .average()
                .orElse(0.0);
        logger.info(() -> "Average credits: " + avg);
        return avg;
    }

    public void printAllTitles() {
        logger.info("Printing all course titles:");
        storage.stream()
                .map(Course::getTitle)
                .forEach(title -> logger.info(() -> "  " + title));
    }

    public String concatenateTitles() {
        String result = storage.stream()
                .map(Course::getTitle)
                .reduce("", (acc, title) -> acc.isEmpty() ? title : acc + " | " + title);
        logger.info(() -> "Concatenated titles: " + result);
        return result;
    }
}
