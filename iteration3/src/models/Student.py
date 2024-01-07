#beyza
import logging
from iteration3.src.controllers.StudentController import StudentController
from iteration3.src.enums.ApprovalStatus import ApprovalStatus
from iteration3.src.enums.CourseType import CourseType
from iteration3.src.models.SelectedCourse import SelectedCourse
from iteration3.src.models.Transcript import Transcript
from iteration3.src.models.User import User
from iteration3.src.utils.DatabaseManager import DatabaseManager
from iteration3.src.utils.Util import Util
from iteration3.src.interfaces.Color import Color
from iteration3.src.enums.CourseStatus import CourseStatus
from iteration3.src.enums.CourseResult import CourseResult
from iteration3.src.models.CourseSection import CourseSection
from iteration3.src.CommandLineInterface.CLIStudent import CLIStudent


class Student(User):
    def __init__(self, userId, password, firstName, lastName, status, notifications, email, identityNumber, currentSemester,
                  selectedCourses, advisorOfStudent, approvalStatus, transcript):
        super().__init__(userId, password, firstName, lastName, status, notifications)
        self._email = email
        self._identityNumber = identityNumber
        self._currentSemester = currentSemester
        self._selectedCourses = selectedCourses
        self._advisorOfStudent = advisorOfStudent
        self._approvalStatus = approvalStatus
        self._transcript = transcript

    @classmethod
    def dictToObject(cls, studentDict):

        from iteration3.src.models.Advisor import Advisor


        return cls(
            studentDict['userId'],
            studentDict['password'],
            studentDict['firstName'],
            studentDict['lastName'],
            studentDict['status'],
            studentDict['notifications'],
            studentDict['email'],
            studentDict['identityNumber'],
            studentDict['currentSemester'],
            [SelectedCourse.dictToObject(selectedCourse) for selectedCourse in studentDict['selectedCourses']],
            Advisor.dictToObject(studentDict['advisorOfStudent']),
            ApprovalStatus.dictToEnum(studentDict['approvalStatus']),
            Transcript.dictToObject(studentDict['transcript'])
        )
        
    def listAvailableCourseSections(self):
        allSelectableCourseSections = []
        courses = DatabaseManager.getInstance().getCourseList()
        availableCourseSections = []

        for course in courses:
            if course.getCourseCode() == "CSE4297" or course.getCourseCode() == "CSE4298":
                if self._transcript.checkEngineeringProjectAvailability():
                    allSelectableCourseSections.extend(course.getCourseSections())
            elif self._currentSemester < course.getGivenSemester():
                if self._transcript.calculateGPA() >= 3.0:
                    allSelectableCourseSections.extend(course.getCourseSections())
            else:
                allSelectableCourseSections.extend(course.getCourseSections())

 
        for courseSection in allSelectableCourseSections:
            
            course = courseSection.findCourseOfCourseSection() 
            if courseSection.checkAvailability() and course.checkPrerequisite(self) and not self._checkIfItWasTaken(course) and not self._checkIfItExistsInSelectedCourses(course) and self._checkCourseType(course):
                availableCourseSections.append(courseSection)
            
        repeatCourseSections = self._findRepeatCourseSections()
        for repeatCourseSection in repeatCourseSections:
            course = repeatCourseSection.findCourseOfCourseSection()
            if(courseSection.checkAvailability() and not self._checkIfItExistsInSelectedCourses(course) and self._checkCourseType(course)):
                availableCourseSections.append(repeatCourseSection)

        return availableCourseSections
    
    
    def _checkIfItExistsInSelectedCourses(self, course):
        i = 0
        for selectedCourse in self._selectedCourses:
            if selectedCourse.getCourse().getCourseCode() == course.getCourseCode():
                return True
            i = i + 1
        return False

    
    def _checkIfItWasTaken(self, course):
        takenCourses = self._transcript.acquirePassedCourses()
        for takenCourse in takenCourses:
            if takenCourse.getCourse().getCourseName() == course.getCourseName():
                return True
        return False    
    
    def _findRepeatCourseSections(self):
        takenCourses = self._transcript.getTakenCourses()
        repeatCourseSections = []

        for takenCourse in takenCourses:
            if takenCourse.getLetterGrade() == "DD" or takenCourse.getLetterGrade() == "DC":
                #check if the course is offered in the current semester
                courses = DatabaseManager.getInstance().getCourseList()
                for course in courses:
                    if course.getCourseCode() == takenCourse.getCourse().getCourseCode():
                        repeatCourseSections.extend(course.getCourseSections())

        return repeatCourseSections 
        
    
    def _checkCourseType(self, course):
        nteCounter = 0
        teCounter = 0
        fteCounter = 0
        ueCounter = 0

        for selectedCourse in self._selectedCourses:
            courseType = selectedCourse.getCourse().getCourseType()

            if courseType == CourseType.NONTECHNICAL_ELECTIVE:
                nteCounter += 1
            elif courseType == CourseType.TECHNICAL_ELECTIVE:
                teCounter += 1
            elif courseType == CourseType.FACULTY_ELECTIVE:
                fteCounter += 1
            elif courseType == CourseType.UNIVERSITY_ELECTIVE:
                ueCounter += 1

        for selectedCourse in self._selectedCourses:
            courseType = selectedCourse.getCourse().getCourseType()

            if courseType == CourseType.NONTECHNICAL_ELECTIVE and nteCounter >= 2:
                return False
            elif courseType == CourseType.TECHNICAL_ELECTIVE and teCounter >= 4:
                return False
            elif courseType == CourseType.FACULTY_ELECTIVE and fteCounter >= 1:
                return False
            elif courseType == CourseType.UNIVERSITY_ELECTIVE and ueCounter >= 1:
                return False

        return True
        
        
    def addNewCourse(self, selectedCourse):
        constraints = DatabaseManager.getInstance().getConstraints()
        if len(self._selectedCourses) >= int(constraints.get(1)):
            logging.warning(self._userId + " - Student cannot take more than " + str(constraints.get(1)) + " courses in one term.")
            raise Exception("You cannot take more than " + str(constraints.get(1)) + " courses in one term.")

        
        if selectedCourse not in self._selectedCourses:
            self._selectedCourses.append(selectedCourse)
            DatabaseManager.getInstance().saveToDatabase()
            logging.log(logging.INFO,self._userId + " - Course: " + selectedCourse.getCourse().getCourseCode()
                    + " added to student: " + self._userId)
            return
        else:
            logging.log(logging.INFO, self._userId + " - Course: " + selectedCourse.course.courseCode
        + " is already added to student: " + self._userId)
            raise Exception("Course: "+selectedCourse.getCourse().getCourseCode() +"is already added to your courses.")

        
        
    def deleteCourse(self, selectedCourse):
        if (selectedCourse in self._selectedCourses) and (selectedCourse.getStatus() is not CourseStatus.PENDING):
            self._selectedCourses.remove(selectedCourse)
            DatabaseManager.getInstance().saveToDatabase()
            logging.info(self._userId + " - Course: " + selectedCourse.getCourse().getCourseCode()
                    + " deleted from student: " + self._userId) 
            return
        else:  
            logging.info(self._userId + " - Course: " + selectedCourse.getCourse().getCourseCode()
                    + " is not deleted from student: " + self._userId)
            raise Exception("Course is pending for approval. You cannot delete it.")
    
    def _checkCompulsoryCourses(self):
        courseGrades = self._transcript.getTakenCourses()
        for courseGrade in courseGrades:
            if courseGrade.getCourseResult() == CourseResult.FAILED:
                for selectedCourse in self._selectedCourses:
                    if selectedCourse.getCourse() is not courseGrade.getCourse():
                        for section in self.listAvailableCourseSections():
                            if section.findCourseOfCourseSection() == courseGrade.getCourse():
                                logging.warning("Student: " + self._userId + " has failed course: "
                                        + selectedCourse.getCourse().getCourseCode()
                                        + " and has not added it to his/her courses.")
                                return True
        return False
                        
    
    def sendSelectedCoursesToApproval(self):

        if(DatabaseManager.getInstance().getConstraints()[4] == "False" and DatabaseManager.getInstance().getConstraints()[2] == "False"):
            raise Exception("You are not in registration nor add-drop week. You cannot send your courses to approval.")
        
        if(self._approvalStatus == ApprovalStatus.FINALIZED_REGISTRATION):
            raise Exception("You have already finalized your registration. You cannot send your courses to approval.")

        numberOfDraftCourses = 0
        for selectedCourse in self._selectedCourses:
            if selectedCourse.getStatus() is CourseStatus.DRAFT:
                numberOfDraftCourses = numberOfDraftCourses + 1

        #Check if the student acceeds the maximum number of courses that can be taken in one term
        if (numberOfDraftCourses > int(DatabaseManager.getInstance().getConstraints()[1])):
            logging.warning(self._userId + " - Student: " + self._userId + " cannot take more than " + str(DatabaseManager.getInstance().getConstraints()[1]) + " courses in one term.")
            raise Exception("You cannot take more than " + str(DatabaseManager.getInstance().getConstraints()[1]) + " courses in one term.")

        self._approvalStatus = ApprovalStatus.DONE
        for selectedCourse in self._selectedCourses:
            if selectedCourse.getStatus() is CourseStatus.PENDING:
                self._approvalStatus = ApprovalStatus.PENDING
            

        if self._approvalStatus is ApprovalStatus.PENDING:
            logging.warning(self._userId + " - Student: " + self._userId + " already sent his/her courses to approval.")
            raise Exception("You already sent your courses to approval!")
            
        
        if self._checkCompulsoryCourses():
            raise Exception("You have failed courses that you have not added to your courses.")
            
        
        if numberOfDraftCourses == 0:
            logging.warning(self._userId + " - Student: " + self._userId + " has no course to send to approval.")
            Exception("You have no course to send to approval!")
        
        self._approvalStatus = ApprovalStatus.PENDING
        for selectedCourse in self._selectedCourses:
            if selectedCourse.getStatus() == CourseStatus.DRAFT:
                selectedCourse.setStatus(CourseStatus.PENDING)
                selectedCourse.getCourseSection().incrementStudentCount()

                #If selected course is a repeat course, set its course result to ACTIVE in the transcript
                if selectedCourse.getCourseSection() in self._findRepeatCourseSections():  
                    for courseGrade in self._transcript.getTakenCourses():
                        if courseGrade.getCourse().getCourseCode() == courseGrade.getCourse().getCourseCode():
                            courseGrade.setCourseResult(CourseResult.ACTIVE)

        
        self._advisorOfStudent.addNotification(self._firstName + " " + self._lastName + " has requested a course approval.")
        logging.info(self._userId + " - Student: " + self._userId + " sent his/her courses to approval.")    
        DatabaseManager.getInstance().saveToDatabase()

    def getMyPage(self):
        cliStudent = CLIStudent(StudentController(self))
        cliStudent.menuPage()

    def fillTable(self):
        timeTable = [[] for _ in range(5)]

        for i in range(5):
            for j in range(9):
                timeTable[i].append("")

        for i in range(len(self._selectedCourses)):
            for j in range(len(self._selectedCourses[i].getCourseSection().getSectionDay())):
                day = self._selectedCourses[i].getCourseSection().getSectionDay()[j]
                time = self._selectedCourses[i].getCourseSection().getSectionTime()[j]
                if day == "Monday":
                    self.fillTableWithValues(time, i, 0, timeTable)
                elif day == "Tuesday":
                    self.fillTableWithValues(time, i, 1, timeTable) 
                elif day == "Wednesday":
                    self.fillTableWithValues(time, i, 2, timeTable)
                elif day == "Thursday":
                    self.fillTableWithValues(time, i, 3, timeTable)
                elif day == "Friday":
                    self.fillTableWithValues(time, i, 4, timeTable)
                else:   
                    print("Invalid day.")

        return timeTable

    def fillTableWithValues(self, time, i, day, timeTable):
        if time == "08:30-09:20":
            timeTable[day][0] += self._selectedCourses[i].getCourseSection().getSectionCode() + "-"
        elif time == "09:30-10:20":
            timeTable[day][1] += self._selectedCourses[i].getCourseSection().getSectionCode() + "-"
        elif time == "10:30-11:20":
            timeTable[day][2] += self._selectedCourses[i].getCourseSection().getSectionCode() + "-"
        elif time == "11:30-12:20":
            timeTable[day][3] += self._selectedCourses[i].getCourseSection().getSectionCode() + "-"
        elif time == "13:00-13:50":
            timeTable[day][4] += self._selectedCourses[i].getCourseSection().getSectionCode() + "-"
        elif time == "14:00-14:50":
            timeTable[day][5] += self._selectedCourses[i].getCourseSection().getSectionCode() + "-"
        elif time == "15:00-15:50":
            timeTable[day][6] += self._selectedCourses[i].getCourseSection().getSectionCode() + "-"
        elif time == "16:00-16:50":
            timeTable[day][7] += self._selectedCourses[i].getCourseSection().getSectionCode() + "-"
        else:
            print("Invalid time")

    def checkConflictCourses(self):
        timeTable = self.fillTable()
        conflictedCourses = []

        for i in range(5):
            for j in range(9):
                courseCodes = timeTable[i][j].split("-")
                if len(courseCodes) > 1:
                    for q in range(len(courseCodes)):
                        conflictedCourses.append(courseCodes[q])
        return conflictedCourses


    def fetchSelectedCoursesForAdvisor(self):
        selectedCourses = []
        for selectedCourse in self._selectedCourses:
            if selectedCourse.getStatus() is not CourseStatus.DRAFT:
                selectedCourses.append(selectedCourse)
        return selectedCourses

    def createTimeTable(self):
        timeTable = self.fillTable()
        return timeTable
    
    def getAdvisorOfStudent(self):
        return self._advisorOfStudent

    def getApprovalStatus(self):
        return self._approvalStatus
    
    def getFirstName(self):
        return self._firstName
    
    def getLastName(self):
        return self._lastName
    
    def getSelectedCourses(self):
        return self._selectedCourses
    
    def getTranscript(self):
        return self._transcript
    
    def setApprovalStatus(self, approvalStatus):
        self._approvalStatus = approvalStatus

    def setAdvisorOfStudent(self, advisorOfStudent):
        self._advisorOfStudent = advisorOfStudent
    
    