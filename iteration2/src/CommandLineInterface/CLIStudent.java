package iteration2.src.CommandLineInterface;

import java.util.*;

import iteration2.src.utils.Util;
import iteration2.src.controllers.StudentController;
import iteration2.src.enums.CourseStatus;
import iteration2.src.interfaces.Color;
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
            Util.clearScreen();

            if (studentController.getNotification() != null) {
                Util.paintTextln(" Notification\n " +
                        "**************", Color.YELLOW);
                for (String string : studentController.getNotification())
                    Util.paintTextln(string, Color.RED);
                studentController.clearNotifications();
            }

            System.out.println("\n\n");
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
                Util.sendFeedback("Invalid Input: " + str, Color.RED);
            }

        }

    }

    private void showMyCoursesPage() {
        shouldQuit = true;

        while (shouldQuit) {
            Util.clearScreen();
            System.out.println(
                    " My Courses\n" +
                            "**************\n");
            System.out.printf("    %10s    %50s    %15s    Status\n", "Code", "Name", "Section");
            // " Code Name Section Status\n" +
            // " ____ ____ _______ ______");
            System.out.printf("    %10s    %50s    %15s    ______\n", "____", "____", "_______");

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
                    if (Util.validateNumber(str, studentController.getSelectedCourses().toArray())) {
                        if (deleteCourse(str)) {
                            Util.sendFeedback("Course successfully deleted", Color.GREEN);
                        } else {
                            Util.sendFeedback("Course deletion failed", Color.RED);
                        }
                    } else {
                        throw new Exception("Invalid input: " + str);
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
                Util.sendFeedback(e.getLocalizedMessage(), Color.RED);
            }

        }
    }

    private void showAddCoursePage() {
        shouldQuit = true;

        while (shouldQuit) {
            Util.clearScreen();
            System.out.println(
                    " Avaliable Courses(To Add)\n" +
                            "**************\n");
            System.out.printf("    %10s    %50s    %15s    %20s    %s\n", "Code", "Name", "Section", "Instructor",
                    "Credit");
            System.out.printf("    %10s    %50s    %15s    %20s    %s\n", "____", "____", "_______", "__________",
                    "______");

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
                } else if (Util.validateNumber(str, studentController.getAvaliableCourseSections().toArray())) {
                    if (addCourse(str)) {
                        Util.sendFeedback("Course successfully added", Color.GREEN);
                    } else {
                        Util.sendFeedback("Course addition failed", Color.RED);
                    }
                }

                else {
                    throw new Exception("Invalid input: " + str);
                }

            } catch (Exception e) {
                Util.sendFeedback(e.getLocalizedMessage(), Color.RED);
            }

        }

    }

    private void showTimetablePage() {
        shouldQuit = true;
        while (shouldQuit) {
            Util.clearScreen();

            System.out.println(" Timetable\n" +
                    "***********\n" +
                    " Hours/Days    Monday    Tuesday    Wednesday    Thursday    Friday\n" +
                    " __________    ______    _______    _________    ________    ______\n");
            Util.printTimeTable(studentController.getTimeTable());

            System.out.println("\n\npress b to go back");
            System.out.println("press q to quit");
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
                Util.sendFeedback(e.getLocalizedMessage(), Color.RED);
            }
        }
    }

    private boolean addCourse(String str) {
        int rowNumber = Integer.parseInt(Util.getRowNumberFromInput(str));
        CourseSection selectedCourseSection = studentController.getAvaliableCourseSections().get(rowNumber - 1);
        if (studentController.addSelectedCourse(
                new SelectedCourse(selectedCourseSection.findCourseOfCourseSection(), selectedCourseSection))) {
            return true;
        }
        return false;
    }

    private boolean deleteCourse(String str) {
        int rowNumber = Integer.parseInt(Util.getRowNumberFromInput(str));
        if (studentController.removeSelectedCourse(studentController.getSelectedCourses().get(rowNumber - 1))) {
            return true;
        }
        return false;
    }

    private void listAvaliableCourseSections() {
        List<CourseSection> avaliableCourseSections = studentController.getAvailableCourseSections();
        int rowCount = 1;
        Course course;
        for (CourseSection courseSection : avaliableCourseSections) {
            course = courseSection.findCourseOfCourseSection();
            System.out.printf("%d.  %10s    %50s    %15s    %20s    %d\n", rowCount, course.getCourseCode(),
                    course.getCourseName(),
                    courseSection.getSectionCode(), courseSection.getLecturerName(), course.getCourseCredit());
            rowCount++;
        }

    }

    private void listSelectedCourses() {
        int rowCount = 1;
        for (SelectedCourse selectedCourse : studentController.getSelectedCourses()) {

            System.out.printf("%d.  %10s    %50s    %15s    ", rowCount, selectedCourse.getCourse().getCourseCode(),
                    selectedCourse.getCourse().getCourseName(),
                    selectedCourse.getCourseSection().getSectionCode());

            if (selectedCourse.getStatus() == CourseStatus.APPROVED)
                Util.paintText(selectedCourse.getStatus() + "\n", Color.GREEN);
            else if (selectedCourse.getStatus() == CourseStatus.PENDING)
                Util.paintText(selectedCourse.getStatus() + "\n", Color.YELLOW);
            else if (selectedCourse.getStatus() == CourseStatus.DENIED)
                Util.paintText(selectedCourse.getStatus() + "\n", Color.RED);
            else
                System.out.print(selectedCourse.getStatus() + "\n");
            /*
             * System.out.println(rowCount + ". " +
             * selectedCourse.getCourse().getCourseCode() + "\t"
             * + selectedCourse.getCourse().getCourseName() + "\t"
             * + selectedCourse.getCourseSection().getSectionCode() + "\t" +
             * selectedCourse.getStatus());
             */
            rowCount++;
        }
    }

}