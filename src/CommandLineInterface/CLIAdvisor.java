package CommandLineInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import controllers.AdvisorController;
import enums.CourseStatus;
import models.Advisor;
import models.Course;
import models.SelectedCourse;
import models.Student;

public class CLIAdvisor {

    private Advisor advisor;
    private AdvisorController advisorController;
    public CLIAdvisor(Advisor advisor){
        this.advisor = advisor;
        advisorController = new AdvisorController(advisor);
    }


    public void menuPage() {
        AdvisorController advisorController = new AdvisorController(advisor);
        System.out.println(" Menu ");
        System.out.println("********");
        System.out.println("    1. Student List");
        System.out.println("    2. Log out");

        boolean shouldQuit = false;
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();

        while(true) {
            if (choice.equals ("1")) {
                List<Student> students = advisorController.getStudentListOrderByStatus();
                shouldQuit = showStudentPage(students);

                if(shouldQuit) {
                    break;
                }
            }
            else if (choice.equals( "2")) {
                advisorController.logOut();
                break;
            }
            else {
                System.out.println("Invalid choice");
            }
        }
        input.close();

    }

    //boolean a dönüştür
    public boolean showStudentPage(List<Student> students) {

        Scanner input = new Scanner(System.in);
        System.out.println(" Student List");
        System.out.println("****************");
        System.out.println("   Number      Name");
        System.out.println("   ------      -----");

        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getSchoolNumber() + "    " + students.get(i).getFirstName() + " " + students.get(i).getLastName());
        }

        System.out.println("Select Student:");
        System.out.println("Press b to back");
        System.out.println("Press q to quit");

        List<SelectedCourse> selectedCoursesThatStatusIsPending = new ArrayList<SelectedCourse>();
        while (true) {
            String choice = input.nextLine();
            try {
                int choice2 = Integer.parseInt(choice);
                if (choice2 > 0 && choice2 <= students.size()) {
                    for(int i=0; i<students.get(choice2).getSelectedCourses().size();i++) {
                        if (students.get(choice2).getSelectedCourses().get(i).getStatus() == CourseStatus.PENDING) {
                            selectedCoursesThatStatusIsPending.add(students.get(choice2).getSelectedCourses().get(i));
                        }
                    }
                    coursesOfStudentPage(students.get(choice2 - 1), selectedCoursesThatStatusIsPending);
                }
                else {
                    System.out.println("Invalid choice.Try again");
                }
            } catch (NumberFormatException e) {
                if (choice.equals("b")) {
                    return false;
                } else if (choice.equals("q")) {
                    advisorController.logOut();
                    return true;
                } else {
                    System.out.println("Invalid choice.Try again");
                }
            }
        }

    }


   public boolean  coursesOfStudentPage(Student student, List <SelectedCourse> courses){

        Scanner input = new Scanner(System.in);

       System.out.println(" Courses Of The Student ");
       System.out.println("***************************");
       System.out.println("   Code         Number        Section       Status ");
       System.out.println("  ------        ------         ------       ------ ");

       for(int i = 0; i < courses.size() ; i++) {
           System.out.println((i+1) + ". " + courses.get(i).getCourse().getCourseCode()  + "    " + courses.get(i).getCourse().getCourseName() + "    " + courses.get(i).getCourseSection() + "    " + courses.get(i).getStatus());
       }

       System.out.println("Select Course: " + "\n");
       System.out.println("Press a to approve");
       System.out.println("Press d to deny");
       System.out.println("Press b to back");
       System.out.println("Press q to quit");
       while(true) {
           String choice = input.nextLine();
           try {
               int choiceInt = Integer.parseInt(choice);
               if (choiceInt > 0 && choiceInt <= courses.size()) {
                   String choice2 = input.nextLine();

                   if (choice2.equals("a")) {
                       advisorController.approveCourse(student,courses.get(choiceInt - 1));
                       System.out.println();
                   }
                   else if (choice2.equals("d")) {
                       advisorController.denyCourse(student,courses.get(choiceInt - 1));
                   }
                   else if (choice.equals("b")) {
                       return false;
                   }
                   else if (choice.equals("q")) {
                       advisorController.logOut();
                       return true;
                   }
                   else {
                       System.out.println("Invalid choice.Try again.");
                   }
               }
           }
           catch(NumberFormatException e){
               if (choice.equals("b")) {
                   return false;
               } else if (choice.equals("q")) {
                   advisorController.logOut();
                   return true;
               } else {
                   System.out.println("Invalid choice.Try again");
               }
           }

       }


   }










}
