from enums.ApprovalStatus import ApprovalStatus

class AdvisorController:

    def __init__(self, currentAdvisor):
        self.currentAdvisor = currentAdvisor.__init__()
        self.studentList = currentAdvisor.fetchAdvisedStudents()

    def approveCourse(self, student, selectedCourse):
        self.currentAdvisor.acceptCourse(student, selectedCourse)

    def denyCourse(self, student, selectedCourse):
        self.currentAdvisor.rejectCourse(student, selectedCourse)
        selectedCourse.getCourseSection().decrementStudentCount()

    def getStudentListOrderByStatus(self):
        studentListOrder = []

        for student in self.studentList:
            if(student.getStatus() == ApprovalStatus.PENDING):
                studentListOrder.append(student)
        
        for student in self.studentList:
            if(student.getStatus() == ApprovalStatus.DONE):
                studentListOrder.append(student)

        return studentListOrder


    def getNotification(self):
        return self.currentAdvisor.getNotification()

    def logOut(self):
        self.currentAdvisor.logout()

    def clearNotifications(self):
        self.currentAdvisor.clearNotifications()
    
    def getCurrentAdvisor(self):
        return self.currentAdvisor
    
    def setCurrentAdvisor(self, currentAdvisor):
        self.currentAdvisor = currentAdvisor