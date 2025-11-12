package ua.university;

import java.time.LocalDate;
import java.util.Objects;

public class Course {
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
    public String toString() {
        return "Course{" + title + ", level=" + level + ", credits=" + credits + ", start=" + startDate + '}';
    }
}
