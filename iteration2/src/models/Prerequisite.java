package iteration2.src.models;
import java.util.ArrayList;
import java.util.List;

public class Prerequisite {

    private List<Course> prerequisiteOfCourses; // elimizdeki dersin prerequisite derslerini tutuyor
    public Prerequisite() {
    }

    public Prerequisite(List<Course> prerequisiteOfCourses) {
        this.prerequisiteOfCourses = prerequisiteOfCourses;
    }

    public boolean checkPrerequisiteCoursePassed(Student student, Course course){
        int lengthPrerequisiteCourses = course.getPrerequisiteInformation().prerequisiteOfCourses.size();
        List<Course> passedCourses = student.getTranscript().acquirePassedCourses();
        int lengthPassedCourses = passedCourses.size();
        ArrayList<Boolean> isPrerequisiteCoursesPassed = new ArrayList<Boolean>();
        for(int i = 0; i < lengthPrerequisiteCourses; i++){
            for(int j = 0; j < lengthPassedCourses; j++){
                isPrerequisiteCoursesPassed.add(prerequisiteOfCourses.get(i).getCourseCode().equals(passedCourses.get(j).getCourseCode()));
            }
            if(!isContainTrue(isPrerequisiteCoursesPassed)){
                return false;
            }
        }
        return true; // if all prerequisite courses passed
    }

    private boolean isContainTrue(ArrayList<Boolean> list){
        int length = list.size();
        for(int i = 0; i < length; i++){
            if(list.get(i)){ // if there is any true value then return true
                return true;
            }
        }
        return false; // if there is no any true value inside the list
    }

    public List<Course> getPrerequisiteOfCourses() {
        return prerequisiteOfCourses;
    }

    public void setPrerequisiteOfCourses(List<Course> prerequisiteOfCourses) {
        this.prerequisiteOfCourses = prerequisiteOfCourses;
    }
}


