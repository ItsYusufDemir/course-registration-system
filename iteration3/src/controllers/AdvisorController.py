

class AdvisorController:
    currentAdvisor = None
    studentList = None

    def __init__(self, currentAdvisor):
        self.currentAdvisor = currentAdvisor.__init__()
        self.studentList = currentAdvisor.fetchAdvisedStudents()

    def approveCourse(self, student, selectedCourse):
        currentAdvisor.acceptCourse(student, selectedCourse)

    def denyCourse(self, student, selectedCourse):
        currentAdvisor.rejectCourse(student, selectedCourse)
        selectedCourse.getCourseSection().decrementStudentCount()

    def getStudentListOrderByStatus(self):
        studentListOrder = []

        for student in self.studentList:
            if(student.getStatus() == StudentStatus.PENDING):
                studentListOrder.append(student)
        
        for student in self.studentList:
            if(student.getStatus() == StudentStatus.DONE):
                studentListOrder.append(student)

        return studentListOrder


    def getNotification(self):
        return currentAdvisor.getNotification()

    def logOut(self):
        currentAdvisor.logout()

    def clearNotifications(self):
        currentAdvisor.clearNotifications()
    
    def getCurrentAdvisor(self):
        return currentAdvisor
    
    def setCurrentAdvisor(self, currentAdvisor):
        self.currentAdvisor = currentAdvisor