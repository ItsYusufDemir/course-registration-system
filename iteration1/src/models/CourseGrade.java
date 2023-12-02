package iteration1.src.models;
import iteration1.src.enums.CourseResult;

public class CourseGrade {
    private Course course;
    private String letterGrade;
    private CourseResult courseResult;

    public CourseGrade() {
    }

    public CourseGrade(Course course, String letterGrade, CourseResult courseResult) {
        this.course = course;
        this.letterGrade = letterGrade;
        this.courseResult = courseResult;
    }

    public double convertLetterGradeToScore() {
        double score = 0.0;
        switch (letterGrade) {
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
                score = 2.5;
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


    public Course getCourse() {
        return course;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public CourseResult getCourseResult() {
        return courseResult;
    }
}
