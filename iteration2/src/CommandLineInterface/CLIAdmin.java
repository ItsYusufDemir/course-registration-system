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
                        "  1. Student list\n" +
                        "  2. Advisor list\n" +
                        "  3. Course list\n" +
                        "  4. Constraints\n\n" +
                        "Press q to quit" +
                        "Press corresponding row number to review and make changes for the list you want.\n" +
                        "Press 4th option to edit constraints of the system\n");

        String str = scanner.nextLine();
        if (str.equals("q")) {
            DatabaseManager.getInstance().saveToDatabase();
            System.exit(0);
        } else if (str.equals("1")) {

            
        } else if (str.equals("2")) {
            
        } else if (str.equals("3")) {
                
        } else if (str.equals("4")) {

        } else {
            System.out.println("Invalid input");
            System.out.println("\n\n\n");
            menuPage();
        }

    }
}
