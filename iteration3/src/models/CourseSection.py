import logging
from iteration3.src.utils.DatabaseManager import DatabaseManager

class CourseSection:

    def __init__(self, studentCapacity, lecturerName, sectionTime, sectionDay, classroom, sectionCode, studentCountInsideCourseSection):
        self._studentCapacity = studentCapacity
        self._lecturerName = lecturerName
        self._sectionTime = sectionTime
        self._sectionDay = sectionDay
        self._classroom = classroom
        self._sectionCode = sectionCode
        self._studentCountInsideCourseSection = studentCountInsideCourseSection

    @classmethod
    def dictToObject(cls, dict):
        return cls(
            dict['studentCapacity'],
            dict['lecturerName'],
            dict['sectionTime'],
            dict['sectionDay'],
            dict['classroom'],
            dict['sectionCode'],
            dict['studentCountInsideCourseSection']
        )

    def checkAvailability(self):
        if self._studentCapacity >= self._studentCountInsideCourseSection:
            return True
        else:
            return False
        
    def incrementStudentCount(self):
        self._studentCountInsideCourseSection += 1
        logging.log(logging.INFO, "Student count incremented. Current section count: " + str(self._studentCountInsideCourseSection))
        return self._studentCountInsideCourseSection
    
    def decrementStudentCount(self):
        self._studentCountInsideCourseSection -= 1
        logging.log(logging.INFO, "Student count decremented. Current section count: " + str(self._studentCountInsideCourseSection))
        return self._studentCountInsideCourseSection
    
    def findCourseOfCourseSection(self):
        index = self._sectionCode.index(".")
        courseCode = self._sectionCode[:index]

        courses = DatabaseManager().getInstance().getCourseList()
        for course in courses:
            if course.getCourseCode() == courseCode:
                return course
        return None
    
    def getSectionCode(self):
            return self._sectionCode
        
    def getStudentCapacity(self):
        return self._studentCapacity
        
    def getLecturerName(self):
        return self._lecturerName
        
    def getSectionTime(self):
        return self._sectionTime
        
    def getSectionDay(self):
        return self._sectionDay
        
    def getClassroom(self):
        return self._classroom
        
    def getStudentCountInsideCourseSection(self):
        return self._studentCountInsideCourseSection        
            
    
    
    
        






        