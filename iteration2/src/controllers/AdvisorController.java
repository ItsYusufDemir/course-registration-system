package iteration2.src.controllers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import iteration2.src.enums.ApprovalStatus;
import iteration2.src.models.Advisor;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;
import iteration2.src.utils.DatabaseManager;

public class AdvisorController {

    private Advisor currentAdvisor;
    
    


    public AdvisorController(Advisor currentAdvisor) {
        this.currentAdvisor = currentAdvisor;
    }


    public void approveCourse(Student student, SelectedCourse selectedCourse) {
        currentAdvisor.acceptCourse(student, selectedCourse);
    }

    public void denyCourse(Student student, SelectedCourse selectedCourse) {
        currentAdvisor.rejectCourse(student, selectedCourse);
        selectedCourse.getCourseSection().decrementStudentCount();
    }


    public void logOut(){
        currentAdvisor.logout();
    }

    public List<Student> getStudentList() {
       return currentAdvisor.getStudentListOrderByStatus();
    }


    // GETTER AND SETTER
    public Advisor getCurrentAdvisor() {
        return currentAdvisor;
    }

    public void setCurrentAdvisor(Advisor currentAdvisor) {
        this.currentAdvisor = currentAdvisor;
    }
}
