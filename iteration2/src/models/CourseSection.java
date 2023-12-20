package iteration2.src.models;

import iteration2.src.utils.DatabaseManager;
import iteration2.src.utils.Util;

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

    public CourseSection(int studentCapacity, String lecturerName, ArrayList<String> sectionTime,
            ArrayList<String> sectionDay, String classroom, String sectionCode) {
        this.studentCapacity = studentCapacity;
        this.lecturerName = lecturerName;
        this.sectionTime = sectionTime;
        this.sectionDay = sectionDay;
        this.classroom = classroom;
        this.sectionCode = sectionCode;
        this.studentCountInsideCourseSection = 0;
    }

    public boolean checkAvailibilty() {
        if (studentCapacity >= studentCountInsideCourseSection) {
            return true;
        } else {
            return false;
        }
    }

    public int incrementStudentCount() {
        studentCountInsideCourseSection++;
        Util.getLogger().info("Student count incremented. Current section count: " + studentCountInsideCourseSection);
        return studentCountInsideCourseSection;
    }

    public int decrementStudentCount() {
        studentCountInsideCourseSection--;
        Util.getLogger().info("Student count decremented. Current section count: " + studentCountInsideCourseSection);
        return studentCountInsideCourseSection;
    }

    public Course findCourseOfCourseSection() {
        List<Course> courses = DatabaseManager.getInstance().getCourses();
        for (Course course : courses) {

            String courseCode = this.getSectionCode().substring(0, this.getSectionCode().indexOf("."));
            if (course.getCourseCode().equals(courseCode)) {
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

    public void setStudentCapacity(int studentCapacity) {
        this.studentCapacity = studentCapacity;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public void setSectionTime(ArrayList<String> sectionTime) {
        this.sectionTime = sectionTime;
    }

    public void setSectionDay(ArrayList<String> sectionDay) {
        this.sectionDay = sectionDay;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public void setStudentCountInsideCourseSection(int studentCountInsideCourseSection) {
        this.studentCountInsideCourseSection = studentCountInsideCourseSection;
    }

}
