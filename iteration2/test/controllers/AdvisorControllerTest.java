package controllers;

import models.Advisor;
import models.Student;
import org.junit.Test;
import utils.DatabaseManager;

import java.util.List;

import static org.junit.Assert.*;

public class AdvisorControllerTest {

    @Test
    public void getStudentListOrderByStatus() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Advisor advisor = databaseManager.getAdvisors().get(0);
        AdvisorController advisorController = new AdvisorController(advisor);
        List<Student> studentList = databaseManager.fetchAdvisedStudents(advisor);
        List<Student> sortedStudentList = advisorController.getStudentListOrderByStatus();

        for (Student student: studentList) {
            System.out.println(student.getFirstName());
        }

        System.out.println("Sorted List");

        for (Student student: sortedStudentList) {
            System.out.println(student.getFirstName());
        }
    }
}