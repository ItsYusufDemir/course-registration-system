class AdminController:
    def __init__(self, currentAdmin):
        self._currentAdmin = currentAdmin

    def getCourseList(self):
        return self._currentAdmin.fetchCourseList()
    
    def getConstraints(self):
        return self._currentAdmin.fetchConstraints()
    
    def createCourse(self, course):
        return self._currentAdmin.createCourse(course)
    
    def deleteCourse(self, course):
        return self._currentAdmin.deleteCourse(course)
    
    def editConstraint(self, editedAttributes):
        return self._currentAdmin.editConstraints(editedAttributes)
    
    def findCourseByCourseCode(self, courseCode):
        return self._currentAdmin.findCourseByCourseCode(courseCode)
    
    def setNotificationToStudentAndAdvisor(self,notification):
        self._currentAdmin.setNotificationToStudentAndAdvisor(notification)
    
    def logout(self):
        self._currentAdmin.logout()