package iteration2.src.utils;

import java.util.ArrayList;
import java.util.List;

import iteration2.src.models.User;

public class AuthenticateService {

    public User authenticateUser(String userID, String password) {

        DatabaseManager database = DatabaseManager.getInstance();
        List<User> users = new ArrayList<>();

        users.addAll(database.getAdmins());
        users.addAll(database.getAdvisors());
        users.addAll(database.getStudents());

        for (User user : users) {
            if (user.checkCredentials(userID, password)) {
                Util.getLogger().info("User:" + userID + " logged in");
                return user;

            }

        }
        Util.getLogger().info("User:" + userID + " failed to log in");
        return null;

    }

}
