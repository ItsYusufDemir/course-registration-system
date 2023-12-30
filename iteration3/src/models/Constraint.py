class Constraint:
    def __init__(self, maxNumberOfCoursesStudentTake, addDropWeek, minRequiredECTSForTermProject):
        self._maxNumberOfCoursesStudentTake = maxNumberOfCoursesStudentTake
        self._addDropWeek = addDropWeek
        self._minRequiredECTSForTermProject = minRequiredECTSForTermProject

    def editConstraint(self, editedAttributes):
        self._maxNumberOfCoursesStudentTake = int(editedAttributes[1])
        self._addDropWeek = bool(editedAttributes[2])
        self._minRequiredECTSForTermProject = int(editedAttributes[3])

    def fetchAttributes(self):
        attributes = {1 : str(self._maxNumberOfCoursesStudentTake), 
                      2: str(self._addDropWeek),
                      3: str(self._minRequiredECTSForTermProject)}
        return attributes
        
