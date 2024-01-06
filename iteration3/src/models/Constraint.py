
from iteration3.src.utils.DatabaseManager import DatabaseManager


class Constraint:
    def __init__(self, maxNumberOfCoursesStudentTake, addDropWeek, minRequiredECTSForTermProject,isRegistrationWeek):
        self._maxNumberOfCoursesStudentTake = maxNumberOfCoursesStudentTake
        self._addDropWeek = addDropWeek
        self._minRequiredECTSForTermProject = minRequiredECTSForTermProject
        self._isRegistrationWeek = isRegistrationWeek

    @classmethod
    def dictToObject(cls, dict):
        return cls(
            dict['maxNumberOfCoursesStudentTake'],
            dict['addDropWeek'],
            dict['minRequiredECTSForTermProject'],
            dict['isRegistrationWeek']
        )

    def editConstraint(self, editedAttributes):

        if(editedAttributes[2].lower() == "true" and editedAttributes[4].lower() == "true"):
            raise Exception("Add-Drop week and Registration week cannot be enabled at the same time.")

        #Send notification to students and advisors if add-drop week is enabled
        if(self._addDropWeek == False and editedAttributes[2].lower() == "true"):
            for student in DatabaseManager().getInstance().getStudentList():
                student.addNotification("Add-Drop week has been enabled. You can now add/drop courses.")

            for advisor in DatabaseManager().getInstance().getAdvisorList():
                advisor.addNotification("Add-Drop week has been enabled. Students can now add/drop courses.")

        self._maxNumberOfCoursesStudentTake = int(editedAttributes[1])
        self._addDropWeek = editedAttributes[2].lower() == 'true'
        self._minRequiredECTSForTermProject = int(editedAttributes[3])
        self._isRegistrationWeek = editedAttributes[4].lower() == 'true'

        DatabaseManager().getInstance().saveToDatabase()
            

    def fetchAttributes(self):
        attributes = {1 : str(self._maxNumberOfCoursesStudentTake), 
                      2: str(self._addDropWeek),
                      3: str(self._minRequiredECTSForTermProject),
                      4: str(self._isRegistrationWeek)}
                      
        return attributes
        
    def addDropWeek(self):
        return self._addDropWeek
    
    def isRegistrationWeek(self):
        return self._isRegistrationWeek
    

