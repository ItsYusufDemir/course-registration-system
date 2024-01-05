
from dotenv import load_dotenv
import os
import sys

load_dotenv()
sys.path.append(os.getenv("ROOT_PATH"))

import unittest
from iteration3.src.models.Student import Student

class UserTest(unittest.TestCase):

    def testCheckCredentials(self):
        user = Student("123", "password", "John", "Doe", True, None, None, 0, None, None, None, None,None)
        self.assertTrue(user.checkCredentials("123", "password"))
        self.assertFalse(user.checkCredentials("123", "wrongpassword"))

if __name__ == '__main__':
    unittest.main()
