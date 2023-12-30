from utils.Util import Util
from interfaces.Color import Color
from enums.CourseStatus import CourseStatus
from models.SelectedCourse import SelectedCourse

class CLIStudent(object):
    
    

    def __init__(self, studentController):
        self._studentController = studentController
        self._shouldQuit = True


    def _menuPage(self):
        
        while(self._shouldQuit):
            Util.clearScreen()
            print("\n\n")
            print(" Menu\n" +
                            "********\n" +
                            "  1. My Courses\n" +
                            "  2. Log out\n\n" +
                            "Press q to quit")
            _choice = input()
            if _choice == "1":
                self._showMyCoursesPage()
            elif _choice == "2":
                break
            elif _choice == "q":
                self._shouldQuit = False
            else:
                Util.sendFeedback("Invalid input" + _choice, Color.RED)


    def _showMyCoursesPage(self):
        while(self._shouldQuit):
            Util.clearScreen()
            print("\n\n")
            print(" My Courses\n" +
                  "**************\n")
            print("    %10s    %50s    %15s    Status\n", "Code", "Name", "Section")
            print("    %10s    %50s    %15s    ______\n", "____", "____", "_______")

            self._listSelectedCourses()

            print( "\n\n1. Add Course\n" +
                    "2. Delete Course\n" +
                    "3. Show Timetable\n" +
                    "4. Send To Approval\n")
            
            print("Press b to go back\n")
            print("Press q to quit\n")

            _choice = input()

            try:
                if _choice == "1":
                    self._showAddCoursePage()
                elif _choice == "2":
                    _choice = input("Enter the row number of the course you want to delete : ")
                    if Util.validateNumber(_choice):  
                        if self._deleteCourse():
                            Util.sendFeedback("Course deleted successfully", Color.GREEN)
                        else:
                            Util.sendFeedback("Course deletion failed", Color.RED)
                    else:
                        raise Exception("Invalid input" + _choice)
                elif _choice == "3":
                    self._showTimeTablePage()
                elif _choice == "4":
                    self._studentController.sendSelectedCoursesToApproval()
                elif _choice == "b":
                    break
                elif _choice == "q":
                    self._shouldQuit = False
                else:
                    raise Exception("Invalid input" + _choice)
                    
            except Exception as e:
                Util.sendFeedback(e, Color.RED)
    
    def _showAddCoursePage(self):
        self._shouldQuit = True
        while(self._shouldQuit):
            Util.clearScreen()
            print("\n\n")
            print(" Add Course\n" +
                  "**************\n")
            print("    %10s    %50s    %15s    %20s    %s\n", "Code", "Name", "Section", "Instructor", "Credit")
            print("    %10s    %50s    %15s    %20s    %s\n", "____", "____", "_______", "__________", "______")

            self._listAvailableCourseSections()
            
            print("Press b to go back\n")
            print("Press q to quit\n")
            

            _choice = input("Enter the row number of the course you want to add : ")

            try:
                if _choice == "b":
                    break

                elif _choice == "q":
                    self._shouldQuit = False

                elif Util.validateNumber(_choice): 

                    if self._addCourse(_choice):
                        Util.sendFeedback("Course added successfully", Color.GREEN)
                    else:
                        Util.sendFeedback("Course addition failed", Color.RED)

                else:
                    raise Exception("Invalid input" + _choice)
                
            except Exception as e:
                print(e)
    def _showTimeTablePage(self):
        self._shouldQuit = True
        while(self._shouldQuit):
            Util.clearScreen()
            Util.printTimeTable(self._studentController.getStudentTimeTable())
            print("\n\n press b to go back\n")
            print("Press q to quit\n")

            _choice = input()

            try:
                if _choice == "b":
                    break
                elif _choice == "q":
                    self._shouldQuit = False
                else:
                    raise Exception("Invalid input" + _choice)
            except Exception as e:
                Util.sendFeedback(e, Color.RED)
    
    def _addCourse(self, str):
        rowNumber = int(str) #TODO: check if valid row number
        selectedCourseSection = self._studentController.getAvaliableCourseSections().get(rowNumber-1)
        if self._studentController.addSelectedCourse( SelectedCourse( selectedCourseSection.findCourseOfCourseSection(), selectedCourseSection ) ):
            return True

        return False
    
    def _deleteCourse(self, str):
        rowNumber = int(str) #TODO: check if valid row number 
        if self._studentController.removeSelectedCourse( self._studentController.getSelectedCourses().get(rowNumber-1) ):
            return True
        
        return False
    
    def _listAvailableCourseSections(self):
        rowCount = 1
        for courseSection in self._studentController.getAvaliableCourseSections():
            course = courseSection.findCourseOfCourseSection()
            print("%d.  %10s    %50s    %15s    %20s    %d\n", rowCount, course.getCourseCode(), course.getCourseName(),
                    courseSection.getSectionCode(), courseSection.getLecturerName(), course.getCourseCredit())
            rowCount += 1

    def _listSelectedCourses(self):
        rowCount = 1
        for selectedCourse in self._studentController.getSelectedCourses():
            print("%d.  %10s    %50s    %15s    \n", rowCount, selectedCourse.getCourseCode(), selectedCourse.getCourseName(),
                    selectedCourse.getCourseSection().getSectionCode())
            
            if selectedCourse.getStatus() == CourseStatus.APPROVED:
                Util.paintText(selectedCourse.getStatus() + "\n", Color.GREEN)
            elif selectedCourse.getStatus() == CourseStatus.PENDING:
                Util.paintText(selectedCourse.getStatus() + "\n", Color.YELLOW)
            elif selectedCourse.getStatus() == CourseStatus.DENIED:
                Util.paintText(selectedCourse.getStatus() + "\n", Color.RED)
            else:
                print(selectedCourse.getStatus() + "\n")
            
            rowCount += 1
    