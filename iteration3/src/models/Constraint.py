class Constraint:
    def __init__(self, maxNumberOfCoursesStudentTake, addDropWeek, minRequiredECTSForTermProject):
        self.maxNumberOfCoursesStudentTake = maxNumberOfCoursesStudentTake
        self.addDropWeek = addDropWeek
        self.minRequiredECTSForTermProject = minRequiredECTSForTermProject

    @classmethod
    def dictToObject(cls, dict):
        return cls(
            dict['maxNumberOfCoursesStudentTake'],
            dict['addDropWeek'],
            dict['minRequiredECTSForTermProject']
        )

    def editConstraint(self, editedAttributes):
        self.maxNumberOfCoursesStudentTake = int(editedAttributes[1])
        self.addDropWeek = bool(editedAttributes[2])
        self.minRequiredECTSForTermProject = int(editedAttributes[3])

    def fetchAttributes(self):
        attributes = {1 : str(self.maxNumberOfCoursesStudentTake), 
                      2: str(self.addDropWeek),
                      3: str(self.minRequiredECTSForTermProject)}
        return attributes
        
