import logging
from iteration3.src.models.User import User
from iteration3.src.models.Course import Course
from iteration3.src.models.Constraint import Constraint
from iteration3.src.utils.DatabaseManager import DatabaseManager
from iteration3.src.CommandLineInterface.CLIAdmin import CLIAdmin
from iteration3.src.controllers.AdminController import AdminController

class Admin(User): 

    def __init__(self, userId, password, firstName, lastName, status, notifications):
        super().__init__(userId, password, firstName, lastName, status, notifications)

    @classmethod
    def dictToObject(cls, adminDict):
        return cls(adminDict["userId"], adminDict["password"], adminDict["firstName"],
                    adminDict["lastName"], adminDict["status"], adminDict["notifications"])

    def fetchCourseList(self)->[Course]:
        return DatabaseManager().getInstance().getCourseList()
    
    def fetchConstraints(self)->[Constraint]:
        return DatabaseManager().getInstance().getConstraints()
    
    def createCourse(self,course)->Course:
        self.fetchCourseList().append(course)
        logging.log(logging.INFO, f"Admin: {self._userId} created a new course: {course.getCourseCode()}")
        DatabaseManager().getInstance().saveToDatabase()
        return course
    
    def deleteCourse(self, course) -> bool:
        courseList = self.fetchCourseList()
        if course in courseList:
            courseList.remove(course)
            logging.log(logging.INFO, f"Admin: {self._userId} deleted a course: {course.getCourseCode()}")
            DatabaseManager().getInstance().saveToDatabase()
            return True
        else:
            # Course not found in the list
            return False
    
    def editConstraints(self,constraints)->bool:
        result = DatabaseManager().getInstance().editConstraints(constraints)
        logging.log(logging.INFO, f"Admin: {self._userId} updated constraints")
        DatabaseManager().getInstance().saveToDatabase()
        return result
    
    def findCourseByCourseCode(self,courseCode)->Course:
        return DatabaseManager().getInstance().findCourseByCourseCode(courseCode)

    def getMyPage(self):
        CLIAdmin(AdminController(self)).menuPage()

    def setNotificationToStudentAndAdvisor(self, notification):
        for student in DatabaseManager.getInstance().getStudentList():
            student.addNotification(notification)
        for advisor in DatabaseManager.getInstance().getAdvisorList():
            advisor.addNotification(notification)
        DatabaseManager.getInstance().saveToDatabase()