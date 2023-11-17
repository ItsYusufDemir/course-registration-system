package models;
import java.util.List;

import CommandLineInterface.CLIAdvisor;
import enums.ApprovalStatus;
import enums.CourseResult;
import enums.CourseStatus;
import models.User;
import utils.DatabaseManager;

public class Advisor extends User {


    public Advisor() {
        
    }


    public Advisor(String userID, String userName , String password, String firstName, String lastName, boolean status) {
        super(userID, password, firstName, lastName, status);
    }

    public void getMyPage() {
        CLIAdvisor cliAdviser = new CLIAdvisor(this);
        cliAdviser.menuPage();
    }


    public void acceptCourse(Student student, SelectedCourse selectedCourse) {

        if(selectedCourse.getStatus() != CourseStatus.PENDING) {
            System.out.println("This course is not pending for approval.");
            return;
        }

        selectedCourse.setStatus(CourseStatus.APPROVED);

        CourseGrade newCourse = new CourseGrade(selectedCourse.getCourse(), null, CourseResult.ACTIVE);

        student.getTranscript().getTakenCourses().add(newCourse);
        

        if(student.getSelectedCourses().size() == 0) {
            student.setApprovalStatus(ApprovalStatus.DONE);
        }
    }

    public void rejectCourse(Student student, SelectedCourse selectedCourse) {
        selectedCourse.setStatus(CourseStatus.DENIED);
        student.getSelectedCourses().remove(selectedCourse);

        if(student.getSelectedCourses().size() == 0) {
            student.setApprovalStatus(ApprovalStatus.DONE);
        }
    }

    public List<Student> fetchAdvisedStudents() {
        return DatabaseManager.getInstance().fetchAdvisedStudents(this);
    }

}

