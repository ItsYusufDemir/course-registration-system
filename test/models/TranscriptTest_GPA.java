package models;

import utils.DatabaseManager;

public class TranscriptTest_GPA {

    @org.junit.Test
    public void calculateGPA() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Transcript transcript = databaseManager.getStudents().get(0).getTranscript();
        double gpa = transcript.calculateGPA();
        System.out.println(gpa);
    }

}