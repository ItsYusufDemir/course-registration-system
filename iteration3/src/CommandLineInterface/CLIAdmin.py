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
                        "  2. Constraint Settings\n"
                        "  3. Log out\n")
        
        str = input()
        
        if str == "1":
            Util.clearScreen()
            self.courseListPage()
        elif str == "2":
            Util.clearScreen()
            self.constraintPage()
        elif str == "3":
            Util.clearScreen()
            self.adminController.logout()
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
        print("Press b to go back")
        print("Press q to quit\n")

        _choice = input()

        if _choice == "c":
            self.createNewCoursePage()
            self.courseListPage()
        elif _choice == "d":
            print("Enter the row number of the course you want to delete : ", end='')
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
        editedAttributes = self.adminController.getConstraints()

        print(" Constraint Settings\n" +
                        "**************\n")
        
        print("1. Add-Drop: " + editedAttributes.get(2))
        print("2. Max Number Of Courses That Can Be Taken: " + editedAttributes.get(1))
        print("3. Min Required ECTS For Term Project: " + editedAttributes.get(3))
        print("4. Registration Week: " + editedAttributes.get(4))
        print("")
        print("Press b to go back")
        print("Press q to quit")
        print()
        print("Enter the row number to edit:", end='')

        _choice = input()

        if _choice == "1":
            print("Enter true or false for add drop: ", end='')
            _choice = input()
            if _choice.lower() in ["true", "false"]:
                editedAttributes[2] = _choice.lower()
                Util.sendFeedback("Add-Drop value changed successfully", Color.GREEN)
                if _choice.lower() == "true":
                    self.adminController.setNotificationToStudentAndAdvisor("Add-Drop period has started")
                else:
                    self.adminController.setNotificationToStudentAndAdvisor("Add-Drop period has ended")
            else:
                Util.sendFeedback("Invalid input", Color.RED)
            self.adminController.editConstraint(editedAttributes)
            self.constraintPage()
        elif _choice == "2":
            print("Enter the max number of courses that can be taken: ", end='')
            _choice = input()
            if Util.isValidNumber(_choice):
                editedAttributes[1] = int(_choice)
                Util.sendFeedback("Max number of courses that can be taken changed successfully", Color.GREEN)
            else:
                Util.sendFeedback("Invalid input", Color.RED)
            self.adminController.editConstraint(editedAttributes)
            self.constraintPage()
        elif _choice == "3":
            print("Enter the min required ECTS for term project: ", end='')
            _choice = input()
            if Util.isValidNumber(_choice):
                editedAttributes[3] = int(_choice)
                Util.sendFeedback("Min required ECTS for term project changed successfully", Color.GREEN)
            else:
                Util.sendFeedback("Invalid input", Color.RED)
            self.adminController.editConstraint(editedAttributes)
            self.constraintPage()
        elif _choice == "4":
            print("Enter true or false for registration week: ", end='')
            _choice = input()
            if _choice.lower() in ["true", "false"]:
                editedAttributes[4] = _choice.lower()
                Util.sendFeedback("Add-Drop value changed successfully", Color.GREEN)
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
        lecturerName = None
        sectionTime = None
        sectionDate = None
        classroom = None
        sectionCode = None
        currentInput = None

        Util.paintTextln("********************Create New Course Page********************\n", Color.GREEN)

        print("-> Enter the course information for the fields.")

        while True:
            print("1.\tCourse Credit: ", end='')
            try:
                currentInput = input()
                courseCredit = int(currentInput)
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                continue

        while True:
            print("2.\tCourse ECTS: ", end='')
            try:
                currentInput = input()
                courseECTS = int(currentInput)
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                continue

        print("3.\tCourse Name: ", end='')
        courseName = input()

        print("4.\tCourse Code: ", end='')
        courseCode = input()

        while True:
            print("5.\tCourse Semester: ", end='')
            try:
                currentInput = input()
                givenSemester = int(currentInput)
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                continue
        
        while True:
            print("6.\tCourse Type: Compulsory(1), Non-technical Elective(2), Technical Elective(3), University Elective(4), Faculty Elective(5): ", end='')
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
                2: CourseType.NONTECHNICAL_ELECTIVE,
                3: CourseType.TECHNICAL_ELECTIVE,
                4: CourseType.UNIVERSITY_ELECTIVE,
                5: CourseType.FACULTY_ELECTIVE
        }

        while True:
            print("7.\tNumber of Pre-requisite Courses: ", end='')
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

        i = 0
        while i < numberOfPreRequisiteCourses:
            print(str(i + 1) + ".\t", end='')
            prerequisiteCourseCode = input()
            prerequisiteCourse = self.findCourseByCourseCode(prerequisiteCourseCode)

            if prerequisiteCourse is None:
                Util.sendFeedback("There is no course with this course code! Try again", Color.RED)
            else:
                prerequisiteCourses.append(prerequisiteCourse)
                i = i + 1  # Only increment i when a valid input is received
                
        

        while True:
            print("8.\tNumber of Course Sections: ", end='')
            try:
                currentInput = input()
                numberOfCourseSections = int(currentInput)
                break
            except:
                Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                continue

        for i in range(numberOfCourseSections):
            print("Section " + str(i + 1))

            while True:
                print("1.\tStudent Capacity: ", end='')
                try:
                    currentInput = input()
                    studentCapacity = int(currentInput)
                    break
                except:
                    Util.sendFeedback("Invalid input ! Please enter an integer value", Color.RED)
                    continue
        
            print("2.\tLecturer Name: ", end='')
            lecturerName = input()

            print("\nFollowing information for section day and time")
            print("Input Format for Section Day:\tMonday, Monday, Tuesday")
            print("Input Format for Section Time:\t08:30-09:20, 09:30-10:30, 08:30-09:20")

            while True:
                print("3.\tSection Day: ", end='')
                sectionDate = input()
                sectionDateList = Util.makeArrayList(",", sectionDate)
                if Util.isInputFormatTrueForDay(sectionDateList):
                    break
                else:
                    Util.sendFeedback("Invalid input ! Please enter a valid day", Color.RED)
                    continue

            while True:
                print("4.\tSection Time: ", end='')
                sectionTime = input()
                sectionTimeList = Util.makeArrayList(",", sectionTime)
                if Util.isInputFormatTrueForTime(sectionTimeList):
                    break
                else:
                    Util.sendFeedback("Invalid input ! Please enter a valid time", Color.RED)
                    continue

            if len(sectionTimeList) == len(sectionDateList):
                break


            else:
                Util.sendFeedback("Number of entered days must be equal to number of times entered", Color.RED)
                continue
        

        print("9.\tClassroom: ", end='')
        classroom = input()

        print("10.\tSection Code: ", end='')
        sectionCode = input()

        courseSections.append(CourseSection(studentCapacity, lecturerName, sectionDateList, sectionTimeList, classroom, sectionCode,0))
        prerequisite = Prerequisite(prerequisiteCourses)
        course = Course(courseCredit, courseECTS,givenSemester, courseName, courseCode, prerequisite, courseSections, switcher.get(courseTypeCode))

        print(course.__dict__)
       

        Util.clearScreen()

        if self.adminController.createCourse(course):
            Util.sendFeedback("SUCCESS: " + courseCode + " is created.", Color.GREEN)
            return True
        else:
            Util.sendFeedback("FAIL! " + courseCode + " can't created.", Color.RED)
            return False

    def findCourseByCourseCode(self, courseCode):
        return self.adminController.findCourseByCourseCode(courseCode)
