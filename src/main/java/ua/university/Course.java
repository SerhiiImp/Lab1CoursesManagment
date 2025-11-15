package ua.university;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class Course implements Comparable<Course> {
    private final String title;
    private final String description;
    private final int credits;
    private final LocalDate startDate;
    private final CourseLevel level;

    public Course(String title, String description, int credits, LocalDate startDate, CourseLevel level) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title is required");
        this.title = title.trim();
        this.description = description == null ? "" : description.trim();
        if (credits <= 0) throw new IllegalArgumentException("credits must be > 0");
        this.credits = credits;
        this.startDate = Objects.requireNonNull(startDate);
        this.level = Objects.requireNonNull(level);
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getCredits() { return credits; }
    public LocalDate getStartDate() { return startDate; }
    public CourseLevel getLevel() { return level; }

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
