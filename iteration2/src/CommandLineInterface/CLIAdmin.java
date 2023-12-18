package iteration2.src.CommandLineInterface;

import java.util.*;
import java.util.List;

import iteration2.src.enums.CourseType;
import iteration2.src.interfaces.Color;
import iteration2.src.models.Course;
import iteration2.src.models.CourseSection;
import iteration2.src.models.Prerequisite;
import iteration2.src.utils.DatabaseManager;
import iteration2.src.controllers.AdminController;
import iteration2.src.utils.Util;

public class CLIAdmin {

    private Scanner scanner;
    private AdminController adminController;

    public CLIAdmin(AdminController adminContoller) {
        this.adminController = adminContoller;
        scanner = new Scanner(System.in);
    }

    public void menuPage() {
        Util.clearScreen();
        System.out.println(
                " Menu\n" +
                        "********\n" +
                        "  1. Course list\n" +
                        "  2. Constraint Settings\n\n" +
                        "Press q to quit\n" +
                        "Press corresponding row number to review and make changes for the list you want.\n");

        String str = scanner.nextLine();
        if (str.equals("q")) {
            DatabaseManager.getInstance().saveToDatabase();
            Util.clearScreen();
            adminController.logOut();
        } else if (str.equals("1")) {
            Util.clearScreen();
            courseListPage();
        } else if (str.equals("2")) {
            Util.clearScreen();
            constraintPage();
        } else {
            Util.sendFeedback("Invalid input", Color.RED);
            menuPage();
        }

    }

    private void courseListPage() {
        Util.clearScreen();
        scanner = new Scanner(System.in);
        List<Course> courses = adminController.getCourseList();

        System.out.println(
                " All Courses\n" +
                        "**************\n" +
                        "  Code\t\t\t\t Name\t \n" +
                        "  ____\t ____\t");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println(
                    "  " + (i + 1) + ". " + courses.get(i).getCourseCode() + "\t " + courses.get(i).getCourseName());
        }
        System.out.println(
                "\n\nPress c to create a new course\n" +
                        "Press d to delete course\n");
        System.out.println("Press b to go back");
        System.out.println("Press q to quit");

        String str = scanner.nextLine();

