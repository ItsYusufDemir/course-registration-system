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

    def checkPrerequisite(student):
        None
    
    def acquireAvailableSections(self):
        availableCourseSections = []
        for i in self._courseSections:
            None
    
    

    def equals(self, course):
        if self._courseCode == course._courseCode:
            return True
        else:
            return False


    