package iteration2.src.models;

import java.util.*;

import iteration2.src.CommandLineInterface.CLIStudent;
import iteration2.src.controllers.StudentController;
import iteration2.src.enums.ApprovalStatus;
import iteration2.src.enums.CourseStatus;
import iteration2.src.interfaces.Color;
import iteration2.src.utils.DatabaseManager;
import iteration2.src.utils.Util;
import iteration2.src.enums.CourseResult;

// not sure about access identifier
public class Student extends User {
    private String email;
    private String identityNumber;
    private int currentSemester;
    private List<SelectedCourse> selectedCourses;
    private Advisor advisorOfStudent;
    private ApprovalStatus approvalStatus;
    private Transcript transcript;

    public Student() {

    }

    // not sure about constructor access identifier

    public Student(String userID, String password, String firstName, String lastName, boolean status, String email,
            String identityNumber, int currentSemester, List<SelectedCourse> selectedCourses, Advisor advisorOfStudent,
            ApprovalStatus approvalStatus, Transcript transcript) {
        super(userID, password, firstName, lastName, status);
        this.email = email;
        this.identityNumber = identityNumber;
        this.currentSemester = currentSemester;
        this.selectedCourses = selectedCourses;
        this.advisorOfStudent = advisorOfStudent;
        this.approvalStatus = ApprovalStatus.DONE;
        this.transcript = transcript;
    }

    public List<CourseSection> listAvailableCourseSections() {
        List<CourseSection> allSelectableCourseSections = new ArrayList<>();
        List<Course> courses = DatabaseManager.getInstance().getCourses();
        List<CourseSection> availableCourseSections = new ArrayList<>();

        // Find all possible course sections
        for (int i = 0; i < courses.size(); i++) {

            // Check engineering project availability
            if (courses.get(i).getCourseCode().equals("CSE4297") || courses.get(i).getCourseCode().equals("CSE4298")) {
                if (transcript.checkEngineeringProjectAvailability()) {
                    allSelectableCourseSections.addAll(courses.get(i).getCourseSections());
                }
            } else if (this.currentSemester < courses.get(i).getGivenSemester()) {// if course is from upper semester
                if (this.transcript.calculateGPA() >= 3.0) // if GPA of the student is greater than 3.0)
                    allSelectableCourseSections.addAll(courses.get(i).getCourseSections());

            } // If it is a current semester course or a course from lower semester
            else {
                allSelectableCourseSections.addAll(courses.get(i).getCourseSections());
            }

        }

        // Eliminate the ones that are not available
        for (CourseSection courseSection : allSelectableCourseSections) {
            Course course = courseSection.findCourseOfCourseSection();

            if (courseSection.checkAvailibilty() && course.checkPrerequisite(this) &&
                    !checkIfItWasTaken(course) &&
                    !checkIfItExistsInSelectedCourses(course) && checkCourseType(course)) {
                availableCourseSections.add(courseSection);
            }

        }

        availableCourseSections.addAll(findRepeatCourseSections());

        return availableCourseSections;// hata vermesin diye şimdilik yazdım
    }

