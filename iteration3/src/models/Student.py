#beyza
from enums import ApprovalStatus
import User
class Student(User):
    def __init__(self, userID, password, firstName, lastName, status, email, identityNumber, currentSemester, selectedCourses, advisorOfStudent, transcript):
        super().__init__(userID, password, firstName, lastName, status)
        self.email = email
        self.identityNumber = identityNumber
        self.currentSemester = currentSemester
        self.selectedCourses = selectedCourses
        self.advisorOfStudent = advisorOfStudent
        self.approvalStatus = ApprovalStatus.DONE
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