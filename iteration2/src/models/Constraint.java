package iteration2.src.models;

public class Constraint {
    private int maxNumberOfCoursesStudentTake;
    private boolean isAddDropWeek;
    private int minRequiredECTSForTermProject;

    public Constraint(int maxNumberOfCoursesStudentTake, boolean isAddDropWeek, int minRequiredECTSForTermProject) {
        this.maxNumberOfCoursesStudentTake = maxNumberOfCoursesStudentTake;
        this.isAddDropWeek = isAddDropWeek;
        this.minRequiredECTSForTermProject = minRequiredECTSForTermProject;
    }

    public int getMaxNumberOfCoursesStudentTake() {
        return maxNumberOfCoursesStudentTake;
    }

    public void setMaxNumberOfCoursesStudentTake(int maxNumberOfCoursesStudentTake) {
        this.maxNumberOfCoursesStudentTake = maxNumberOfCoursesStudentTake;
    }

    public boolean isAddDropWeek() {
        return isAddDropWeek;
    }

    public void setAddDropWeek(boolean isAddDropWeek) {
        this.isAddDropWeek = isAddDropWeek;
    }

    public int getMinRequiredECTSForTermProject() {
        return minRequiredECTSForTermProject;
    }

    public void setMinRequiredECTSForTermProject(int minRequiredECTSForTermProject) {
        this.minRequiredECTSForTermProject = minRequiredECTSForTermProject;
    }

}
