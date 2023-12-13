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

    public HashMap<Integer,String> getConstraints() {
        return DatabaseManager.getInstance().getConstraints();
    }

    public Course createCourse(Course course) {
        getCourseList().add(course);
        DatabaseManager.getInstance().saveToDatabase();
        return course;
    }


    public boolean editConstraint(HashMap<Integer, String> editedAttributes) {
        boolean ret = DatabaseManager.getInstance().editConstraint(editedAttributes);
        DatabaseManager.getInstance().saveToDatabase();
        return ret;
    }

    public boolean deleteCourse(Course course) {
        boolean ret = getCourseList().remove(course);
        DatabaseManager.getInstance().saveToDatabase();
        return ret;
    }


    public Course findCourseByCourseCode(String courseCode) {
        return DatabaseManager.getInstance().findCourseByCourseCode(courseCode);
    }

   

}
