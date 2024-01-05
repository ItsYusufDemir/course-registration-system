
from dotenv import load_dotenv
import os
import sys

load_dotenv()
sys.path.append(os.getenv("ROOT_PATH"))

import unittest
from iteration3.src.models.Constraint import Constraint

class ConstraintTest_editConstraints(unittest.TestCase):
    def testEditConstraints(self):

        edited_attributes = {1: "5", 2: "True", 3: "10", 4: "False"}
        constraint = Constraint(10,False,5,False)

        constraint.editConstraint(edited_attributes)

        self.assertEqual(5,constraint.maxNumberOfCoursesStudentTake)
        self.assertEqual(True,constraint.addDropWeek)
        self.assertEqual(10,constraint.minRequiredECTSForTermProject)

if __name__ == '__main__':
    unittest.main()