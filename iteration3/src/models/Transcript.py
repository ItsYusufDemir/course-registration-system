from models.CourseGrade import CourseGrade
from enums.CourseResult import CourseResult
from utils.DatabaseManager import DatabaseManager
from enums.CourseType import CourseType


class Transcript(object):
    
    def __init__(self, takenCourses):
        self.takenCourses = takenCourses

    @classmethod
    def dictToObject(cls, dict):
        return cls(
            [CourseGrade.dictToObject(courseGrade) for courseGrade in dict['takenCourses']]
        )
    
    def addTakenCourse(self, course):
        self.takenCourses.append( CourseGrade(course, None, CourseResult.ACTIVE) )

    def acquirePassedCourses(self):
        passedCourses = list()
        for course in self.takenCourses:
            if course.getCourseResult() == CourseResult.PASSED:
                passedCourses.append(course)
        
        return passedCourses
    
    def calculateCompletedCredits(self):
        total = 0.0
        for course in self.takenCourses:
            if course.getCourseResult() == CourseResult.PASSED:
                total += course.getCourse().getCredit()
        
        return total
    
    def calculateGPA(self):
        totalPoint = 0.0
        totalCredit = 0.0

        for courseGrade in self.takenCourses:
            if courseGrade.getCourseResult() != CourseResult.PASSED:
                continue
            totalPoint += courseGrade.getCourse().getCourseCredit() * courseGrade.convertLetterGradeToScore()
            totalCredit += courseGrade.getCourse().getCourseCredit()
        try:
            return totalPoint / totalCredit
        except ZeroDivisionError:
            return 0
    
    def checkEngineeringProjectAvailability(self):
        totalCreditsForEngineeringProject = 0.0

        constraintsMap = DatabaseManager.getInstance().getConstraints()

        restricedCourses = ["ISG121", "ISG122"]
        restricedCourseTypes = [CourseType.NONTECHNICAL_ELECTIVE, CourseType.UNIVERSITY_ELECTIVE,
                                    CourseType.TECHNICAL_ELECTIVE,CourseType.FACULTY_ELECTIVE,]
        
        for takenCourse in self.takenCourses:
           if (takenCourse.getCourseResult() == CourseResult.PASSED and 
               takenCourse.getCourse().getCourseType() not in restricedCourseTypes and
                takenCourse.getCourse().getCourseCode() not in restricedCourses ):
               totalCreditsForEngineeringProject += takenCourse.getCourse().getCredit()

        if totalCreditsForEngineeringProject >= int(constraintsMap[3]):
            return True
        else:
            return False
        
    def getTakenCourses(self):
        return self.takenCourses