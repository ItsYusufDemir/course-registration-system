from enum import Enum
import json
from typing import Dict




class DatabaseManager:
    _instance = None

    def __init__(self):
        self._courseList = []
        self._studentList = []
        self._advisorList = []
        self._constraintList = []
        self._adminList = []

    @classmethod
    def getInstance(cls):
        if cls._instance is None:
            cls._instance = cls()
            cls._instance._initialize()
        return cls._instance

    def _initialize(self):

        from iteration3.src.models.Admin import Admin
        from iteration3.src.models.Advisor import Advisor
        from iteration3.src.models.Constraint import Constraint
        from iteration3.src.models.Course import Course
        from iteration3.src.models.Student import Student


        #Read json files and store them in the corresponding lists

        with open("iteration3/data/courses.json", "r", encoding='utf-8') as jsonFile:
            coursesJsonString = json.load(jsonFile)
            self._courseList = [Course.dictToObject(courseDict) for courseDict in coursesJsonString]

        with open("iteration3/data/advisors.json", "r", encoding='utf-8') as jsonFile:
            advisorsJsonString = json.load(jsonFile)
            self._advisorList = [Advisor.dictToObject(advisorDict) for advisorDict in advisorsJsonString]
        
        with open("iteration3/data/students.json", "r", encoding='utf-8') as jsonFile:
            studentsJsonString = json.load(jsonFile)
            self._studentList = [Student.dictToObject(studentDict) for studentDict in studentsJsonString]

            for student in self._studentList:
                for advisor in self._advisorList:
                    if student.getAdvisorOfStudent().getUserId() == advisor.getUserId():
                        student.setAdvisorOfStudent(advisor)
        
        with open("iteration3/data/constraints.json", "r", encoding='utf-8') as jsonFile:
            constraintsJsonString = json.load(jsonFile)
            self._constraintList = [Constraint.dictToObject(constraintDict) for constraintDict in constraintsJsonString]

        with open("iteration3/data/admins.json", "r", encoding='utf-8') as jsonFile:
            adminsJsonString = json.load(jsonFile)
            self._adminList = [Admin.dictToObject(adminDict) for adminDict in adminsJsonString]
        

    def _saveTranscriptsToDatabase(self):
        for student in self._studentList:
            transcript = [student._transcript]
            filePath = f"iteration3/data/transcripts/{student._userId}.json"
            
            with open(filePath, "w", encoding="utf-8") as jsonFile:
                json.dump(transcript, jsonFile, indent=4, default=self.jsonDefault, ensure_ascii=False)
    
    def saveCoursesList(self):
        with open("iteration3/data/courses.json", "w", encoding="utf-8") as json_file:
            json.dump(self._courseList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)

    def saveStudentList(self):
        with open("iteration3/data/students.json", "w", encoding="utf-8") as json_file:
            json.dump(self._studentList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)
        self._saveTranscriptsToDatabase()

    def saveAdvisorList(self):
        with open("iteration3/data/advisors.json", "w", encoding="utf-8") as json_file:
            json.dump(self._advisorList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)

    def saveConstraintsList(self):
        with open("iteration3/data/constraints.json", "w", encoding="utf-8") as json_file:
            json.dump(self._constraintList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)

    def saveAdminList(self):
        with open("iteration3/data/admins.json", "w", encoding="utf-8") as json_file:
            json.dump(self._adminList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)

    def saveToDatabase(self):
        self.saveCoursesList()
        self.saveStudentList()
        self.saveAdvisorList()
        self.saveConstraintsList()
        self.saveAdminList()

    def fetchAdvisedStudents(self, advisor):
        studentsOfAdvisor = [student for student in self._studentList if student.getAdvisorOfStudent().getUserId() == advisor.userId]
        return studentsOfAdvisor

    def editConstraints(self, editedAttributes: Dict[int, str]) -> bool:
        #TODO: check if editedAttributes is valid
        self._constraintList[0].editConstraint(editedAttributes)
        return True
    
    def findCourseByCourseCode(self, courseCode):
        for course in self._courseList:
            if course._courseCode == courseCode:
                return course
        return None
    
    # Getters
    def getCourseList(self):
        return self._courseList

    def getStudentList(self):
        return self._studentList

    def getAdvisorList(self):
        return self._advisorList

    def getAdminList(self):
        return self._adminList
    
    def getConstraints(self):
        constraints = self._constraintList[0]  # Assuming 'constraints' is a list of Constraint objects
        constraintsMap = {
            1: str(constraints._maxNumberOfCoursesStudentTake),
            2: str(constraints._addDropWeek),
            3: str(constraints._minRequiredECTSForTermProject),
            4: str(constraints._isRegistrationWeek)
        }

        return constraintsMap
    
    def jsonDefault(self, obj):
        """
        Custom JSON serialization function to handle non-serializable objects.
        """
        if isinstance(obj, Enum):
            # Handle Enum objects by converting them to their values
            return obj.value
        elif hasattr(obj, '__dict__'):
            # If the object has a '__dict__' attribute, use it for serialization
            return obj.__dict__
        elif isinstance(obj, set):
            # Handle sets by converting them to lists
            return list(obj)
        else:
            # For other types, raise a TypeError (you can customize this part)
            raise TypeError(f"Object of type {type(obj)} is not JSON serializable")
        



    
