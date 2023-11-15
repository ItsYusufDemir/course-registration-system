package models;
import java.util.List;

import CommandLineInterface.CLIAdvisor;
import enums.ApprovalStatus;
import enums.CourseStatus;

public class Advisor extends User {

    private List<Student> advisedStudent;


    public Advisor(String userID, String userName , String password, String firstName, String lastName, boolean status) {
        super(userID, password, firstName, lastName, status);
    }

    public void getMyPage() {
        CLIAdvisor cliAdviser = new CLIAdvisor(this);
        cliAdviser.menuPage();
    }
    public void acceptCourse(Student student, SelectedCourse selectedCourse) {

        selectedCourse.setStatus(CourseStatus.APPROVED);

        student.getTranscript().completedCourses.add(selectedCourse);
        student.getSelectedCourses().remove(selectedCourse);

        if(student.getSelectCourse().length == 0) {
            student.setAprovalStatus(ApprovalStatus.DONE);
        }
    }

    public void rejectCourse(Student student, SelectedCourse selectedCourse) {
        selectedCourse.setStatus(CourseStatus.Denied);
        student.getSelectedCourses.remove(selectedCourse);

        if(student.getSelectCourse().length == 0) {
            student.setAprovalStatus(ApprovalStatus.Done);
        }
    }

    public List<Student> getAdvisedStudent() {
        return advisedStudent;
    }

    public void setAdvisedStudent(List<Student> advisedStudent) {
        this.advisedStudent = advisedStudent;
    }
}

