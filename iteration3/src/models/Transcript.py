from models.CourseGrade import CourseGrade
from enums.CourseResult import CourseResult
from utils.DatabaseManager import DatabaseManager
from enums.CourseType import CourseType


class Transcirpt(object):
    
    def __init__(self, takenCourses):
        self._takenCourses = takenCourses
    
    def addTakenCourse(self, course):
        self._takenCourses.append( CourseGrade(course, None, CourseResult.ACTIVE) )

    def acquirePassedCourses(self):
        self.passedCourses = list()
        for self.course in self._takenCourses:
            if self.course.result == CourseResult.PASSED:
                self.passedCourses.append(self.course)
        
        return self.passedCourses
    
    def calculateCompletedCredits(self):
        self.total = 0.0
        for self.course in self._takenCourses:
            if self.course.result == CourseResult.PASSED:
                self.total += self.course.getCourse().getCredit()
        
        return self.total
    
    def calculateGPA(self):
        self.totalPoint = 0.0
        self.totalCredit = 0.0

        for self.courseGrade in self._takenCourses:
            if self.courseGrade.result != CourseResult.PASSED:
                continue
            self.totalPoint += self.courseGrade.getCourse().getCourseCredit() * self.courseGrade.convertLetterGradeToScore()
            self.totalCredit += self.courseGrade.getCourse().getCourseCredit()
        
        return self.totalPoint / self.totalCredit
    
    def checkEngineeringProjectAvailability(self):
        self.totalCreditsForEngineeringProject = 0.0

        self.constraint = DatabaseManager.getInstance().getConstraint() # TODO: not sure if we will dictionary or not

        self.restricedCourses = ["ISG121", "ISG122"]
        self.restricedCourseTypes = [CourseType.NONTECHNICAL_ELECTIVE, CourseType.UNIVERSITY_ELECTIVE,
                                    CourseType.TECHNICAL_ELECTIVE,CourseType.FACULTY_ELECTIVE,]
        
        for self.takenCourse in self._takenCourses:
           if (self.takenCourse.getCourseResult() == CourseResult.PASSED and 
               self.takenCourse.getCourse().getCourseType() not in self.restricedCourseTypes and
                 self.takenCourse.getCourse().getCourseCode() not in self.restricedCourses ):
               self.totalCreditsForEngineeringProject += self.takenCourse.getCourse().getCredit()

        if self.totalCreditsForEngineeringProject >= self.constraint[3]:
            return True
        else:
            return False