package ua.university;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ua.university.exception.InvalidDataException;

public class Course implements Comparable<Course> {
    private static final Logger logger = Logger.getLogger(Course.class.getName());
    private static final int MIN_CREDITS = 1;
    private static final int MAX_CREDITS = 10;

    private final String title;
    private final String description;
    private final int credits;
    private final LocalDate startDate;
    private final CourseLevel level;

    @JsonCreator
    public Course(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("credits") int credits,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("level") CourseLevel level) {
        List<String> errors = new ArrayList<>();

        if (title == null || title.isBlank()) {
            errors.add("title: cannot be null or empty");
        }
        if (description == null) {
            errors.add("description: cannot be null");
        }
        if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
            errors.add("credits: must be between " + MIN_CREDITS + " and " + MAX_CREDITS);
        }
        if (startDate == null) {
            errors.add("startDate: cannot be null");
        }
        if (level == null) {
            errors.add("level: cannot be null");
        }

        if (!errors.isEmpty()) {
            logger.warning(() -> "Validation failed for Course: " + errors);
            throw new InvalidDataException(errors);
        }

        this.title = title != null ? title.trim() : "";
        this.description = description != null ? description.trim() : "";
        this.credits = credits;
        this.startDate = startDate;
        this.level = level;

        logger.info(() -> "Course created successfully: " + this.title);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCredits() {
        return credits;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public CourseLevel getLevel() {
        return level;
    }

    @Override
    public int compareTo(Course other) {
        return this.title.compareTo(other.title);
    }

    public static Comparator<Course> byCredits() {
        return Comparator.comparingInt(Course::getCredits);
    }

    public static Comparator<Course> byStartDate() {
        return Comparator.comparing(Course::getStartDate);
    }

    public static Comparator<Course> byLevel() {
        return Comparator.comparing(Course::getLevel);
    }

    @Override
    public String toString() {
        return "Course{" + title + ", level=" + level + ", credits=" + credits + ", start=" + startDate + '}';
    }
}
