package CoursesClass;

import ua.util.ValidationHelper;
import ua.util.Utils;
import java.util.Objects;

public class Instructor {
    private String firstName;
    private String lastName;
    private int expertise;

    private Instructor(String firstName,String lastName,int expertise){
        Utils.validateInstructor(firstName, lastName, expertise);

        this.firstName = firstName;
        this.lastName = lastName;
        this.expertise = expertise;
    }

    public static Instructor createInstructor(String firstName, String lastName, int expertise){
            return new Instructor(firstName,lastName,expertise);
    }


    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public int getExpertise() { return expertise; }


    public void setFirstName(String firstName) {
        ValidationHelper.requireNonEmpty(firstName, "First Name");
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        ValidationHelper.requireNonEmpty(lastName, "Last Name");
        this.lastName = lastName;
    }

    public void setExpertise(int expertise) {
        ValidationHelper.requirePositive(expertise, "Expertise");
        this.expertise = expertise;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString(){
        return "Instructor { " + getFullName() + ", Expertise = " + expertise + ".}";
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Instructor other = (Instructor) obj;
        return Objects.equals(firstName, other.firstName) &&
                Objects.equals(lastName, other.lastName) &&
                expertise == other.expertise;
    }

    @Override
    public int hashCode(){
        return Objects.hash(lastName,firstName,expertise);
    }

}