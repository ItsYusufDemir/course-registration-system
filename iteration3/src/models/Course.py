from enums.CourseType import CourseType
from models.CourseSection import CourseSection
from models.Prerequisite import Prerequisite


class Course:

    def __init__(self, courseCredit, courseECTS, givenSemester, courseName, courseCode, prerequisiteInformation, courseSections, courseType):
        self.courseCredit = courseCredit
        self.courseECTS = courseECTS
        self.givenSemester = givenSemester
        self.courseName = courseName
        self.courseCode = courseCode
        self.prerequisiteInformation = prerequisiteInformation
        self.courseSections = courseSections
        self.courseType = courseType

    @classmethod
    def dictToObject(cls, courseDict):
        return cls(
            courseDict['courseCredit'],
            courseDict['courseECTS'],
            courseDict['givenSemester'],
            courseDict['courseName'],
            courseDict['courseCode'],
            Prerequisite.dictToObject(courseDict['prerequisiteInformation']) if courseDict['prerequisiteInformation'] else None,
            [CourseSection.dictToObject(courseSection) for courseSection in courseDict['courseSections']],
            CourseType.dictToEnum(courseDict['courseType'])
        )


    def checkPrerequisite(self, student):
        if self.prerequisiteInformation is None:
            return True
        elif not self.prerequisiteInformation.getPrerequisiteOfCourses():
            return True
        else:
            return self.prerequisiteInformation.checkPrerequisiteCoursePassed(student, self)
        
    
    def acquireAvailableSections(self):
        availableCourseSections = []
        for courseSection in self.courseSections:
            if courseSection.checkAvailability():
                availableCourseSections.append(courseSection)
        return availableCourseSections
    
    

    def equals(self, course):
        if self.courseCode == course.getCourseCode:
            return True
        else:
            return False
        
    def getCourseCode(self):
        return self.courseCode

    def getPrerequisiteInformation(self):
        return self.prerequisiteInformation
    
    def getCourseName(self):
        return self.courseName
    
    def getGivenSemester(self):
        return self.givenSemester
    
    def getCourseSections(self):
        return self.courseSections
    
    def getCourseCredit(self):
        return self.courseCredit

    def getCourseECTS(self):
        return self.courseECTS

    def getCourseType(self):
        return self.courseType


    