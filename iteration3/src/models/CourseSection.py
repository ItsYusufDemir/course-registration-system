import logging
from utils.DatabaseManager import DatabaseManager

class CourseSection:

    def __init__(self, studentCapacity, lecturerName, sectionTime, sectionDay, classroom, sectionCode, studentCountInsideCourseSection):
        self.studentCapacity = studentCapacity
        self.lecturerName = lecturerName
        self.sectionTime = sectionTime
        self.sectionDay = sectionDay
        self.classroom = classroom
        self.sectionCode = sectionCode
        self.studentCountInsideCourseSection = studentCountInsideCourseSection

    def checkAvailability(self):
        if self.studentCapacity >= self.studentCountInsideCourseSection:
            return True
        else:
            return False
        
    def incrementStudentCount(self):
        self.studentCountInsideCourseSection += 1
        logging.log(logging.INFO, "Student count incremented. Current section count: " + self.studentCountInsideCourseSection)
        return self.studentCountInsideCourseSection
    
    def decrementStudentCount(self):
        self.studentCountInsideCourseSection -= 1
        logging.log(logging.INFO, "Student count decremented. Current section count: " + self.studentCountInsideCourseSection)
        return self.studentCountInsideCourseSection
    
    def findCourseOfCourseSection(self):
        index = self.sectionCode.index(".")
        courseCode = self.sectionCode[:index]

        courses = DatabaseManager().getCourseList()
        for course in courses:
            if course.getCourseCode == courseCode:
                return course
        return None
    
    
        






        