package models;
import java.util.*;

public class Course {
    private int courseCredit;
    private int courseECTS;
    private String courseName;
    private String courseCode;
    private Prerequisite prerequisiteInformation;
    private List<CourseSection> courseSections;
    private boolean isCompulsory;

    public Course() {
    }

    public Course(int courseCredit, int courseECTS, String courseName, String courseCode, Prerequisite prerequisiteInformation, List<CourseSection> courseSections, boolean isCompulsory) {
        this.courseCredit = courseCredit;
        this.courseECTS = courseECTS;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.prerequisiteInformation = prerequisiteInformation;
        this.courseSections = courseSections;
        this.isCompulsory = isCompulsory;
    }

    public boolean checkPrerequisite(Student student){
        if(prerequisiteInformation == null)
            return true;
        else if (prerequisiteInformation.getPrerequisiteOfCourses().isEmpty())
            return true;
        else
        return prerequisiteInformation.checkPrequisiteCoursePassed(student, this);
    }

    public List<CourseSection> acquireAvailableSections() {
        List<CourseSection> availableCourseSections = new ArrayList<>();
        for (CourseSection courseSection: courseSections) {
            if(courseSection.checkAvailibilty()){
                availableCourseSections.add(courseSection);
            }
        }
        return availableCourseSections;
    }

    public Prerequisite getPrerequisiteInformation() {
        return prerequisiteInformation;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCourseECTS() {
        return courseECTS;
    }

    public void setCourseECTS(int courseECTS) {
        this.courseECTS = courseECTS;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setPrerequisiteInformation(Prerequisite prerequisiteInformation) {
        this.prerequisiteInformation = prerequisiteInformation;
    }

    public List<CourseSection> getCourseSections() {
        return courseSections;
    }


    public boolean getIsCompulsory() {
        return isCompulsory;
    }


    

}