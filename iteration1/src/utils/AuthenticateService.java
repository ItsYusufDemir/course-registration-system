package iteration1.src.utils;

import java.util.ArrayList;
import java.util.List;
import iteration1.src.utils.DatabaseManager;

import iteration1.src.models.User;

public class AuthenticateService {

    public User authenticateUser(String userID, String password) {

        DatabaseManager database = DatabaseManager.getInstance();
        List<User> users = new ArrayList<>();

        users.addAll(database.getStudents());
        users.addAll(database.getAdvisors());

        for (User user : users) {
            if (user.checkCredentials(userID, password)) {
                return user;
            }

        }

        return null;

    }

}
