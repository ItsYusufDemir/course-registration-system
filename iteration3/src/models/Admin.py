import logging
from models.User import User
from models.Course import Course
from models.Constraint import Constraint
from utils.DatabaseManager import DatabaseManager
from CommandLineInterface.CLIAdmin import CLIAdmin
from controllers.AdminController import AdminController

class Admin(User): 

    def fetchCourseList(self)->[Course]:
        return DatabaseManager().getInstance().getCourseList()
    
    def fetchConstraints(self)->[Constraint]:
        return DatabaseManager().getInstance().getConstraints()
    
    def createCourse(self,course)->Course:
        self.fetchCourseList().append(course)
        logging.log(logging.INFO, "Admin: " + self.userId + " created a new course: " + course.getCourseCode())
        DatabaseManager().getInstance().saveToDatabase()
        return course
    
    def deleteCourse(self,course)->bool:
        result = self.fetchCourseList().remove(course)
        logging.log(logging.INFO, "Admin: " + self.userId + " deleted a course: " + course.getCourseCode())
        DatabaseManager().getInstance().saveToDatabase()
        return result
    
    def editConstraints(self,constraints)->bool:
        result = DatabaseManager().editConstraints(constraints)
        logging.log(logging.INFO, "Admin: " + self.userId + " updated constraints")
        DatabaseManager().getInstance().saveToDatabase()
        return result
    
    def findCourseByCourseCode(self,courseCode)->Course:
        return DatabaseManager().getInstance().findCourseByCourseCode(courseCode)

    def getMyPage(self):
        CLIAdmin(AdminController(self)).menuPage()