from iteration3.src.enums.CourseType import CourseType
from iteration3.src.models.CourseSection import CourseSection
from iteration3.src.models.Prerequisite import Prerequisite


class Course:

    def __init__(self, courseCredit, courseECTS, givenSemester, courseName, courseCode, prerequisiteInformation, courseSections, courseType):
        self._courseCredit = courseCredit
        self._courseECTS = courseECTS
        self._givenSemester = givenSemester
        self._courseName = courseName
        self._courseCode = courseCode
        self._prerequisiteInformation = prerequisiteInformation
        self._courseSections = courseSections
        self._courseType = courseType

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
        if self._prerequisiteInformation is None:
            return True
        elif not self._prerequisiteInformation.getPrerequisiteOfCourses():
            return True
        else:
            return self._prerequisiteInformation.checkPrerequisiteCoursePassed(student, self)
        
    
    def acquireAvailableSections(self):
        availableCourseSections = []
        for courseSection in self._courseSections:
            if courseSection.checkAvailability():
                availableCourseSections.append(courseSection)
        return availableCourseSections
    
    

    def equals(self, course):
        if self._courseCode == course.getCourseCode:
            return True
        else:
            return False
        
    def getCourseCode(self):
        return self._courseCode

    def getPrerequisiteInformation(self):
        return self._prerequisiteInformation
    
    def getCourseName(self):
        return self._courseName
    
    def getGivenSemester(self):
        return self._givenSemester
    
    def getCourseSections(self):
        return self._courseSections
    
    def getCourseCredit(self):
        return self._courseCredit

    def getCourseECTS(self):
        return self._courseECTS

    def getCourseType(self):
        return self._courseType


    