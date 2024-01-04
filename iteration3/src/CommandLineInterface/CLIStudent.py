from utils.DatabaseManager import DatabaseManager
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

            if self._studentController.getNotifications() is not None:
                Util.paintTextln(" Notifications\n " +
                                "**************", Color.YELLOW)
                for string in self._studentController.getNotifications():
                    Util.paintTextln(string, Color.RED)
                self._studentController.clearNotifications()


            print("\n\n")
            print(" Menu\n" +
                            "********\n" +
                            "  1. My Courses\n" +
                            "  2. Log out")
            _choice = input()
            if _choice == "1":
                self._showMyCoursesPage()
            elif _choice == "2":
                self._studentController.logout()
                self._shouldQuit = False
            else:
                Util.sendFeedback("Invalid input" + _choice, Color.RED)


    def _showMyCoursesPage(self):
        while(self._shouldQuit):
            Util.clearScreen()
            print("\n\n")
            print(" My Courses\n" +
                "**************\n")
            print("    %-10s    %-45s    %-15s    Status\n" % ("Code", "Name", "Section"))
            print("    %-10s    %-45s    %-15s    ______\n" % ("____", "____", "_______"))


            self._listSelectedCourses()

            print( "\n\n1. Add Course\n" +
                    "2. Delete Course\n" +
                    "3. Show Timetable\n" +
                    "4. Send To Approval\n")
            
            print("Press b to go back")
            print("Press q to quit\n")

            _choice: str = input()

            try:
                if _choice == "1":
                    if DatabaseManager.getInstance().getConstraints().get(4) == "false"  and  DatabaseManager.getInstance().getConstraints().get(1) == "false":
                        Util.sendFeedback("You are not in registration or add-drop week. You cannot select a new course.", Color.RED)
                    elif self._studentController.getApprovalStatus() == "FINALIZED_REGISTRATION":
                        Util.sendFeedback("You have already finalized your registration. You cannot select a new course.", Color.RED)
                    else:
                        self._showAddCoursePage()

                elif _choice == "2":
                    if DatabaseManager.getInstance().getConstraints().get(4) == "false"  and  DatabaseManager.getInstance().getConstraints().get(1) == "false":
                        Util.sendFeedback("You are not in registration or add-drop week. You cannot delete a course.", Color.RED)
                    elif self._studentController.getApprovalStatus() == "FINALIZED_REGISTRATION":
                        Util.sendFeedback("You have already finalized your registration. You cannot delete a course.", Color.RED)                        
                    else:
                        _choice = input("Enter the row number of the course you want to delete: ")
                        if Util.validateNumber(_choice, self._studentController.getSelectedCourses()):  
                            if self._deleteCourse(_choice):
                                Util.sendFeedback("Course deleted successfully", Color.GREEN)
                            else:
                                Util.sendFeedback("Course deletion failed", Color.RED)
                        else:
                            raise Exception("Invalid input" + _choice)
                        
                elif _choice == "3":
                    self._showTimeTablePage()

                elif _choice == "4":
                    if DatabaseManager.getInstance().getConstraints().get(4) == "false"  and  DatabaseManager.getInstance().getConstraints().get(1) == "false":
                        Util.sendFeedback("You are not in registration or add-drop week. You can not send your selected courses to approval", Color.RED)  
                    elif self._studentController.getApprovalStatus() == "FINALIZED_REGISTRATION":
                        Util.sendFeedback("You have already finalized your registration. You can not send your selected courses to approval", Color.RED)
                    else: 
                        self._studentController.sendSelectedCoursesToApproval()  

                elif _choice == "b":
                    break

                elif _choice == "q":
                    self._studentController.logout()
                    self._shouldQuit = False
                    
                else:
                    raise Exception("Invalid input" + _choice)
                    
            except Exception as e:
                if str(e) == "Invalid input" + _choice:
                    Util.sendFeedback("Invalid input", Color.RED)
                else:
                    raise
    
    def _showAddCoursePage(self):

        self._shouldQuit = True
        while(self._shouldQuit):
            Util.clearScreen()
            print("\n\n")
            print(" Add Course\n" +
                "**************\n")
            print("    %-10s    %-50s    %-15s    %-20s    %s\n" % ("Code", "Name", "Section", "Instructor", "Credit"))
            print("    %-10s    %-50s    %-15s    %-20s    %s\n" % ("____", "____", "_______", "__________", "______"))


            self._listAvailableCourseSections()
            
            print("\nPress b to go back")
            print("Press q to quit\n")
            

            _choice = input("Enter the row number of the course you want to add: ")

            try:
                if _choice == "b":
                    break

                elif _choice == "q":
                    self._studentController.logout()
                    self._shouldQuit = False

                elif Util.validateNumber(_choice, self._studentController.getAvailableCourseSections()): 

                    if self._addCourse(_choice):
                        Util.sendFeedback("Course added successfully", Color.GREEN)
                    else:
                        Util.sendFeedback("Course addition failed", Color.RED)

                else:
                    raise Exception("Invalid input" + _choice)
                
            except Exception as e:
                raise e
    def _showTimeTablePage(self):
        self._shouldQuit = True
        while(self._shouldQuit):
            Util.clearScreen()
            Util.printTimeTable(self._studentController.getTimeTable())
            print("\n\nPress b to go back")
            print("Press q to quit\n")

            _choice = input()

            try:
                if _choice == "b":
                    break
                elif _choice == "q":
                    self._studentController.logout()
                    self._shouldQuit = False
                else:
                    raise Exception("Invalid input" + _choice)
            except Exception as e:
                Util.sendFeedback(e, Color.RED)
    
    def _addCourse(self, str):
        rowNumber = int(str) #TODO: check if valid row number
        selectedCourseSection = self._studentController.getAvailableCourseSections()[(rowNumber-1)]
        if self._studentController.addSelectedCourse( SelectedCourse( selectedCourseSection.findCourseOfCourseSection(), CourseStatus.DRAFT, selectedCourseSection ) ):
            return True

        return False
    
    def _deleteCourse(self, str):
        rowNumber = int(str) #TODO: check if valid row number 
        if self._studentController.removeSelectedCourse( self._studentController.getSelectedCourses()[rowNumber-1] ):
            return True
        
        return False
    
    def _listAvailableCourseSections(self):
        rowCount = 1
        for courseSection in self._studentController.getAvailableCourseSections():
            course = courseSection.findCourseOfCourseSection()
            print(f"{rowCount}.  {course.getCourseCode():<10}  {course.getCourseName():<50}  {courseSection.getSectionCode():<15}  {courseSection.getLecturerName():<20}  {course.getCourseCredit()}")
            rowCount += 1

    def _listSelectedCourses(self):
        rowCount = 1
        for selectedCourse in self._studentController.getSelectedCourses():
            print(f"{rowCount}.  {selectedCourse.getCourse().getCourseCode():<10}  {selectedCourse.getCourse().getCourseName():<50}  {selectedCourse.getCourseSection().getSectionCode():<15}",  end="")            
            if selectedCourse.getStatus() == CourseStatus.APPROVED:
                Util.paintText(f"{selectedCourse.getStatus().value}\n", Color.GREEN)
            elif selectedCourse.getStatus() == CourseStatus.PENDING:
                Util.paintText(f"{selectedCourse.getStatus().value}\n", Color.YELLOW)
            elif selectedCourse.getStatus() == CourseStatus.DENIED:
                Util.paintText(f"{selectedCourse.getStatus().value}\n", Color.RED)
            else:
                print(f"{selectedCourse.getStatus().value}\n")
            
            rowCount += 1

    