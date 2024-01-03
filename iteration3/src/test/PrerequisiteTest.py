import unittest
from enums.CourseResult import CourseResult

from models.Course import Course
from models.CourseGrade import CourseGrade
from models.Prerequisite import Prerequisite
from models.SelectedCourse import SelectedCourse
from models.Student import Student
from models.Transcript import Transcript  

class PrerequisiteTest(unittest.TestCase):

    def testCheckPrequisiteCoursePassed(self):
        course1 = Course(5, 5, 1, "course1", "1", None, None, None)
        course4 = Course(5, 5, 1, "course4", "4", None, None, None)

        prerequisite_of_courses2 = [course1]
        prerequisite2 = Prerequisite(prerequisite_of_courses2)

        prerequisite_of_courses4 = [course4]
        prerequisite4 = Prerequisite(prerequisite_of_courses4)

        course2 = Course(10, 10, 1, "course2", "2", prerequisite2, None, None)
        course3 = Course(7, 7, 1, "course3", "3", prerequisite4, None, None)

        course_grade1 = CourseGrade(course1, "AA", CourseResult.PASSED)
        course_grade2 = CourseGrade(course2, "BB", CourseResult.PASSED)
        course_grade3 = CourseGrade(course3, "CC", CourseResult.PASSED)

        course_grades = [course_grade1, course_grade2, course_grade3]

        transcript = Transcript(course_grades)

        selected_course1 = SelectedCourse(course1, None)
        selected_course2 = SelectedCourse(course2, None)
        selected_course3 = SelectedCourse(course3, None)

        selected_courses = [selected_course1, selected_course2, selected_course3]

        student = Student("1", "1", "eren", "duyuk", False, "a", "1", 0, selected_courses, None, None, transcript)

        self.assertTrue(course2.checkPrerequisite(student), "Prerequisite should be passed")
        self.assertFalse(course3.checkPrerequisite(student), "Prerequisite should not be passed")

if __name__ == '__main__':
    unittest.main()
