import java.util.*;

// not sure about access identifier
public class Student {
    private String email;
    private int schoolNumber;
    private String identityNumber;
    private List<SelectedCourse> selectedCourses;
    private Advisor advisorOfStudent;
    private ApprovalStatus approvalStatus;
    private Transcript transcript;

    // not sure about constructor access identifier
    public Student(String email, int schoolNumber, String identityNumber, List<SelectedCourse> selectedCourses, Advisor advisorOfStudent, ApprovalStatus approvalStatus, Transcript transcript) {
        this.email = email;
        this.schoolNumber = schoolNumber;
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

    public List<Course> listAvailableCourses(){
        List<Course> allCourses = database;
        List<Course> passedCourses = transcript.getPassed(); // muho
        List<Course> availableCourses = new ArrayList<Course>();
        for (Course currentCourse : allCourses) {
            if (!(passedCourses.contains(currentCourse)) && currentCourse.checkPrerequisite(this)) {
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

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void addNewCourse(SelectedCourse selectedCourse){
        selectedCourses.add(selectedCourse);
    }

    public void deleteCourse(SelectedCourse selectedCourse){
        selectedCourses.remove(selectedCourse);
    }
}
