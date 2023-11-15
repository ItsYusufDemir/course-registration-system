package models;
import enums.CourseStatus;

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

    public boolean isApproved() {
        if (status == CourseStatus.APPROVED) {
            return true;
        } 
        else {
            return false;
        }
    }

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