package iteration2.src.controllers;
import iteration2.src.models.Admin;
import iteration2.src.models.Course;
import java.util.HashMap;
import java.util.List;

public class AdminController {
    private Admin currentAdmin;

    public AdminController() {

    }

    public AdminController(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    public List<Course> getCourseList(){
        return currentAdmin.getCourseList();
    }

    public HashMap<Integer, String> getConstraints(){
        return currentAdmin.getConstraints();
    }

    public Course createCourse(Course course){
        return currentAdmin.createCourse(course);
    }

    public void editConstraint(HashMap<Integer, String> editedAttributes){
        currentAdmin.editConstraint(editedAttributes);
    }

    public boolean deleteCourse(Course course){
        return currentAdmin.deleteCourse(course);
    }

    public Course findCourseByCourseCode(String courseCode){
        return currentAdmin.findCourseByCourseCode(courseCode);
    }

}
