import logging
from iteration3.src.utils.DatabaseManager import DatabaseManager

class CourseSection:

    def __init__(self, studentCapacity, lecturerName, sectionTime, sectionDay, classroom, sectionCode, studentCountInsideCourseSection):
        self.studentCapacity = studentCapacity
        self.lecturerName = lecturerName
        self.sectionTime = sectionTime
        self.sectionDay = sectionDay
        self.classroom = classroom
        self.sectionCode = sectionCode
        self.studentCountInsideCourseSection = studentCountInsideCourseSection

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
        if self.studentCapacity >= self.studentCountInsideCourseSection:
            return True
        else:
            return False
        
    def incrementStudentCount(self):
        self.studentCountInsideCourseSection += 1
        logging.log(logging.INFO, "Student count incremented. Current section count: " + str(self.studentCountInsideCourseSection))
        return self.studentCountInsideCourseSection
    
    def decrementStudentCount(self):
        self.studentCountInsideCourseSection -= 1
        logging.log(logging.INFO, "Student count decremented. Current section count: " + str(self.studentCountInsideCourseSection))
        return self.studentCountInsideCourseSection
    
    def findCourseOfCourseSection(self):
        index = self.sectionCode.index(".")
        courseCode = self.sectionCode[:index]

        courses = DatabaseManager().getInstance().getCourseList()
        for course in courses:
            if course.getCourseCode() == courseCode:
                return course
        return None
    
    def getSectionCode(self):
            return self.sectionCode
        
    def getStudentCapacity(self):
        return self.studentCapacity
        
    def getLecturerName(self):
        return self.lecturerName
        
    def getSectionTime(self):
        return self.sectionTime
        
    def getSectionDay(self):
        return self.sectionDay
        
    def getClassroom(self):
        return self.classroom
        
    def getStudentCountInsideCourseSection(self):
        return self.studentCountInsideCourseSection        
            
    
    
    
        






        