from models.Constraint import Constraint
import unittest

class ConstraintTest_editConstraints(unittest.TestCase):
    def testEditConstraints(self):

        edited_attributes = {1: "5", 2: "True", 3: "10"}
        constraint = Constraint(10,False,5)

        constraint.editConstraints(edited_attributes)

        self.assertEqual(5,constraint.maxNumberOfCoursesStudentTake)
        self.assertEqual(True,constraint.addDropWeek)
        self.assertEqual(10,constraint.minRequiredECTSForTermProject)

if __name__ == '__main__':
    unittest.main()