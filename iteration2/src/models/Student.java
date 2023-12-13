package iteration2.src.models;
import java.util.*;
import iteration2.src.CommandLineInterface.CLIStudent;
import iteration2.src.controllers.StudentController;
import iteration2.src.enums.ApprovalStatus;
import iteration2.src.enums.CourseStatus;
import iteration2.src.utils.DatabaseManager;

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

    public Student(String userID, String password, String firstName, String lastName, boolean status, String email, String identityNumber, int currentSemester, List<SelectedCourse> selectedCourses, Advisor advisorOfStudent, ApprovalStatus approvalStatus, Transcript transcript) {
        super(userID, password, firstName, lastName, status);
        this.email = email;
        this.identityNumber = identityNumber;
        this.currentSemester = currentSemester;
        this.selectedCourses = selectedCourses;
        this.advisorOfStudent = advisorOfStudent;
        this.approvalStatus = ApprovalStatus.DONE;
        this.transcript = transcript;
    }


    public List<CourseSection> listAvailableCourses() {
        List<CourseSection> allSelectableCourseSections = new ArrayList<>();
        List<Course> courses = DatabaseManager.getInstance().getCourses();
        List<CourseSection> availableCourseSections = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            if (this.currentSemester < courses.get(i).getGivenSemester() // if course is from upper semester
                    && this.transcript.calculateGPA() >= 3.0 // if GPA of the student is greater than 3.0
                    && courses.get(i).checkPrerequisite(this)) { // if prerequisite courses of that course are completed
                allSelectableCourseSections.addAll(courses.get(i).getCourseSections());
            } else if (this.currentSemester == courses.get(i).getGivenSemester() // if course is from current semester
                    && courses.get(i).checkPrerequisite(this)) { // if prerequisite courses of that course are completed
                allSelectableCourseSections.addAll(courses.get(i).getCourseSections());
            }

            for (CourseSection courseSection : allSelectableCourseSections) {
                Course course = courseSection.findCourseOfCourseSection();
                if (courseSection.checkAvailibilty() && course.checkPrerequisite(this) &&
                        !this.transcript.acquirePassedCourses().contains(course) &&
                        !checkIfItExistsInSelectedCourses(course) && checkCourseType(course)) {
                    availableCourseSections.add(courseSection);
                }
                availableCourseSections.addAll(findRepeatCourseSections());
            }
            return availableCourseSections;
            // Courselara bakıcam döneminden üst dersse gpa artı prerequisite check
        }
        return availableCourseSections;// hata vermesin diye şimdilik yazdım
    }

    private boolean checkIfItExistsInSelectedCourses(Course course){
        for (SelectedCourse selectedCourse: selectedCourses){
            if(selectedCourse.getCourse().equals(course)){
                return true;
            }
        }
        return false;
    }

    private List<CourseSection> findRepeatCourseSections(){
       List<CourseGrade> takenCourses = this.transcript.getTakenCourses();
       List<CourseSection> repeatCourses = new ArrayList<>();
       for(CourseGrade course: takenCourses) {
           if(course.getLetterGrade() == "DD" || course.getLetterGrade() == "DC"){
               repeatCourses.addAll(course.getCourse().getCourseSections());
           }
       }
       return repeatCourses;
    }




    private boolean checkCourseType(Course course){
        int nteCounter = 0;
        int teCounter = 0;
        int fteCounter = 0;
        int ueCounter = 0;

        for(SelectedCourse selectedCourse : selectedCourses){
            if(selectedCourse.getCourse().getCourseType().toString().equals("NONTECHNICAL_ELECTIVE") ){
                nteCounter++;
            }
            else if(selectedCourse.getCourse().getCourseType().toString().equals("TECHNICAL_ELECTIVE") ){
                teCounter++;
            }
            else if(selectedCourse.getCourse().getCourseType().toString().equals("FACULTY_ELECTIVE" ) ){
                fteCounter++;
            }
            else if(selectedCourse.getCourse().getCourseType().toString().equals("UNIVERSITY_ELECTIVE") ){
                ueCounter++;
            }
        }


        if(course.getCourseType().toString().equals("NONTECHNICAL_ELECTIVE") && nteCounter >= 2){
            return false;
        }
        else if(course.getCourseType().toString().equals("TECHNICAL_ELECTIVE") && teCounter >= 4){
            return false;
        }
        else if(course.getCourseType().toString().equals("FACULTY_ELECTIVE") && fteCounter >= 1){
            return false;
        }
        else if(course.getCourseType().toString().equals("UNIVERSITY_ELECTIVE") && teCounter >= 1){
            return false;
        }
        else{
        return true;
        }
    }

    public boolean addNewCourse(SelectedCourse selectedCourse){
       // should we check the capacity here or while listing the available courses?
        if(!selectedCourses.contains(selectedCourse))
            return selectedCourses.add(selectedCourse);
        else{
            return false;

        }
    }

    public boolean deleteCourse(SelectedCourse selectedCourse){
        return selectedCourses.remove(selectedCourse);
    }


    @Override
    public void getMyPage() {
        CLIStudent cliStudent = new CLIStudent(new StudentController(this));
        cliStudent.menuPage();
    }

    public List<CourseSection> checkConflictCourses(){
       return null;
    }

    public String getTimeTable(){
       return "";
    }

    public void sendSelectedCoursesToApproval(){
        this.setApprovalStatus(ApprovalStatus.PENDING);
        for (SelectedCourse selectedCourse: this.getSelectedCourses()){
            if(selectedCourse.getStatus() == CourseStatus.DRAFT){
                selectedCourse.setStatus(CourseStatus.PENDING);
                selectedCourse.getCourseSection().incrementStudentCount();
            }
        }
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
}
