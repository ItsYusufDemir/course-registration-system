package iteration2.src.controllers;

import java.util.ArrayList;
import java.util.List;

import iteration2.src.enums.CourseStatus;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;
import iteration2.src.enums.ApprovalStatus;

public class StudentController {
    private Student currentStudent;

    public StudentController(Student currentStudent) {
        // we may create student here
        this.currentStudent = currentStudent;
    }

    public void addSelectedCourse(SelectedCourse selectedCourse) {
        currentStudent.addNewCourse(selectedCourse);
    }

    public void removeSelectedCourse(SelectedCourse selectedCourse) {
        currentStudent.deleteCourse(selectedCourse);
    }

    public void sendSelectedCoursesToApproval() {
        currentStudent.setApprovalStatus(ApprovalStatus.PENDING);
        List<SelectedCourse> selectedCourses = currentStudent.getSelectedCourses();
        for (SelectedCourse selectedCourse : selectedCourses) {
            if (selectedCourse.getStatus() == CourseStatus.DRAFT) {
                selectedCourse.setStatus(CourseStatus.PENDING);
                selectedCourse.getCourseSection().incrementStudentCount();

            }
        }
        currentStudent.setSelectedCourses(selectedCourses);
    }
}