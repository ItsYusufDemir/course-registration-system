import logging
from iteration3.src.models.User import User
from iteration3.src.utils.DatabaseManager import DatabaseManager
from iteration3.src.CommandLineInterface.CLIAdmin import CLIAdmin
from iteration3.src.controllers.AdminController import AdminController

class Admin(User): 

    def fetchCourseList(self):
        return DatabaseManager().getCourseList()
    
    def fetchConstraints(self):
        return DatabaseManager().getConstraints()
    
    def createCourse(self,course):
        self.fetchCourseList().append(course)
        logging.log(logging.INFO, "Admin: " + self.userId + " created a new course: " + course.courseCode)
        DatabaseManager().saveToDatabase()
        return course
    
    def deleteCourse(self,course):
        result = self.fetchCourseList().remove(course)
        logging.log(logging.INFO, "Admin: " + self.userId + " deleted a course: " + course.courseCode)
        DatabaseManager().saveToDatabase()
        return result
    
    def editConstraints(self,constraints):
        result = DatabaseManager().editConstraints(constraints)
        logging.log(logging.INFO, "Admin: " + self.userId + " updated constraints")
        DatabaseManager().saveToDatabase()
        return constraints
    
    def findCourseByCourseCode(self,courseCode):
        return DatabaseManager().findCourseByCourseCode(courseCode)

    def getMyPage(self):
        CLIAdmin(AdminController(self)).menuPage()