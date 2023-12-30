from CommandLineInterface.CLIAdvisor import CLIAdvisor
from enums.ApprovalStatus import ApprovalStatus
from enums.CourseStatus import CourseStatus
from interfaces.Color import Color
from models.User import User
from models.CourseGrade import CourseGrade
from utils.DatabaseManager import DatabaseManager
from utils.Util import Util


class Advisor(User):

    def __init__(self, userId, password, firstName, lastName, status, notifications):
        super().__init__(userId, password, firstName, lastName, status, notifications)

    def getMyPage(self):
        cliAdvisor = CLIAdvisor()
        cliAdvisor.menuPage()
    
    def acceptCourse(self, student, selectedCourse):

        if(selectedCourse.status != CourseStatus.PENDING):
            Util.sendFeedback("This course is not pending for approval.", Color.RED)
            Util.getLogger().warning(self.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                    + " is not pending for approval.")
            return
        
        selectedCourse.setStatus(CourseStatus.APPROVED)

        newCourse = CourseGrade(selectedCourse.getCourse())

        student.getTranscript().getTakenCourses().add(newCourse)

        Util.getLogger().info(self.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                + " is approved for student: " + student.getUserId())
        
        for course in student.getSelectedCourses():
            if(course.getStatus() == CourseStatus.PENDING):
                return

        student.setStatus(ApprovalStatus.DONE)
        Util.getLogger().info(self.getUserId() + " - Student: " + student.getUserId() + " is approved.")

        DatabaseManager.getInstance().saveToDatabase()




    def rejectCourse(self, student, selectedCourse):

        if(selectedCourse.status != CourseStatus.PENDING):
            Util.sendFeedback("This course is not pending for approval.", Color.RED)
            return
        
        selectedCourse.setStatus(CourseStatus.DENIED)

        notification = "Your " + selectedCourse.getCourse().getCourseCode() + " course is rejected."
        self.setNotificationToStudent(student, notification)

        Util.getLogger().info(self.getUserId() + " - Course: " + selectedCourse.getCourse().getCourseCode()
                + " is rejected for student: " + student.getUserId())
        
        for course in student.getSelectedCourses():
            if(course.getStatus() == CourseStatus.PENDING):
                return
            
        Util.getLogger().info(self.getUserId() + " - Student: " + student.getUserId() + " is approved.")
        student.setApproved(ApprovalStatus.DONE)

        DatabaseManager.getInstance().saveToDatabase()



    def fetchAdvisedStudents(self):
        return DatabaseManager.getInstance().fetchAdvisedStudents(self)


    def setNotificationToStudent(self, student, notification):
        student.addNotification(notification)
        DatabaseManager.getInstance().saveToDatabase()