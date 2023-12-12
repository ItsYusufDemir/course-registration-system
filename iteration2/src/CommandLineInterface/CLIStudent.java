package iteration2.src.CommandLineInterface;

import iteration2.src.controllers.StudentController;
import iteration2.src.models.Course;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;
import iteration2.src.utils.DatabaseManager;
import iteration2.src.models.CourseSection;

import java.util.*;



// TODO: change println to printf so it looks better


public class CLIStudent {

    
    private StudentController studentController;
    private Scanner scanner;
    private boolean shouldQuit;
    
    
    public CLIStudent(StudentController studentController) { 
        this.studentController = studentController;          
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
                    if( validateNumber( str, studentController.getSelectedCourses().toArray() ) ){
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
            
            listAvaliableCourseSections();
            
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
        int rowNumber = Integer.parseInt(getRowNumberFromInput(str));
        if(studentController.addSelectedCourse(studentController.getAvaliableCourseSections().get(rowNumber-1))){ 
            return true;
        }
        return false;
    }



    private boolean deleteCourse(String str){   
        int rowNumber = Integer.parseInt(getRowNumberFromInput(str)); 
        if(studentController.removeSelectedCourse(studentController.getSelectedCourses().get(rowNumber-1)){
            return true;
        }
        return false;
    }


    private String getRowNumberFromInput(String str){
        
        if(str.contains(" ")){
            return str.substring(0, str.indexOf(" "));
        }
        else if(str.contains(",")){
            return str.substring(0, str.indexOf(","));
        }
        else if(str.contains(".")){
            return str.substring(0, str.indexOf("."));
        }
        else{

            return str;
        }
        
        
    }

    private boolean isValidNumber(String str){
        int strLength = getRowNumberFromInput(str).length();
        for(int i = 0; i<strLength; i++){
            if(str.charAt(i) < 48 || str.charAt(i) > 57){
                return false;
            }
        }
        return true;
    }

    private boolean checkIfValidRowNumber(String rowNumber, Object[] list){
        int rowNumberInt = Integer.parseInt(getRowNumberFromInput(rowNumber));
        if(rowNumberInt > list.length || rowNumberInt < 1){
            return false;
        }
        return true;
    }
    
    

    private void listAvaliableCourseSections(){
        List<Course> avaliableCourses = studentController.listAvailableCourseSections(); 
        int rowCount = 1;
        Course course; 
            for(CourseSection courseSection : course.getCourseSections()){
                course = studentController.findCourseOfCourseSection(courseSection);
                System.out.println(rowCount + ". " + course.getCourseCode() + "\t" + course.getCourseName() + "\t"
                        + courseSection.getSectionCode() + "\t"
                        + courseSection.getLecturerName() + "\t" + course.getCourseCredit());
            }
        
            
    }

    private void listSelectedCourses(){
        int rowCount = 1;
        for(SelectedCourse selectedCourse : studentController.getSelectedCourses()){ 
            System.out.println(rowCount + ". " + selectedCourse.getCourse().getCourseCode() + "\t" + selectedCourse.getCourse().getCourseName() + "\t"
                    + selectedCourse.getCourseSection().getSectionCode() + "\t" + selectedCourse.getStatus());
        }
    }

    



}