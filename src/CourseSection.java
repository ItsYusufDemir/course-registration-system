import java.util.List;
import java.util.ArrayList;

public class CourseSection {

    private int studentCapacity;
    private String lecturerName;
    private String sectionTime;
    private String sectionDate;
    private String classroom;
    private String sectionCode;
    private Course course;
    private int studentCountInsideCourseSection;

    public CourseSection(int studentCapacity, String lecturerName, String sectionTime, String sectionDate, String classroom, String sectionCode) {
        this.studentCapacity = studentCapacity;
        this.lecturerName = lecturerName;
        this.sectionTime = sectionTime;
        this.sectionDate = sectionDate;
        this.classroom = classroom;
        this.sectionCode = sectionCode;
        this.studentCountInsideCourseSection = 0;
    }

    public boolean checkAvailibilty(){
        if(studentCapacity >= studentCountInsideCourseSection){
            return true;
        }
        else{
            return false;
        }
    }

    public int incrementStudentCount(){
        studentCountInsideCourseSection++;
        return studentCountInsideCourseSection;
    }

    public int decrementStudentCount(){
        studentCountInsideCourseSection--;
        return studentCountInsideCourseSection;
    }

}

class Student{
 Transcript transcript;
}

class Transcript{
    List<Course> passedCourses;
}