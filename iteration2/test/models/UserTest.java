package iteration2.test.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import iteration2.src.models.Student;
import iteration2.src.models.User;
import iteration2.src.utils.DatabaseManager;
import org.junit.Test;

public class UserTest {

    @Test
    public void testCheckCredentials() {
        User user = new Student("123", "password", "John", "Doe", true, null, null, null, null, null, null);
        assertTrue(user.checkCredentials("123", "password"));
        assertFalse(user.checkCredentials("123", "wrongpassword"));
    }

}