import java.util.*;

public class Course {
    private int courseCredit;
    private int courseECTS;
    private String courseName;
    private String courseCode;
    private Prerequisite prerequisiteInformation;
    private List<CourseSection> courseSections;
    private boolean isCompulsory;

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
        return prerequisiteInformation.checkPrequisiteCoursePassed(student, this);
    }

    public List<CourseSection> getAvailableSections() {
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

}