    private boolean checkIfItExistsInSelectedCourses(Course course) {
        for (SelectedCourse selectedCourse : selectedCourses) {
            if (selectedCourse.getCourse().equals(course)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfItWasTaken(Course course) {
        List<Course> takenCourses = this.transcript.acquirePassedCourses();
        for (Course takenCourse : takenCourses) {
            if (takenCourse.getCourseName().equals(course.getCourseName())) {
                return true;
            }
        }
        return false;
    }

    private List<CourseSection> findRepeatCourseSections() {
        List<CourseGrade> takenCourses = this.transcript.getTakenCourses();
        List<CourseSection> repeatCourseSections = new ArrayList<>();

        for (CourseGrade course : takenCourses) {
            if (course.getLetterGrade() == "DD" || course.getLetterGrade() == "DC") {
                repeatCourseSections.addAll(course.getCourse().getCourseSections());
            }
        }
        return repeatCourseSections;
    }

    private boolean checkCourseType(Course course) {
        int nteCounter = 0;
        int teCounter = 0;
        int fteCounter = 0;
        int ueCounter = 0;
        for (SelectedCourse selectedCourse : selectedCourses) {
            if (selectedCourse.getCourse().getCourseType().toString().equals("NONTECHNICAL_ELECTIVE")) {
                nteCounter++;
            } else if (selectedCourse.getCourse().getCourseType().toString().equals("TECHNICAL_ELECTIVE")) {
                teCounter++;
            } else if (selectedCourse.getCourse().getCourseType().toString().equals("FACULTY_ELECTIVE")) {
                fteCounter++;
            } else if (selectedCourse.getCourse().getCourseType().toString().equals("UNIVERSITY_ELECTIVE")) {
                ueCounter++;
            }
        }

        if (course.getCourseType().toString().equals("NONTECHNICAL_ELECTIVE") && nteCounter >= 2) {
            return false;
        } else if (course.getCourseType().toString().equals("TECHNICAL_ELECTIVE") && teCounter >= 4) {
            return false;
        } else if (course.getCourseType().toString().equals("FACULTY_ELECTIVE") && fteCounter >= 1) {
            return false;
        } else if (course.getCourseType().toString().equals("UNIVERSITY_ELECTIVE") && ueCounter >= 1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean addNewCourse(SelectedCourse selectedCourse) {

        HashMap<Integer, String> constraints = DatabaseManager.getInstance().getConstraints();

        if (selectedCourses.size() >= Integer.parseInt(constraints.get(1))) {
            Util.sendFeedback("You can not take more than " + constraints.get(1) + " courses in one term.", Color.RED);
            Util.getLogger().warning(this.getUserId() + " - Student can not take more than " + constraints.get(1)
                    + " courses in one term.");
            return false;
        }

        if (!selectedCourses.contains(selectedCourse)) {
            selectedCourses.add(selectedCourse);
            DatabaseManager.getInstance().saveToDatabase();
            Util.getLogger().info(this.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                    + " added to student: " + this.getUserId());
            return true;
        } else {
            Util.getLogger().warning(this.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                    + " is already added to student: " + this.getUserId());
            return false;

        }
    }

    public boolean deleteCourse(SelectedCourse selectedCourse) {
        if (selectedCourses.contains(selectedCourse) && selectedCourse.getStatus() != CourseStatus.PENDING) {
            selectedCourses.remove(selectedCourse);
            DatabaseManager.getInstance().saveToDatabase();
            Util.getLogger().info(this.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                    + " deleted from student: " + this.getUserId());
            return true;
        }
        Util.getLogger().warning(this.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                + " is not deleted from student: " + this.getUserId());
        return false;
    }

    @Override
    public void getMyPage() {
        CLIStudent cliStudent = new CLIStudent(new StudentController(this));
        cliStudent.menuPage();
    }

    public boolean checkCompulsoryCourses() {
        List<CourseGrade> courseGrades = this.getTranscript().getTakenCourses();
        for (CourseGrade courseGrade : courseGrades) {
            if (courseGrade.getCourseResult() == CourseResult.FAILED) {
                List<SelectedCourse> selectedCourses = this.getSelectedCourses();
                for (SelectedCourse selectedCourse : selectedCourses) {
                    if (!selectedCourse.getCourse().equals(courseGrade.getCourse())) {
                        for (CourseSection section : this.listAvailableCourseSections()) {
                            if (section.findCourseOfCourseSection().equals(courseGrade.getCourse())) {
                                Util.getLogger().warning("Student: " + this.getUserId() + " has failed course: "
                                        + courseGrade.getCourse().getCourseCode()
                                        + " and has not added it to his/her courses.");
                                return true;

                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void sendSelectedCoursesToApproval() {
        int numberOfDraftCourses = 0;
        this.approvalStatus = ApprovalStatus.DONE;
        for (SelectedCourse selectedCourse : selectedCourses) {
            if (selectedCourse.getStatus() == CourseStatus.PENDING)
                this.approvalStatus = ApprovalStatus.PENDING;

            if (selectedCourse.getStatus() == CourseStatus.DRAFT)
                numberOfDraftCourses++;

        }
        if (this.approvalStatus == ApprovalStatus.PENDING) {
            Util.sendFeedback("You already sent your courses to approval!", Color.RED);
            Util.getLogger().warning(this.getUserId() + " - Student: " + this.getUserId()
                    + " already sent his/her courses to approval.");
            return;
        }

        if (checkCompulsoryCourses()) {
            Util.sendFeedback("Please add your failed courses!", Color.RED);
            return;
        }

        this.setApprovalStatus(ApprovalStatus.PENDING);
        List<SelectedCourse> selectedCourses = this.getSelectedCourses();

        if (numberOfDraftCourses == 0) {
            Util.sendFeedback("You have no course to send to approval!", Color.RED);
            Util.getLogger().warning(this.getUserId() + " - Student: " + this.getUserId()
                    + " has no course to send to approval.");
            return;
        }

        for (SelectedCourse selectedCourse : selectedCourses) {

            if (selectedCourse.getStatus() == CourseStatus.DRAFT) {
                selectedCourse.setStatus(CourseStatus.PENDING);
                selectedCourse.getCourseSection().incrementStudentCount();

            } // If the course is a repeat course
            else if (findRepeatCourseSections().contains(selectedCourse.getCourseSection())) {
                selectedCourse.setStatus(CourseStatus.PENDING);

                for (CourseGrade courseGrade : this.getTranscript().getTakenCourses()) {
                    if (courseGrade.getCourse().getCourseCode().equals(selectedCourse.getCourse().getCourseCode())) {
                        courseGrade.setCourseResult(CourseResult.ACTIVE);
                    }
                }

                selectedCourse.getCourseSection().incrementStudentCount();
            }

        }
        this.setSelectedCourses(selectedCourses);
        advisorOfStudent
                .addNotification(this.getFirstName() + " " + this.getLastName() + " has requested a course approval.");

        Util.sendFeedback("Courses are sent to advisor", Color.GREEN);
        Util.getLogger()
                .info(this.getUserId() + " - Student: " + this.getUserId() + " sent his/her courses to approval.");
        DatabaseManager.getInstance().saveToDatabase();
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

    public String[][] fillTable() {

        String[][] timeTable;

        timeTable = new String[5][8];

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
                    fillTableWithValues(time, i, 0, timeTable);
                } else if (day.equals("Tuesday")) {
                    fillTableWithValues(time, i, 1, timeTable);
                } else if (day.equals("Wednesday")) {
                    fillTableWithValues(time, i, 2, timeTable);
                } else if (day.equals("Thursday")) {
                    fillTableWithValues(time, i, 3, timeTable);
                } else if (day.equals("Friday")) {
                    fillTableWithValues(time, i, 4, timeTable);
                } else {
                    System.out.println("Invalid day.");
                }

            }
        }

        return timeTable;
    }

    public void fillTableWithValues(String time, int i, int day, String[][] timeTable) {
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
        } else {
            System.out.println("Invalid time");
        }
    }

    public List<String> checkConflictCourses() {
        String[][] timeTable = fillTable();
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

    public List<SelectedCourse> fetchSelectedCoursesForAdvisor() {
        List<SelectedCourse> selectedCourses = new ArrayList<SelectedCourse>();
        for (int i = 0; i < this.selectedCourses.size(); i++) {
            if (this.selectedCourses.get(i).getStatus() != CourseStatus.DRAFT) {
                selectedCourses.add(this.selectedCourses.get(i));

            }
        }
        return selectedCourses;
    }

    public List<SelectedCourse> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(List<SelectedCourse> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public String[][] createTimeTable() {
        String[][] timeTable = fillTable();
        return timeTable;
    }

}
