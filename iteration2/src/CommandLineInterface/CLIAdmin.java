package iteration2.src.CommandLineInterface;
import java.util.*;

import javax.xml.crypto.Data;

import iteration2.src.utils.DatabaseManager;
import iteration2.src.models.Admin;
import iteration2.src.controllers.AdminController;

public class CLIAdmin {

    private Scanner scanner;
    private AdminController adminController;
    private Admin currentadmin;
    
    CLIAdmin(Admin admin){
        this.currentadmin = admin;
        adminController = new AdminController(admin);
        scanner = new Scanner(System.in);
    }


    public void menuPage() {

        System.out.println(
                " Menu\n" +
                        "********\n" +
                        "  1. Course list\n" +
                        "  2. Constraint Settings\n\n" +
                        "Press q to quit" +
                        "Press corresponding row number to review and make changes for the list you want.\n" 
                        );

        String str = scanner.nextLine();
        if (str.equals("q")) {
            DatabaseManager.getInstance().saveToDatabase();
            System.exit(0);
        } else if (str.equals("1")) {
            courseListPage();
        } else if (str.equals("2")) {
            constraintsPage();
        } else {
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            menuPage();
        }

    }
}
