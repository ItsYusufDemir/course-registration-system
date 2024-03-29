package iteration1.src.controllers;

import java.util.ArrayList;
import java.util.List;

import iteration1.src.enums.CourseStatus;
import iteration1.src.models.SelectedCourse;
import iteration1.src.models.Student;
import iteration1.src.enums.ApprovalStatus;

public class StudentController{
    private Student currentStudent;

    public StudentController(Student currentStudent) {
        // we may create student here
        this.currentStudent = currentStudent;
    }

    public void addSelectedCourse(SelectedCourse selectedCourse){
        currentStudent.addNewCourse(selectedCourse);
    }
    public void removeSelectedCourse(SelectedCourse selectedCourse){
        currentStudent.deleteCourse(selectedCourse);
    }
    public void sendSelectedCoursesToApproval(){ // DRAFT TAN PENDİNG E COURSE STATUS U CEKMEK LAZIM
        currentStudent.setApprovalStatus(ApprovalStatus.PENDING);
        List<SelectedCourse> selectedCourses =  currentStudent.getSelectedCourses();
        for (SelectedCourse selectedCourse : selectedCourses) {
            selectedCourse.setStatus(CourseStatus.PENDING);
            selectedCourse.getCourseSection().incrementStudentCount();
        }
        currentStudent.setSelectedCourses(selectedCourses);
    }
}