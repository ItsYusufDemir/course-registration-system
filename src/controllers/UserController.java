package models;

import utils.AuthenticateService;

public class UserController {

    private User currentUser;

    public UserController() {
        currentUser = null;
    }

    User loginUser(String username, String password) {
        AuthenticateService authenticateService = new AuthenticateService();
        return currentUser = authenticateService.authenticateUser(username, password);
    }

    void logoutUser() {
        currentUser.logout();
        currentUser = null;
    }

    User getCurrentUser() {
        return currentUser;
    }

    void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
