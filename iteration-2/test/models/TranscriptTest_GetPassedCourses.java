package models;

import org.junit.Test;
import utils.DatabaseManager;

import java.util.List;

import static org.junit.Assert.*;

public class TranscriptTest_GetPassedCourses {

    @Test
    public void getPassedCourses() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Transcript transcript = databaseManager.getStudents().get(0).getTranscript();
        List<Course> testCoursesPassed =  transcript.getPassedCourses();
        System.out.println(testCoursesPassed);
    }
}