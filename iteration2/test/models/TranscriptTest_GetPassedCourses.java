package iteration2.test.models;

import iteration2.src.models.Course;
import iteration2.src.models.Transcript;
import iteration2.src.utils.DatabaseManager;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class TranscriptTest_GetPassedCourses {

    @Test
    public void getPassedCourses() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Transcript transcript = databaseManager.getStudents().get(0).getTranscript();
        List<Course> testCoursesPassed =  transcript.acquirePassedCourses();
        System.out.println(testCoursesPassed);
    }
}