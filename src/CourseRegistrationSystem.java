public class CourseRegistrationSystem {
    
    void start() {
        User user;

        while(true) {

            //Create commond line interface for login page and start it
            CLILogin login = new CLILogin();
            user = login.loginPage();
            
            //Create commond line interface for first menu page and start it
            user.getMyPage(); 

        }
    }

}