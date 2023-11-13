import java.util.List;
import java.util.ArrayList;

public class CourseSection {

    private int studentCapacity;
    private String lecturerName;
    private String sectionTime;
    private String sectionDate;
    private String classroom;
    private String sectionCode;
    private List<Student> students;

    public CourseSection(int studentCapacity, String lecturerName, String sectionTime, String sectionDate, String classroom, String sectionCode) {
        this.studentCapacity = studentCapacity;
        this.lecturerName = lecturerName;
        this.sectionTime = sectionTime;
        this.sectionDate = sectionDate;
        this.classroom = classroom;
        this.sectionCode = sectionCode;
        this.students = new ArrayList<>();
    }

    public boolean checkAvailibilty(){
        if(studentCapacity <= students.size()){
            return true;
        }
        else{
            return false;
        }
    }

    public Student addStudent(Student student){
        students.add(student);
        return student;
    }

    public Student deleteStudent(Student student){
        students.remove(student);
        return student;
    }

    public String courseInfo(){
        return "";
    }

}

class Student{

}