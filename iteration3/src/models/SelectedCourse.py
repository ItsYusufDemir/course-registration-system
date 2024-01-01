import logging
from enums.CourseStatus import CourseStatus

class SelectedCourse(object):
    def __init__(self,course,status,courseSection):
        self.course = course
        self.status = CourseStatus.DRAFT
        self.courseSection = courseSection


    def setStatus(self,status):
        self.status = status
        logging.log(logging.INFO, f"Course ({self.course.getCourseCode()}) status changed to: {status}")

    def getCourse(self):
        return self.course

    def getStatus(self):
        return self.status
    
    def getCourseSection(self):
        return self.courseSection