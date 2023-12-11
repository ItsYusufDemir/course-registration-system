package iteration2.src.CommandLineInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.crypto.Data;

import iteration2.src.controllers.AdvisorController;
import iteration2.src.enums.CourseStatus;
import iteration2.src.models.Advisor;
import iteration2.src.models.SelectedCourse;
import iteration2.src.models.Student;
import iteration2.src.utils.DatabaseManager;

public class CLIAdvisor {

    private Advisor advisor;
    private AdvisorController advisorController;
    public CLIAdvisor(Advisor advisor){
        this.advisor = advisor;
        advisorController = new AdvisorController(advisor);
    }

    public void menuPage() {
        boolean isInvalid = false;
        Scanner input = new Scanner(System.in);
        AdvisorController advisorController = new AdvisorController(advisor);
        while (true) {
            if(!isInvalid){
                isInvalid = false;
      //  while(true) {
            System.out.println(" Menu ");
            System.out.println("********");
            System.out.println("    1. Student List");
            System.out.println("    2. Log out");
            }
            else{
                isInvalid = false;
            }
            boolean shouldQuit = false;
            String choice = input.nextLine();

                if (choice.equals("1")) {
                    List<Student> students = advisorController.getStudentList();
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
                    System.out.println("Invalid choice");
                    isInvalid = true;
                }
            }
    }


    public boolean showStudentPage(List<Student> students) {
        boolean isInvalid = false;
        Scanner input = new Scanner(System.in);
        while (true) {
            if(!isInvalid){
                isInvalid = false;


        System.out.println(" Student List");
        System.out.println("****************");
        System.out.println("   Number      Name");
        System.out.println("   ------      -----");

        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getUserId() + "    " + students.get(i).getFirstName() + " " + students.get(i).getLastName());
        }

        System.out.println("Select Student:");
        System.out.println("Press b to back");
        System.out.println("Press q to quit");
            }
            else{
                isInvalid = false;
            }
        List<SelectedCourse> selectedCoursesThatStatusIsPending = new ArrayList<SelectedCourse>();

            String choice = input.nextLine();
            try {
                int choice2 = Integer.parseInt(choice);
                if (choice2 > 0 && choice2 <= students.size()) {
                    for(int i=0; i<students.get(choice2-1).getSelectedCourses().size();i++) {
                        if (students.get(choice2-1).getSelectedCourses().get(i).getStatus() == CourseStatus.PENDING) {
                            selectedCoursesThatStatusIsPending.add(students.get(choice2-1).getSelectedCourses().get(i));
                        }
                    }
                    boolean shouldQuit = coursesOfStudentPage(students.get(choice2 - 1));
                    if(shouldQuit){
                        return true;
                    }
                }
                else {
                    System.out.println("Invalid choice. Try again");
                    isInvalid = true;
                }
            } catch (NumberFormatException e) {
                if (choice.equals("b")) {
                    return false;
                } else if (choice.equals("q")) {
                    advisorController.logOut();
                    return true;
                } else {
                    System.out.println("Invalid choice. Try again");
                    isInvalid = true;
                }
            }
        }
    }


   public boolean  coursesOfStudentPage(Student student){

       boolean isInvalid = false;
        Scanner input = new Scanner(System.in);
       List<SelectedCourse> courses = student.getSelectedCourses();
       while (true) {
           if(!isInvalid){
               isInvalid = false;

       System.out.println(" Courses Of The Student ");
       System.out.println("***************************");
       System.out.println("   Code         Number        Section       Status ");
       System.out.println("  ------        ------         ------       ------ ");

       for(int i = 0; i < courses.size() ; i++) {
           System.out.println((i+1) + ". " + courses.get(i).getCourse().getCourseCode()  + "    " + courses.get(i).getCourse().getCourseName() + "    " + courses.get(i).getCourseSection().getSectionCode() + "    " + courses.get(i).getStatus());
       }

           System.out.println("Press b to back");
           System.out.println("Press q to quit");
           System.out.println("Select Course: ");
       }
            else{
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
                       advisorController.approveCourse(student,courses.get(choiceInt - 1));
                       DatabaseManager.getInstance().saveToDatabase();
                       System.out.println();
                   }
                   else if (choice2.equals("d")) {
                       advisorController.denyCourse(student,courses.get(choiceInt - 1));
                       DatabaseManager.getInstance().saveToDatabase();
                   }
                   else if (choice.equals("b")) {
                       return false;
                   }
                   else if (choice.equals("q")) {
                       advisorController.logOut();
                       return true;
                   }
                   else {
                       System.out.println("Invalid choice. Try again.");
                       isInvalid = true;
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
                   System.out.println("Invalid choice. Try again");
                   isInvalid = true;
               }
           }

       }


   }
}
