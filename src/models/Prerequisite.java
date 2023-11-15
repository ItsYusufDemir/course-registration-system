package models;
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
        int lengthPrereguisiteCourses = course.getPrerequisiteInformation().prerequisiteOfCourses.size();

        for(int i = 0; i < lengthPrereguisiteCourses; i++){
            // passed courses'ı kedim hesaplamalıyım
            isPrerequisiteCoursesPasses = student.getTranscript.passedCourses.contains(prerequisiteOfCourses.get(i));
            if(!isPrerequisiteCoursesPasses){
                break;
            }
        }
        return isPrerequisiteCoursesPasses;
    }

}


