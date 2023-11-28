package models;
import java.util.List;
import java.util.ArrayList;

public class CourseSection {

    private int studentCapacity;
    private String lecturerName;
    private String sectionTime;
    private String sectionDate;
    private String classroom;
    private String sectionCode;
    private int studentCountInsideCourseSection;

    public CourseSection() {
    }

    public CourseSection(int studentCapacity, String lecturerName, String sectionTime, String sectionDate, String classroom, String sectionCode) {
        this.studentCapacity = studentCapacity;
        this.lecturerName = lecturerName;
        this.sectionTime = sectionTime;
        this.sectionDate = sectionDate;
        this.classroom = classroom;
        this.sectionCode = sectionCode;
        this.studentCountInsideCourseSection = 0;
    }

    public boolean checkAvailibilty(){
        if(studentCapacity >= studentCountInsideCourseSection){
            return true;
        }
        else{
            return false;
        }
    }

    public int incrementStudentCount(){
        studentCountInsideCourseSection++;
        return studentCountInsideCourseSection;
    }

    public int decrementStudentCount(){
        studentCountInsideCourseSection--;
        return studentCountInsideCourseSection;
    }

    public int getStudentCapacity() {
        return studentCapacity;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getSectionTime() {
        return sectionTime;
    }

    public String getSectionDate() {
        return sectionDate;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getSectionCode() {
        return sectionCode;
    }


    public int getStudentCountInsideCourseSection() {
        return studentCountInsideCourseSection;
    }
}
