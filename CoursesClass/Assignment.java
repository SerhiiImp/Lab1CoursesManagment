package CoursesClass;

import ua.util.ValidationHelper;
import ua.util.Utils;
import java.util.Objects;

public class Assignment {
    private String module;
    private int dueData;
    private int maxPoints;

    private Assignment(String module,int dueData,int maxPoints){
        Utils.validateAssignment(module,dueData,maxPoints);
        this.module = module;
        this.dueData = dueData;
        this.maxPoints = maxPoints;
    }

    public static Assignment createAssignment(String module,int dueData,int maxPoints){
        return new Assignment(module, dueData, maxPoints);
    }
    //--Гетери--

    public String getModule(){ return module;}

    public int getDueData(){ return dueData;}

    public int getMaxPoints(){ return maxPoints;}

    //--Сетери--

    public void setModule(String module){
        ValidationHelper.requireNonEmpty(module, "Module Name");
        this.module = module;
    }

    public void setDueData(int dueData){
        ValidationHelper.requirePositive(dueData, "Due Date");
        this.dueData = dueData;
    }

    public void setMaxPoints(int maxPoints){
        ValidationHelper.requirePositive(maxPoints, "Max Points");
        this.maxPoints = maxPoints;
    }

    @Override
    public String toString(){
        return "Assignment{ Module =\'" + module + "\', Due data = \'" + dueData + "\', Max points =\'" + maxPoints + "\'. }";
    }


    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Assignment other = (Assignment) obj;
        return Objects.equals( module, other.module);
    }

    @Override
    public int hashCode(){
        return Objects.hash(module, dueData, maxPoints);
    }
}