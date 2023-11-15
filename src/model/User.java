package model;

import interfaces.Showable;

public abstract class User implements Showable {
    private int userId;
    private String firstName;
    private String lastName;
    private boolean status;

    public User(int userId, String firstName, String lastName, boolean status) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public void logout() {
        System.out.println("User logged out");
        return;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
