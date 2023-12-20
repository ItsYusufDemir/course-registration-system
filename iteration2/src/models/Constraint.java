package iteration2.src.models;

import java.util.HashMap;

public class Constraint {
    private int maxNumberOfCoursesStudentTake;
    private boolean addDropWeek;
    private int minRequiredECTSForTermProject;

    public Constraint() {
    }

    public Constraint(int maxNumberOfCoursesStudentTake, boolean isAddDropWeek, int minRequiredECTSForTermProject) {
        this.maxNumberOfCoursesStudentTake = maxNumberOfCoursesStudentTake;
        this.addDropWeek = isAddDropWeek;
        this.minRequiredECTSForTermProject = minRequiredECTSForTermProject;
    }

    public int getMaxNumberOfCoursesStudentTake() {
        return maxNumberOfCoursesStudentTake;
    }

    public void setMaxNumberOfCoursesStudentTake(int maxNumberOfCoursesStudentTake) {
        this.maxNumberOfCoursesStudentTake = maxNumberOfCoursesStudentTake;
    }

    public boolean getAddDropWeek() {
        return addDropWeek;
    }

    public void setAddDropWeek(boolean isAddDropWeek) {
        this.addDropWeek = isAddDropWeek;
    }

    public int getMinRequiredECTSForTermProject() {
        return minRequiredECTSForTermProject;
    }

    public void setMinRequiredECTSForTermProject(int minRequiredECTSForTermProject) {
        this.minRequiredECTSForTermProject = minRequiredECTSForTermProject;
    }

    public void editConstraint(HashMap<Integer, String> editedAttributes) {
        maxNumberOfCoursesStudentTake = Integer.parseInt(editedAttributes.get(1));
        addDropWeek = editedAttributes.get(2).equals("true") ? true : false;
        minRequiredECTSForTermProject = Integer.parseInt(editedAttributes.get(3));
    }

    public HashMap<Integer, String> fetchAttributes() {
        HashMap<Integer, String> attributes = new HashMap<Integer, String>();
        attributes.put(1, String.valueOf(maxNumberOfCoursesStudentTake));
        attributes.put(2, String.valueOf(addDropWeek));
        attributes.put(3, String.valueOf(minRequiredECTSForTermProject));
        return attributes;
    }

}
