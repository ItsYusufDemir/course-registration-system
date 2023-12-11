package iteration2.test.controllers;


import iteration2.src.controllers.AdvisorController;
import iteration2.src.models.Advisor;
import iteration2.src.models.Student;
import iteration2.src.utils.DatabaseManager;
import org.junit.Test;

import java.util.List;


public class AdvisorControllerTest {

    @Test
    public void getStudentListOrderByStatus() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Advisor advisor = databaseManager.getAdvisors().get(0);
        AdvisorController advisorController = new AdvisorController(advisor);
        List<Student> studentList = databaseManager.fetchAdvisedStudents(advisor);
        List<Student> sortedStudentList = advisorController.getStudentList();

        for (Student student: studentList) {
            System.out.println(student.getFirstName());
        }

        System.out.println("Sorted List");

        for (Student student: sortedStudentList) {
            System.out.println(student.getFirstName());
        }
    }
}