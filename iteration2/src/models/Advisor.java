package iteration2.src.models;
import java.util.ArrayList;
import java.util.List;

import iteration2.src.CommandLineInterface.CLIAdvisor;
import iteration2.src.enums.ApprovalStatus;
import iteration2.src.enums.CourseResult;
import iteration2.src.enums.CourseStatus;
import iteration2.src.models.User;
import iteration2.src.utils.DatabaseManager;

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

      public List<Student> getStudentListOrderByStatus() {

        List<Student> studentList = fetchAdvisedStudents();
        List<Student> studentListOrder = new ArrayList<Student>();
        
        for (Student student :studentList) {
            if(student.getApprovalStatus() == ApprovalStatus.PENDING) {
                studentListOrder.add(student);
            }
        }

       for (Student student :studentList) {
           if(student.getApprovalStatus() == ApprovalStatus.DONE) {
               studentListOrder.add(student);
           }
       }
        return studentListOrder;
    }
}



