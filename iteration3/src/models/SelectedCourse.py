import logging
from iteration3.src.enums.CourseStatus import CourseStatus
from iteration3.src.models.Course import Course
from iteration3.src.models.CourseSection import CourseSection

class SelectedCourse(object):
    def __init__(self,course,status,courseSection):
        self.course = course
        self.status = status
        self.courseSection = courseSection

    @classmethod
    def dictToObject(cls, dict):
        return cls(
            Course.dictToObject(dict['course']),
            CourseStatus.dictToEnum(dict['status']),
            CourseSection.dictToObject(dict['courseSection'])
        )


    def setStatus(self,status):
        self.status = status
        logging.log(logging.INFO, f"Course ({self.course.getCourseCode()}) status changed to: {status}")

    def getCourse(self):
        return self.course
    
    def getCourseSection(self):
        return self.courseSection

    def getStatus(self):
        return self.status
            

