package iteration2.src.models;
import iteration2.src.enums.CourseResult;
import iteration2.src.enums.CourseType;

import java.util.*;

import iteration2.src.utils.DatabaseManager;

public class Transcript{

    private List <CourseGrade> takenCourses;

    public Transcript() {
    }

    public Transcript(List <CourseGrade> takenCourses){
        this.takenCourses = takenCourses;
    }

    public void addCourse(Course course){
        takenCourses.add(new CourseGrade(course, null, CourseResult.ACTIVE));
    }


    
    public List<Course> acquirePassedCourses() {

        List<Course> passedCourses = new ArrayList<Course>();
        for (CourseGrade course : takenCourses) {
            if (course.getCourseResult() == CourseResult.PASSED) {
                passedCourses.add(course.getCourse());
            }
        }
        return passedCourses;
    }
    
    public double calculateCompletedCredits(){
        double total = 0.0;
        for(CourseGrade course : takenCourses){
            if(course.getCourseResult() == CourseResult.PASSED)
                total += course.getCourse().getCourseCredit();
        }
        return total;
    }

    public double calculateGPA(){
        double totalPoint = 0.0;
        double totalCredit = 0.0;
        for(CourseGrade course : takenCourses){
            totalPoint += course.getCourse().getCourseCredit()*course.convertLetterGradeToScore();
            totalCredit += course.getCourse().getCourseCredit();
        }

        return totalPoint/totalCredit;
    }

    public List<CourseGrade> getTakenCourses() {
        return takenCourses;
    }

    public void setTakenCourses(List<CourseGrade> takenCourses) {
        this.takenCourses = takenCourses;
    }

    
    //This method checks if the student has taken all the courses that are required for graduation
    //Which is at least 165 credits exluding nte, te, ue, fe and isg
    public boolean checkEngineeringProjectAvailability() {
        HashMap<Integer,String> constraint = DatabaseManager.getInstance().getConstraints();

        double totalCreditsForEngineeringProject = 0;

        //Those course types are not count for engineering project
        CourseType restrictedCourseTypes[] = {
            CourseType.NONTECHNICAL_ELECTIVE,
            CourseType.UNIVERSITY_ELECTIVE,
            CourseType.TECHNICAL_ELECTIVE,
            CourseType.FACULTY_ELECTIVE,
        };

        //Those courses are not count for engineering project
        String restrictedCourses[] = {"ISG121", "ISG122"};
        

        for(int i = 0; i < takenCourses.size(); i++){

            if(takenCourses.get(i).getCourseResult() == CourseResult.PASSED && 
                Arrays.asList(restrictedCourseTypes).contains(takenCourses.get(i).getCourse().getCourseType()) == false &&
                Arrays.asList(restrictedCourses).contains(takenCourses.get(i).getCourse().getCourseCode()) == false) {


                totalCreditsForEngineeringProject += takenCourses.get(i).getCourse().getCourseCredit();

            }
        }

        if (totalCreditsForEngineeringProject >= Integer.parseInt(constraint.get(3))) {
            return true;
        } else {
            return false;
        }
    }

    

    
    
}