package CoursesClass;

import java.util.List;
import ua.util.ValidationHelper;
import ua.util.Utils;
import java.util.Objects;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private int enrollmentDate;

    private Student(String firstName,String lastName, String email,int enrollmentDate){
         Utils.validateStudent(firstName, lastName, email, enrollmentDate);

         this.firstName = firstName;
         this.lastName = lastName;
         this.email = email;
         this.enrollmentDate = enrollmentDate;
    }

    public static Student createStudent(String firstName,String lastName, String email,int enrollmentDate) {
            return new Student(firstName,lastName,email,enrollmentDate);
    }


    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public String getEmail() {return email;}

    public int getEnrollmentDate(){return enrollmentDate;}

    public List<String> getNameStudent(){ return List.of(firstName,lastName); }

    public String getStudentInfo() {
        return String.format("Name: %s %s, Email: %s, Enrollment: %d",
                firstName, lastName, email, enrollmentDate);
    }

    public void setFirstName(String firstName){
        ValidationHelper.requireNonEmpty(firstName, "First Name");
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        ValidationHelper.requireNonEmpty(lastName, "Last Name");
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        ValidationHelper.requireEmail(email);
        this.email = email;
    }

    public void setEnrollmentDate(int enrollmentDate) {
        ValidationHelper.requirePositive(enrollmentDate , "Enrollment date");
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString(){ return "Student { " + firstName + " " + lastName + ", Email: " + email + ", Date enrollment:" + enrollmentDate + ".}";}
    public String getFullName(){
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student other = (Student) obj;
        return Objects.equals(firstName, other.firstName) &&
                Objects.equals(lastName, other.lastName) &&
                Objects.equals(email, other.email) &&
                enrollmentDate == other.enrollmentDate;
    }


    @Override
    public int hashCode(){
        return Objects.hash(firstName,lastName,email,enrollmentDate);
    }

}