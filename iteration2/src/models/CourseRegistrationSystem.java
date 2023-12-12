package iteration2.src.models;

import iteration2.src.CommandLineInterface.CLILogin;
import iteration2.src.utils.Util;

public class CourseRegistrationSystem {

    public void start() {
        User user;

        while (true) {

            // Create commond line interface for login page and start it
            CLILogin login = new CLILogin();
            user = login.loginPage();

            Util.clearScreen();
            // Create commond line interface for first menu page and start it
            user.getMyPage();

        }
    }

}