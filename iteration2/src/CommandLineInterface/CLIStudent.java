package iteration2.src.CommandLineInterface;

import java.util.*;

import iteration2.src.controllers.StudentController;
import iteration2.src.models.Course;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.CourseSection;

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
        while (shouldQuit) {

            if (studentController.getNotification().isEmpty()) {
                System.out.println(" Notification\n " +
                        "**************");
                for (String string : studentController.getNotification())
                    System.out.println(string);
            }
            System.out.println(
                    " Menu\n" +
                            "********\n" +
                            "  1. My Courses\n" +
                            "  2. Log out\n\n" +
                            "Press q to quit");

            String str = scanner.nextLine();

            if (str.equals("1")) {
                showMyCoursesPage();
            } else if (str.equals("2")) {
                break;
            } else if (str.equals("q")) {
                shouldQuit = false;
            } else {
                System.out.println("Invalid Input: " + str);
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
                            "3. Show Timetable\n" +
                            "4. Send To Approval");

            System.out.println("Press b to go back");
            System.out.println("Press q to quit");

            String str = scanner.nextLine();

            try {
                if (str.equals("1")) {
                    showAddCoursePage();
                } else if (str.equals("2")) {
                    System.out.println("Enter the row number of the course you want to delete : ");
                    str = scanner.nextLine();
                    if (validateNumber(str, studentController.getSelectedCourses().toArray())) {
                        if (deleteCourse(str)) {
                            System.out.println("Course successfully deleted");
                        } else {
                            System.out.println("Course deletion failed");
                        }
                    }
                } else if (str.equals("3")) {
                    showTimetablePage();
                } else if (str.equals("4")) {
                    studentController.sendSelectedCoursesToApproval();
                } else if (str.equals("b")) {
                    break;
                } else if (str.equals("q")) {
                    shouldQuit = false;
                } else {
                    throw new Exception("Invalid input: " + str);

                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    public void showAddCoursePage() {
        shouldQuit = false;
        while (shouldQuit) {

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
                if (str.equals("b")) {
                    break;
                } else if (str.equals("q")) {
                    shouldQuit = false;
                } else if (validateNumber(str, studentController.getAvaliableCourseSections().toArray())) {
                    if (addCourse(str)) {
                        System.out.println("Course successfully added");
                    } else {
                        System.out.println("Course addition failed");
                    }
                }

                else {
                    throw new Exception("Invalid input: " + str);
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }

    }

    public void showTimetablePage() {
        shouldQuit = false;
        while (shouldQuit) {

            System.out.println(" Timetable\n" +
                               "***********" + 
                               " Hours/Days\tMonday\tTuesday\tWednesday\tThursday\tFriday\n" + 
                               " __________\t______\t_______\t_________\t________\t______\n");
            printTimeTable();

            String str = scanner.nextLine();

            try {
                if (str.equals("b")) {
                    break;
                } else if (str.equals("q")) {
                    shouldQuit = false;
                } else {
                    throw new Exception("Invalid input: " + str);
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private boolean validateNumber(String str, Object[] list) {
        if (isValidNumber(str)) {
            if (checkIfValidRowNumber(str, list)) {
                return true;
            }
        }

        return false;
    }

    private boolean addCourse(String str) {
        int rowNumber = Integer.parseInt(getRowNumberFromInput(str));
        CourseSection selectedCourseSection = studentController.getAvaliableCourseSections().get(rowNumber - 1);
        if (studentController.addSelectedCourse(new SelectedCourse(selectedCourseSection.findCourseOfCourseSection(),selectedCourseSection)) ) {
            return true;
        }
        return false;
    }

    private boolean deleteCourse(String str){   
        int rowNumber = Integer.parseInt(getRowNumberFromInput(str)); 
        if(studentController.removeSelectedCourse(studentController.getSelectedCourses().get(rowNumber-1)) ){
            return true;
        }
        return false;
    }

    private String getRowNumberFromInput(String str) {

        if (str.contains(" ")) {
            return str.substring(0, str.indexOf(" "));
        } else if (str.contains(",")) {
            return str.substring(0, str.indexOf(","));
        } else if (str.contains(".")) {
            return str.substring(0, str.indexOf("."));
        } else {

            return str;
        }

    }

    private boolean isValidNumber(String str) {
        int strLength = getRowNumberFromInput(str).length();
        for (int i = 0; i < strLength; i++) {
            if (str.charAt(i) < 48 || str.charAt(i) > 57) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfValidRowNumber(String rowNumber, Object[] list) {
        int rowNumberInt = Integer.parseInt(getRowNumberFromInput(rowNumber));
        if (rowNumberInt > list.length || rowNumberInt < 1) {
            return false;
        }
        return true;
    }

    private void listAvaliableCourseSections() {
        List<CourseSection> avaliableCourseSections = studentController.listAvailableCourseSections();
        int rowCount = 1;
        Course course;
        for (CourseSection courseSection : avaliableCourseSections) {
            course = courseSection.findCourseOfCourseSection();
            System.out.println(rowCount + ". " + course.getCourseCode() + "\t" + course.getCourseName() + "\t"
                    + courseSection.getSectionCode() + "\t"
                    + courseSection.getLecturerName() + "\t" + course.getCourseCredit());
        }

    }

    private void listSelectedCourses() {
        int rowCount = 1;
        for (SelectedCourse selectedCourse : studentController.getSelectedCourses()) {
            System.out.println(rowCount + ". " + selectedCourse.getCourse().getCourseCode() + "\t"
                    + selectedCourse.getCourse().getCourseName() + "\t"
                    + selectedCourse.getCourseSection().getSectionCode() + "\t" + selectedCourse.getStatus());
        }
    }

    private void printTimeTable(){

        String timeTable[][];
        List<String> monCourses;
        List<String> tueCourses;
        List<String> wedCourses;
        List<String> thrCourses;
        List<String> friCourses;

        timeTable = studentController.getTimeTable();

        String[] times = {"08:30-09:20", "09:30-10:20", "10:30-11:20", "11:30-12:20", "13:00 - 13:50", "14:00 - 14:50", "15:00 - 15:50", "16:00 - 16:50"}; 

        for(int i = 0; i < 8; i++){ // 8 is how many hours we have as an option
            System.out.println();
            System.out.print(times[i] + "\t"); // pritns the time
            
            //getting the courses for each day at the current hour we are printing 
            monCourses = divideIntoCourses(timeTable[0][i]);
            tueCourses = divideIntoCourses(timeTable[1][i]);
            wedCourses = divideIntoCourses(timeTable[2][i]);
            thrCourses = divideIntoCourses(timeTable[3][i]);
            friCourses = divideIntoCourses(timeTable[4][i]);
            
            int monCoursesSize = monCourses.size();
            int tueCoursesSize = tueCourses.size();
            int wedCoursesSize = wedCourses.size();
            int thrCoursesSize = thrCourses.size();
            int friCoursesSize = friCourses.size();
            // traversing through the days and printing the courses line by line until all of the courses are printed
            for(int j = 0; j < monCoursesSize || j < tueCoursesSize || j < wedCoursesSize || j < thrCoursesSize || j < friCoursesSize; j++){
                
                if(monCourses.get(j) != null){
                    System.out.print(monCourses.get(j) + "\t");
                }
                else{
                    System.out.print("    \t");
                }

                if(tueCourses.get(j) != null){
                    System.out.print(tueCourses.get(j) + "\t");
                }
                else{
                    System.out.print("    \t");
                }

                if(wedCourses.get(j) != null){
                    System.out.print(wedCourses.get(j) + "\t");
                }
                else{
                    System.out.print("    \t");
                }

                if(thrCourses.get(j) != null){
                    System.out.print(thrCourses.get(j) + "\t");
                }
                else{
                    System.out.print("    \t");
                }

                if(friCourses.get(j) != null){
                    System.out.print(friCourses.get(j) + "\t");
                }
                else{
                    System.out.print("    \t");
                }

                System.out.print("\n");
            }
            System.out.println("__________________________________________________________________________________________");
            
        }
    
    }

    //divides the courses string which is separated with "-" into a courses list
    private List<String> divideIntoCourses(String str){
        List<String> courses = new ArrayList<>();
        String temp = ""; 
        int strLength = str.length();
        for(int i = 0; i < strLength; i++){
            if( str.charAt(i) != '-' ){    // TODO: according to selin we have at the end also "-" this might cause a problem for alignment
                temp += str.charAt(i);     // fix this if it causes a problem
            }
            else{
                courses.add(temp);
                temp = "";
            }
        }

        return courses;
    }

}