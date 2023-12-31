from enums.CourseStatus import CourseStatus
from utils.Util import Util

class SelectedCourse(object):
    def __init__(self,course,status,courseSection):
        self.course = course
        self.status = CourseStatus.DRAFT
        self.courseSection = courseSection


    def setStatus(self,status):
        self.status = status
        # Util.getLogger().info("Course (" + course.getCourseCode(
        #) + ") status changed to :" + status.toString());

    def getCourse(self):
        return self.course

    def getStatus(self):
        return self.status
    
    def getCourseSection(self):
        return self.courseSection