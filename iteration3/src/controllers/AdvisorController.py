from enums.ApprovalStatus import ApprovalStatus

class AdvisorController:

    def __init__(self, currentAdvisor):
        self.currentAdvisor = currentAdvisor
        self.studentList = currentAdvisor.fetchAdvisedStudents()

    def approveCourse(self, student, selectedCourse):
        return self.currentAdvisor.acceptCourse(student, selectedCourse)

    def denyCourse(self, student, selectedCourse):
        return self.currentAdvisor.rejectCourse(student, selectedCourse)

    def getStudentListOrderByStatus(self):
        studentListOrder = []

        for student in self.studentList:
            if(student.getApprovalStatus() == ApprovalStatus.PENDING):
                studentListOrder.append(student)
        
        for student in self.studentList:
            if(student.getApprovalStatus() == ApprovalStatus.DONE):
                studentListOrder.append(student)

        return studentListOrder


    def getNotifications(self):
        return self.currentAdvisor.getNotifications()

    def logOut(self):
        self.currentAdvisor.logout()

    def clearNotifications(self):
        self.currentAdvisor.clearNotifications()
    
    def getCurrentAdvisor(self):
        return self.currentAdvisor
    
    def setCurrentAdvisor(self, currentAdvisor):
        self.currentAdvisor = currentAdvisor