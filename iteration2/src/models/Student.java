package iteration2.src.models;
import java.util.*;
import iteration2.src.CommandLineInterface.CLIStudent;
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


    public List<CourseSection> listAvailableCourses(){
       List<CourseSection> allCourseSections = new ArrayList<>();
       List<Course> courses = DatabaseManager.getInstance().getCourses();
       List<CourseSection> availableCourseSections = new ArrayList<>();
       for (Course course : courses) {
            allCourseSections.addAll(course.getCourseSections());
       }
       for (CourseSection courseSection: allCourseSections) {
           Course course = courseSection.findCourseOfCourseSection();
           if(courseSection.checkAvailibilty() && course.checkPrerequisite(this) && !this.getTranscript().acquirePassedCourses().contains(course) && !checkIfItExistsInSelectedCourses(course) ){
               availableCourseSections.add(courseSection);
           }
           availableCourseSections.addAll(findRepeatCourseSectionss());
       }
       return availableCourseSections;
    }

    private boolean checkIfItExistsInSelectedCourses(Course course){
        for (SelectedCourse selectedCourse: selectedCourses){
            if(selectedCourse.getCourse().equals(course)){
                return true;
            }
        }
        return false;
    }

    private List<CourseSection> findRepeatCourseSectionss(){
       List<CourseGrade> takenCourses = this.getTranscript().getTakenCourses();
       List<CourseSection> repeatCourses = new ArrayList<>();
       for(CourseGrade course: takenCourses) {
           if(course.getLetterGrade() == "DD" || course.getLetterGrade() == "DC"){
               repeatCourses.addAll(course.getCourse().getCourseSections());
           }
       }
       return repeatCourses;
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
        CLIStudent cliStudent = new CLIStudent(this);
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
