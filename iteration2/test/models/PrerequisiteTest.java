package iteration2.test.models;

import iteration2.src.enums.CourseResult;
import iteration2.src.models.Course;
import iteration2.src.models.CourseGrade;
import iteration2.src.models.Prerequisite;
import iteration2.src.models.Student;
import iteration2.src.models.Transcript;
import iteration2.src.utils.DatabaseManager;
import static org.junit.Assert.*;

import java.util.List;

import iteration2.src.enums.ApprovalStatus;
import iteration2.src.models.SelectedCourse;

public class PrerequisiteTest {

    @org.junit.Test
    public void checkPrequisiteCoursePassed() {

        Course course1 = new Course(5, 5, 1, "course1", "1", null, null, null);
        Course course4 = new Course(5, 5, 1, "course4", "4", null, null, null);
        
        List<Course> prerequisiteOfCourses2 = List.of(course1);
        Prerequisite prerequisite2 = new Prerequisite(prerequisiteOfCourses2);
        
        List<Course> prerequisiteOfCourses4 = List.of(course4);
        Prerequisite prerequisite4 = new Prerequisite(prerequisiteOfCourses4);
        
        Course course2 = new Course(10, 10, 1, "course2", "2", prerequisite2, null, null);
        Course course3 = new Course(7, 7 , 1, "course3", "3", prerequisite4, null, null);
        

        CourseGrade courseGrade1 = new CourseGrade(course1, "AA", CourseResult.PASSED);
        CourseGrade courseGrade2 = new CourseGrade(course2, "BB", CourseResult.PASSED);
        CourseGrade courseGrade3 = new CourseGrade(course3, "CC", CourseResult.PASSED);

        List<CourseGrade> courseGrades = List.of(courseGrade1, courseGrade2, courseGrade3);

        Transcript transcript = new Transcript(courseGrades);

        SelectedCourse selectedCourse1 = new SelectedCourse(course1, null);
        SelectedCourse selectedCourse2 = new SelectedCourse(course2, null);
        SelectedCourse selectedCourse3 = new SelectedCourse(course3, null);
        SelectedCourse selectedCourse4 = new SelectedCourse(course4, null);

        List<SelectedCourse> selectedCourses = List.of(selectedCourse1, selectedCourse2, selectedCourse3);

        Student student = new Student("1", "1", "eren", "duyuk", false, "a", "1", selectedCourses, null, null, transcript);

        
        assertTrue("Prerequisite should be passed", course2.checkPrerequisite(student));
        assertFalse("Prerequisite should not be passed", course3.checkPrerequisite(student));         

    }

}