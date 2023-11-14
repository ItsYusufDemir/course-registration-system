public class SelectedCourse {
    private Student student;
    private Course course;
    private CourseStatus status;

    public SelectedCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.status = CourseStatus.Draft;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public boolean isApproved() {
        if (status == CourseStatus.Approved) {
            return true;
        } 
        else {
            return false;
        }
    }
}