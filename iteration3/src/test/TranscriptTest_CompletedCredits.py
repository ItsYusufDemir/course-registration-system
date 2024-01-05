import unittest
from enums.CourseResult import CourseResult

from iteration3.src.models.Course import Course
from iteration3.src.models.CourseGrade import CourseGrade
from iteration3.src.models.Transcript import Transcript


class TranscriptTestCompletedCredits(unittest.TestCase):

    def testCalculateCompletedCredits(self):
        course1 = Course(5, 5, 1, "course1", "1", None, None, None)
        course2 = Course(10, 10, 1, "course2", "2", None, None, None)
        course3 = Course(7, 7, 1, "course3", "3", None, None, None)

        course_grade1 = CourseGrade(course1, "AA", CourseResult.ACTIVE)
        course_grade2 = CourseGrade(course2, "BB", CourseResult.PASSED)
        course_grade3 = CourseGrade(course3, "CC", CourseResult.PASSED)

        course_grades = [course_grade1, course_grade2, course_grade3]

        transcript = Transcript(course_grades)
        completed_credits = transcript.calculateCompletedCredits()

        self.assertAlmostEqual(17.0, completed_credits, delta=0.1)

if __name__ == '__main__':
    unittest.main()
