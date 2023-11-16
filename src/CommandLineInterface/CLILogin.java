package CommandLineInterface;
import java.util.Scanner;
import java.util.Stack;

import controllers.UserController;
import models.User;
//import controllers.UserController;

public class CLILogin {

    private User user;
    public User loginPage(){

        Scanner input = new Scanner(System.in);
        do {
            System.out.println(" Login ");
            System.out.println(" ******** ");

            System.out.print("Username:");
            String username = input.nextLine();

            System.out.print("Password:");
            String password = input.nextLine();

            //UserController userController = new UserController();
            UserController userController = new UserController();
            user = userController.loginUser(username,password);


            if(user == null){
                System.out.println("Invalid username or password.");
            }
        }
      //recursive yapma, do while a dönüştür
        while(user == null );

        input.close();
        return user;
        }

    }

