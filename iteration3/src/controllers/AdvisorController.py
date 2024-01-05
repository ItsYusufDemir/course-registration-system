from iteration3.src.enums.ApprovalStatus import ApprovalStatus

class AdvisorController:

    def __init__(self, currentAdvisor):
        self._currentAdvisor = currentAdvisor
        self._studentList = currentAdvisor.fetchAdvisedStudents()

    def approveCourse(self, student, selectedCourse):
        return self._currentAdvisor.acceptCourse(student, selectedCourse)

    def denyCourse(self, student, selectedCourse):
        return self._currentAdvisor.rejectCourse(student, selectedCourse)



    def getStudentListOrderByStatus(self):
        studentListOrder = []

        for student in self._studentList:
            if(student.getApprovalStatus() == ApprovalStatus.PENDING):
                studentListOrder.append(student)
        
        for student in self._studentList:
            if(student.getApprovalStatus() == ApprovalStatus.DONE):
                studentListOrder.append(student)
        
        for student in self._studentList:
            if(student.getApprovalStatus() == ApprovalStatus.FINALIZED_REGISTRATION):
                studentListOrder.append(student)

        return studentListOrder
    




    def finalizeRegistration(self, student):
        self._currentAdvisor.finalizeRegistration(student)

    


    def getNotifications(self):
        return self._currentAdvisor.getNotifications()

    def logOut(self):
        self._currentAdvisor.logout()

    def clearNotifications(self):
        self._currentAdvisor.clearNotifications()
    
    def getCurrentAdvisor(self):
        return self._currentAdvisor
    
    def setCurrentAdvisor(self, currentAdvisor):
        self._currentAdvisor = currentAdvisor