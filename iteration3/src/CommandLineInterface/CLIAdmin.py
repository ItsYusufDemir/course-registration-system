from enums.CourseType import CourseType
from interfaces.Color import Color
from models.Course import Course
from models.CourseSection import CourseSection
from models.Prerequisite import Prerequisite
from utils.Util import Util

class CLIAdmin():

    def __init__(self, adminController):
        self.adminController = adminController

    def menuPage(self):
        Util.clearScreen()
        print(" Menu\n" +
                        "********\n" +
                        "  1. Course list\n" +
                        "  2. Constraint Settings\n\n" +
                        "Press q to quit\n" +
                        "Press corresponding row number to review and make changes for the list you want.\n")
        
        str = input()
        if str == "q":
            Util.clearScreen()
            self.adminController.logOut()
        elif str == "1":
            Util.clearScreen()
            self.courseListPage()
        elif str == "2":
            Util.clearScreen()
            self.constraintPage()
        else:
            Util.sendFeedback("Invalid input", Color.RED)
            self.menuPage()

    def courseListPage(self):
        Util.clearScreen()
        courses = self.adminController.getCourseList()

        print(" All Courses\n" +
                        "**************\n" +
                        "  Code\t\t\t\t Name\t \n" +
                        "  ____\t ____\t")
        
        for i in range(len(courses)):
            print("  " + str(i + 1) + ". " + courses[i].getCourseCode() + "\t " + courses[i].getCourseName())

        print("\n\nPress c to create a new course\n" +
                        "Press d to delete course\n")
        print("Press b to go back\n")
        print("Press q to quit\n")

        _choice = input()

        if _choice == "c":
            self.createNewCoursePage()
            self.courseListPage()
        elif _choice == "d":
            print("Enter the row number of the course you want to delete : ")
            _choice = input() 
            if Util.validateNumber(_choice, courses):
                if self.adminController.deleteCourse(courses[(int(_choice)-1)]):
                    Util.clearScreen()
                    Util.sendFeedback("Course deleted successfully", Color.GREEN)
                else:
                    Util.clearScreen()
                    Util.sendFeedback("Course cannot be deleted", Color.RED)
            else:
                Util.clearScreen()
                Util.sendFeedback("Invalid input", Color.RED)

            self.courseListPage()
        elif _choice == "b":
            Util.clearScreen()
            self.menuPage()
        elif _choice == "q":
            Util.clearScreen()
            self.adminController.logout()
        else:
            Util.sendFeedback("Invalid input", Color.RED)
            self.courseListPage()


    def constraintPage(self):
        Util.clearScreen()
        editedAttributes = self.adminController.getConstraint()

        print(" Constraint Settings\n" +
                        "**************\n")
        
        print("1. Add-Drop: " + editedAttributes.get(2))
        print("2. Max Number Of Courses That Can Be Taken: " + editedAttributes.get(1))
        print("3. Min Required ECTS For Term Project: " + editedAttributes.get(3))
        print("")
        print("Press b to go back")
        print("Press q to quit")
        print()
        print("Enter the row number to edit:")

        _choice = input()

        if _choice == "1":
            print("Enter true or false for add drop: ")
            _choice = input()
            if _choice == "true":
                editedAttributes.put(2, "true")
                Util.sendFeedback("Add-Drop value changed successfully", Color.GREEN)
            elif _choice == "false":
                editedAttributes.put(2, "false")
                Util.sendFeedback("Add-Drop value changed successfully", Color.GREEN)
            else:
                Util.sendFeedback("Invalid input", Color.RED)

            self.adminController.editConstraint(editedAttributes)
            self.constraintPage()
        elif _choice == "2":
            print("Enter the max number of courses that can be taken: ")
            _choice = input()
            if Util.isValidNumber(_choice):
                editedAttributes.put(1, _choice)
                Util.sendFeedback("Max number of courses that can be taken changed successfully", Color.GREEN)
            else:
                Util.sendFeedback("Invalid input", Color.RED)

            self.adminController.editConstraint(editedAttributes)
            self.constraintPage()
        elif _choice == "3":
            print("Enter the min required ECTS for term project: ")
            _choice = input()
            if Util.isValidNumber(_choice):
                editedAttributes.put(3, _choice)
                Util.sendFeedback("Min required ECTS for term project changed successfully", Color.GREEN)
            else:
                Util.sendFeedback("Invalid input", Color.RED)

            self.adminController.editConstraint(editedAttributes)
            self.constraintPage()
        elif _choice == "b":
            Util.clearScreen()
            self.menuPage()
        elif _choice == "q":
            Util.clearScreen()
            self.adminController.logout()
        else:
            Util.sendFeedback("Invalid input", Color.RED)
            self.constraintPage()
        

    def createNewCoursePage(self):

        prerequisiteCourses = []
        courseSections = []
        sectionTimeList = []
        sectionDateList = []
        courseType = None
        course = None

        courseCredit = None
        courseECTS = None
        numberOfPreRequisiteCourses = None
        courseTypeCode = None
        numberOfCourseSections = None
        studentCapacity = None
        givenSemester = None

        courseName = None
        courseCode = None
        LecturerName = None
        sectionTime = None
        sectionDate = None
        classroom = None
        sectionCode = None
        currentInput = None

        Util.paintTextln("********************Create New Course Page********************\n", Color.GREEN)

        print("-> Enter the course information for the fields.")

        while True:
            print("1.\tCourse Credit: ")
            try:
                currentInput = input()
                courseCredit = int(currentInput)
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                continue

        while True:
            print("2.\tCourse ECTS: ")
            try:
                currentInput = input()
                courseECTS = int(currentInput)
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                continue

        print("3.\tCourse Name: ")
        courseName = input()

        print("4.\tCourse Code: ")
        courseCode = input()

        while True:
            print("5.\tCourse Semester: ")
            try:
                currentInput = input()
                givenSemester = int(currentInput)
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                continue
        
        while True:
            print("6.\tCourse Type: Compulsory(1), Non-technical Elective(2), Technical Elective(3), University Elective(4), Faculty Elective(5): ")
            try:
                currentInput = input()
                courseTypeCode = int(currentInput)

                if courseTypeCode < 1 or courseTypeCode > 5:
                    Util.sendFeedback("Invalid input ! Please enter an integer value between 1 and 5", Color.RED)
                    continue
                
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value between 1 and 5", Color.RED)
                continue

        switcher = {
                1: CourseType.COMPULSORY,
                2: CourseType.NON_TECHNICAL_ELECTIVE,
                3: CourseType.TECHNICAL_ELECTIVE,
                4: CourseType.UNIVERSITY_ELECTIVE,
                5: CourseType.FACULTY_ELECTIVE
        }

        while True:
            print("7.\tNumber of Pre-requisite Courses: ")
            try:
                currentInput = input()
                numberOfPreRequisiteCourses = int(currentInput)
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                continue
        
        print("Write course codes of prerequisite courses one by one")
        prerequisiteCourseCode = None
        prerequisiteCourse = None

        for i in range(numberOfPreRequisiteCourses):
            print(i + ".\t")
            prerequisiteCourseCode = input()
            prerequisiteCourse = self.findCourseByCode(prerequisiteCourseCode)

            if prerequisiteCourse == None:
                Util.sendFeedback("There is no course with this course code! Try again.", Color.RED)
                i = i - 1
                continue
        

        while True:
            print("8.\tNumber of Course Sections: ")
            try:
                currentInput = input()
                numberOfCourseSections = int(currentInput)
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                continue

        for i in range(numberOfCourseSections):
            print("Section " + i)

            while True:
                print("1.\tStudent Capacity: ")
                try:
                    currentInput = input()
                    studentCapacity = int(currentInput)
                    break
                except:
                    Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                    continue
        
            print("2.\tLecturer Name: ")
            LecturerName = input()

            print("\nFollowing information for section day and time")
            print("Input Format for Section Day:\tMonday, Monday, Tuesday")
            print("Input Format for Section Time:\t08:40-10:30, 15:40-17:30")

            while True:
                print("3.\tSection Day: ")
                sectionDate = input()
                sectionDateList = Util.makeArrayList(",", sectionDate)
                if Util.isInputFormatTrueForDay(sectionDateList):
                    break
                else:
                    Util.sendFeedback("Invalid input ! Please enter a valid day", Color.RED)
                    continue

            while True:
                print("4.\tSection Time: ")
                sectionTime = input()
                sectionTimeList = Util.makeArrayList(",", sectionTime)
                if Util.isInputFormatTrueForTime(sectionTimeList):
                    break
                else:
                    Util.sendFeedback("Invalid input ! Please enter a valid time", Color.RED)
                    continue

            if sectionTimeList.size() == sectionDateList.size():
                break

            else:
                Util.sendFeedback("Number of entered days must be equal to number of times entered", Color.RED)
                continue
        

        print("5.\tClassroom: ")
        classroom = input()

        print("6.\tSection Code: ")
        sectionCode = input()

        courseSections.add(CourseSection(studentCapacity, LecturerName, sectionDateList, sectionTimeList, classroom, sectionCode))
        prerequisite = Prerequisite(prerequisiteCourses)
        course = Course(courseCredit, courseECTS, courseName, courseCode, givenSemester, switcher.get(courseTypeCode), prerequisite, courseSections)

        Util.clearScreen()

        if self.adminController.createCourse(course):
            Util.sendFeedback("SUCCESS: " + courseCode + " is created.", Color.GREEN)
            return True
        else:
            Util.sendFeedback("FAIL! " + courseCode + " can't created.", Color.RED)
            return False

    def findCourseByCourseCode(self, courseCode):
        return self.adminController.findCourseByCourseCode(courseCode)
