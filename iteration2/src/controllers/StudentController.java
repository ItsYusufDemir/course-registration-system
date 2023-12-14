package iteration2.src.controllers;

import java.util.List;
import iteration2.src.models.CourseSection;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;

public class StudentController {
    private Student currentStudent;

    public StudentController(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public boolean addSelectedCourse(SelectedCourse selectedCourse) {

        return currentStudent.addNewCourse(selectedCourse);
    }

    public List<CourseSection> getAvailableCourseSections() {
        return currentStudent.listAvailableCourseSections();
    }

    public boolean removeSelectedCourse(SelectedCourse selectedCourse) {
        return currentStudent.deleteCourse(selectedCourse);
    }

    public void sendSelectedCoursesToApproval() {
        currentStudent.sendSelectedCoursesToApproval();
    }

    public List<String> getNotification() {
        return currentStudent.getNotifications();
    }

    public String[][] getTimeTable() {
        return currentStudent.createTimeTable();
    }

    public void logout() {
        System.exit(0);
    }

    public List<CourseSection> getAvaliableCourseSections() {
        return currentStudent.listAvailableCourseSections();
    }

    public List<SelectedCourse> getSelectedCourses() {
        
        return currentStudent.getSelectedCourses();
    }

    public List<String> getNotifications() {
        return currentStudent.getNotifications();
    }

	

    
}