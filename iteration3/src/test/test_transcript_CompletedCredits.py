
from dotenv import load_dotenv
import os
import sys

load_dotenv()
sys.path.append(os.getenv("ROOT_PATH"))

import unittest
from iteration3.src.enums.CourseResult import CourseResult
from iteration3.src.models.Course import Course
from iteration3.src.models.CourseGrade import CourseGrade
from iteration3.src.models.Transcript import Transcript


class TranscriptTestCompletedCredits(unittest.TestCase):

    def testCalculateCompletedCredits(self):
        course1 = Course(5, 5, 1, "course1", "1", None, None, None)
        course2 = Course(10, 10, 1, "course2", "2", None, None, None)
        course3 = Course(7, 7, 1, "course3", "3", None, None, None)

        courseGrade1 = CourseGrade(course1, "AA", CourseResult.ACTIVE)
        courseGrade2 = CourseGrade(course2, "BB", CourseResult.PASSED)
        courseGrade3 = CourseGrade(course3, "CC", CourseResult.PASSED)

        courseGrades = [courseGrade1, courseGrade2, courseGrade3]

        transcript = Transcript(courseGrades)
        completedCredits = transcript.calculateCompletedCredits()

        self.assertAlmostEqual(17.0, completedCredits, delta=0.1)

if __name__ == '__main__':
    unittest.main()
