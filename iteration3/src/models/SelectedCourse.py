import logging
from iteration3.src.enums.CourseStatus import CourseStatus
from iteration3.src.models.Course import Course
from iteration3.src.models.CourseSection import CourseSection

class SelectedCourse(object):
    def __init__(self,course,status,courseSection):
        self._course = course
        self._status = status
        self._courseSection = courseSection

    @classmethod
    def dictToObject(cls, dict):
        return cls(
            Course.dictToObject(dict['course']),
            CourseStatus.dictToEnum(dict['status']),
            CourseSection.dictToObject(dict['courseSection'])
        )


    def setStatus(self,status):
        self._status = status
        logging.log(logging.INFO, f"Course ({self._course.getCourseCode()}) status changed to: {status}")

    def getCourse(self):
        return self._course
    
    def getCourseSection(self):
        return self._courseSection

    def getStatus(self):
        return self._status
    
    def setStatus(self, status):
        self._status = status
            

