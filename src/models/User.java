package models;

import interfaces.Showable;
import interfaces.Saveable;

public abstract class User implements Showable, Saveable {
    private String userId;
    private String firstName;
    private String lastName;
    private boolean status;

    public User(String userId, String password, String firstName, String lastName, boolean status) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
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
}
