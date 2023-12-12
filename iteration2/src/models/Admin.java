package iteration2.src.models;

import java.util.HashMap;
import java.util.List;

import iteration2.src.utils.DatabaseManager;

public class Admin {

    public Admin() {
    }

    public List<Course> getCourseList() {
        return DatabaseManager.getInstance().getCourses();
    }

    public Constraint getConstraints() {
        return DatabaseManager.getInstance().getConstraints();
    }

    public Course createCourse(Course course) {
        getCourseList().add(course);
        DatabaseManager.getInstance().saveToDatabase();
        return course;
    }

    public Course createCourse(int courseCredit, int courseECTS, String courseName, String courseCode,
            Prerequisite prerequisiteInformation, List<CourseSection> courseSections, CourseType courseType) {
        Course course = new Course(courseCredit, courseECTS, courseName, courseCode, prerequisiteInformation,
                courseSections, courseType);
        getCourseList().add(course);
        return course;
    }

    public void editConstraint(HashMap<Integer, String> editedAttributes) {
        Constraint constraint = getConstraints();
        constraint.editConstraint(editedAttributes);
        DatabaseManager.getInstance().saveToDatabase();
    }

    public Course deleteCourse(Course course) {
        getCourseList().remove(course);
        DatabaseManager.getInstance().saveToDatabase();
        return course;
    }

    public Course findCourseByCourseCode(String courseCode) {
        return DatabaseManager.getInstance().findCourseByCourseCode(courseCode);
    }

}
