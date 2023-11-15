package CommandLineInterface;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import controllers.AdvisorController;
import models.Advisor;
import models.Course;
import models.Student;

public class CLIAdvisor {

    Advisor advisor;
    public CLIAdvisor(Advisor advisor){
        this.advisor = advisor;
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
            if (choice == "1") {
                List<Student> students = advisorController.getStudentListOrderByStatus();
                shouldQuit = showStudentPage(students);

                if(shouldQuit) {
                    break;
                }
            }
            else if (choice == "2") {
                advisorController.logout();
                break;
            }
            else {
                System.out.println("Invalid choice");
            }
        }

    }

    //boolean a dönüştür
    public boolean showStudentPage(List<Student> students) {

        Scanner input = new Scanner(System.in);
        System.out.println(" Student List");
        System.out.println("****************");
        System.out.println("   Number      Name");
        System.out.println("   ------      -----");

        for (int i = 0; i < students.length; i++) {
            System.out.println((i + 1) + ". " + students.get(i).getSchoolNumber() + "    " + students.get(i).getFirstName() + " " + students[i].getLastName());
        }

        System.out.println("Select Student:");
        System.out.println("Press b to back");
        System.out.println("Press q to quit");


        while (true) {
            String choice = input.nextLine();
            try {
                int choice2 = Integer.parseInt(choice);
                if (choice2 > 0 && choice2 <= students.length) {
                    coursesOfStudentPage(students[choice2 - 1], courses);
                }
                else {
                    System.out.println("Invalid choice.Try again");
                }
            } catch (NumberFormatException e) {
                if (choice == "b") {
                    return false;
                } else if (choice == "q") {
                    advisorController.logout();
                    return true;
                } else {
                    System.out.println("Invalid choice.Try again");
                }
            }
        }

    }


   public boolean  CoursesOfStudentPage(Student student, Course[] courses){

        Scanner input = new Scanner(System.in);

       System.out.println(" Courses Of The Student ");
       System.out.println("***************************");
       System.out.println("   Code         Number        Section       Status ");
       System.out.println("  ------        ------         ------       ------ ");

       for(int i = 0; i < courses.length ; i++) {
           System.out.println((i+1) + ". " + courses[i].getCourseCode() + "    " + courses[i].getName() + "    " + courses[i].getSection() + "    " + courses[i].getStatus);
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
               if (choiceInt > 0 && choiceInt <= courses.length) {
                   String choice2 = input.nextLine();

                   if (choice2 == "a") {
                       advisorController.approveCourse(student,courses[choiceInt-1]);
                       System.out.println();
                   }
                   else if (choice2 == "d") {
                       advisorController.denyCourse(student,courses[choiceInt-1]);
                   }
                   else if (choice == "b") {
                       return false;
                   }
                   else if (choice == "q") {
                       advisorController.logout();
                       return true;
                   }
                   else {
                       System.out.println("Invalid choice.Try again.");
                   }
               }
           }
           catch(NumberFormatException e){
               if (choice == "b") {
                   return false;
               } else if (choice == "q") {
                   advisorController.logout();
                   return true;
               } else {
                   System.out.println("Invalid choice.Try again");
               }
           }

       }
       return false;

   }










}
