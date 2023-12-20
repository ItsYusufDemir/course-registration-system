package iteration2.src.models;

import java.util.List;

import iteration2.src.CommandLineInterface.CLIAdvisor;
import iteration2.src.enums.ApprovalStatus;
import iteration2.src.enums.CourseResult;
import iteration2.src.enums.CourseStatus;
import iteration2.src.interfaces.Color;
import iteration2.src.utils.DatabaseManager;
import iteration2.src.utils.Util;

public class Advisor extends User {

    public Advisor() {

    }

    public Advisor(String userID, String userName, String password, String firstName, String lastName, boolean status) {
        super(userID, password, firstName, lastName, status);
    }

    public void getMyPage() {
        CLIAdvisor cliAdviser = new CLIAdvisor(this);
        cliAdviser.menuPage();
    }

    public void acceptCourse(Student student, SelectedCourse selectedCourse) {

        if (selectedCourse.getStatus() != CourseStatus.PENDING) {
            Util.sendFeedback("This course is not pending for approval.", Color.RED);
            Util.getLogger().warning(this.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                    + " is not pending for approval.");
            return;
        }

        selectedCourse.setStatus(CourseStatus.APPROVED);

        CourseGrade newCourse = new CourseGrade(selectedCourse.getCourse(), null, CourseResult.ACTIVE);

        student.getTranscript().getTakenCourses().add(newCourse);

        Util.getLogger().info(this.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                + " is approved for student: " + student.getUserId());

        // sadece pending olanların size ı 0 ise
        for (SelectedCourse course : student.getSelectedCourses()) {
            if (course.getStatus() == CourseStatus.PENDING) {
                return;
            }
        }

        student.setApprovalStatus(ApprovalStatus.DONE);
        Util.getLogger().info(this.getUserId() + " - Student: " + student.getUserId() + " is approved.");

        DatabaseManager.getInstance().saveToDatabase();
    }

    public void rejectCourse(Student student, SelectedCourse selectedCourse) {

        if (selectedCourse.getStatus() != CourseStatus.PENDING) {
            Util.sendFeedback("This course is not pending for approval.", Color.RED);
            return;
        }

        selectedCourse.setStatus(CourseStatus.DENIED);
        String notification = "Your " + selectedCourse.getCourse().getCourseName() + " is rejected.";
        setNotificationToStudent(student, notification);
        Util.getLogger().info(this.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                + " is rejected for student: " + student.getUserId());
        // sadece pending olanların size ı 0 ise
        for (SelectedCourse course : student.getSelectedCourses()) {
            if (course.getStatus() == CourseStatus.PENDING) {
                return;
            }
        }

        Util.getLogger().info(this.getUserId() + " - Student: " + student.getUserId() + " is approved.");
        student.setApprovalStatus(ApprovalStatus.DONE);

        DatabaseManager.getInstance().saveToDatabase();
    }

    public List<Student> fetchAdvisedStudents() {
        return DatabaseManager.getInstance().fetchAdvisedStudents(this);
    }

    public void setNotificationToStudent(Student student, String notification) {
        student.addNotification(notification);

        DatabaseManager.getInstance().saveToDatabase();
    }
}
