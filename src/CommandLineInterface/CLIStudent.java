package CommandLineInterface;

import java.util.*;

import controllers.StudentController;
import models.Course;
import models.SelectedCourse;
import models.Student;
import utils.DatabaseManager;
import models.CourseSection;

public class CLIStudent {

    Stack<Integer> pageNumeber;
    private Scanner scanner;
    private controllers.StudentController StudentController;
    private Student currentStudent;
    private List<SelectedCourse> avaliableCourseSections;
    private List<CourseSection> selectedCourseSections;

    public CLIStudent(Student student) {
        currentStudent = student;
        StudentController = new StudentController(currentStudent);
        scanner = new Scanner(System.in);
    }

    public void menuPage() {

        // scanner = new Scanner(System.in);

        System.out.println(
                " Menu\n" +
                        "********\n" +
                        "  1. My Courses\n" +
                        "  2. Log out\n\n" +
                        "Press q to quit");

        String str = scanner.nextLine();
        if (str.equals("q")) {
            DatabaseManager.getInstance().saveToDatabase();
            System.exit(0);
        } else if (str.equals("1")) {

            myCoursesPage(currentStudent.listAvailableCourses());
        } else if (str.equals("2")) {
            DatabaseManager.getInstance().saveToDatabase();
            return;
        } else {
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            menuPage();
        }

    }

    public void myCoursesPage(List<Course> courses) {

        scanner = new Scanner(System.in);

        System.out.println(
                " My Courses\n" +
                        "**************\n" +
                        "  Code\t Name\t Section\t Status\n" +
                        "  ____\t ____\t _______\t ______");
        listSelectedCourses(currentStudent);
        System.out.println(
                "\n\n1. Add Course\n" +
                        "2. Delete Course\n" +
                        "3. Send To Approval");

        System.out.println("Press b to go back");
        System.out.println("Press q to quit");

        String str = scanner.nextLine();

        if (str.equals("1")) {
            addCoursePage(currentStudent.listAvailableCourses());
        } else if (str.startsWith("2")) {
            System.out.println("Enter the course code you want to delete : ");
            str = scanner.nextLine();
            if ((str.charAt(0) < 58 && str.charAt(0) > 47) && str.length() == 1) {
                if (deleteCourse(str.charAt(0), currentStudent.getSelectedCourses())) {
                    System.out.println("Course deleted successfully");
                } else {
                    System.out.println("delete failed");
                }
            } else {
                System.out.println("Invalid input");
                System.out.println("\n\n\n");
            }
            myCoursesPage(currentStudent.listAvailableCourses());
        } else if (str.equals("3")) {
            StudentController.sendSelectedCoursesToApproval();
            myCoursesPage(currentStudent.listAvailableCourses());
        } else if (str.equals("b")) {
            DatabaseManager.getInstance().saveToDatabase();
            menuPage();
        } else if (str.equals("q")) {
            DatabaseManager.getInstance().saveToDatabase();
            System.exit(0);
        } else {
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            myCoursesPage(courses);

        }

    }

    public void addCoursePage(List<Course> courses) {
        scanner = new Scanner(System.in);

        System.out.println(
                " Avaliable Courses(To Add)\n" +
                        "**************\n" +
                        "  Code\t Name\t Section\t Instructor\t Credit\n" +
                        "  ____\t ____\t _______\t __________\t ______");
        listAvaliableCourseSections(courses);
        System.out.println("\n\npress b to go back");
        System.out.println("press q to quit");
        System.out.println("Enter the course code you want to add : ");

        String str = scanner.nextLine();

        if (str.equals("b")) {
            myCoursesPage(currentStudent.listAvailableCourses());
            DatabaseManager.getInstance().saveToDatabase();
        } else if (str.equals("q")) {
            DatabaseManager.getInstance().saveToDatabase();
            System.exit(0);
        } else if ((str.charAt(0) < 58 && str.charAt(0) > 47) && str.length() == 1) {
            if (addCourse(str.charAt(0), currentStudent.listAvailableCourses())) {
                System.out.println("Course added successfully");
            } else {
                System.out.println("add failed");
            }

            addCoursePage(currentStudent.listAvailableCourses());
        } else {
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            addCoursePage(currentStudent.listAvailableCourses());
        }
    }

    public boolean addCourse(char c, List<Course> courses) {
        int index = Integer.parseInt(c + "");
        if (index <= courses.size() && checkIfAlreadyAdded(avaliableCourseSections.get(index - 1))) {
            StudentController.addSelectedCourse(avaliableCourseSections.get(index - 1));
            return true;
        } else {
            return false;
        }
    }

