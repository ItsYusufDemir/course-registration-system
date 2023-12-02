package models;

import org.junit.Test;
import utils.DatabaseManager;

import static org.junit.Assert.*;

public class TranscriptTest_CompletedCredits {

    @Test
    public void calculateCompletedCredits() {
    DatabaseManager databaseManager = DatabaseManager.getInstance();
        Transcript transcript = databaseManager.getStudents().get(0).getTranscript();
        double completedCredits = transcript.calculateCompletedCredits();
        System.out.println(completedCredits);
    }
}