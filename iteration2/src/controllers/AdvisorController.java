package iteration2.src.controllers;

import java.util.ArrayList;
import java.util.List;

import iteration2.src.enums.ApprovalStatus;
import iteration2.src.models.Advisor;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;

public class AdvisorController {

    private Advisor currentAdvisor;
    private List<Student> studentList;

    public AdvisorController(Advisor currentAdvisor) {
        this.currentAdvisor = currentAdvisor;
        this.studentList = currentAdvisor.fetchAdvisedStudents();
    }

    public void approveCourse(Student student, SelectedCourse selectedCourse) {
        currentAdvisor.acceptCourse(student, selectedCourse);
    }

    public void denyCourse(Student student, SelectedCourse selectedCourse) {
        currentAdvisor.rejectCourse(student, selectedCourse);
        selectedCourse.getCourseSection().decrementStudentCount();
    }

    public List<Student> getStudentListOrderByStatus() {

        List<Student> studentListOrder = new ArrayList<Student>();

        for (Student student : studentList) {
            if (student.getApprovalStatus() == ApprovalStatus.PENDING) {
                studentListOrder.add(student);
            }
        }

        for (Student student : studentList) {
            if (student.getApprovalStatus() == ApprovalStatus.DONE) {
                studentListOrder.add(student);
            }
        }
        return studentListOrder;
    }

    public List<String> getNotification() {
        return currentAdvisor.getNotifications();
    }

    public void logOut() {
        currentAdvisor.logout();
    }

    public void clearNotifications() {
        currentAdvisor.clearNotifications();
    }

    // GETTER AND SETTER
    public Advisor getCurrentAdvisor() {
        return currentAdvisor;
    }

    public void setCurrentAdvisor(Advisor currentAdvisor) {
        this.currentAdvisor = currentAdvisor;
    }
}
