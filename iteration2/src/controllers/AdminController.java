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

    public List<Student> getStudentList(){
        return currentAdmin.getStudentList();
    }

    public List<Advisor> getAdvisorList(){
        return currentAdmin.getAdvisorList();
    }

    public List<Course> getCourseList(){
        return currentAdmin.getCourseList();
    }

    public Student createStudentList(){
        return currentAdmin.createStudentList();
    }

    public Advisor createAdvisorList(){
        return currentAdmin.createAdvisorList();
    }

    public Course createCourseList(){
        return currentAdmin.createCourseList();
    }

    public void editConstraint(HashMap<Integer, String> editedAttributes){
        currentAdmin.editConstraint(editedAttributes);
    }

    public Student editStudent(Student student,HashMap<Integer, String> editedAttributes){
        return currentAdmin.editStudent(student, editedAttributes);
    }

    public Advisor editAdvisor(Advisor advisor,HashMap<Integer, String> editedAttributes){
        return currentAdmin.editAdvisor(advisor, editedAttributes);
    }
    public Course editCourse(Course course,HashMap<Integer, String> editedAttributes){
        return currentAdmin.editCourse(course, editedAttributes);
    }


    public boolean deleteStudentList(Student student){
        return currentAdmin.deleteStudentList();
    }

    public boolean deleteAdvisorList(Advisor advisor){
        return currentAdmin.deleteAdvisorList();
    }

    public boolean createCourseList(Course course){
        return currentAdmin.deleteCourseList();
    }
}
