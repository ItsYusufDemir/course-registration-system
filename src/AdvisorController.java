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

    private void setStudentList() {
        DatabaseManager databaseManager = new DatabaseManager();
        this.studentList = databaseManager.getByID("Student", currentAdvisor.getID());
    }


    public void approveCourse(Student student, SelectedCourse selectedCourse) {
        currentAdvisor.acceptCourse(student, selectedCourse);
    }

    public void denyCourse(Student student, SelectedCourse selectedCourse) {
        currentAdvisor.rejectCourse(student, selectedCourse);
    }


   public List<Student> getStudentListOrderByStatus() {
        setStudentList();

        List<Student> studentListOrder = new List<Student>;
        for (Student student :studentList) {
            if(student.getApprovalStatus() = ApprovalStatus.Pending) {
                studentListOrder.add(student);
            }
        }

       for (Student student :studentList) {
           if(student.getApprovalStatus() = ApprovalStatus.Done) {
               studentListOrder.add(student);
           }
       }
        return studentListOrder;
    }

    /*private List<Student> sortStudentListForName(List<Student> studentList) {
        return Collections.sort(studentList, Comparator.comparing(Student::getFirstName).thenComparing(Student::getLastName));
    }*/






    // GETTER AND SETTER
    public Advisor getCurrentAdvisor() {
        return currentAdvisor;
    }

    public void setCurrentAdvisor(Advisor currentAdvisor) {
        this.currentAdvisor = currentAdvisor;
    }
}
