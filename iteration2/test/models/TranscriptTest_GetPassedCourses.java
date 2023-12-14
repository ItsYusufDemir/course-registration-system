package iteration2.test.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import iteration2.src.enums.CourseResult;
import iteration2.src.models.Course;
import iteration2.src.models.CourseGrade;
import iteration2.src.models.Transcript;

public class TranscriptTest_GetPassedCourses {

    @Test
    public void getPassedCourses() {
        Course course1 = new Course(5, 5, 1, "course1", "1", null, null, null);
        Course course2 = new Course(10, 10, 1, "course2", "2", null, null, null);
        Course course3 = new Course(7, 7 , 1, "course3", "3", null, null, null);
        
        CourseGrade courseGrade1 = new CourseGrade(course1, "FF", CourseResult.FAILED);
        CourseGrade courseGrade2 = new CourseGrade(course2, "BB", CourseResult.PASSED);
        CourseGrade courseGrade3 = new CourseGrade(course3, "CC", CourseResult.PASSED);

        List<CourseGrade> courseGrades = List.of(courseGrade1, courseGrade2, courseGrade3);

        Transcript transcript = new Transcript(courseGrades);
        List<Course> testCoursesPassed = transcript.acquirePassedCourses();
        
        assertFalse("Passed courses should contain course1", testCoursesPassed.contains(course1));
        assertTrue("Passed courses should contain course2", testCoursesPassed.contains(course2));
        assertTrue("Passed courses should contain course3", testCoursesPassed.contains(course3));
    }
}