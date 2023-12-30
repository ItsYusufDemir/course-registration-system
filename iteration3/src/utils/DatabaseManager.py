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

        with open("iteration3/data/courses.json", "r") as json_file:
            coursesJsonString = json.load(json_file)
            self.courseList = [Course(**courseJsonString) for courseJsonString in coursesJsonString]
        
        
        with open("iteration3/data/students.json", "r") as json_file:
            studentsJsonString = json.load(json_file)
            self.studentList = [Student(**studentJsonString) for studentJsonString in studentsJsonString]

       
        with open("iteration3/data/advisors.json", "r") as json_file:
            advisorsJsonString = json.load(json_file)
            self.advisorList = [Advisor(**advisorJsonString) for advisorJsonString in advisorsJsonString]
        
        with open("iteration3/data/constraints.json", "r") as json_file:
            constraintsJsonString = json.load(json_file)
            self.constraintList = [Constraint(**constraintJsonString) for constraintJsonString in constraintsJsonString]

        with open("iteration3/data/admins.json", "r") as json_file:
            adminsJsonString = json.load(json_file)
            self.adminList = [Admin(**adminJsonString) for adminJsonString in adminsJsonString]
       

    def saveTranscriptsToDatabase(self):
        for student in self.studentList:
            transcript = [student.transcript]
            filePath = f"iteration3/data/transcripts/{student.userId}.json"
            
            with open(filePath, "w") as jsonFile:
                json.dump(transcript, jsonFile, indent=4, default=lambda o: o.__dict__)
    
    def saveCoursesList(self):
        with open("iteration3/data/courses.json", "w") as json_file:
            json.dump(self.courseList, json_file, indent=4, default=lambda o: o.__dict__)

    def saveStudentList(self):
        with open("iteration3/data/students.json", "w") as json_file:
            json.dump(self.student_list, json_file, indent=4, default=lambda o: o.__dict__)

        self.saveTranscriptsToDatabase()

    def saveAdvisorList(self):
        with open("iteration3/data/advisors.json", "w") as json_file:
            json.dump(self.advisor_list, json_file, indent=4, default=lambda o: o.__dict__)

    def saveConstraintsList(self):
        with open("iteration3/data/constraints.json", "w") as json_file:
            json.dump(self.constraintList, json_file, indent=4, default=lambda o: o.__dict__)

    def saveAdminList(self):
        with open("iteration3/data/admins.json", "w") as json_file:
            json.dump(self.admin_list, json_file, indent=4, default=lambda o: o.__dict__)

    def saveToDatabase(self):
        self.saveCoursesList()
        self.saveStudentList()
        self.saveAdvisorList()
        self.saveConstraintsList()
        self.saveAdminList()

    def fetchAdvisedStudents(self, advisor):
        studentsOfAdvisor = [student for student in self.studentList if student.advisorOfStudent.userId == advisor.userId]
        return studentsOfAdvisor

    def editConstraint(self, editedAttributes: Dict[int, str]) -> bool:
        self.constraints[0].editConstraint(editedAttributes)
        return True
    
    def findCourseByCourseCode(self, courseCode):
        for course in self.courseList:
            if course.courseCode == courseCode:
                return course
        return None
    
    # Getters
    def getCourses(self):
        return self.courseList

    def getStudents(self):
        return self.studentList

    def getAdvisors(self):
        return self.advisorList

    def getAdmins(self):
        return self.adminList
    
    def getConstraints(self):
        constraints = self.constraintList[0]  # Assuming 'constraints' is a list of Constraint objects
        constraintsMap = {
            1: str(constraints.maxNumberOfCoursesStudentTake),
            2: str(constraints.addDropWeek),
            3: str(constraints.minRequiredECTSForTermProject)
        }

        return constraintsMap

    
