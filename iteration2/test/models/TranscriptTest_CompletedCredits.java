package iteration2.test.models;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import iteration2.src.enums.CourseResult;
import iteration2.src.models.Course;
import iteration2.src.models.CourseGrade;
import iteration2.src.models.Transcript;

public class TranscriptTest_CompletedCredits {

    @Test
    public void calculateCompletedCredits() {
        Course course1 = new Course(5, 5, 1, "course1", "1", null, null, null);
        Course course2 = new Course(10, 10, 1, "course2", "2", null, null, null);
        Course course3 = new Course(7, 7 , 1, "course3", "3", null, null, null);
        
        CourseGrade courseGrade1 = new CourseGrade(course1, "AA", CourseResult.ACTIVE);
        CourseGrade courseGrade2 = new CourseGrade(course2, "BB", CourseResult.PASSED);
        CourseGrade courseGrade3 = new CourseGrade(course3, "CC", CourseResult.PASSED);

        List<CourseGrade> courseGrades = List.of(courseGrade1, courseGrade2, courseGrade3);

        Transcript transcript = new Transcript(courseGrades);
        double completedCredits = transcript.calculateCompletedCredits();
        
        assertEquals(17.0, completedCredits, 0.1);
    }
}