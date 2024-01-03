from utils.DatabaseManager import DatabaseManager


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

        #Send notification to students and advisors if add-drop week is enabled
        if(self.addDropWeek == False and editedAttributes[2].lower() == "true"):
            for student in DatabaseManager().getInstance().getStudentList():
                student.addNotification("Add-Drop week has been enabled. You can now add/drop courses.")

            for advisor in DatabaseManager().getInstance().getAdvisorList():
                advisor.addNotification("Add-Drop week has been enabled. Students can now add/drop courses.")

        self.maxNumberOfCoursesStudentTake = int(editedAttributes[1])
        self.addDropWeek = editedAttributes[2].lower() == 'true'
        self.minRequiredECTSForTermProject = int(editedAttributes[3])

        DatabaseManager().getInstance().saveToDatabase()
            

    def fetchAttributes(self):
        attributes = {1 : str(self.maxNumberOfCoursesStudentTake), 
                      2: str(self.addDropWeek),
                      3: str(self.minRequiredECTSForTermProject)}
        return attributes
        
