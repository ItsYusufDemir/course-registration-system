package iteration2.src;

import iteration2.src.models.CourseRegistrationSystem;
import iteration2.src.utils.Util;

public class Main {
    public static void main(String[] args) {

        Util.initLogger();

        // Create the course registration system and start it
        CourseRegistrationSystem courseRegistrationSystem = new CourseRegistrationSystem();
        Util.getLogger().info("Course registration system starting.");
        courseRegistrationSystem.start();

    }
}
