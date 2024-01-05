
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

class TranscriptTestGPA(unittest.TestCase):

    def testCalculateGPA(self):
        course1 = Course(5, 5, 1, "course1", "1", None, None, None)
        course2 = Course(10, 10, 1, "course2", "2", None, None, None)
        course3 = Course(7, 7, 1, "course3", "3", None, None, None)

        course_grade1 = CourseGrade(course1, "AA", CourseResult.PASSED)
        course_grade2 = CourseGrade(course2, "BB", CourseResult.PASSED)
        course_grade3 = CourseGrade(course3, "CC", CourseResult.PASSED)

        course_grades = [course_grade1, course_grade2, course_grade3]

        transcript = Transcript(course_grades)
        gpa = transcript.calculateGPA()

        self.assertAlmostEqual((64.0 / 22), gpa, delta=0.01, msg="GPA should match the expected value")

if __name__ == '__main__':
    unittest.main()
