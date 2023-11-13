import java.util.*;

public class Transcript{



    List <Course> completedCourses;

    public Transcript(){
        completedCourses = new ArrayList<Course>();
    
    }

    public void addCourse(Course course){
        completedCourses.add(course);
    }

    public void removeCourse(Course course){
        completedCourses.remove(course);
    }

    

    public double calculateCompletedCredits(){
        double total = 0.0;
        for(Course course : completedCourses){
            
            total += course.courseCredit;
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
        return score;
    }
    */
}