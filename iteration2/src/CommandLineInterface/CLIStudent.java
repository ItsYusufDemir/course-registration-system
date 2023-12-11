package iteration2.src.CommandLineInterface;

import java.util.*;

import javax.xml.crypto.Data;

import iteration2.src.controllers.StudentController;
import iteration2.src.models.Course;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;
import iteration2.src.utils.DatabaseManager;
import iteration2.src.models.CourseSection;

public class CLIStudent {

    private Scanner scanner;
    private StudentController StudentController;
    private Student currentStudent;
    private List<SelectedCourse> avaliableCourseSections;
    private List<CourseSection> selectedCourseSections;
    private int numberOfAvaliableCourses;

    public CLIStudent(Student student) {
        currentStudent = student;
        StudentController = new StudentController(currentStudent);
        scanner = new Scanner(System.in);
    }

    public void menuPage() {

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
            System.out.println("Enter the row number of the course you want to delete : ");
            str = scanner.nextLine();
            if ((str.charAt(0) < 58 && str.charAt(0) > 47) && str.length() == 1) {
                if (deleteCourse(str.charAt(0), currentStudent.getSelectedCourses())) {
                    System.out.println("Course deleted successfully");
                    DatabaseManager.getInstance().saveToDatabase();
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
        System.out.println("Enter the row number of the course you want to add : ");

        String str = scanner.nextLine();

        if (str.equals("b")) {
            myCoursesPage(currentStudent.listAvailableCourses());
            DatabaseManager.getInstance().saveToDatabase();
        } else if (str.equals("q")) {
            DatabaseManager.getInstance().saveToDatabase();
            System.exit(0);
        } else if ((str.charAt(0) < 58 && str.charAt(0) > 47) && str.length() == 1) {
            if (addCourse(str.charAt(0))) {
                System.out.println("Course added successfully");
                DatabaseManager.getInstance().saveToDatabase();
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

    public boolean addCourse(char c) {
        int index = Integer.parseInt(c + "");
        if (index <= numberOfAvaliableCourses && checkIfAlreadyAdded(avaliableCourseSections.get(index - 1))) {
            StudentController.addSelectedCourse(avaliableCourseSections.get(index - 1));
            return true;
        } else {
            return false;
        }
    }


    public boolean checkIfAlreadyAdded(SelectedCourse selectedCourse) {

        for (int i = 0; i < currentStudent.getSelectedCourses().size(); i++) {

            if (selectedCourse.getCourse().getCourseCode().equals(currentStudent.getSelectedCourses().get(i).getCourse().getCourseCode())) {
                return false;
            }
            
        }
        return true;
    }


    public boolean deleteCourse(char c, List<SelectedCourse> courses) {
        int index = Integer.parseInt(c + "");
        String courseWeWantToDelete = currentStudent.getSelectedCourses().get(index-1).getStatus().toString();
        if (index <= courses.size() && ( courseWeWantToDelete.equalsIgnoreCase("DRAFT"))) {
            StudentController.removeSelectedCourse(currentStudent.getSelectedCourses().get(index - 1));
            return true;
        } else {
            return false;
        }
    }

    private void listAvaliableCourseSections(List<Course> courses) {
        int i = 1;
        int sectionLength = 0;
        avaliableCourseSections = new ArrayList<SelectedCourse>();

        for (Course course : courses) {
            
            sectionLength = course.acquireAvailableSections().size();
            
            for (int j = 0; j < sectionLength; j++) {
                System.out.println(i + ". " + course.getCourseCode() + "\t" + course.getCourseName() + "\t"
                        + course.acquireAvailableSections().get(j).getSectionCode() + "\t"
                        + course.getCourseSections().get(j).getLecturerName() + "\t" + course.getCourseCredit());
                i++;
                avaliableCourseSections.add(new SelectedCourse(course, course.acquireAvailableSections().get(j)));
                numberOfAvaliableCourses++;
            }
        }
    }

    private void listSelectedCourses(Student student) {
        int i = 0;
        selectedCourseSections = new ArrayList<CourseSection>();
        for (SelectedCourse course : student.getSelectedCourses()) {
            i++;
            if (course.getCourseSection() != null) {
                System.out.println(i + ". " + course.getCourse().getCourseCode() + "\t"
                        + course.getCourse().getCourseName() + "\t" + course.getCourseSection().getSectionCode() + "\t"
                        + course.getStatus());
                selectedCourseSections.add(course.getCourseSection());
            }
        }
    }
}
