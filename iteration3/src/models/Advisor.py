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
            logging.log(logging.INFO, f"{self.getUserId()} - Course: {selectedCourse.getCourse().getCourseCode()} is not pending for approval.")
            raise Exception("This course is not pending for approval.")
        
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
            raise Exception("This course is not pending for reject.")
        
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