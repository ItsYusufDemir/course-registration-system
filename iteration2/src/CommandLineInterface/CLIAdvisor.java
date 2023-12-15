package iteration2.src.CommandLineInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import iteration2.src.controllers.AdvisorController;
import iteration2.src.enums.Color;
import iteration2.src.enums.CourseStatus;
import iteration2.src.models.Advisor;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;
import iteration2.src.utils.Util;

public class CLIAdvisor {

    private Advisor advisor;
    private AdvisorController advisorController;
    Scanner input;

    public CLIAdvisor(Advisor advisor) {
        this.advisor = advisor;
        advisorController = new AdvisorController(advisor);
    }

    public void menuPage() {

        boolean isInvalid = false;
        input = new Scanner(System.in);
        AdvisorController advisorController = new AdvisorController(advisor);
        while (true) {
            Util.clearScreen();
            if (advisorController.getNotification() != null && advisorController.getNotification().size() > 0) {
                Util.paintTextln(" Notifications\n " +
                        "**************", Color.YELLOW);
                for (String string : advisorController.getNotification())
                    Util.paintTextln(string, Color.RED);
                advisorController.clearNotifications();
            }

            System.out.println("\n\n");

            if (!isInvalid) {
                isInvalid = false;
                // while(true) {
                System.out.println(" Menu ");
                System.out.println("********");
                System.out.println("    1. Student List");
                System.out.println("    2. Log out");
            } else {
                isInvalid = false;
            }
            boolean shouldQuit = false;
            String choice = input.nextLine();

            if (choice.equals("1")) {
                List<Student> students = advisorController.getStudentListOrderByStatus();
                shouldQuit = showStudentPage(students);

                if (shouldQuit) {
                    break;
                } else {
                    continue;
                }
            } else if (choice.equals("2")) {
                advisorController.logOut();
                break;
            } else {
                Util.sendFeedback("Invalid choice", Color.RED);
                isInvalid = true;
            }
        }
    }

    public boolean showStudentPage(List<Student> students) {
        boolean isInvalid = false;
        while (true) {
            if (!isInvalid) {
                isInvalid = false;

                System.out.println(" Student List");
                System.out.println("****************");
                /*
                 * System.out.println("   Number      Name");
                 * System.out.println("   ------      -----");
                 */
                System.out.printf("%10s    %30s\n", "Number", "Name");
                for (int i = 0; i < students.size(); i++) {

                    System.out.printf("%d.  %10s    %30s\n", i + 1, students.get(i).getUserId(),
                            students.get(i).getFirstName() + " " + students.get(i).getLastName());
                    /*
                     * System.out.println((i + 1) + ". " + students.get(i).getUserId() + "    "
                     * + students.get(i).getFirstName() + " " + students.get(i).getLastName());
                     */
                }

                System.out.println("Select Student:");
                System.out.println("Press b to back");
                System.out.println("Press q to quit");
            } else {
                isInvalid = false;
            }
            List<SelectedCourse> selectedCoursesThatStatusIsPending = new ArrayList<SelectedCourse>();

            String choice = input.nextLine();
            try {
                int choice2 = Integer.parseInt(choice);
                if (choice2 > 0 && choice2 <= students.size()) {
                    for (int i = 0; i < students.get(choice2 - 1).getSelectedCourses().size(); i++) {
                        if (students.get(choice2 - 1).getSelectedCourses().get(i).getStatus() == CourseStatus.PENDING) {
                            selectedCoursesThatStatusIsPending
                                    .add(students.get(choice2 - 1).getSelectedCourses().get(i));
                        }
                    }
                    boolean shouldQuit = coursesOfStudentPage(students.get(choice2 - 1));
                    if (shouldQuit) {
                        return true;
                    }
                } else {
                    Util.sendFeedback("Invalid choice. Try again", Color.RED);
                    isInvalid = true;
                }
            } catch (NumberFormatException e) {
                if (choice.equals("b")) {
                    return false;
                } else if (choice.equals("q")) {
                    advisorController.logOut();
                    return true;
                } else {
                    Util.sendFeedback("Invalid choice. Try again", Color.RED);
                    isInvalid = true;
                }
            }
        }
    }

    public boolean coursesOfStudentPage(Student student) {

        boolean isInvalid = false;
        List<SelectedCourse> courses = student.fetchSelectedCoursesForAdvisor();
        while (true) {
            if (!isInvalid) {
                isInvalid = false;

                Util.clearScreen();

                System.out.println(
                        " Student: " + student.getFirstName() + " " + student.getLastName() + "'s TimeTable \n");
                Util.printTimeTable(student.createTimeTable());

                System.out.println(" Courses Of The Student ");
                System.out.println("***************************");
                /*
                 * System.out.println("   Code         Number        Section       Status ");
                 * System.out.println("  ------        ------         ------       ------ ");
                 */
                System.out.printf("    %10s    %50s    %15s    %15s\n", "Code", "Name", "Section", "Status");
                System.out.printf("    %10s    %50s   %15s     %15s\n", "-----", "------", "--------", "------");

                for (int i = 0; i < courses.size(); i++) {
                    /*
                     * System.out.println((i + 1) + ". " +
                     * courses.get(i).getCourse().getCourseCode() + "    "
                     * + courses.get(i).getCourse().getCourseName() + "    "
                     * + courses.get(i).getCourseSection().getSectionCode() + "    " +
                     * courses.get(i).getStatus());
                     */
                    System.out.printf("%d.  %10s    %50s    %15s             ", i + 1,
                            courses.get(i).getCourse().getCourseCode(),
                            courses.get(i).getCourse().getCourseName(),
                            courses.get(i).getCourseSection().getSectionCode());

                    if (courses.get(i).getStatus() == CourseStatus.APPROVED)
                        Util.paintText(courses.get(i).getStatus() + "\n", Color.GREEN);
                    else if (courses.get(i).getStatus() == CourseStatus.PENDING)
                        Util.paintText(courses.get(i).getStatus() + "\n", Color.YELLOW);
                    else if (courses.get(i).getStatus() == CourseStatus.DENIED)
                        Util.paintText(courses.get(i).getStatus() + "\n", Color.RED);
                    else
                        System.out.print(courses.get(i).getStatus() + "\n");
                }

                System.out.println("Press b to back");
                System.out.println("Press q to quit");
                System.out.println("Select Course: ");
            } else {
                isInvalid = false;
            }
            String choice = input.nextLine();
            try {
                int choiceInt = Integer.parseInt(choice);
                if (choiceInt > 0 && choiceInt <= courses.size()) {
                    System.out.println("Press a to approve");
                    System.out.println("Press d to deny");

                    String choice2 = input.nextLine();

                    if (choice2.equals("a")) {
                        advisorController.approveCourse(student, courses.get(choiceInt - 1));
                        System.out.println();
                    } else if (choice2.equals("d")) {
                        advisorController.denyCourse(student, courses.get(choiceInt - 1));
                    } else if (choice.equals("b")) {
                        return false;
                    } else if (choice.equals("q")) {
                        advisorController.logOut();
                        return true;
                    } else {
                        Util.sendFeedback("Invalid choice. Try again", Color.RED);
                        isInvalid = true;
                    }
                } else {
                    ;
                    Util.sendFeedback("Invalid choice!", Color.RED);
                }
            } catch (NumberFormatException e) {
                if (choice.equals("b")) {
                    return false;
                } else if (choice.equals("q")) {
                    advisorController.logOut();
                    return true;
                } else {
                    Util.sendFeedback("Invalid choice. Try again", Color.RED);
                    isInvalid = true;
                }
            }

        }

    }
}
