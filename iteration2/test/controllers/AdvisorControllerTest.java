package iteration2.test.controllers;

import iteration2.src.controllers.AdvisorController;
import iteration2.src.models.Advisor;
import iteration2.src.models.Student;
import iteration2.src.utils.DatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AdvisorControllerTest {

    private DatabaseManager databaseManager;
    private AdvisorController advisorController;

    @Before
    public void setUp() {
        databaseManager = DatabaseManager.getInstance();
        Advisor advisor = databaseManager.getAdvisors().get(0);
        advisorController = new AdvisorController(advisor);
    }

    @org.junit.Test
    public void shouldReturnStudentListOrderedByStatus() {
        List<Student> studentList = databaseManager.fetchAdvisedStudents(advisorController.getCurrentAdvisor());
        List<Student> sortedStudentList = advisorController.getStudentListOrderByStatus();
        assertStudentListOrderedByStatus(studentList, sortedStudentList);
    }

    private void assertStudentListOrderedByStatus(List<Student> originalList, List<Student> sortedList) {:
        for (int i = 1; i < sortedList.size(); i++) {
            assertEquals("Student list is not ordered by status", 
                         originalList.get(i - 1).getApprovalStatus(), 
                         sortedList.get(i).getApprovalStatus());
        }
    }
}
