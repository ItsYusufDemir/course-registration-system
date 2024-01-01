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

        with open("iteration3/data/courses.json", "r", encoding='utf-8') as json_file:
            coursesJsonString = json.load(json_file)
            self.courseList = [Course(**courseJsonString) for courseJsonString in coursesJsonString]
        
        
        with open("iteration3/data/students.json", "r", encoding='utf-8') as json_file:
            studentsJsonString = json.load(json_file)
            self.studentList = [Student(**studentJsonString) for studentJsonString in studentsJsonString]

       
        with open("iteration3/data/advisors.json", "r", encoding='utf-8') as json_file:
            advisorsJsonString = json.load(json_file)
            self.advisorList = [Advisor(**advisorJsonString) for advisorJsonString in advisorsJsonString]
        
        with open("iteration3/data/constraints.json", "r", encoding='utf-8') as json_file:
            constraintsJsonString = json.load(json_file)
            self.constraintList = [Constraint(**constraintJsonString) for constraintJsonString in constraintsJsonString]

        with open("iteration3/data/admins.json", "r", encoding='utf-8') as json_file:
            adminsJsonString = json.load(json_file)
            self.adminList = [Admin(**adminJsonString) for adminJsonString in adminsJsonString]
        

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
        studentsOfAdvisor = [student for student in self.studentList if student.advisorOfStudent.userId == advisor.userId]
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
        if hasattr(obj, '__dict__'):
            return obj.__dict__
        elif isinstance(obj, (list, tuple)):
            return [self.jsonDefault(item) for item in obj]
        elif isinstance(obj, set):
            return [self.jsonDefault(item) for item in obj]
        elif isinstance(obj, Enum):
            return obj.value  # Serialize the Enum as its value
        else:
            return obj
        



    
