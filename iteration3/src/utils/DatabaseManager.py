from enum import Enum
import json
from typing import Dict




class DatabaseManager:
    _instance = None

    def __init__(self):
        self.courseList = []
        self.studentList = []
        self.advisorList = []
        self.constraintList = []
        self.adminList = []

    @classmethod
    def getInstance(cls):
        if cls._instance is None:
            cls._instance = cls()
            cls._instance.initialize()
        return cls._instance

    def initialize(self):

        from models.Admin import Admin
        from models.Advisor import Advisor
        from models.Constraint import Constraint
        from models.Course import Course
        from models.Student import Student


        #Read json files and store them in the corresponding lists

        with open("iteration3/data/courses.json", "r", encoding='utf-8') as jsonFile:
            coursesJsonString = json.load(jsonFile)
            self.courseList = [Course.dictToObject(courseDict) for courseDict in coursesJsonString]
        
        with open("iteration3/data/students.json", "r", encoding='utf-8') as jsonFile:
            studentsJsonString = json.load(jsonFile)
            self.studentList = [Student.dictToObject(studentDict) for studentDict in studentsJsonString]
       
        with open("iteration3/data/advisors.json", "r", encoding='utf-8') as jsonFile:
            advisorsJsonString = json.load(jsonFile)
            self.advisorList = [Advisor.dictToObject(advisorDict) for advisorDict in advisorsJsonString]
        
        with open("iteration3/data/constraints.json", "r", encoding='utf-8') as jsonFile:
            constraintsJsonString = json.load(jsonFile)
            self.constraintList = [Constraint.dictToObject(constraintDict) for constraintDict in constraintsJsonString]

        with open("iteration3/data/admins.json", "r", encoding='utf-8') as jsonFile:
            adminsJsonString = json.load(jsonFile)
            self.adminList = [Admin.dictToObject(adminDict) for adminDict in adminsJsonString]
        

    def saveTranscriptsToDatabase(self):
        for student in self.studentList:
            transcript = [student.transcript]
            filePath = f"iteration3/data/transcripts/{student.userId}.json"
            
            with open(filePath, "w", encoding="utf-8") as jsonFile:
                json.dump(transcript, jsonFile, indent=4, default=self.jsonDefault, ensure_ascii=False)
    
    def saveCoursesList(self):
        with open("iteration3/data/courses.json", "w", encoding="utf-8") as json_file:
            json.dump(self.courseList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)


    def saveStudentList(self):
        with open("iteration3/data/students.json", "w", encoding="utf-8") as json_file:
            json.dump(self.studentList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)
        self.saveTranscriptsToDatabase()

    def saveAdvisorList(self):
        with open("iteration3/data/advisors.json", "w", encoding="utf-8") as json_file:
            json.dump(self.advisorList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)

    def saveConstraintsList(self):
        with open("iteration3/data/constraints.json", "w", encoding="utf-8") as json_file:
            json.dump(self.constraintList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)

    def saveAdminList(self):
        with open("iteration3/data/admins.json", "w", encoding="utf-8") as json_file:
            json.dump(self.adminList, json_file, indent=4, default=self.jsonDefault, ensure_ascii=False)

    def saveToDatabase(self):
        self.saveCoursesList()
        self.saveStudentList()
        self.saveAdvisorList()
        self.saveConstraintsList()
        self.saveAdminList()

    def fetchAdvisedStudents(self, advisor):
        studentsOfAdvisor = [student for student in self.studentList if student.getAdvisorOfStudent().getUserId() == advisor.userId]
        return studentsOfAdvisor

    def editConstraints(self, editedAttributes: Dict[int, str]) -> bool:
        self.constraintList[0].editConstraint(editedAttributes)
        return True
    
    def findCourseByCourseCode(self, courseCode):
        for course in self.courseList:
            if course.courseCode == courseCode:
                return course
        return None
    
    # Getters
    def getCourseList(self):
        return self.courseList

    def getStudentList(self):
        return self.studentList

    def getAdvisorList(self):
        return self.advisorList

    def getAdminList(self):
        return self.adminList
    
    def getConstraints(self):
        constraints = self.constraintList[0]  # Assuming 'constraints' is a list of Constraint objects
        constraintsMap = {
            1: str(constraints.maxNumberOfCoursesStudentTake),
            2: str(constraints.addDropWeek),
            3: str(constraints.minRequiredECTSForTermProject)
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
        



    
