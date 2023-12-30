from src.enums.CourseStatus import CourseStatus
from src.models.SelectedCourse import SelectedCourse

class CLIStudent(object):
    
    

    def __init__(self, studentController):
        self._studentController = studentController
        self._shouldQuit = True


    def _menuPage(self):
        
        while(self._shouldQuit):
            #Util.clearScreen() #TODO: Add this
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
                print("Invalid input" + _choice) # TODO: Add color


    def _showMyCoursesPage(self):
        while(self._shouldQuit):
            #Util.clearScreen() #TODO: Add this
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

            self._choice = input()

            try:
                if self._choice == "1":
                    self._showAddCoursePage()
                elif self._choice == "2":
                    self._choice = input("Enter the row number of the course you want to delete : ")
                    if self._choice.isdigit():  #TODO: use util validate number method
                        if self._deleteCourse():
                            print("Course deleted successfully") #TODO: Add color
                        else:
                            print("Course deletion failed")
                    else:
                        raise Exception("Invalid input" + self._choice)
                elif self._choice == "3":
                    self._showTimeTablePage()
                elif self._choice == "4":
                    self._studentController.sendSelectedCoursesToApproval()
                elif self._choice == "b":
                    break
                elif self._choice == "q":
                    self._shouldQuit = False
                else:
                    raise Exception("Invalid input" + self._choice)
                    
            except Exception as e:
                print(e) #TODO: change to Util send feedback method
    
    def _showAddCoursePage(self):
        self._shouldQuit = True
        while(self._shouldQuit):
            #Util.clearScreen() #TODO: Add this
            print("\n\n")
            print(" Add Course\n" +
                  "**************\n")
            print("    %10s    %50s    %15s    %20s    %s\n", "Code", "Name", "Section", "Instructor", "Credit")
            print("    %10s    %50s    %15s    %20s    %s\n", "____", "____", "_______", "__________", "______")

            self._listAvailableCourseSections()
            
            print("Press b to go back\n")
            print("Press q to quit\n")
            

            self._choice = input("Enter the row number of the course you want to add : ")

            try:
                if self._choice == "b":
                    break

                elif self._choice == "q":
                    self._shouldQuit = False

                elif self._choice.isdigit(): #TODO: use util validate number method

                    if self._addCourse(self._choice):
                        print("Course added successfully") # TODO: Add color
                    else:
                        print("Course addition failed")

                else:
                    raise Exception("Invalid input" + self._choice)
                
            except Exception as e:
                print(e)
    def _showTimeTablePage(self):
        self._shouldQuit = True
        while(self._shouldQuit):
            #Util.clearScreen()
            #Util.printTimeTable(self._studentController.getStudentTimeTable()) #TODO: Add this
            print("\n\n press b to go back\n")
            print("Press q to quit\n")

            self._choice = input()

            try:
                if self._choice == "b":
                    break
                elif self._choice == "q":
                    self._shouldQuit = False
                else:
                    raise Exception("Invalid input" + self._choice)
            except Exception as e:
                print(e) #TODO: change to Util send feedback method
    
    def _addCourse(self, str):
        rowNumber = int(str) #TODO: use util get row num method
        selectedCourseSection = self._studentController.getAvaliableCourseSections().get(rowNumber-1)
        if self._studentController.addSelectedCourse( SelectedCourse( selectedCourseSection.findCourseOfCourseSection(), selectedCourseSection ) ):
            return True

        return False
    
    def _deleteCourse(self, str):
        rowNumber = int(str) #TODO: use util get row num method
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
                print(selectedCourse.getStatus() + "\n") #TODO: Add color
            elif selectedCourse.getStatus() == CourseStatus.PENDING:
                print(selectedCourse.getStatus() + "\n")
            elif selectedCourse.getStatus() == CourseStatus.DENIED:
                print(selectedCourse.getStatus() + "\n")
            else:
                print(selectedCourse.getStatus() + "\n")
            
            rowCount += 1
    