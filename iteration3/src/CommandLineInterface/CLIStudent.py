from iteration3.src.enums.ApprovalStatus import ApprovalStatus
from iteration3.src.utils.DatabaseManager import DatabaseManager
from iteration3.src.utils.Util import Util
from iteration3.src.interfaces.Color import Color
from iteration3.src.enums.CourseStatus import CourseStatus
from iteration3.src.models.SelectedCourse import SelectedCourse

class CLIStudent(object):
    
    

    def __init__(self, studentController):
        self._studentController = studentController
        self._shouldQuit = True


    def menuPage(self):
        
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
            print("    %-10s    %-50s    %-15s    Status" % ("Code", "Name", "Section"))
            print("    %-10s    %-50s    %-15s    ______\n" % ("____", "____", "_______"))


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
                    if DatabaseManager.getInstance().getConstraints().get(4) == "False"  and  DatabaseManager.getInstance().getConstraints().get(2) == "False":
                        Util.sendFeedback("You are not in registration nor add-drop week. You cannot select a new course.", Color.RED)
                    elif self._studentController.getApprovalStatus() == ApprovalStatus.FINALIZED_REGISTRATION:
                        Util.sendFeedback("Your registration has been finalized. You cannot select a new course.", Color.RED)
                    else:
                        self._showAddCoursePage()

                elif _choice == "2":
                    if DatabaseManager.getInstance().getConstraints().get(4) == "False"  and  DatabaseManager.getInstance().getConstraints().get(2) == "False":
                        Util.sendFeedback("You are not in registration nor add-drop week. You cannot delete a course.", Color.RED)
                    elif self._studentController.getApprovalStatus() == ApprovalStatus.FINALIZED_REGISTRATION:
                        Util.sendFeedback("Your registration has been finalized. You cannot delete a course.", Color.RED)                        
                    else:
                        _choice = input("Enter the row number of the course you want to delete: ")
                        if(Util.validateNumber(_choice, self._studentController.getSelectedCourses()) ): 
                            self._deleteCourse(_choice)
                            Util.sendFeedback("Course deleted successfully", Color.GREEN)
                        else:
                            raise Exception("Invalid input " + _choice)
                        
                elif _choice == "3":
                    self._showTimeTablePage()

                elif _choice == "4":
                    self._studentController.sendSelectedCoursesToApproval()
                    Util.sendFeedback("Selected courses sent to approval successfully", Color.GREEN)
                elif _choice == "b":
                    break

                elif _choice == "q":
                    self._studentController.logout()
                    self._shouldQuit = False
                    
                else:
                    raise Exception("Invalid input " + _choice)
                    
            except Exception as e:
                Util.sendFeedback(str(e), Color.RED)
    
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

                else:
                    if (Util.validateNumber(_choice, self._studentController.getAvailableCourseSections())):
                        self._addCourse(_choice)
                        Util.sendFeedback("Course added successfully", Color.GREEN)
                    else:
                        raise Exception("Invalid input: " + _choice)
            except Exception as e:
                Util.sendFeedback(str(e), Color.RED)
            
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
                Util.sendFeedback(str(e), Color.RED)
    
    def _addCourse(self, str):
        rowNumber = int(str) 
        selectedCourseSection = self._studentController.getAvailableCourseSections()[(rowNumber-1)]
        if self._studentController.addSelectedCourse( SelectedCourse( selectedCourseSection.findCourseOfCourseSection(), CourseStatus.DRAFT, selectedCourseSection ) ):
            return True

        return False
    
    def _deleteCourse(self, str):
        rowNumber = int(str)
        if self._studentController.removeSelectedCourse( self._studentController.getSelectedCourses()[rowNumber-1] ):
            return True
        
        return False
    
    def _listAvailableCourseSections(self):
        rowCount = 1
        for courseSection in self._studentController.getAvailableCourseSections():
            course = courseSection.findCourseOfCourseSection()
            print(f"{rowCount}.  {course.getCourseCode():<10}    {course.getCourseName():<50}    {courseSection.getSectionCode():<15}    {courseSection.getLecturerName():<20}    {course.getCourseCredit()}")
            rowCount += 1

    def _listSelectedCourses(self):
        rowCount = 1
        for selectedCourse in self._studentController.getSelectedCourses():
            print(f"{rowCount}.  {selectedCourse.getCourse().getCourseCode():<10}    {selectedCourse.getCourse().getCourseName():<50}    {selectedCourse.getCourseSection().getSectionCode():<15}    ",  end="")            
            if selectedCourse.getStatus() == CourseStatus.APPROVED:
                Util.paintTextln(f"{selectedCourse.getStatus().value}", Color.GREEN)
            elif selectedCourse.getStatus() == CourseStatus.PENDING:
                Util.paintTextln(f"{selectedCourse.getStatus().value}", Color.YELLOW)
            elif selectedCourse.getStatus() == CourseStatus.DENIED:
                Util.paintTextln(f"{selectedCourse.getStatus().value}", Color.RED)
            elif selectedCourse.getStatus() == CourseStatus.ACTIVE:
                Util.paintTextln(f"{selectedCourse.getStatus().value}", Color.GREEN)
            elif selectedCourse.getStatus() == CourseStatus.APPROVED_FINALIZED:
                Util.paintTextln("APPROVED FINALIZED", Color.GREEN)
            elif selectedCourse.getStatus() == CourseStatus.DENIED_FINALIZED:
                Util.paintTextln("DENIED FINALIZED", Color.RED)
            else:
                Util.paintText(f"{selectedCourse.getStatus().value}\n", Color.DEFAULT)
            
            rowCount += 1

    