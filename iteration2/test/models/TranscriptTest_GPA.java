package iteration2.test.models;

import static org.junit.Assert.assertEquals;

import iteration2.src.models.Transcript;
import iteration2.src.utils.DatabaseManager;

public class TranscriptTest_GPA {

    @org.junit.Test
    public void calculateGPA() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Transcript transcript = databaseManager.getStudents().get(0).getTranscript();
        double gpa = transcript.calculateGPA();
        
        assertEquals("GPA should match the expected value", 3.0, gpa, 0.01);
    }

}