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
        self.email = email
        self.identityNumber = identityNumber
        self.currentSemester = currentSemester
        self.selectedCourses = selectedCourses
        self.advisorOfStudent = advisorOfStudent
        self.approvalStatus = approvalStatus
        self.transcript = transcript

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
                if self.transcript.checkEngineeringProjectAvailability():
                    allSelectableCourseSections.extend(course.getCourseSections())
            elif self.currentSemester < course.getGivenSemester():
                if self.transcript.calculateGPA() >= 3.0:
                    allSelectableCourseSections.extend(course.getCourseSections())
            else:
                allSelectableCourseSections.extend(course.getCourseSections())

 
        for courseSection in allSelectableCourseSections:
            
            course = courseSection.findCourseOfCourseSection() 

            if courseSection.checkAvailability() and course.checkPrerequisite(self) and not self.checkIfItWasTaken(course) and not self.checkIfItExistsInSelectedCourses(course) and self.checkCourseType(course):
                availableCourseSections.append(courseSection)
            
        repeatCourseSections = self.findRepeatCourseSections()
        for repeatCourseSection in repeatCourseSections:
            course = repeatCourseSection.findCourseOfCourseSection()
            if(courseSection.checkAvailability() and not self.checkIfItExistsInSelectedCourses(course) and self.checkCourseType(course)):
                availableCourseSections.append(repeatCourseSection)

        return availableCourseSections
    
    
    def checkIfItExistsInSelectedCourses(self, course):
        i = 0
        for selectedCourse in self.selectedCourses:
            if selectedCourse.getCourse().getCourseCode() == course.getCourseCode():
                return True
            i = i + 1
        return False

    
    def checkIfItWasTaken(self, course):
        takenCourses = self.transcript.acquirePassedCourses()
        for takenCourse in takenCourses:
            if takenCourse.getCourse().getCourseName() == course.getCourseName():
                return True
        return False    
    
    def findRepeatCourseSections(self):
        takenCourses = self.transcript.getTakenCourses()
        repeatCourseSections = []

        for takenCourse in takenCourses:
            if takenCourse.letterGrade == "DD" or takenCourse.letterGrade == "DC":
                repeatCourseSections.extend(takenCourse.course.courseSections)

        return repeatCourseSections 
        
    
    def checkCourseType(self, course):
        nteCounter = 0
        teCounter = 0
        fteCounter = 0
        ueCounter = 0

        for selectedCourse in self.selectedCourses:
            courseType = selectedCourse.getCourse().getCourseType()

            if courseType == CourseType.NONTECHNICAL_ELECTIVE:
                nteCounter += 1
            elif courseType == CourseType.TECHNICAL_ELECTIVE:
                teCounter += 1
            elif courseType == CourseType.FACULTY_ELECTIVE:
                fteCounter += 1
            elif courseType == CourseType.UNIVERSITY_ELECTIVE:
                ueCounter += 1

        for selectedCourse in self.selectedCourses:
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
        if len(self.selectedCourses) >= int(constraints.get(1)):
            Util.sendFeedback("You cannot take more than " + str(constraints.get(1)) + " courses in one term.", Color.RED)
            logging.warning(self.userId + " - Student cannot take more than " + str(constraints.get(1)) + " courses in one term.")
            return False
        
        if selectedCourse not in self.selectedCourses:
            self.selectedCourses.append(selectedCourse)
            DatabaseManager.getInstance().saveToDatabase()
            logging.log(logging.INFO,self.userId + " - Course: " + selectedCourse.course.courseCode
                    + " added to student: " + self.userId)
            return True
        else:
            logging.log(logging.INFO, self.userId + " - Course: " + selectedCourse.course.courseCode
        + " is already added to student: " + self.userId)

        
        
    def deleteCourse(self, selectedCourse):
        if (selectedCourse in self.selectedCourses) and (selectedCourse.getStatus() is not CourseStatus.PENDING):
            self.selectedCourses.remove(selectedCourse)
            DatabaseManager.getInstance().saveToDatabase()
            logging.info(self.userId + " - Course: " + selectedCourse.course.courseCode
                    + " deleted from student: " + self.userId)
            return True 
        else:  
            logging.info(self.userId + " - Course: " + selectedCourse.course.courseCode
                    + " is not deleted from student: " + self.userId)
            return False
    
    def checkCompulsoryCourses(self):
        courseGrades = self.transcript.takenCourses
        for courseGrade in courseGrades:
            if courseGrade.courseResult == CourseResult.FAILED:
                for selectedCourse in self.selectedCourses:
                    if selectedCourse.course is not courseGrade.course:
                        for section in self.listAvailableCourseSections():
                            if section.findCourseOfCourseSection == courseGrade.course:
                                logging.warning("Student: " + self.userId + " has failed course: "
                                        + courseGrade.course.courseCode
                                        + " and has not added it to his/her courses.")
                                return True
        return False
                        
    
    def sendSelectedCoursesToApproval(self):

        numberOfDraftCourses = 0
        for selectedCourse in self.selectedCourses:
            if selectedCourse.status is CourseStatus.DRAFT:
                numberOfDraftCourses = numberOfDraftCourses + 1

        #Check if the student acceeds the maximum number of courses that can be taken in one term
        if (numberOfDraftCourses > int(DatabaseManager.getInstance().getConstraints()[1])):
            Util.sendFeedback("You cannot take more than " + str(DatabaseManager.getInstance().getConstraints()[1]) + " courses in one term.", Color.RED)
            logging.warning(self.userId + " - Student: " + self.userId + " cannot take more than " + str(DatabaseManager.getInstance().getConstraints()[1]) + " courses in one term.")
            return

        self.approvalStatus = ApprovalStatus.DONE
        for selectedCourse in self.selectedCourses:
            if selectedCourse.status is CourseStatus.PENDING:
                self.approvalStatus = ApprovalStatus.PENDING
            

        if self.approvalStatus is ApprovalStatus.PENDING:
            Util.sendFeedback("You already sent your courses to approval!", Color.RED)
            logging.warning(self.userId + " - Student: " + self.userId + " already sent his/her courses to approval.")
            return
        
        if self.checkCompulsoryCourses():
            Util.sendFeedback("Please add your failed courses!", Color.RED)
            return
        
        if numberOfDraftCourses == 0:
            Util.sendFeedback("You have no course to send to approval!", Color.RED)
            logging.warning(self.userId + " - Student: " + self.userId + " has no course to send to approval.")
            return
        
        self.approvalStatus = ApprovalStatus.PENDING

        for selectedCourse in self.selectedCourses:
            if selectedCourse.status == CourseStatus.DRAFT:
                selectedCourse.status = CourseStatus.PENDING
                selectedCourse.courseSection.incrementStudentCount()

                #If selected course is a repeat course, set its course result to ACTIVE in the transcript
                if selectedCourse.courseSection in self.findRepeatCourseSections():  
                    for courseGrade in self.transcript.takenCourses:
                        if courseGrade.course.courseCode == selectedCourse.course.courseCode:
                            courseGrade.courseResult = CourseResult.ACTIVE

        
        self.advisorOfStudent.addNotification(self.firstName + " " + self.lastName + " has requested a course approval.")
        Util.sendFeedback("Courses are sent to advisor.", Color.GREEN)    
        logging.info(self.userId + " - Student: " + self.userId + " sent his/her courses to approval.")    
        DatabaseManager.getInstance().saveToDatabase()

    def getMyPage(self):
        cliStudent = CLIStudent(StudentController(self))
        cliStudent._menuPage()

    def fillTable(self):
        timeTable = [[] for _ in range(5)]

        for i in range(5):
            for j in range(9):
                timeTable[i].append("")

        for i in range(len(self.selectedCourses)):
            for j in range(len(self.selectedCourses[i].getCourseSection().getSectionDay())):
                day = self.selectedCourses[i].getCourseSection().sectionDay[j]
                time = self.selectedCourses[i].getCourseSection().sectionTime[j]
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
            timeTable[day][0] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "09:30-10:20":
            timeTable[day][1] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "10:30-11:20":
            timeTable[day][2] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "11:30-12:20":
            timeTable[day][3] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "13:00-13:50":
            timeTable[day][4] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "14:00-14:50":
            timeTable[day][5] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "15:00-15:50":
            timeTable[day][6] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "16:00-16:50":
            timeTable[day][7] += self.selectedCourses[i].courseSection.sectionCode + "-"
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
        for selectedCourse in self.selectedCourses:
            if selectedCourse.status is not CourseStatus.DRAFT:
                selectedCourses.append(selectedCourse)
        return selectedCourses

    def createTimeTable(self):
        timeTable = self.fillTable()
        return timeTable
    
    def getAdvisorOfStudent(self):
        return self.advisorOfStudent

    def getApprovalStatus(self):
        return self.approvalStatus
    
    def getFirstName(self):
        return self.firstName
    
    def getLastName(self):
        return self.lastName
    
    def getSelectedCourses(self):
        return self.selectedCourses
    
    def getTranscript(self):
        return self.transcript
    
    def setApprovalStatus(self, approvalStatus):
        self.approvalStatus = approvalStatus

    def setAdvisorOfStudent(self, advisorOfStudent):
        self.advisorOfStudent = advisorOfStudent
    
    