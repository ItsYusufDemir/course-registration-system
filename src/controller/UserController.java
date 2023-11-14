package controller;

import model.User;

public class UserController {
    private User currentUser;

    // We may not need userController. If we have a user constructor with username
    // and password,we can just use constructor and call the private authenticate
    // the user.

    public UserController() {
    }

    public User authenticateUser(String username, String password) {

        AuthenticatService authenticatService = new AuthenticatService();

        User user = authenticatService.authenticateUser(username, password);
        return user;
    }

}
