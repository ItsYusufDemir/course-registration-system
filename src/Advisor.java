import java.util.List;

public class Advisor extends User {

    private List<Student> advisedStudent;

    public Advisor(String userID, String userName , String password, String firstName, String lastName, boolean status) {
        super(userID, userName, password, firstName, lastName, status);
        CLIAdviser cliAdviser = new CLIAdviser(this);
        cliAdviser.menuPage();
    }

    public void acceptCourse(Student student, Course course) {

        for (SelectedCourse selectedCourse: student.selectedCourses) {
            if(course.courseName.equals(selectedCourse.course.courseName)) {
                selectedCourse.setStatus(CourseStatus.Approved);
            }
        }

        student.getTakenCourses.add(course);
        student.selectedCourses.remove(course);

    }

    public void rejectCourse(Student student, Course course) {
        for (SelectedCourse selectedCourse: student.selectedCourses) {
            if(course.courseName.equals(selectedCourse.course.courseName)) {
                selectedCourse.setStatus(CourseStatus.Approved);
            }
        }
    }

}
