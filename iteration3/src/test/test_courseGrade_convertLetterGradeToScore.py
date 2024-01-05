
from dotenv import load_dotenv
import os
import sys

load_dotenv()
sys.path.append(os.getenv("ROOT_PATH"))

import unittest
from iteration3.src.models.Course import Course
from iteration3.src.models.CourseGrade import CourseGrade


class CourseGradeConvertLetterGradeToScore(unittest.TestCase):

    def testConvertLetterGradeToScore(self):
        course1 = Course(5, 5, 1, "course1", "1", None, None, None)
        course_grade1 = CourseGrade(course1, "BA", "PASSED")

        score = course_grade1.convertLetterGradeToScore()

        self.assertTrue(3.5 == score, "Score should match the expected value")

if __name__ == '__main__':
    unittest.main()