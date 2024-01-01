#beyza
import logging
from enums.ApprovalStatus import ApprovalStatus
from models.User import User

class Student(User):
    def __init__(self, userId, password, firstName, lastName, status, notifications, email, identityNumber, currentSemester,
                  selectedCourses, advisorOfStudent, approvalStatus, transcript):
        super().__init__(userId, password, firstName, lastName, status, notifications)
        self.email = email
        self.identityNumber = identityNumber
        self.currentSemester = currentSemester
        self.selectedCourses = selectedCourses
        self.advisorOfStudent = advisorOfStudent
        self.approvalStatus = approvalStatus
        self.transcript = transcript
        
    def listAvailableCourseSections(self):
        pass
    
    def checkIfItExistsInSelectedCourses(self, course):
        pass
    
    def checkIfItWasTaken(self, course):
        pass
    
    def findRepeatCourseSections(self):
        pass
    
    def checkCourseType(self, course):
        pass
    
    def addNewCourse(self, selectedCourse):
        pass
    
    def deleteCourse(self, selectedCourse):
        pass
    
    def checkCompulsoryCourses(self):
        pass
    
    def sendSelectedCoursesToApproval(self):
        pass
    
    def getMyPage(self):
        pass