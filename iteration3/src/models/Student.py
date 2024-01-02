#beyza
import logging
from enums.ApprovalStatus import ApprovalStatus
from models.SelectedCourse import SelectedCourse
from models.Transcript import Transcript
from models.User import User
from utils.DatabaseManager import DatabaseManager
from utils.Util import Util
from interfaces.Color import Color
from enums.CourseStatus import CourseStatus
from enums.CourseResult import CourseResult
from models.CourseSection import CourseSection
from CommandLineInterface.CLIStudent import CLIStudent


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

        from models.Advisor import Advisor


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
        courses = DatabaseManager.getInstance().getCourses()
        availableCourseSections = []

        for course in courses:
            if course._courseCode == "CSE4297" or course._courseCode == "CSE4298":
                if self.transcript.checkEngineeringProjectAvailability():
                    allSelectableCourseSections.extend(course._courseSections)
            elif self.currentSemester < course._givenSemester:
                if self.transcript.calculateGPA() >= 3.0:
                    allSelectableCourseSections.extend(course._courseSections)
            else:
                allSelectableCourseSections.extend(course._courseSections)

        for courseSection in allSelectableCourseSections:
            course = courseSection.findCourseOfCourseSection()

            if courseSection.checkAvailability() and course.checkPrerequisite(self) and not self.checkIfItWasTaken(course) and not self.checkIfItExistsInSelectedCourses(course) and self.checkCourseType(course):
                availableCourseSections.append(courseSection)
        
        availableCourseSections.extend(self.findRepeatCourseSections())
        return availableCourseSections
    
    
    def checkIfItExistsInSelectedCourses(self, course):
        for selectedCourse in self.selectedCourses:
            if selectedCourse.course == course:
                return True
        return False

    
    def checkIfItWasTaken(self, course):
        takenCourses = self.transcript.acquirePassedCourses()
        for takenCourse in takenCourses:
            if takenCourse.courseName == course.courseName:
                return True
        return False    
    
    def findRepeatCourseSections(self):
        takenCourses = self.transcript.takenCourses
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
            if str(selectedCourse.course.courseType) == "NONTECHNICAL_ELECTIVE":
                nteCounter = nteCounter + 1
            elif str(selectedCourse.course.courseType) == "TECHNICAL_ELECTIVE":
                teCounter = teCounter + 1
            elif str(selectedCourse.course.courseType) == "FACULTY_ELECTIVE":
                fteCounter = fteCounter + 1
            elif str(selectedCourse.course.courseType) == "UNIVERSITY_ELECTIVE":
                ueCounter = ueCounter + 1
        if str(selectedCourse.course.courseType) == "NONTECHNICAL_ELECTIVE" and nteCounter >= 2:
            return False
        elif str(selectedCourse.course.courseType) == "TECHNICAL_ELECTIVE" and teCounter >= 4:
            return False
        elif str(selectedCourse.course.courseType) == "FACULTY_ELECTIVE" and fteCounter >= 1:
            return False
        elif str(selectedCourse.course.courseType) == "UNIVERSITY_ELECTIVE" and ueCounter >= 1:
            return False
        else: 
            return True
        
    def addNewCourse(self, selectedCourse):
        constraints = DatabaseManager.getInstance().getConstraints()
        if len(self.selectedCourses) >= int(constraints.get(1)):
            Util.sendFeedback("You cannot take more than " + str(constraints.get(1) + " courses in one term.", Color.RED))
            Util.getLogger().warning(self.userId + " - Student cannot take more than " + str(constraints.get(1)) + " courses in one term.")
            return False
        
        if selectedCourse not in self.selectedCourses:
            self.selectedCourses.append(selectedCourse)
            DatabaseManager.getInstance().saveToDatabase()
            Util.getLogger().info(self.userId + " - Course: " + selectedCourse.course.courseCode
                    + " added to student: " + self.userId)
            return True
        else:
            Util.getLogger().warning(self.userId + " - Course: " + selectedCourse.course.courseCode
        + " is already added to student: " + self.userId)

        
        
    def deleteCourse(self, selectedCourse):
        if selectedCourse not in self.selectedCourses and selectedCourse.status is not CourseStatus.PENDING:
            self.selectedCourses.delete(selectedCourse)
            DatabaseManager.getInstance().saveToDatabase()
            Util.getLogger().info(self.userId + " - Course: " + selectedCourse.course.courseCode
                    + " deleted from student: " + self.userId)
            return True 
        else:  
            Util.getLogger().warning(self.userId + " - Course: " + selectedCourse.course.courseCode
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
                                Util.getLogger().warning("Student: " + self.userId + " has failed course: "
                                        + courseGrade.course.courseCode
                                        + " and has not added it to his/her courses.")
                                return True
        return False
                        
    
    def sendSelectedCoursesToApproval(self):
        numberOfDraftCourses = 0
        self.approvalStatus = ApprovalStatus.DONE
        for selectedCourse in self.selectedCourses:
            if selectedCourse.status is CourseStatus.PENDING:
                self.approvalStatus = ApprovalStatus.PENDING
            
            if selectedCourse.status is CourseStatus.DRAFT:
                numberOfDraftCourses = numberOfDraftCourses + 1

        if self.approvalStatus is ApprovalStatus.PENDING:
            Util.sendFeedback("You already sent your courses to approval!", Color.RED)
            Util.getLogger().warning(self.userId + " - Student: " + self.userId + " already sent his/her courses to approval.")
            return
        
        if self.checkCompulsoryCourses:
            Util.sendFeedback("Please add your failed courses!", Color.RED)
            return
        
        self.approvalStatus = ApprovalStatus.PENDING

        if numberOfDraftCourses == 0:
            Util.sendFeedback("You have no course to send to approval!", Color.RED)
            Util.getLogger().warning(self.userId + " - Student: " + self.userId + " has no course to send to approval.")
            return
        
        for selectedCourse in self.selectedCourses:
            if selectedCourse.status is CourseStatus.DRAFT:
                selectedCourse.status = CourseStatus.PENDING
                selectedCourse.courseSection.incrementStudentCount()
            elif selectedCourse.courseSection not in self.findRepeatCourseSections():
                selectedCourse.status = CourseStatus.PENDING
                
                for courseGrade in self.transcript.takenCourses:
                    if courseGrade.course.courseCode == selectedCourse.course.courseCode:
                        courseGrade.courseResult = CourseResult.ACTIVE

                selectedCourse.courseSection.incrementStudentCount()
        
        self.advisorOfStudent.addNotification(self.firstName + " " + self.lastName + " has requested a course approval.")
        Util.sendFeedback("Courses are sent to advisor.", Color.GREEN)    
        Util.getLogger().info(self.userId + " - Student: " + self.userId + " sent his/her courses to approval.")    
        DatabaseManager.getInstance().saveToDatabase()

    def getMyPage(self):
        cliStudent = CLIStudent()
        cliStudent.menuPage()

    def fillTable(self):
        timeTable = []
        for i in range(5):
            for j in range(9):
                timeTable[i][j] = ""

        for i in range(len(self.selectedCourses)):
            for j in range(len(self.selectedCourses[i].courseSection.sectionDay())):
                day = self.selectedCourses[i].courseSection.sectionDay[j]
                time = self.selectedCourses[i].courseSection.sectionTime[j]
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
        elif time == "12:30-13:20":
            timeTable[day][4] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "13:30-14:20":
            timeTable[day][5] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "14:30-15:20":
            timeTable[day][6] += self.selectedCourses[i].courseSection.sectionCode + "-"
        elif time == "15:30-16:20":
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