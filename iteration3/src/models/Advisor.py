from dataclasses import dataclass
import logging

from iteration3.src.CommandLineInterface.CLIAdvisor import CLIAdvisor
from iteration3.src.controllers.AdvisorController import AdvisorController
from iteration3.src.enums.ApprovalStatus import ApprovalStatus
from iteration3.src.enums.CourseResult import CourseResult
from iteration3.src.enums.CourseStatus import CourseStatus
from iteration3.src.interfaces.Color import Color
from iteration3.src.models.User import User
from iteration3.src.models.CourseGrade import CourseGrade
from iteration3.src.utils.DatabaseManager import DatabaseManager
from iteration3.src.utils.Util import Util

@dataclass
class Advisor(User):

    def __init__(self, userId, password, firstName, lastName, status, notifications):
        super().__init__(userId, password, firstName, lastName, status, notifications)

    @classmethod
    def dictToObject(self, dict):
        return Advisor(
            dict['userId'],
            dict['password'],
            dict['firstName'],
            dict['lastName'],
            dict['status'],
            dict['notifications']
        )


    def getMyPage(self):
        cliAdvisor = CLIAdvisor(AdvisorController(self))
        cliAdvisor.menuPage()
    

    def acceptCourse(self, student, selectedCourse):

        if(selectedCourse.status != CourseStatus.PENDING):
            Util.sendFeedback("This course is not pending for approval.", Color.RED)
            logging.log(logging.INFO, f"{self.getUserId()} - Course: {selectedCourse.getCourse().getCourseCode()} is not pending for approval.")
            return False
        
        selectedCourse.setStatus(CourseStatus.APPROVED)

        newCourse = CourseGrade(selectedCourse.getCourse(), None, CourseResult.ACTIVE)

        student.getTranscript().getTakenCourses().append(newCourse)

        logging.log(logging.INFO, f"{self.getUserId()} - Course: {selectedCourse.getCourse().getCourseCode()} is approved for student: {student.getUserId()}")
        
        #If there is any pending course, do not change the approval status
        for course in student.getSelectedCourses():
            if(course.getStatus() == CourseStatus.PENDING):
                DatabaseManager.getInstance().saveToDatabase()
                return True

        student.setApprovalStatus(ApprovalStatus.DONE)
        logging.log(logging.INFO, f"{self.getUserId()} - Student: {student.getUserId()} is approved.")
        DatabaseManager.getInstance().saveToDatabase()

        return True




    def rejectCourse(self, student, selectedCourse):

        if(selectedCourse.status != CourseStatus.PENDING):
            Util.sendFeedback("This course is not pending for approval.", Color.RED)
            return False
        
        selectedCourse.setStatus(CourseStatus.DENIED)
        selectedCourse.getCourseSection().decrementStudentCount()

        notification = "Your " + selectedCourse.getCourse().getCourseCode() + " course is rejected."
        self.setNotificationToStudent(student, notification)

        logging.log(logging.INFO, f"{self.getUserId()} - Course: {selectedCourse.getCourse().getCourseCode()} is rejected for student: {student.getUserId()}")
        
        for course in student.getSelectedCourses():
            if(course.getStatus() == CourseStatus.PENDING):
                DatabaseManager.getInstance().saveToDatabase()
                return True

        logging.log(logging.INFO, f"{self.getUserId()} - Student: {student.getUserId()} is approved.")            
        student.setApprovalStatus(ApprovalStatus.DONE)

        DatabaseManager.getInstance().saveToDatabase()
        return True



    def finalizeRegistration(student):

        selectedCourses = student.getSelectedCourses()

        for course in selectedCourses:
            if(course.getStatus() == CourseStatus.APPROVED):
                course.setStatus(CourseStatus.APPROVED_FINALIZED)
            else:
                course.setStatus(CourseStatus.DENIED_FINALIZED)
        
        student.setStatus(ApprovalStatus.FINALIZED_REGISTRATION)                  
        DatabaseManager.getInstance().saveToDatabase()




    def fetchAdvisedStudents(self):
        return DatabaseManager.getInstance().fetchAdvisedStudents(self)


    def setNotificationToStudent(self, student, notification):
        student.addNotification(notification)
        DatabaseManager.getInstance().saveToDatabase()