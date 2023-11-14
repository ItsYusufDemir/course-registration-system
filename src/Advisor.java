import java.util.List;

public class Advisor extends User {

    private List<Student> advisedStudent;


    public Advisor(String userID, String userName , String password, String firstName, String lastName, boolean status) {
        super(userID, userName, password, firstName, lastName, status);
    }

    public void getMyPage() {
        CLIAdviser cliAdviser = new CLIAdviser(this);
        cliAdviser.menuPage();
    }
    public void acceptCourse(Student student, SelectedCourse selectedCourse) {

        selectedCourse.setStatus(CourseStatus.Approved);

        student.getTranscript().completedCourses.add(selectedCourse);
        student.getSelectedCourses().remove(selectedCourse);

        if(student.getSelectCourse().length == 0) {
            student.setAprovalStatus(ApprovalStatus.Done);
        }
    }

    public void rejectCourse(Student student, SelectedCourse selectedCourse) {
        selectedCourse.setStatus(CourseStatus.Denied);
        student.selectedCourses.remove(selectedCourse);

        if(student.getSelectCourse().length == 0) {
            student.setAprovalStatus(ApprovalStatus.Done);
        }
    }
}
