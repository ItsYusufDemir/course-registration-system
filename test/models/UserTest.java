package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utils.DatabaseManager;

public class UserTest {

    @org.junit.Test
    public void checkCredentials() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        User user = databaseManager.getStudents().get(0);
        boolean result = user.checkCredentials(user.getUserId(), user.getPassword());
        System.out.println("result -> " + result);

    }

    @Test
    public void testCheckCredentials() {
        User user = new Student("123", "password", "John", "Doe", true, null, null, null, null, null, null);
        assertTrue(user.checkCredentials("123", "password"));
        assertFalse(user.checkCredentials("123", "wrongpassword"));
    }

    @Test
    public void testGettersAndSetters() {
        User user = new Student("123", "password", "John", "Doe", true, null, null, null, null, null, null);
        assertEquals("123", user.getUserId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertTrue(user.isStatus());

        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setStatus(false);

        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertFalse(user.isStatus());
    }

}