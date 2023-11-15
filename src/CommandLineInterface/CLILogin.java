package CommandLineInterface;
import java.util.Scanner;
import java.util.Stack;
import models.User;
//import controllers.UserController;

public class CLILogin {
    public User loginPage(){

        do {
            System.out.println(" Login ");
            System.out.println(" ******** ");
            Scanner input = new Scanner(System.in);

            System.out.print("Username:");
            String username = input.nextLine();

            System.out.print("Password:");
            String password = input.nextLine();

            //UserController userController = new UserController();
            User user = new User(username, password);

            if(user == null){
                System.out.println("Invalid username or password.");
            }
        }
      //recursive yapma, do while a dönüştür
        while(user == null );

        return user;
        }
        input.close();
    }

