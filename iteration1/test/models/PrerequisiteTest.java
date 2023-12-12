package iteration1.test.models;

import iteration2.src.models.Course;
import iteration2.src.models.Prerequisite;
import iteration2.src.models.Student;
import iteration2.src.utils.DatabaseManager;

import static org.junit.Assert.*;

public class PrerequisiteTest {

    @org.junit.Test
    public void checkPrequisiteCoursePassed() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Student student =  databaseManager.getStudents().get(0);
        Course course = databaseManager.getCourses().get(0);

        Prerequisite prerequisiteTest = course.getPrerequisiteInformation();
        boolean isPassed = prerequisiteTest.checkPrerequisiteCoursePassed(student, course);
        System.out.println("isPassed -> " + isPassed );

        // assert true
        // assert false

    }
}