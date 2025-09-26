package CoursesClass;

import ua.util.ValidationHelper;
import ua.util.Utils;
import java.util.Objects;

public class Course {
    private String title;
    private String description;
    public int credits;
    public int startDate;

    private Course(String title,String description,int credits,int startDate){
        Utils.validateCourse(title,description, credits, startDate);

        this.title = title;
        this.description = description;
        this.credits = credits;
        this.startDate = startDate;
    }

    public static Course createCourse(String title,String description,int credits,int startDate){
            return new Course(title,description,credits,startDate);
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() { return title; }

    public int getCredits() { return credits; }

    public int getStartDate() { return startDate; }

    public void setTitle(String title) {
        ValidationHelper.requireNonEmpty(title, "Course Title");
        this.title = title;
    }

    public void setCredits(int credits) {
        ValidationHelper.requirePositive(credits, "Credits");
        this.credits = credits;
    }

    public void setDescription(String description) {
        ValidationHelper.requireNonEmpty(description, "Course Description");
        this.description = description;

    }

    public void setStartDate(int startDate) {
        ValidationHelper.requirePositive(startDate, "Start Date");
        this.startDate = startDate;
    }

    @Override
    public String toString(){
        return "Course { " + title + ", " + description + ", " + credits + ", " + startDate + ". }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course other = (Course) obj;
        return Objects.equals(title, other.title) &&
                Objects.equals(description, other.description) &&
                credits == other.credits &&
                startDate == other.startDate;
    }

    @Override
    public int hashCode(){
        return Objects.hash(title,description,credits,startDate);
    }
}