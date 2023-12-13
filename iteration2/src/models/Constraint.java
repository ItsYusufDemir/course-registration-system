package iteration2.src.models;

import java.util.HashMap;

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

    public void editConstraint(HashMap<Integer, String> editedAttributes) {
        maxNumberOfCoursesStudentTake = Integer.parseInt(editedAttributes.get(1));
        isAddDropWeek = editedAttributes.get(2).equals("true") ? true : false;
        minRequiredECTSForTermProject = Integer.parseInt(editedAttributes.get(3));
    }

    public HashMap<Integer, String> getAttributes() {
        HashMap<Integer, String> attributes = new HashMap<Integer, String>();
        attributes.put(1, String.valueOf(maxNumberOfCoursesStudentTake));
        attributes.put(2, String.valueOf(isAddDropWeek));
        attributes.put(3, String.valueOf(minRequiredECTSForTermProject));
        return attributes;
    }

}
