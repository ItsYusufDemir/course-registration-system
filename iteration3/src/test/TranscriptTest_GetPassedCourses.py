import unittest
from iteration3.src.enums.CourseResult import CourseResult
from iteration3.src.models.Course import Course
from iteration3.src.models.CourseGrade import CourseGrade
from iteration3.src.models.Transcript import Transcript

class TranscriptTestGetPassedCourses(unittest.TestCase):

    def testGetPassedCourses(self):
        course1 = Course(5, 5, 1, "course1", "1", None, None, None)
        course2 = Course(10, 10, 1, "course2", "2", None, None, None)
        course3 = Course(7, 7, 1, "course3", "3", None, None, None)

        course_grade1 = CourseGrade(course1, "FF", CourseResult.FAILED)
        course_grade2 = CourseGrade(course2, "BB", CourseResult.PASSED)
        course_grade3 = CourseGrade(course3, "CC", CourseResult.PASSED)

        course_grades = [course_grade1, course_grade2, course_grade3]

        transcript = Transcript(course_grades)
        test_courses_passed = transcript.acquirePassedCourses()

        self.assertFalse(course1 in test_courses_passed, "Passed courses should not contain course1")
        self.assertTrue(course2 in test_courses_passed, "Passed courses should contain course2")
        self.assertTrue(course3 in test_courses_passed, "Passed courses should contain course3")

if __name__ == '__main__':
    unittest.main()