    /*
     * public void addCoursePage(List<Course> courses) {
     * scanner = new Scanner(System.in);
     * 
     * System.out.println(
     * " Avaliable Courses(To Add)\n" +
     * "**************\n" +
     * "  Code\t Name\t Section\t Instructor\t Credit\n" +
     * "  ____\t ____\t _______\t __________\t ______");
     * listAvaliableCourseSections(courses);
     * System.out.println("\n\npress b to go back");
     * System.out.println("press q to quit");
     * System.out.println("Enter the course code you want to add : ");
     * 
     * String str = scanner.nextLine();
     * String courseCode = str.replaceAll(" ", "");
     * char[] courseCodeArray = courseCode.toCharArray();
     * 
     * if (str.equals("b")) {
     * myCoursesPage(currentStudent.listAvailableCourses());
     * DatabaseManager.getInstance().saveToDatabase();
     * } else if (str.equals("q")) {
     * DatabaseManager.getInstance().saveToDatabase();
     * System.exit(0);
     * } else if (checkCourseSelectionInput(courseCodeArray) &&
     * (!str.startsWith(" "))
     * && (str.charAt(0) < 58 && str.charAt(0) > 47)) {
     * ArrayList<Integer> courseCodeIntArray = convertCourseSelectionInputToInt(str,
     * 0);
     * int coursesLength = avaliableCourseSections.size();
     * for (int i = 1; i <= coursesLength; i++) {
     * if (courseCodeIntArray.contains(i) &&
     * checkIfAlreadyAdded(avaliableCourseSections.get(i - 1)))
     * StudentController.addSelectedCourse(avaliableCourseSections.get(i - 1));
     * }
     * addCoursePage(currentStudent.listAvailableCourses());
     * } else {
     * System.out.println("Invalid input");
     * System.out.println("\n\n\n");
     * addCoursePage(currentStudent.listAvailableCourses());
     * }
     * }
     */

    public boolean checkIfAlreadyAdded(SelectedCourse selectedCourse) {

        for (CourseSection currentCourse : selectedCourseSections) {
            if (currentCourse.getSectionCode().contains(selectedCourse.getCourse().getCourseName()))
                return false;
        }
        return true;
    }

    /*
     * public boolean deleteCourse(String str, List<SelectedCourse> courses){
     * 
     * String courseCode = str.replaceAll(" ", "");
     * char[] courseCodeArray = courseCode.toCharArray();
     * 
     * if(checkCourseSelectionInput(courseCodeArray)){
     * ArrayList<Integer> courseCodeIntArray = convertCourseSelectionInputToInt(str,
     * 0);
     * int coursesLength = courses.size();
     * for(int i = 1; i<=coursesLength; i++){
     * if(courseCodeIntArray.contains(i))
     * StudentController.removeSelectedCourse(currentStudent.getSelectedCourses().
     * get(i-1));
     * }
     * return true;
     * }
     * else{
     * return false;
     * }
     * 
     * }
     */
    public boolean deleteCourse(char c, List<SelectedCourse> courses) {
        int index = Integer.parseInt(c + "");
        if (index <= courses.size()) {
            StudentController.removeSelectedCourse(currentStudent.getSelectedCourses().get(index - 1));
            return true;
        } else {
            return false;
        }
    }

    /*
     * private boolean checkCourseSelectionInput(char[] courseCodeArray) {
     * 
     * for (char c : courseCodeArray) {
     * if (!Character.isLetterOrDigit(c)) {
     * return false;
     * }
     * }
     * return true;
     * }
     * 
     * 
     * private ArrayList<Integer> convertCourseSelectionInputToInt(String string,
     * int startingPoint) {
     * string = string.substring(startingPoint);
     * int strLength = string.length();
     * String tempString = "";
     * ArrayList<Integer> courseCodeIntArray = new ArrayList<Integer>();
     * 
     * for (int i = 0; i < strLength; i++) {
     * if (string.charAt(i) == ' ') {
     * courseCodeIntArray.add(Integer.parseInt(tempString));
     * tempString = "";
     * 
     * } else {
     * tempString += string.charAt(i);
     * }
     * }
     * System.out.println(Integer.parseInt(tempString));
     * courseCodeIntArray.add(Integer.parseInt(tempString));
     * tempString = "";
     * 
     * return courseCodeIntArray;
     * }
     */
    private void listAvaliableCourseSections(List<Course> courses) {
        int i = 0;
        int sectionLength = 0;
        avaliableCourseSections = new ArrayList<SelectedCourse>();
        for (Course course : courses) {
            i++;
            sectionLength = course.acquireAvailableSections().size();
            for (int j = 0; j < sectionLength; j++) {
                System.out.println(i + ". " + course.getCourseCode() + "\t" + course.getCourseName() + "\t"
                        + course.acquireAvailableSections().get(j) + "\t"
                        + course.getCourseSections().get(j).getLecturerName() + "\t" + course.getCourseCredit());
                i++;
                avaliableCourseSections.add(new SelectedCourse(course, course.acquireAvailableSections().get(j)));
            }
        }
    }

    private void listSelectedCourses(Student student) {
        int i = 0;
        selectedCourseSections = new ArrayList<CourseSection>();
        for (SelectedCourse course : student.getSelectedCourses()) {
            i++;
            if (course.getCourseSection() != null) {
                System.out.println(i + ". " + course.getCourseSection().getSectionCode() + "\t"
                        + course.getCourse().getCourseName() + "\t" + course.getCourseSection() + "\t"
                        + course.getStatus());
                selectedCourseSections.add(course.getCourseSection());
            }
        }
    }
}
