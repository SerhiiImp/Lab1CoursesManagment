import CoursesClass.*;

public class Main {
    public static void main(String[] args) {
        Student Serhii = Student.createStudent("Serhii","Kitsul","tochka@email.com",2023);
        System.out.println(Serhii);

        Assignment Javalb1 = Assignment.createAssignment("Java Lab1", 3009 , 10);
        System.out.println(Javalb1);

        Course JavaCourse = Course.createCourse("Java Course", "Java Developer", 3 , 2025);
        System.out.println(JavaCourse);

        CoursesClass.Module ModuleJava1 = CoursesClass.Module.createModule("Module lab1-2-3-4", "Plan learning");
        System.out.println(ModuleJava1);

        Instructor Volodya = Instructor.createInstructor("Volodya", "Abramovich", 15);
        System.out.println(Volodya);


    }
}