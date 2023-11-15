package models;
import enums.CourseResult;
import utils.DatabaseManager;

import java.util.*;

public class Transcript{


    
    private List <CourseGrade> takenCourses;
    DatabaseManager databasemanager = DatabaseManager.getInstance();

    public Transcript(List <CourseGrade> takendCourses){
        this.takenCourses = takenCourses;
    }

    public void addCourse(Course course){
        takenCourses.add(new CourseGrade(course, null, CourseResult.ACTIVE));
    }

    
    public List<Course> getPassedCourses() {
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
            
            total += course.getCourse().getCourseCredit();
        }
        return total;
    }

    /*private double convertLetterGradeToScore(String grade){
        double score = 0.0;
        switch (grade) {
            case "AA":
                score = 4.0;    
                break;
            case "BA":
                score = 3.5;    
                break;
            case "BB":
                score = 3.0;    
                break;
            case "CB":
                score = 2.5;;    
                break;            
            case "CC":
                score = 2.0;    
                break;
            case "DC":
                score = 1.5;    
                break;
            case "DD":
                score = 1.0;    
                break;
            case "FD":
                score = 0.5;    
                break;
            case "FF":
                score = 0.0;    
                break;

            default:
                score = 0.0;
                break;
        }

        return totalPoint/totalCredit;
    }
    */
}