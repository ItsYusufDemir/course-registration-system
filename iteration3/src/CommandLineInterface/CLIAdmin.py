

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
            DatabaseManaget.getInstance().saveToDatabase()
            Util.clearScreen()
            adminController.logout()
        elif str == "1":
            Util.clearScreen()
            courseListPage()
        elif str == "2":
            Util.clearScreen()
            constraintPage()
        else:
            Util.sendFeedback("Invalid input", Color.RED)
            menuPage()

    def courseListPage(self):
        Util.clearScreen()
        courses = adminController.getCourseList()

        print(" All Courses\n" +
                        "**************\n" +
                        "  Code\t\t\t\t Name\t \n" +
                        "  ____\t ____\t")
        
        for course,i in courses:
            print("  " + (i + 1) + ". " + courses.get(i).getCourseCode() + "\t " + courses.get(i).getCourseName())

        print("\n\nPress c to create a new course\n" +
                        "Press d to delete course\n")
        print("Press b to go back\n")
        print("Press q to quit\n")

        str = input()

        if str == "c":
            createNewCoursePage()
            courseListPage()
        elif str == "d":
            print("Enter the row number of the course you want to delete : ")
            str = input()
            if util.isValidNumber(str) and Util.checkIfValidRowNumber(str, courses):
                if adminController.deleteCourse(course):
                    Util.clearScreen()
                    Util.sendFeedback("Course deleted successfully", Color.GREEN)
                else:
                    Util.clearScreen()
                    Util.sendFeedback("Course cannot be deleted", Color.RED)
            else:
                Util.clearScreen()
                Util.sendFeedback("Invalid input", Color.RED)
        elif str == "b":
            Util.clearScreen()
            menuPage()
        elif str == "q":
            Util.clearScreen()
            adminController.logout()
        else:
            Util.sendFeedback("Invalid input", Color.RED)
            courseListPage()


    def constraintPage(self):
        Util.clearScreen()
        editedAttributes = adminController.getConstraint()

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

        str = input()

        if str == "1":
            print("Enter true or false for add drop: ")
            str = input()
            if str == "true":
                editedAttributes.put(2, "true")
                Util.sendFeedback("Add-Drop value changed successfully", Color.GREEN)
            elif str == "false":
                editedAttributes.put(2, "false")
                Util.sendFeedback("Add-Drop value changed successfully", Color.GREEN)
            else:
                Util.sendFeedback("Invalid input", Color.RED)

            adminController.editConstraint(editedAttributes)
            constraintPage()
        elif str == "2":
            print("Enter the max number of courses that can be taken: ")
            str = input()
            if util.isValidNumber(str):
                editedAttributes.put(1, str)
                Util.sendFeedback("Max number of courses that can be taken changed successfully", Color.GREEN)
            else:
                Util.sendFeedback("Invalid input", Color.RED)

            adminController.editConstraint(editedAttributes)
            constraintPage()
        elif str == "3":
            print("Enter the min required ECTS for term project: ")
            str = input()
            if util.isValidNumber(str):
                editedAttributes.put(3, str)
                Util.sendFeedback("Min required ECTS for term project changed successfully", Color.GREEN)
            else:
                Util.sendFeedback("Invalid input", Color.RED)

            adminController.editConstraint(editedAttributes)
            constraintPage()
        elif str == "b":
            Util.clearScreen()
            menuPage()
        elif str == "q":
            Util.clearScreen()
            adminController.logout()
        else:
            Util.sendFeedback("Invalid input", Color.RED)
            constraintPage()
        

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
            prerequisiteCourse = findCourseByCode(prerequisiteCourseCode)

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

        util.clearScreen()

        if adminController.createCourse(course):
            Util.sendFeedback("SUCCESS: " + courseCode + " is created.", Color.GREEN)
            return True
        else:
            Util.sendFeedback("FAIL! " + courseCode + " can't created.", Color.RED)
            return False

    def findCourseByCourseCode(self, courseCode):
        return adminController.findCourseByCourseCode(courseCode)
