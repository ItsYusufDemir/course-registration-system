import logging
from iteration3.src.models.User import User
from iteration3.src.models.Course import Course
from iteration3.src.models.Constraint import Constraint
from iteration3.src.utils.DatabaseManager import DatabaseManager
from iteration3.src.CommandLineInterface.CLIAdmin import CLIAdmin
from iteration3.src.controllers.AdminController import AdminController

class Admin(User): 

    def fetchCourseList(self)->[Course]:
        return DatabaseManager().getCourseList()
    
    def fetchConstraints(self)->[Constraint]:
        return DatabaseManager().getConstraints()
    
    def createCourse(self,course)->Course:
        self.fetchCourseList().append(course)
        logging.log(logging.INFO, "Admin: " + self.userId + " created a new course: " + course.courseCode)
        DatabaseManager().saveToDatabase()
        return course
    
    def deleteCourse(self,course)->bool:
        result = self.fetchCourseList().remove(course)
        logging.log(logging.INFO, "Admin: " + self.userId + " deleted a course: " + course.courseCode)
        DatabaseManager().saveToDatabase()
        return result
    
    def editConstraints(self,constraints)->bool:
        result = DatabaseManager().editConstraints(constraints)
        logging.log(logging.INFO, "Admin: " + self.userId + " updated constraints")
        DatabaseManager().saveToDatabase()
        return result
    
    def findCourseByCourseCode(self,courseCode)->Course:
        return DatabaseManager().findCourseByCourseCode(courseCode)

    def getMyPage(self):
        CLIAdmin(AdminController(self)).menuPage()