package iteration2.src.controllers;

import java.util.List;
import iteration2.src.models.CourseGrade;
import iteration2.src.enums.CourseStatus;
import iteration2.src.models.Course;
import iteration2.src.models.CourseSection;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;
import iteration2.src.enums.ApprovalStatus;
import iteration2.src.enums.CourseResult;

public class StudentController {
    private Student currentStudent;

    public StudentController(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    // TEST YAZ BUNA
    private boolean checkCompulsoryCourses() {
        List<CourseGrade> courseGrade = currentStudent.getTranscript().getTakenCourses();
        for (CourseGrade course : courseGrade) {
            if (course.getCourseResult() == CourseResult.FAILED) {
                List<SelectedCourse> selectedCourses = currentStudent.getSelectedCourses();
                for (SelectedCourse selectedCourse : selectedCourses) {
                    if (!selectedCourse.getCourse().equals(course.getCourse())) {
                        if (currentStudent.listAvailableCourses().contains(course.getCourse())) {
                            System.err.println("You have to take " + course.getCourse().getCourseName() + " again.");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void addSelectedCourse(SelectedCourse selectedCourse) {
        currentStudent.addNewCourse(selectedCourse);
    }

    public List<CourseSection> getAvailableCourseSections() {
        return currentStudent.listAvailableCourses();
    }

    public void removeSelectedCourse(SelectedCourse selectedCourse) {
        currentStudent.deleteCourse(selectedCourse);
    }

    public void sendSelectedCoursesToApproval() {
        currentStudent.sendSelectedCoursesToApproval();
    }

    public String getNotification() {
        return "";
    }

    public String getTimeTable() {
        return "";
    }

    public void logout() {
        System.exit(0);
    }

    public void sendSelectedCoursesToApprovalBugFixed() {
        if (checkCompulsoryCourses()) {
            return;
        }

        currentStudent.setApprovalStatus(ApprovalStatus.PENDING);
        List<SelectedCourse> selectedCourses = currentStudent.getSelectedCourses();
        for (SelectedCourse selectedCourse : selectedCourses) {
            if (selectedCourse.getStatus() == CourseStatus.DRAFT) {
                selectedCourse.setStatus(CourseStatus.PENDING);
                selectedCourse.getCourseSection().incrementStudentCount();

            }
        }
        currentStudent.setSelectedCourses(selectedCourses);
    }
}