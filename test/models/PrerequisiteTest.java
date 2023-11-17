package models;

import org.testng.annotations.Test;
import utils.DatabaseManager;

import static org.junit.Assert.*;

public class PrerequisiteTest {

    @org.junit.Test
    public void checkPrequisiteCoursePassed() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Student student =  databaseManager.getStudents().get(0);
        Course course = databaseManager.getCourses().get(0);

        Prerequisite prerequisiteTest = course.getPrerequisiteInformation();
        boolean isPassed = prerequisiteTest.checkPrequisiteCoursePassed(student, course);
        System.out.println("isPassed -> " + isPassed );

        // assert true
        // assert false

    }
}