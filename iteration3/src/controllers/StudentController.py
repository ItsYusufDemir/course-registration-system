#beyz
class StudentController:
    def __init__(self, currentStudent):
        self._currentStudent = currentStudent
        
    def addSelectedCourse(self, selectedCourse):
        return self._currentStudent.addNewCourse(selectedCourse)
    
    def getAvailableCourseSections(self):
        return self._currentStudent.listAvailableCourseSections()
    
    def removeSelectedCourse(self, selectedCourse):
        return self._currentStudent.deleteCourse(selectedCourse)
    
    def sendSelectedCoursesToApproval(self):
        return self._currentStudent.sendSelectedCoursesToApproval()

    def getNotifications(self):
        return self._currentStudent.getNotifications()
    
    def getTimeTable(self):
        return self._currentStudent.createTimeTable()
    
    def logout(self):
        self._currentStudent.logout()

    def clearNotifications(self):
        return self._currentStudent.clearNotifications()
    
    def getSelectedCourses(self):
        return self._currentStudent.getSelectedCourses()
    
    def getApprovalStatus(self):
        return self._currentStudent.getApprovalStatus()
