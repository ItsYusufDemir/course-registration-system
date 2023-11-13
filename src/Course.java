import java.util.*;

public class Course {
    private int courseCredit;
    private int courseECTS;
    private String courseName;
    private String getCourseName;
    private Prerequisite prerequisiteInformation;
    private List<CourseSection> courseSections;
    private boolean isCompulsory;

    public Course(int courseCredit, int courseECTS, String courseName, String getCourseName, Prerequisite prerequisiteInformation, List<CourseSection> courseSections, boolean isCompulsory) {
        this.courseCredit = courseCredit;
        this.courseECTS = courseECTS;
        this.courseName = courseName;
        this.getCourseName = getCourseName;
        this.prerequisiteInformation = prerequisiteInformation;
        this.courseSections = courseSections;
        this.isCompulsory = isCompulsory;
    }

    public String courseInfo(){
        return "";
    }

    public Prerequisite getPrerequisiteInformation() {
        return prerequisiteInformation;
    }

}