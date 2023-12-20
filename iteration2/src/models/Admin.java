package iteration2.src.models;

import java.util.HashMap;
import java.util.List;
import iteration2.src.CommandLineInterface.CLIAdmin;
import iteration2.src.controllers.AdminController;
import iteration2.src.utils.DatabaseManager;
import iteration2.src.utils.Util;

public class Admin extends User {

    public Admin() {
    }

    public List<Course> fetchCourseList() {
        return DatabaseManager.getInstance().getCourses();
    }

    public HashMap<Integer, String> fetchConstraints() {
        return DatabaseManager.getInstance().getConstraints();
    }

    public Course createCourse(Course course) {
        fetchCourseList().add(course);
        DatabaseManager.getInstance().saveToDatabase();

        Util.getLogger().info(this.getUserId() + " - Course: " + course.getCourseCode() + " created successfully");
        return course;
    }

    public boolean editConstraint(HashMap<Integer, String> editedAttributes) {
        boolean ret = DatabaseManager.getInstance().editConstraint(editedAttributes);
        DatabaseManager.getInstance().saveToDatabase();

        if (ret)
            Util.getLogger()
                    .info(this.getUserId() + " - Constraint edited successfully: " + editedAttributes.toString());
        else
            Util.getLogger().warning(this.getUserId() + " - Constraint edit failed: " + editedAttributes.toString());
        return ret;
    }

    public boolean deleteCourse(Course course) {
        boolean ret = fetchCourseList().remove(course);
        DatabaseManager.getInstance().saveToDatabase();

        if (ret)
            Util.getLogger().info(this.getUserId() + " - Course: " + course.getCourseCode() + " deleted successfully");
        else
            Util.getLogger().warning(this.getUserId() + " - Course: " + course.getCourseCode() + " delete failed");
        return ret;
    }

    public Course findCourseByCourseCode(String courseCode) {
        return DatabaseManager.getInstance().findCourseByCourseCode(courseCode);
    }

    @Override
    public void getMyPage() {
        CLIAdmin cliAdmin = new CLIAdmin(new AdminController(this));
        cliAdmin.menuPage();
    }
}
