package CommandLineInterface;
package CommandLineInterface;

import java.util.*;
//TODO: add the course section check so that you cant take 2 sections from the same course

import models.Course;
import models.SelectedCourse;
import models.Student;

public class CLIStudent {

    
    Stack<Integer> pageNumeber;
    private Scanner scanner;
    private StudentController studentController;
    private Student currentStudent;

    public CLIStudent(Student student){
        currentStudent = student;
        studentController = new StudentController(currentStudent);
    }

    public void menuPage(){

        scanner = new Scanner(System.in);

        System.out.println(
            " Menu\n" +
            "********\n" +
            "  1. My Courses\n" +
            "  2. Log out\n" +
            "Press q to quit");

        String str = scanner.nextLine();
        if(str.equals("q")){
            System.exit(0); 
        }
        else if(str.equals("1")){
            
            myCoursesPage(currentStudent.listAvaliableCourseSections());
        }
        else if(str.equals("2")){
            return;
        }
        else{
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            menuPage();
        }


    }


    public void myCoursesPage(List<Course> courses){

        scanner = new Scanner(System.in);

        System.out.println(
            " My Courses\n" +
            "**************\n" +
            "  Code\t Name\t Section\t Status\n" +
            "  ____\t ____\t _______\t ______");
        listSelectedCourses(currentStudent);
        System.out.println(
            "1. Add Course\n" +
            "2. Delete Course\n" +
            "3. Send To Approval");

        System.out.println("Press b to go back");
        System.out.println("Press q to quit");

        String str = scanner.nextLine();

        if(str.equals("1")){
            addCoursePage(courses);
        }
        else if(str.startsWith("2")){
            if(deleteCourse(str, courses)){
                System.out.println("Course deleted successfully");
            }
            else{
                System.out.println("Invalid input");
                System.out.println("\n\n\n");
            }
            myCoursesPage(courses);
        }
        else if(str.equals("3")){
            studentController.sendSelectedCoursesToApproval();
        }
        else if(str.equals("b")){
            menuPage();
        }
        else if(str.equals("q")){
            System.exit(0); 
        }
        else{
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            myCoursesPage(courses);

        }
            
    }

    public void addCoursePage(List<Course> courses){
        scanner = new Scanner(System.in);

        System.out.println(
            " Avaliable Courses(To Add)\n" +
            "**************\n" +
            "  Code\t Name\t Section\t Instructor\t Credit\n" +
            "  ____\t ____\t _______\t __________\t ______");
        listAvaliableCourseSections(courses);
        System.out.println("press b to go back");
        System.out.println("press q to quit");

        String str = scanner.nextLine();
        String courseCode = str.replaceAll(" ", "");
        char[] courseCodeArray = courseCode.toCharArray();

        if(checkCourseSelectionInput(courseCodeArray) && (!str.startsWith(" "))){
            ArrayList<Integer> courseCodeIntArray = convertCourseSelectionInputToInt(str);
            int coursesLength = courses.size();
            for(int i = 1; i<=coursesLength; i++){
                if(courseCodeIntArray.contains(i))
                    studentController.addSelectedCourse(courses.get(i-1));
            }
            addCoursePage(courses);
        }
        else if(str.equals("b")){
            myCoursesPage(courses);
        }
        else if(str.equals("q")){
            System.exit(0); 
        }
        else{
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            addCoursePage(courses);
        }
    }

    public boolean deleteCourse(String str, List<Course> courses){
        
        String courseCode = str.replaceAll(" ", "");
        char[] courseCodeArray = courseCode.toCharArray();

        if(checkCourseSelectionInput(courseCodeArray)){
            ArrayList<Integer> courseCodeIntArray = convertCourseSelectionInputToInt(str);
            int coursesLength = courses.size();
            for(int i = 1; i<=coursesLength; i++){
                if(courseCodeIntArray.contains(i))
                    studentController.removeSelectedCourse(currentStudent.selectedCourses.get(i-1));
            }
            return true;
        }
        else{
            return false;
        }
        
    } 

    private boolean checkCourseSelectionInput(char[] courseCodeArray){
        
        for(char c : courseCodeArray){
            if(!Character.isLetterOrDigit(c)){
                return false;
            }
        }
        return true;
    }

    private ArrayList<Integer> convertCourseSelectionInputToInt(String string){
        string = string.substring(1);
        int strLength = string.length();
        String tempString = "";
        ArrayList<Integer> courseCodeIntArray = new ArrayList<Integer>();
        for(int i = 0; i < strLength; i++){
            if(string.charAt(i) == ' '){
                courseCodeIntArray.add(Integer.parseInt(tempString));
            }
            else{
                tempString += string.charAt(i);
            }
        }
        return courseCodeIntArray;
    }

    private void listAvaliableCourseSections(List<Course> courses){ 
        int i = 0;
        int sectionLength = 0;
        for(Course course : courses){
            i++;
            sectionLength = course.getAvaliableCourseSections().size();
            for(int j = 0; j< sectionLength; j++)
            System.out.println(i + ". " + course.getCourseCode() + "\t" + course.getCourseName() + "\t" + course.getAvaliableCourseSections().get(j) + "\t" + course.getCourseInstructor() + "\t" + course.getCourseCredit());
        }
    }


    private void listSelectedCourses(Student student){ 
        int i = 0;
        for(SelectedCourse course : student.selectedCourses){
            i++;
            System.out.println(i + ". " + course.getCourse().getCourseCode() + "\t" + course.getCourse().getCourseName() + "\t" + course.getCourseSection() + "\t" + course.getStatus());
        }
    }
}
