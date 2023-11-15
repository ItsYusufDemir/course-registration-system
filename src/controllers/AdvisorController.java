package controllers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import enums.ApprovalStatus;
import models.Advisor;
import models.SelectedCourse;
import models.Student;
import utils.DatabaseManager;

public class AdvisorController {

    private Advisor currentAdvisor;
    private Student currentStudent;
    private List<Student> studentList;
    private List<Student> approvelOfStudents;



    public AdvisorController(Advisor currentAdvisor) {
        this.currentAdvisor = currentAdvisor;
        this.studentList = currentAdvisor.getAdvisedStudent();
    }

    /* gerek var mı
    private void setStudentList() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        this.studentList = databaseManager.getStudentsOfAdvisor(currentAdvisor.getUserId());
    }
    */


    public void approveCourse(Student student, SelectedCourse selectedCourse) {
        currentAdvisor.acceptCourse(student, selectedCourse);
    }

    public void denyCourse(Student student, SelectedCourse selectedCourse) {
        currentAdvisor.rejectCourse(student, selectedCourse);
    }


   public List<Student> getStudentListOrderByStatus() {

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

    /*private List<Student> sortStudentListForName(List<Student> studentList) {
        return Collections.sort(studentList, Comparator.comparing(Student::getFirstName).thenComparing(Student::getLastName));
    }*/






    // GETTER AND SETTER
    public Advisor getCurrentAdvisor() {
        return currentAdvisor;
    }

    public void setCurrentAdvisor(Advisor currentAdvisor) {
        this.currentAdvisor = currentAdvisor;
    }
}
