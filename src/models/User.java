package models;

import interfaces.Showable;
import utils.AuthenticateService;
import interfaces.Saveable;

public abstract class User implements Showable, Saveable {
    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private boolean status;

    public User() {

    }

    public User(String userId, String password, String firstName, String lastName, boolean status) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public User(String username, String password) {
        AuthenticateService authenticateService = new AuthenticateService();
        User user = authenticateService.authenticateUser(username, password);
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.status = user.isStatus();
    }

    public void logout() {
        System.out.println("User logged out");
        return;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() { return password; }
}
