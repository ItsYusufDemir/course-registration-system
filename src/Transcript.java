import java.util.*;

public class Transcript{


    
    private List <CourseGrade> takenCourses;
    

    
    
    DatabaseManager databasemanager = DatabaseManager.getInstance();

    public Transcript(List <CourseGrade> takendCourses){
        this.takenCourses = takenCourses;
        
    
    }

    public void addCourse(Course course){
        takenCourses.add(new CourseGrade(course, null));

    }

    
    public List<Course> getPassedCourse() {
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


   
    public double calculateGPA(){
        double totalPoint = 0.0;
        double totalCredit = 0.0;
        for(CourseGrade course : takenCourses){
            totalPoint += course.getCourse().getCourseCredit()*course.convertLetterGradeToScore();
            totalCredit += course.getCourse().getCourseCredit();
        }

        return totalPoint/totalCredit;
    }


}