from enums import CourseStatus
from utils import Util

class SelectedCourse(object):
    def __init__(self,course,status,courseSection):
        self._course = course
        self._status = CourseStatus.DRAFT
        self._courseSection = courseSection


    def setStatus(self,status):
        self._status = status
        # Util.getLogger().info("Course (" + course.getCourseCode(
        #) + ") status changed to :" + status.toString());

    def getCourse(self):
        return self._course

    def getStatus(self):
        return self._status
    
    def getCourseSection(self):
        return self._courseSection