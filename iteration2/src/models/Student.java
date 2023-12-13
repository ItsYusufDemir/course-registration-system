package iteration2.src.models;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;

import iteration2.src.CommandLineInterface.CLIStudent;
import iteration2.src.enums.ApprovalStatus;
import iteration2.src.utils.DatabaseManager;

// not sure about access identifier
public class Student extends User {
    private String email;
    private String identityNumber;
    private List<SelectedCourse> selectedCourses;
    private Advisor advisorOfStudent;
    private ApprovalStatus approvalStatus;
    private Transcript transcript;
    private String[][] timeTable;

    public Student() {

    }

    // not sure about constructor access identifier

    public Student(String userID, String password, String firstName, String lastName, boolean status, String email, String identityNumber, List<SelectedCourse> selectedCourses, Advisor advisorOfStudent, ApprovalStatus approvalStatus, Transcript transcript) {
        super(userID, password, firstName, lastName, status);
        this.email = email;
        this.identityNumber = identityNumber;
        this.selectedCourses = selectedCourses; // BUNU BEN MI OLUSTURUCAM
        this.advisorOfStudent = advisorOfStudent;
        this.approvalStatus = ApprovalStatus.DONE;
        this.transcript = transcript;
    }

    public List<SelectedCourse> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(List<SelectedCourse> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    public List<Course> listAvailableCourses() {

        List<Course> allCourses = DatabaseManager.getInstance().getCourses();
        List<Course> passedCourses = transcript.acquirePassedCourses(); // muho

        List<Course> availableCourses = new ArrayList<Course>();

        for (Course currentCourse : allCourses) {

            boolean doesContain = false;
            for (int i = 0; i < passedCourses.size(); i++) {
                if (passedCourses.get(i).getCourseCode().equals(currentCourse.getCourseCode())) {
                    doesContain = true;
                }
            }

            if (!(doesContain) && currentCourse.checkPrerequisite(this)) {
                availableCourses.add(currentCourse);
            }
        }
        return availableCourses;
    }
    
    /*
        public List<CourseSection> listAvailableCourseSections(){
        List<Course> allCourses = database;
        List<Course> passedCourses = transcript.getPassed(); // muho
        List<CourseSection> availableCourses = new List<Course>();
        for (int i = 0; i < allCourses.size(); i++) {
            Course currentCourse = allCourses.get(i);
            if(!(passedCourses.contains(currentCourse)) && currentCourse.checkPrerequisite(this)){
                List<CourseSection> currentCourseSections = currentCourse.getAvailableCourseSections();
                availableCourses.addAll(currentCourseSections);
            }
        }
        return availableCourses;
    }
     */

    public void addNewCourse(SelectedCourse selectedCourse) {
        selectedCourses.add(selectedCourse);
    }

    public void deleteCourse(SelectedCourse selectedCourse) {
        selectedCourses.remove(selectedCourse);
    }

    @Override
    public void getMyPage() {
        CLIStudent cliStudent = new CLIStudent(this);
        cliStudent.menuPage();
    }


    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Advisor getAdvisorOfStudent() {
        return advisorOfStudent;
    }

    public void setAdvisorOfStudent(Advisor advisorOfStudent) {
        this.advisorOfStudent = advisorOfStudent;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public void fillTable() {
        timeTable = new String[5][9];

        for (int i = 0; i < 5; i++) {
            for (int m = 0; m < 8; m++) {
                timeTable[i][m] = "";
            }
        }
        for (int i = 0; i < selectedCourses.size(); i++) {

            for (int m = 0; m < selectedCourses.get(i).getCourseSection().getSectionDay().size(); m++) {


                String day = selectedCourses.get(i).getCourseSection().getSectionDay().get(m);
                String time = selectedCourses.get(i).getCourseSection().getSectionTime().get(m);
                if (day.equals("Monday")) {
                    fillTableWithValues(time, i, 0);
                } else if (day.equals("Tuesday")) {
                    fillTableWithValues(time, i, 1);
                } else if (day.equals("Wednesday")) {
                    fillTableWithValues(time, i, 2);
                } else if (day.equals("Thursday")) {
                    fillTableWithValues(time, i, 3);
                } else if (day.equals("Friday")) {
                    fillTableWithValues(time, i, 4);
                } else {
                    System.out.println("Invalid day.");
                }


            }
        }
    }

    public void fillTableWithValues(String time, int i, int day) {
        if (time.equals("08:30-09:20")) {
            timeTable[day][0] += selectedCourses.get(i).getCourseSection().getSectionCode() + "-";
        } else if (time.equals("09:30-10:20")) {
            timeTable[day][1] += selectedCourses.get(i).getCourseSection().getSectionCode() + "-";
        } else if (time.equals("10:30-11:20")) {
            timeTable[day][2] += selectedCourses.get(i).getCourseSection().getSectionCode() + "-";
        } else if (time.equals("11:30-12:20")) {
            timeTable[day][3] += selectedCourses.get(i).getCourseSection().getSectionCode() + "-";
        } else if (time.equals("12:30-13:20")) {
            timeTable[day][4] += selectedCourses.get(i).getCourseSection().getSectionCode() + "-";
        } else if (time.equals("13:30-14:20")) {
            timeTable[day][5] += selectedCourses.get(i).getCourseSection().getSectionCode() + "-";
        } else if (time.equals("14:30-15:20")) {
            timeTable[day][6] += selectedCourses.get(i).getCourseSection().getSectionCode() + "-";
        } else if (time.equals("15:30-16:20")) {
            timeTable[day][7] += selectedCourses.get(i).getCourseSection().getSectionCode() + "-";
        } else if (time.equals("16:30-17:20")) {
            timeTable[day][8] += selectedCourses.get(i).getCourseSection().getSectionCode() + "-";
        } else {
            System.out.println("Invalid time");
        }
    }


    public List<String> checkConflictCourses() {
        fillTable();
        List<String> conflictedCourses = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {
            for (int m = 0; m < 9; m++) {
                String[] courseCodes = timeTable[i][m].split("-");
                int length = courseCodes.length;
                if (length > 1) {
                    for (int q = 0; q < length; q++) {
                        conflictedCourses.add(courseCodes[q]);
                    }
                }

            }
        }
        return conflictedCourses;
    }


}












