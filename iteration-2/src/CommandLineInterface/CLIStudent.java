package CommandLineInterface;

import controllers.StudentController;
import models.Course;
import models.SelectedCourse;
import models.Student;
import utils.DatabaseManager;
import models.CourseSection;

import java.util.*;

import models.Student;

// TODO: change println to printf so it looks better
// TODO: change student dependencies to student controller(low coupling)

public class CLIStudent {

    private Student student;
    private StudentController studentController;
    private Scanner scanner;
    private boolean shouldQuit;
    
    
    public CLIStudent(Student student) {
        this.student = student;     // TODO: this class should not have a student dependency(low coupling)
        studentController = new StudentController(student);
        scanner = new Scanner(System.in);
    }
    
    public void menuPage() {
        shouldQuit = true;
        while(shouldQuit){

            System.out.println(
                    " Menu\n" +
                            "********\n" +
                            "  1. My Courses\n" +
                            "  2. Log out\n\n" +
                            "Press q to quit");
        
            String str = scanner.nextLine();

                
           if(str.equals("1")){
                showMyCoursesPage();
            }
            else if(str.equals("2")){
                break;
            }
            else if(str.equals("q")){
                shouldQuit = false;
            }
            else{
                System.out.println("Invalid Input");
            }
            
            
        
        }

    }

    public void showMyCoursesPage() {
        shouldQuit = false;
        while (shouldQuit) {
            System.out.println(
                " My Courses\n" +
                        "**************\n" +
                        "  Code\t Name\t Section\t Status\n" +
                        "  ____\t ____\t _______\t ______");
        
            listSelectedCourses();

            System.out.println(
                    "\n\n1. Add Course\n" +
                        "2. Delete Course\n" +
                        "3. Send To Approval");

            System.out.println("Press b to go back");
            System.out.println("Press q to quit");
            
            String str = scanner.nextLine();

            try {
                if(str.equals("1")){
                    showAddCoursePage();
                }
                else if(str.equals("2")){
                    System.out.println("Enter the row number of the course you want to delete : ");
                    str = scanner.nextLine();
                    if( validateNumber( str, student.getSelectedCourses().toArray() ) ){ //TODO: student dependency, there should be a method at the studentController class
                        if(deleteCourse(str)){
                            System.out.println("Course successfully deleted");
                        }
                        else{
                            System.out.println("Course deletion failed");
                        }
                    }
                }
                else if(str.equals("3")){
                    studentController.sendSelectedCoursesToApproval();
                }
                else if(str.equals("b")){
                    break;
                }
                else if(str.equals("q")){
                    shouldQuit = false;
                }
                else{
                    System.out.println("Invalid input");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }    
    }


    public void showAddCoursePage(){
        shouldQuit = false;
        while(shouldQuit){

            System.out.println(
                " Avaliable Courses(To Add)\n" +
                        "**************\n" +
                        "  Code\t Name\t Section\t Instructor\t Credit\n" +
                        "  ____\t ____\t _______\t __________\t ______");
            
            listAvaliableCourses();

            System.out.println("\n\npress b to go back");
            System.out.println("press q to quit");
            System.out.println("Enter the row number of the course you want to add : ");

            String str = scanner.nextLine();

            try {
                if(str.equals("b")){
                    break;
                }
                else if(str.equals("q")){
                    shouldQuit = false;
                }
                else if( validateNumber( str, getAvaliableCourseSections().toArray() ) ){
                    if(addCourse(str)){ 
                        System.out.println("Course successfully added");
                    }
                    else{
                        System.out.println("Course addition failed");
                    }
                }
            
            } catch (Exception e) {
                System.out.println("Invalid input");
            }

        }

    }

    private boolean validateNumber(String str, Object[] list){
        if(isValidNumber(str)){
            if(checkIfValidRowNumber(str, list)){
                return true;
            }
        }

        return false;
    }

    private boolean addCourse(String str){
        int rowNumber = Integer.parseInt(str);
        if(true /* check if valid section */){ // TODO: this should be done at the studentController class
            return false;
        }
        return true;
        // TODO: call method from student controller to add the course with the given course section
    }



    private boolean deleteCourse(String str){   // TODO: these checks should be done at the studentController class
        int rowNumber = Integer.parseInt(str);  // TODO: this method should only call the studentController method
        String selectedCourseStatus = student.getSelectedCourses().get(rowNumber-1).getStatus().toString();
        if(selectedCourseStatus.equalsIgnoreCase("DRAFT")){
            studentController.removeSelectedCourse(student.getSelectedCourses().get(rowNumber-1));
            return true;
        }
        return false;
    }


    private String getRowNumberFromInput(String str){
        return str.substring(0, str.indexOf(" "));
    }

    private boolean isValidNumber(String str){
        int strLength = str.length();
        for(int i = 0; i<strLength; i++){
            if(str.charAt(i) < 48 || str.charAt(i) > 57){
                return false;
            }
        }
        return true;
    }

    private boolean checkIfValidRowNumber(String rowNumber, Object[] list){
        int rowNumberInt = Integer.parseInt(rowNumber);
        if(rowNumberInt > list.length || rowNumberInt < 1){
            return false;
        }
        return true;
    }
    
    

    private void listAvaliableCourses(){
        List<Course> avaliableCourses = student.listAvailableCourses(); // TODO: this class should not have a student dependency(low coupling)
        int rowCount = 1;
        for(Course course : avaliableCourses){
            for(CourseSection courseSection : course.getCourseSections()){
                System.out.println(rowCount + ". " + course.getCourseCode() + "\t" + course.getCourseName() + "\t"
                        + courseSection.getSectionCode() + "\t"
                        + courseSection.getLecturerName() + "\t" + course.getCourseCredit());
            }
        }
            
    }

    private void listSelectedCourses(){
        int rowCount = 1;
        for(SelectedCourse selectedCourse : student.getSelectedCourses()){
            System.out.println(rowCount + ". " + selectedCourse.getCourse().getCourseCode() + "\t" + selectedCourse.getCourse().getCourseName() + "\t"
                    + selectedCourse.getCourseSection().getSectionCode() + "\t" + selectedCourse.getStatus());
        }
    }

    private List<CourseSection> getAvaliableCourseSections(){ // TODO: this should be done at the studentController class
        int courseSize = student.listAvailableCourses().size();
        int sectionSize = 0;
        List<Course> temp = student.listAvailableCourses();
        List<CourseSection> tempCourseSections = new ArrayList<CourseSection>();
        for(int i = 0; i<courseSize; i++){
            sectionSize = temp.get(i).getCourseSections().size();
            for(int j = 0; j<sectionSize ;j++){
                tempCourseSections.add(temp.get(i).getCourseSections().get(j));
            }
        }
        return tempCourseSections;

    }



}