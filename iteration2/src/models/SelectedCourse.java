package iteration2.src.models;
import iteration2.src.enums.CourseStatus;

public class SelectedCourse {
    private Course course;
    private CourseStatus status;
    private CourseSection courseSection;

    public SelectedCourse() {
    }

    public SelectedCourse(Course course, CourseSection courseSection) {
        this.course = course;
        this.status = CourseStatus.DRAFT;
        this.courseSection = courseSection;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    /* 
    public boolean mayitbeApproved() {
        if (status == CourseStatus.APPROVED) {
            return true;
        } 
        else {
            return false;
        }
    }
    */

    public Course getCourse() {
        return course;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public CourseSection getCourseSection() {
        return courseSection;
    }
}