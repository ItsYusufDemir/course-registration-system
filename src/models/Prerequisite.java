package models;
import java.util.List;

public class Prerequisite {

    private List<Course> prerequisiteOfCourses; // elimizdeki dersin prerequisite derslerini tutuyor
    private List<Course> prerequisiteForCourses; // bu ders hangi derlerin prerequisite dersi ona bakıyor

    public Prerequisite() {
    }

    public Prerequisite(List<Course> prerequisiteOfCourses, List<Course> prerequisiteForCourses) {
        this.prerequisiteOfCourses = prerequisiteOfCourses;
        this.prerequisiteForCourses = prerequisiteForCourses;
    }

    public boolean checkPrequisiteCoursePassed(Student student, Course course){
        boolean isPrerequisiteCoursesPasses = true;
        int lengthPrerequisiteCourses = course.getPrerequisiteInformation().prerequisiteOfCourses.size();

        List<Course> passedCourses = student.getTranscript().getPassedCourses();
        int lengthPassedCourses = passedCourses.size();

        for(int i = 0; i < lengthPrerequisiteCourses; i++){
            isPrerequisiteCoursesPasses = student.getTranscript().getPassedCourses().contains(prerequisiteOfCourses.get(i));
            for(int j = 0; j < lengthPassedCourses; j++){
                isPrerequisiteCoursesPasses = prerequisiteOfCourses.get(i).getCourseCode().equals(passedCourses.get(j).getCourseCode());
                if(!isPrerequisiteCoursesPasses){
                    break;
                }
            }
        }
        return isPrerequisiteCoursesPasses;
    }

    public List<Course> getPrerequisiteOfCourses() {
        return prerequisiteOfCourses;
    }

    public void setPrerequisiteOfCourses(List<Course> prerequisiteOfCourses) {
        this.prerequisiteOfCourses = prerequisiteOfCourses;
    }

    public List<Course> getPrerequisiteForCourses() {
        return prerequisiteForCourses;
    }

    public void setPrerequisiteForCourses(List<Course> prerequisiteForCourses) {
        this.prerequisiteForCourses = prerequisiteForCourses;
    }
}


