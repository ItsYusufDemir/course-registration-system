package controllers;

import java.util.ArrayList;
import java.util.List;

import enums.CourseStatus;
import models.SelectedCourse;
import models.Student;
import enums.ApprovalStatus;

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
        }
        currentStudent.setSelectedCourses(selectedCourses);
    }
}