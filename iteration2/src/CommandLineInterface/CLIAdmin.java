package iteration2.src.CommandLineInterface;
import java.util.*;
import java.lang.*;
import java.util.concurrent.ExecutionException;

import javax.xml.crypto.Data;

import com.fasterxml.jackson.databind.jsonschema.SchemaAware;

import iteration2.src.enums.ApprovalStatus;
import iteration2.src.enums.Color;
import iteration2.src.enums.CourseType;
import iteration2.src.models.Course;
import iteration2.src.models.CourseSection;
import iteration2.src.models.Prerequisite;
import iteration2.src.utils.DatabaseManager;
import iteration2.src.models.Admin;
import iteration2.src.controllers.AdminController;
import iteration2.src.utils.Util;


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
            System.out.println("  " + (i+1) + ". " + courses.get(i).getCourseCode() + "\t " + courses.get(i).getCourseName());
        }
        System.out.println(
                "\n\nPress c to create a new course\n" +
                        "Press d to delete course\n");
        System.out.println("Press b to go back");
        System.out.println("Press q to quit");

        String str = scanner.nextLine();

        if (str.equals("c")) {
            createNewCoursePage();
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

    public void constraintPage (){
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

    public boolean createNewCoursePage(){

        ArrayList prerequisiteCourses = new ArrayList();
        ArrayList courseSections = new ArrayList();
        Prerequisite prerequisiteInformation;
        CourseSection courseSection;
        CourseType courseType;
        Course course;

        int courseCredit;
        int courseECTS;
        int numberOfPrerequisiteCourses;
        int courseTypeCode;
        int numberOfCourseSections;
        int studentCapacity;
        int givenSemester;

        String courseName;
        String courseCode;
        String lecturerName;
        String sectionTime;
        String sectionDate;
        String classroom;
        String sectionCode;

        Util.paintText("********************Create New Course Page********************\n", Color.GREEN);


        System.out.println("-> Enter the course information for the fields.");

        while(true){
            System.out.println("1.\tCourse Credit: ");
            try{
                courseCredit = scanner.nextInt();
                break;
            }catch (Exception e){
                Util.paintText("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        while(true){
            System.out.println("2.\tCourse ECTS: ");
            try{
                courseECTS = scanner.nextInt();
                break;
            }catch (Exception e){
                Util.paintText("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        System.out.println("3.\tCourse Name: ");
        courseName = scanner.nextLine();

        System.out.println("4.\tCourse Code: ");
        courseCode = scanner.nextLine();

        while(true){
            System.out.println("5.\tCourse Semester: ");
            try{
                givenSemester = scanner.nextInt();
                break;
            }catch (Exception e){
                Util.paintText("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        while(true){
            System.out.println("6.\tCourse Type: Compulsory(1), Non-technical Elective(2), Technical Elective(3), University Elective(4), Faculty Elective(5): ");
            try{
                courseTypeCode = scanner.nextInt();
                break;
            }catch (Exception e){
                Util.paintText("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        switch (courseTypeCode){
            case 1: courseType = CourseType.COMPULSORY;
            break;
            case 2: courseType = CourseType.NONTECHNICAL_ELECTIVE;
            break;
            case 3: courseType = CourseType.TECHNICAL_ELECTIVE;
            break;
            case 4: courseType = CourseType.UNIVERSITY_ELECTIVE;
            break;
            case 5: courseType = CourseType.FACULTY_ELECTIVE;
            break;
            default: courseType = CourseType.COMPULSORY;
        }

        while(true){
            System.out.println("7.\t Number of Prerequisite Courses: ");
            try{
                numberOfPrerequisiteCourses = scanner.nextInt();
                break;
            }catch (Exception e){
                Util.paintText("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        System.out.println("Write course codes of prerequisite courses one by one");
        String prerequisiteCourseCode;
        Course prerequisiteCourse;
        for(int i = 1; i <= numberOfPrerequisiteCourses; i++){
            System.out.print(i + ".\t");
            prerequisiteCourseCode = scanner.nextLine();
            prerequisiteCourse = findCourse(prerequisiteCourseCode);
            if(prerequisiteCourse != null){
                prerequisiteCourses.add(prerequisiteCourse);
            } else{
                Util.paintText("There is no course with this course code! Try again.", Color.RED);
                i--;
            }
        }

        while(true){
            System.out.println("8.\tNumber Of Sections: ");
            try{
                numberOfCourseSections = scanner.nextInt();
                break;
            }catch (Exception e){
                Util.paintText("Invalid input ! Please enter an integer value", Color.RED);
            }
        }


        for(int i = 1; i <= numberOfCourseSections; i++){
            System.out.println("Section " + i);

            while(true){
                System.out.println("1.\tStudent Capacity: ");
                try{
                    studentCapacity = scanner.nextInt();
                    break;
                }catch (Exception e){
                    Util.paintText("Invalid input ! Please enter an integer value", Color.RED);
                }
            }


            System.out.println("2.\tLecturer Name: ");
            lecturerName = scanner.nextLine();

            System.out.println("3.\tSection Time: ");
            sectionTime = scanner.nextLine();

            System.out.println("4.\tSection Date: ");
            sectionDate = scanner.nextLine();

            System.out.println("5.\tClassroom: ");
            classroom = scanner.nextLine();

            System.out.println("5.\tSection Code: ");
            sectionCode = scanner.nextLine();

            courseSections.add(new CourseSection(studentCapacity, lecturerName, sectionTime, sectionDate, classroom, sectionCode));
        }

        Prerequisite prerequisite = new Prerequisite(prerequisiteCourses);

        course = new Course(courseCredit, courseECTS, givenSemester, courseName, courseCode, prerequisite, courseSections, courseType);

        Util.clearScreen();
        if(adminController.createCourse(course) != null){
            Util.paintText("SUCCESS: " + courseCode + " is created.", Color.GREEN);
            return true;
        } else{
            Util.paintText("FAIL! " + courseCode + " can't created.", Color.RED);
            return true;
        }

    }

    private Course findCourse(String courseCode){
        return adminController.findCourse(courseCode);
    }
}
