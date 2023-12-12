package iteration2.src.controllers;
import java.util.List;
import iteration2.src.enums.CourseStatus;
import iteration2.src.models.Course;
import iteration2.src.models.CourseSection;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;
import iteration2.src.enums.ApprovalStatus;

public class StudentController {
    private Student currentStudent;

    public StudentController(Student currentStudent) {
        this.currentStudent = currentStudent;
    }


    public void addSelectedCourse(SelectedCourse selectedCourse){
        currentStudent.addNewCourse(selectedCourse);
    }

    public List<CourseSection> getAvailableCourseSections(){
        return currentStudent.listAvailableCourses();
    }

    public void removeSelectedCourse(SelectedCourse selectedCourse){
        currentStudent.deleteCourse(selectedCourse);
    }

    public void sendSelectedCoursesToApproval(){
        currentStudent.sendSelectedCoursesToApproval();
    }

    public String getNotification(){
        return "";
    }
    public String getTimeTable(){
        return "";
    }
    public void logout(){
        System.exit(0);
    }
}