import java.util.*;


public class CLIStudent {

    
    
    Stack<Integer> pageNumeber;
    private Scanner scanner;
    private StudentController studentController;
    private Student currentStudent;

    public CLIStudent(Student student){
        currentStudent = student;
        studentController = new StudentController(currentStudent);
    }

    public void menuPage(){

        scanner = new Scanner(System.in);

        System.out.println(
            " Menu\n" +
            "********\n" +
            "  1. My Courses\n" +
            "  2. Log out\n" +
            "Press q to quit");

        String str = scanner.nextLine();
        if(str.equals("q")){
            System.exit(0); 
        }
        else if(str.equals("1")){
            
            myCoursesPage(studentController.getAvaliableCourses());
        }
        else if(str.equals("2")){
            return;
        }
        else{
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            menuPage();
        }


    }


    public void myCoursesPage(Course[] courses){

        scanner = new Scanner(System.in);

        System.out.println(
            " My Courses\n" +
            "**************\n" +
            "  Code\t Name\t Section\t Status\n" +
            "  ____\t ____\t _______\t ______");
        listSelectedCourses(currentStudent);
        System.out.println(
            "1. Add Course\n" +
            "2. Delete Course\n" +
            "3. Send To Approval");

        System.out.println("Press b to go back");
        System.out.println("Press q to quit");

        String str = scanner.nextLine();

        if(str.equals("1")){
            addCoursePage(courses);
        }
        else if(str.equals("2")){
            deleteCoursePage(courses);
        }
        else if(str.equals("3")){
            studentController.sendSelectedCoursesToApproval();
        }
        else if(str.equals("b")){
            menuPage();
        }
        else if(str.equals("q")){
            System.exit(0); 
        }
        else{
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            myCoursesPage(courses);

        }
            
    }

    public void addCoursePage(Course[] courses){
        scanner = new Scanner(System.in);

        System.out.println(
            " Avaliable Courses(To Add)\n" +
            "**************\n" +
            "  Code\t Name\t Section\t Instructor\t Credit\n" +
            "  ____\t ____\t _______\t __________\t ______");
        listAvaliableCourses(courses);
        System.out.println("press b to go back");
        System.out.println("press q to quit");

        String str = scanner.nextLine();
        String courseCode = str.replaceAll(" ", "");
        char[] courseCodeArray = courseCode.toCharArray();

        if(checkCourseSelectionInput(courseCodeArray)){
            ArrayList<Integer> courseCodeIntArray = convertCourseSelectionInputToInt(str);
            for(int i : courseCodeIntArray){
                studentController.addSelectedCourse(courses[i-1]);
            }
        }
        else if(str.equals("b")){
            myCoursesPage(courses);
        }
        else if(str.equals("q")){
            System.exit(0); 
        }
        else{
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            addCoursePage(courses);
        }
    }

    public void deleteCoursePage(Course[] courses){
        //TODO burda ne yapcam emin degilim, yani ekstra ekrana gerek var mi? aslinda iyi olur gibi ama diagrami yok
    } 

    private boolean checkCourseSelectionInput(char[] courseCodeArray){
        
        for(char c : courseCodeArray){
            if(!Character.isLetterOrDigit(c)){
                return false;
            }
        }
        return true;
    }

    private ArrayList<Integer> convertCourseSelectionInputToInt(String string){
        int strLength = string.length();
        String tempString = "";
        ArrayList<Integer> courseCodeIntArray = new ArrayList<Integer>();
        for(int i = 0; i < strLength; i++){
            if(string.charAt(i) == ' '){
                courseCodeIntArray.add(Integer.parseInt(tempString));
            }
            else{
                tempString += string.charAt(i);
            }
        }
        return courseCodeIntArray;
    }

    private void listAvaliableCourses(Course[] courses){
        int i = 0;
        for(Course course : courses){
            i++;
            System.out.println(i + ". " + course.getCourseCode() + "\t" + course.getCourseName() + "\t" + course.getCourseSection() + "\t" + course.getCourseInstructor() + "\t" + course.getCourseCredit());
        }
    }


    private void listSelectedCourses(Student student){
        int i = 0;
        for(SelectedCourse course : student.selectedCourses){
            i++;
            System.out.println(i + ". " + course.getCourse().getCourseCode() + "\t" + course.getCourse().getCourseName() + "\t" + course.getCourse().getCourseSection() + "\t" + student.approvalStatus);
        }
    }
}
