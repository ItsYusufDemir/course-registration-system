package model;

import interfaces.Showable;

public abstract class User implements Showable {
    private int userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean status;

    public User(int userId, String username, String password, String firstName, String lastName, boolean status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    private User() {
    }

    public void logout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
