package iteration2.src.models;
import iteration2.src.utils.DatabaseManager;

import java.util.List;
import java.util.ArrayList;

public class CourseSection {

    private int studentCapacity;
    private String lecturerName;
    private ArrayList<String> sectionTime; // [8:00, 9:00, 8:00]
    private ArrayList<String> sectionDay; // [Monday, Monday, Tuesday]
    private String classroom;
    private String sectionCode;
    private int studentCountInsideCourseSection;

    public CourseSection() {
    }

    public CourseSection(int studentCapacity, String lecturerName, ArrayList<String> sectionTime, ArrayList<String> sectionDay, String classroom, String sectionCode) {
        this.studentCapacity = studentCapacity;
        this.lecturerName = lecturerName;
        this.sectionTime = sectionTime;
        this.sectionDay = sectionDay;
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

    public Course findCourseOfCourseSection(){
        List<Course> courses = DatabaseManager.getInstance().getCourses();
        for (Course course : courses) {
            if(course.getCourseCode().equals(this.getSectionCode().split(".")[0])){
                return course;
            }
        }
        return null;
    }


    public int getStudentCapacity() {
        return studentCapacity;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public ArrayList<String> getSectionTime() {
        return sectionTime;
    }

    public ArrayList<String> getSectionDate() {
        return sectionDay;
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

    public ArrayList<String> getSectionDay() {
        return sectionDay;
    }

    
}
