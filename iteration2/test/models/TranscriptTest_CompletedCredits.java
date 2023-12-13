package iteration2.test.models;
import iteration2.src.models.Transcript;
import iteration2.src.utils.DatabaseManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class TranscriptTest_CompletedCredits {

    @Test
    public void calculateCompletedCredits() {
    DatabaseManager databaseManager = DatabaseManager.getInstance();
        Transcript transcript = databaseManager.getStudents().get(0).getTranscript();
        double completedCredits = transcript.calculateCompletedCredits();
        
        assertEquals(3.0, completedCredits, 0.0);
    }
}