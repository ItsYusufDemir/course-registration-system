import unittest
from models.User import Student

class UserTest(unittest.TestCase):

    def testCheckCredentials(self):
        user = Student("123", "password", "John", "Doe", True, None, None, 0, None, None, None, None)
        self.assertTrue(user.check_credentials("123", "password"))
        self.assertFalse(user.check_credentials("123", "wrongpassword"))

if __name__ == '__main__':
    unittest.main()
