
from iteration3.src.utils.DatabaseManager import DatabaseManager


class Constraint:
    def __init__(self, maxNumberOfCoursesStudentTake, addDropWeek, minRequiredECTSForTermProject,isRegistrationWeek):
        self.maxNumberOfCoursesStudentTake = maxNumberOfCoursesStudentTake
        self.addDropWeek = addDropWeek
        self.minRequiredECTSForTermProject = minRequiredECTSForTermProject
        self.isRegistrationWeek = isRegistrationWeek

    @classmethod
    def dictToObject(cls, dict):
        return cls(
            dict['maxNumberOfCoursesStudentTake'],
            dict['addDropWeek'],
            dict['minRequiredECTSForTermProject'],
            dict['isRegistrationWeek']
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
        self.isRegistrationWeek = editedAttributes[4].lower() == 'true'

        DatabaseManager().getInstance().saveToDatabase()
            

    def fetchAttributes(self):
        attributes = {1 : str(self.maxNumberOfCoursesStudentTake), 
                      2: str(self.addDropWeek),
                      3: str(self.minRequiredECTSForTermProject),
                      4: str(self.isRegistrationWeek)}
                      
        return attributes
        
    def addDropWeek(self):
        return self.addDropWeek
    
    def isRegistrationWeek(self):
        return self.isRegistrationWeek
    

