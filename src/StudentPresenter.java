import java.util.ArrayList;
import java.util.List;

public class StudentPresenter{
    private Student currentStudent;

    public StudentPresenter(Student currentStudent) {
        // we may create student here
        this.currentStudent = currentStudent;
    }

    public void addSelectedCourse(SelectedCourse selectedCourse){
        currentStudent.addNewCourse(selectedCourse);
    }
    public void removeSelectedCourse(SelectedCourse selectedCourse){
        currentStudent.deleteCourse(selectedCourse);
    }
    public void sendSelectedCoursesToApproval(){ // DRAFT TAN PENDİNG E COURSE STATUS U CEKMEK LAZIM
        currentStudent.setApprovalStatus(ApprovalStatus.PENDING);
        List<SelectedCourse> selectedCourses =  currentStudent.getSelectedCourses();
        for (SelectedCourse selectedCourse : selectedCourses) {
            selectedCourse.setStatus(CourseStatus.Pending);
        }
        currentStudent.setSelectedCourses(selectedCourses);
    }
}