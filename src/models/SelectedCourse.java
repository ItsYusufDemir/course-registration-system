package models;
import enums.CourseStatus;

public class SelectedCourse {
    private Course course;
    private CourseStatus status;

    public SelectedCourse() {
    }

    public SelectedCourse(Course course) {
        this.course = course;
        this.status = CourseStatus.DRAFT;
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

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseStatus getStatus() {
        return status;
    }


    
}