        if (str.equals("c")) {
            createNewCoursePage();
            courseListPage();
        } else if (str.startsWith("d")) {
            System.out.println("Enter the row number of the course you want to delete : ");
            str = scanner.nextLine();
            if (Util.isValidNumber(str) && Util.checkIfValidRowNumber(str, courses.toArray())) {
                Course course = courses.get(Integer.parseInt(Util.getRowNumberFromInput(str)) - 1);
                if (adminController.deleteCourse(course)) {
                    Util.clearScreen();
                    Util.sendFeedback("Course deleted successfully", Color.GREEN);
                } else {
                    Util.clearScreen();
                    Util.sendFeedback("Delete failed", Color.RED);
                }
            } else {
                Util.sendFeedback("Invalid input", Color.RED);
            }
            courseListPage();
        } else if (str.equals("b")) {
            Util.clearScreen();
            menuPage();
        } else if (str.equals("q")) {
            Util.clearScreen();
            adminController.logOut();
        } else {
            Util.sendFeedback("Invalid input", Color.RED);
            courseListPage();
        }

    }

    private void constraintPage() {
        Util.clearScreen();
        scanner = new Scanner(System.in);
        HashMap<Integer, String> editedAttributes = adminController.getConstraints();

        System.out.println(
                " Constraint Settings\n" +
                        "**************\n");
        System.out.println("1. Add-Drop: " + editedAttributes.get(2));
        System.out.println("2. Max Number Of Courses That Can Be Taken: " + editedAttributes.get(1));
        System.out.println("3. Min Required ECTS For Term Project: " + editedAttributes.get(3));
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
                editedAttributes.put(2, "true");
                System.out.println("Add-Drop changed successfully");
            } else if (str.equals("false")) {
                editedAttributes.put(2, "false");
                System.out.println("Add-Drop changed successfully");
            } else {
                Util.sendFeedback("Invalid input", Color.RED);
            }
            adminController.editConstraint(editedAttributes);
            constraintPage();
        } else if (str.startsWith("2")) {
            System.out.println("Enter the new value for Max Number Of Courses That Can Be Taken: ");
            str = scanner.nextLine();
            if (Util.isValidNumber(str)) {
                editedAttributes.put(1, str);
                System.out.println("Max Number Of Courses That Can Be Taken changed successfully");
            } else {
                Util.sendFeedback("Invalid input", Color.RED);
            }
            adminController.editConstraint(editedAttributes);
            constraintPage();
        } else if (str.startsWith("3")) {
            System.out.println("Enter the new value for Min Required ECTS For Term Project: ");
            str = scanner.nextLine();
            if (Util.isValidNumber(str)) {
                editedAttributes.put(3, str);
                System.out.println("Min Required ECTS For Term Project changed successfully");
            } else {
                Util.sendFeedback("Invalid input", Color.RED);
            }
            adminController.editConstraint(editedAttributes);
            constraintPage();
        } else if (str.equals("b")) {
            Util.clearScreen();
            menuPage();
        } else if (str.equals("q")) {
            Util.clearScreen();
            adminController.logOut();
        } else {
            Util.sendFeedback("Invalid input", Color.RED);
            constraintPage();
        }

    }

    private boolean createNewCoursePage() {

        ArrayList<Course> prerequisiteCourses = new ArrayList<Course>();
        ArrayList<CourseSection> courseSections = new ArrayList<CourseSection>();
        ArrayList<String> sectionTimeList = new ArrayList<String>();
        ArrayList<String> sectionDateList = new ArrayList<String>();
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
        String currentInput;

        Util.paintTextln("********************Create New Course Page********************\n", Color.GREEN);

        System.out.println("-> Enter the course information for the fields.");

        while (true) {
            System.out.print("1.\tCourse Credit: ");
            try {
                currentInput = scanner.nextLine();
                courseCredit = Integer.parseInt(currentInput);
                break;
            } catch (Exception e) {
                Util.paintTextln("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        while (true) {
            System.out.print("2.\tCourse ECTS: ");
            try {
                currentInput = scanner.nextLine();
                courseECTS = Integer.parseInt(currentInput);
                break;
            } catch (Exception e) {

                Util.paintTextln("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        System.out.print("3.\tCourse Name: ");
        courseName = scanner.nextLine();

        System.out.print("4.\tCourse Code: ");
        courseCode = scanner.nextLine();

        while (true) {
            System.out.print("5.\tCourse Semester: ");
            try {
                currentInput = scanner.nextLine();
                givenSemester = Integer.parseInt(currentInput);
                if (givenSemester > 10) {
                    Util.paintTextln("Invalid input ! Computer Engineering faculty has maximum 10 semester\n"
                            + "Please enter integer between 1-10", Color.RED);
                }
                break;
            } catch (Exception e) {
                Util.paintTextln("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        while (true) {
            System.out.print(
                    "6.\tCourse Type: Compulsory(1), Non-technical Elective(2), Technical Elective(3), University Elective(4), Faculty Elective(5): ");
            try {
                currentInput = scanner.nextLine();
                courseTypeCode = Integer.parseInt(currentInput);

                if (courseTypeCode < 1 || courseTypeCode > 5) {
                    Util.paintTextln("Invalid input ! Please enter an integer value between 1-5", Color.RED);
                    continue;
                }

                break;
            } catch (Exception e) {
                Util.paintTextln("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        switch (courseTypeCode) {
            case 1:
                courseType = CourseType.COMPULSORY;
                break;
            case 2:
                courseType = CourseType.NONTECHNICAL_ELECTIVE;
                break;
            case 3:
                courseType = CourseType.TECHNICAL_ELECTIVE;
                break;
            case 4:
                courseType = CourseType.UNIVERSITY_ELECTIVE;
                break;
            case 5:
                courseType = CourseType.FACULTY_ELECTIVE;
                break;
            default:
                courseType = CourseType.COMPULSORY;
        }

        while (true) {
            System.out.print("7.\t Number of Prerequisite Courses: ");
            try {
                currentInput = scanner.nextLine();
                numberOfPrerequisiteCourses = Integer.parseInt(currentInput);
                break;
            } catch (Exception e) {
                Util.paintTextln("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        System.out.println("Write course codes of prerequisite courses one by one");
        String prerequisiteCourseCode;
        Course prerequisiteCourse;
        for (int i = 1; i <= numberOfPrerequisiteCourses; i++) {
            System.out.print(i + ".\t");
            prerequisiteCourseCode = scanner.nextLine();
            prerequisiteCourse = findCourseByCourseCode(prerequisiteCourseCode);
            if (prerequisiteCourse != null) {
                prerequisiteCourses.add(prerequisiteCourse);
            } else {
                Util.paintTextln("There is no course with this course code! Try again.", Color.RED);
                i--;
            }
        }

        while (true) {
            System.out.print("8.\tNumber Of Sections: ");
            try {
                currentInput = scanner.nextLine();
                numberOfCourseSections = Integer.parseInt(currentInput);
                break;
            } catch (Exception e) {
                Util.paintTextln("Invalid input ! Please enter an integer value", Color.RED);
            }
        }

        for (int i = 1; i <= numberOfCourseSections; i++) {
            System.out.println("Section " + i);

            while (true) {
                System.out.print("1.\tStudent Capacity: ");
                try {
                    currentInput = scanner.nextLine();
                    studentCapacity = Integer.parseInt(currentInput);
                    break;
                } catch (Exception e) {
                    Util.paintTextln("Invalid input ! Please enter an integer value", Color.RED);
                }
            }

            System.out.print("2.\tLecturer Name: ");
            lecturerName = scanner.nextLine();

            System.out.println("\nFollowing information for section day and time");
            System.out.println("Input Format for Section Day:\tMonday, Monday, Tuesday");
            System.out.println("Input Format for Section Time:\t08:30-09:20, 09:30-10:30, 08:30-09:20\n");

            while (true) {
                while (true) {
                    System.out.print("3.\tSection Day: ");
                    sectionDate = scanner.nextLine();
                    sectionDateList = Util.makeArrayList(",", sectionDate);
                    if (Util.isInputFormatTrueForDay(sectionDateList)) {
                        break;
                    } else {
                        Util.paintTextln("Invalid input format! Please enter in this format: \tMonday, Monday, Tuesday",
                                Color.RED);
                    }
                }

                while (true) {
                    System.out.print("4.\tSection Time: ");
                    sectionTime = scanner.nextLine();
                    sectionTimeList = Util.makeArrayList(",", sectionTime);

                    if (Util.isInputFormatTrueForTime(sectionTimeList)) {
                        break;
                    } else {
                        Util.paintTextln(
                                "Invalid input format! Please enter in this format: \t08:30-09:20, 09:30-10:30, 08:30-09:20",
                                Color.RED);
                    }
                }

                if (sectionTimeList.size() == sectionDateList.size()) {
                    break;
                } else {
                    Util.paintTextln("Number of entered days must be equal to number of times entered", Color.RED);
                }
            }

            System.out.print("5.\tClassroom: ");
            classroom = scanner.nextLine();

            System.out.print("6.\tSection Code: ");
            sectionCode = scanner.nextLine();

            courseSections.add(new CourseSection(studentCapacity, lecturerName, sectionTimeList, sectionDateList,
                    classroom, sectionCode));
        }

        Prerequisite prerequisite = new Prerequisite(prerequisiteCourses);

        course = new Course(courseCredit, courseECTS, givenSemester, courseName, courseCode, prerequisite,
                courseSections, courseType);

        Util.clearScreen();

        if (adminController.createCourse(course) != null) {
            Util.sendFeedback("SUCCESS: " + courseCode + " is created.", Color.GREEN);
            return true;
        } else {
            Util.sendFeedback("FAIL! " + courseCode + " can't created.", Color.RED);
            return false;
        }

    }

    private Course findCourseByCourseCode(String courseCode) {
        return adminController.findCourseByCourseCode(courseCode);
    }
}
