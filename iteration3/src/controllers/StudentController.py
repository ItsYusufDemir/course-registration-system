#beyz
class StudentController:
    def __init__(self, currentStudent):
        self.currentStudent = currentStudent
        
    def addSelectedCourse(self, selectedCourse):
        return self.currentStudent.addNewCourse(selectedCourse)
    
    def getAvailableCourseSections(self):
        return self.currentStudent.listAvailableCourseSections()
    
    def removeSelectedCourse(self, selectedCourse):
        return self.currentStudent.deleteCourse(selectedCourse)
    
    def sendSelectedCoursesToApproval(self):
        return self.currentStudent.sendSelectedCoursesToApproval()

    def getNotifications(self):
        return self.currentStudent.getNotifications()
    
    def getTimeTable(self):
        return self.currentStudent.createTimeTable()
    
    def logout(self):
        self.currentStudent.logout()

    def clearNotifications(self):
        return self.currentStudent.clearNotifications()
    
    def getSelectedCourses(self):
        return self.currentStudent.getSelectedCourses()
    
    def getTimeTable(self):
        return self.currentStudent.createTimeTable()
    
    def getApprovalStatus(self):
        return self.currentStudent.getApprovalStatus()
