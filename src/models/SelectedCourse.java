package models;
import enums.CourseStatus;

public class SelectedCourse {
    private Student student;
    private Course course;
    private CourseStatus status;
    private CourseSection courseSection;

    public SelectedCourse(Student student, Course course, CourseSection courseSection) {
        this.student = student;
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

    public Student getStudent() {
        return student;
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