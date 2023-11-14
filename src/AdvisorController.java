import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvisorController {

    private Advisor currentAdvisor;
    private Student currentStudent;
    private List<Student> studentList;
    private List<Student> approvelOfStudents;



    public AdvisorController(Advisor currentAdvisor) {
        this.currentAdvisor = currentAdvisor;
    }

    public List<Student> getStudentList() {
        DatabaseManager databaseManager = new DatabaseManager();
        studentList = databaseManager.getByID("Student", currentAdvisor.getID());
        return studentList;
    }


    public void approveCourse(Student student, Course course) {
        currentAdvisor.acceptCourse(student, course);
    }

    public void denyCourse(Student student, Course course) {
        currentAdvisor.rejectCourse(student, course);
    }


    private List<Student> findStudentList(boolean approvalStatus) {
        List<Student> approvedStudent = new List<Student>;
        for (Student student :studentList) {
            if(student.approvalStatus = ApprovalStatus.Pending) {
                approvedStudent.add(student);
            }
        }
    }

    private List<Student> sortStudentListForName(List<Student> studentList) {
        return Collections.sort(studentList, Comparator.comparing(Student::getFirstName).thenComparing(Student::getLastName));
    }

    public void finishApprovalOfCurrentStudent() {
        currentStudent.approvalStatus = ApprovalStatus.Done;
        currentStudent = null;
    }






    public Advisor getCurrentAdvisor() {
        return currentAdvisor;
    }

    public void setCurrentAdvisor(Advisor currentAdvisor) {
        this.currentAdvisor = currentAdvisor;
    }
}
