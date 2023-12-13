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
        return null;
    }

    public List<SelectedCourse> getSelectedCourses() {
        return null;
    }

    public List<String> getNotifications() {
        return null;
    }

	public List<CourseSection> listAvailableCourseSections() {
		return null;
	}

    
}