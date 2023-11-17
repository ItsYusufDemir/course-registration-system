package models;

import utils.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TranscriptTest {

    @org.junit.Test
    public void calculateGPA() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Transcript transcript = databaseManager.getStudents().get(0).getTranscript();
        double gpa = transcript.calculateGPA();
        System.out.println(gpa);
    }

}