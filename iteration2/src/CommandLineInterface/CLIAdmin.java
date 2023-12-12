package iteration2.src.CommandLineInterface;
import java.util.*;

import javax.xml.crypto.Data;

import com.fasterxml.jackson.databind.jsonschema.SchemaAware;

import iteration2.src.utils.DatabaseManager;
import iteration2.src.models.Admin;
import iteration2.src.controllers.AdminController;

public class CLIAdmin {

    private Scanner scanner;
    private AdminController adminController;
    private Admin currentadmin;
    
    CLIAdmin(Admin admin){
        this.currentadmin = admin;
        adminController = new AdminController(admin);
        scanner = new Scanner(System.in);
    }


    public void menuPage() {

        System.out.println(
                " Menu\n" +
                        "********\n" +
                        "  1. Course list\n" +
                        "  2. Constraint Settings\n\n" +
                        "Press q to quit" +
                        "Press corresponding row number to review and make changes for the list you want.\n" 
                        );

        String str = scanner.nextLine();
        if (str.equals("q")) {
            DatabaseManager.getInstance().saveToDatabase();
            System.exit(0);
        } else if (str.equals("1")) {
            courseListPage();
        } else if (str.equals("2")) {
            constraintPage();
        } else {
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            menuPage();
        }

    }



        public void courseListPage() {

        scanner = new Scanner(System.in);
        List<Course> courses = adminController.getCourseList();

        System.out.println(
                " My Courses\n" +
                        "**************\n" +
                        "  Code\t Name\t \n" +
                        "  ____\t ____\t");
        for(int i = 0; i < courses.size(); i++){
            System.out.println("  " + (i+1) + ". " + courses.get(i).getCode() + "\t " + courses.get(i).getName());
        }
        System.out.println(
                "\n\nPress c to create a new course\n" +
                        "Press d to delete course\n");
        System.out.println("Press b to go back");
        System.out.println("Press q to quit");

        String str = scanner.nextLine();

        if (str.equals("c")) {
            createNewStudentPage();
        } else if (str.startsWith("d")) {
            System.out.println("Enter the row number of the course you want to delete : ");
            str = scanner.nextLine();
            if (isValidNumber(str) && checkIfValidRowNumber(str, courses)) {
                if (deleteCourse(course)) {
                    System.out.println("Course deleted successfully");
                } else {
                    System.out.println("delete failed");
                }
            } else {
                System.out.println("Invalid input");
                System.out.println("\n\n\n"); 
            }
            courseListPage();
        } else if (str.equals("b")) {
            menuPage();
        } else if (str.equals("q")) {
            System.exit(0);
        } else {
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            myCoursesPage(courses);
        }

    }

    public void constraintPage {
        scanner = new Scanner(System.in);
        Constraint constraint = adminController.getConstraint();
        
        System.out.println(
                " Constraint Settings\n" +
                        "**************\n");
        System.out.println("1. Add-Drop: " + constraint.getIsAddDropWeek);
        System.out.println("2. Max Number Of Courses That Can Be Taken:: " + constraint.getmaxNumberOfCoursesStudentTake);
        System.out.println("");
        System.out.println("Press b to go back");
        System.out.println("Press q to quit");
        System.out.println();
        System.out.println("Enter the row number to edit:");

        String str = scanner.nextLine();

        if (str.equals("1")) {
            System.out.println("Enter true or false for add drop: ");
            str = scanner.nextLine();
            if (str.equals("true")) {
                constraint.setIsAddDropWeek(true);
                System.out.println("Add-Drop changed successfully");
            } else if (str.equals("false")) {
                constraint.setIsAddDropWeek(false);
                System.out.println("Add-Drop changed successfully");
            } else {
                System.out.println("Invalid input");
                System.out.println("\n\n\n"); 
            }
            constraintPage();
        } else if (str.startsWith("2")) {
            System.out.println("Enter the new value for Max Number Of Courses That Can Be Taken: ");
            str = scanner.nextLine();
            if (isValidNumber(str)) {
                constraint.setmaxNumberOfCoursesStudentTake(Integer.parseInt(str));
                System.out.println("Max Number Of Courses That Can Be Taken changed successfully");
            } else {
                System.out.println("Invalid input");
                System.out.println("\n\n\n"); 
            }
            constraintPage();
        } else if (str.equals("b")) {
            menuPage();
        } else if (str.equals("q")) {
            System.exit(0);
        } else {
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            constraintPage();
        }
    }

}
