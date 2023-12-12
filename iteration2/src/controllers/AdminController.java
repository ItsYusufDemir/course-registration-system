package iteration2.src.controllers;
import iteration2.src.models.Admin;
import iteration2.src.models.Advisor;
import iteration2.src.models.Course;
import iteration2.src.models.Student;
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

    public Constraint getConstraint(){
        return currentAdmin.getConstraint();
    }

    public Course createCourse(Course course){
        return currentAdmin.createCourseList();
    }

    public void editConstraint(HashMap<Integer, String> editedAttributes){
        currentAdmin.editConstraint(editedAttributes);
    }

    public boolean deleteCourse(Course course){
        return currentAdmin.deleteCourse(course);
    }
}
