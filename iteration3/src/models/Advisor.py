from dataclasses import dataclass
import logging

from CommandLineInterface.CLIAdvisor import CLIAdvisor
from controllers.AdvisorController import AdvisorController
from enums.ApprovalStatus import ApprovalStatus
from enums.CourseResult import CourseResult
from enums.CourseStatus import CourseStatus
from interfaces.Color import Color
from models.User import User
from models.CourseGrade import CourseGrade
from utils.DatabaseManager import DatabaseManager
from utils.Util import Util

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
            return
        
        selectedCourse.setStatus(CourseStatus.APPROVED)

        newCourse = CourseGrade(selectedCourse.getCourse(), None, CourseResult.ACTIVE)

        student.getTranscript().getTakenCourses().append(newCourse)

        logging.log(logging.INFO, f"{self.getUserId()} - Course: {selectedCourse.getCourse().getCourseCode()} is approved for student: {student.getUserId()}")
        
        for course in student.getSelectedCourses():
            if(course.getStatus() == CourseStatus.PENDING):
                return

        student.setApprovalStatus(ApprovalStatus.DONE)
        logging.log(logging.INFO, f"{self.getUserId()} - Student: {student.getUserId()} is approved.")
        DatabaseManager.getInstance().saveToDatabase()




    def rejectCourse(self, student, selectedCourse):

        if(selectedCourse.status != CourseStatus.PENDING):
            Util.sendFeedback("This course is not pending for approval.", Color.RED)
            return
        
        selectedCourse.setStatus(CourseStatus.DENIED)

        notification = "Your " + selectedCourse.getCourse().getCourseCode() + " course is rejected."
        self.setNotificationToStudent(student, notification)

        logging.log(logging.INFO, f"{self.getUserId()} - Course: {selectedCourse.getCourse().getCourseCode()} is rejected for student: {student.getUserId()}")
        
        for course in student.getSelectedCourses():
            if(course.getStatus() == CourseStatus.PENDING):
                return

        logging.log(logging.INFO, f"{self.getUserId()} - Student: {student.getUserId()} is approved.")            
        student.setApprovalStatus(ApprovalStatus.DONE)

        DatabaseManager.getInstance().saveToDatabase()



    def fetchAdvisedStudents(self):
        return DatabaseManager.getInstance().fetchAdvisedStudents(self)


    def setNotificationToStudent(self, student, notification):
        student.addNotification(notification)
        DatabaseManager.getInstance().saveToDatabase()