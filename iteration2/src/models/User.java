package iteration2.src.models;

import java.util.List;

import iteration2.src.interfaces.Showable;

public abstract class User implements Showable {
    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private boolean status;
    private List<String> notifications;

    public User() {

    }

    public User(String userId, String password, String firstName, String lastName, boolean status) {
        this.userId = userId;
        this.firstName = firstName;
        this.password = password;
        this.lastName = lastName;
        this.status = status;
    }

    public void logout() {
        System.out.println("User logged out");
        return;
    }

    public boolean checkCredentials(String userId, String password) {
        if (this.userId.equals(userId) && this.password.equals(password))
            return true;
        return false;
    }

    public String getUserId() {
        return userId;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addNotification(String notification) {
        notifications.add(notification);
    }

    public void removeNotification(String notification) {
        notifications.remove(notification);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    

    
}
