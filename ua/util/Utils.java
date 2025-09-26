package ua.util;

public class Utils {

    public static void validateStudent(String firstName, String lastName, String email, int enrollmentDate) {
        ValidationHelper.requireNonEmpty(firstName, "First Name");
        ValidationHelper.requireNonEmpty(lastName, "Last Name");
        ValidationHelper.requireEmail(email);
        ValidationHelper.requirePositive(enrollmentDate, "Enrollment Date");
    }

    public static void validateCourse(String title, String description, int credits, int startDate) {
        ValidationHelper.requireNonEmpty(title, "Course Title");
        ValidationHelper.requireNonEmpty(description, "Course Description");
        ValidationHelper.requirePositive(credits, "Credits");
        ValidationHelper.requirePositive(startDate, "Start Date");
    }

    public static void validateInstructor(String firstName, String lastName, int expertise) {
        ValidationHelper.requireNonEmpty(firstName, "First Name");
        ValidationHelper.requireNonEmpty(lastName, "Last Name");
        ValidationHelper.requirePositive(expertise, "Expertise");
    }

    public static void validateAssignment(String module, int dueData, int maxPoints) {
        ValidationHelper.requireNonEmpty(module, "Module Name");
        ValidationHelper.requirePositive(dueData, "Due Date");
        ValidationHelper.requirePositive(maxPoints, "Max Points");
    }

    public static void validateModule(String title, String content) {
        ValidationHelper.requireNonEmpty(title, "Module Title");
        ValidationHelper.requireNonEmpty(content, "Module Content");
    }
}
