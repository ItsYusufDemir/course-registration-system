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
        if self._courseCode == course._courseCode:
            return True
        else:
            return False
        
    def getCourseCode(self):
        return self._courseCode

    def getPrerequisiteInformation(self):
        return self._prerequisiteInformation

    