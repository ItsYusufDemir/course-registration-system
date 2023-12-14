package iteration2.test.models;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import iteration2.src.models.CourseGrade;
import iteration2.src.models.Transcript;
import iteration2.src.enums.CourseResult;
import iteration2.src.enums.CourseStatus;
import iteration2.src.models.Advisor;
import iteration2.src.models.Course;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;


public class CourseGrade_convertLetterGradeToScore {

    @Test
    public void convertLetterGradeToScore() {
        Course course1 = new Course(5, 5, 1, "course1", "1", null, null, null);
       CourseGrade courseGrade1 = new CourseGrade(course1, "BA", CourseResult.PASSED);

        double score = courseGrade1.convertLetterGradeToScore();
        
        assertTrue("Score should match the expected value", 3.5 == score);
    }
    
}
