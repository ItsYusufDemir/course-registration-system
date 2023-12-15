package iteration2.src.models;
import java.util.List;

import javax.xml.crypto.Data;

import iteration2.src.CommandLineInterface.CLIAdvisor;
import iteration2.src.enums.ApprovalStatus;
import iteration2.src.enums.Color;
import iteration2.src.enums.CourseResult;
import iteration2.src.enums.CourseStatus;
import iteration2.src.utils.DatabaseManager;
import iteration2.src.utils.Util;

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
            Util.sendFeedback("This course is not pending for approval.", Color.RED);
            return;
        }

        selectedCourse.setStatus(CourseStatus.APPROVED);

        CourseGrade newCourse = new CourseGrade(selectedCourse.getCourse(), null, CourseResult.ACTIVE);

        student.getTranscript().getTakenCourses().add(newCourse);
        

        //sadece pending olanların size ı 0 ise
        for(SelectedCourse course : student.getSelectedCourses()) {
            if(course.getStatus() == CourseStatus.PENDING) {
                return;
            }
        }

        student.setApprovalStatus(ApprovalStatus.DONE);   
        
        DatabaseManager.getInstance().saveToDatabase();
    }

    public void rejectCourse(Student student, SelectedCourse selectedCourse) {

        if(selectedCourse.getStatus() != CourseStatus.PENDING) {
            Util.sendFeedback("This course is not pending for approval.", Color.RED);
            return;
        }

        selectedCourse.setStatus(CourseStatus.DENIED);
        
        
        String notification = "Your " + selectedCourse.getCourse().getCourseName() + " is rejected.";
        setNotificationToStudent(student, notification);


        //sadece pending olanların size ı 0 ise
        for(SelectedCourse course : student.getSelectedCourses()) {
            if(course.getStatus() == CourseStatus.PENDING) {
                return;
            }
        }

        student.setApprovalStatus(ApprovalStatus.DONE);   
        

        DatabaseManager.getInstance().saveToDatabase();
    }

    public List<Student> fetchAdvisedStudents() {
        return DatabaseManager.getInstance().fetchAdvisedStudents(this);
    }

    public void setNotificationToStudent(Student student, String notification ) {
        student.addNotification(notification);

        DatabaseManager.getInstance().saveToDatabase();
    }
}

