from controllers.AdvisorController import AdvisorController
from enums.CourseStatus import CourseStatus
from interfaces.Color import Color
from models.SelectedCourse import SelectedCourse
from models.Student import Student
from utils.Util import Util

class CLIAdvisor(object):

    def __init__(self, advisor, advisorController):
        self._advisor = advisor
        self._advisorController = advisorController


    def menuPage(self):
       isInvalid = False
       while True:
           Util.clearScreen()
           if self._advisorController.getNotification() != None and len(self. _advisorController.getNotification()) > 0:
                 Util.paintTextln(" Notifications\n " +
                        "**************", Color.YELLOW)
           
           for string in  self._advisorController.getNotification():
                 Util.paintTextln(string, Color.YELLOW)
                 self._advisorController.clearNotifications()

           print("\n\n")

           if not isInvalid:
                isInvalid = False
                print(""" Menu 
                         ********
                                1. StudentList
                                2.Log out """)    
           else:
                isInvalid = False
            
           shouldQuit = False

           choice = input()

           if choice == 1:
                students = self._advisorController.getStudentListOrderByStatus()
                shouldQuit = self.showStudentPage(students)

                if(shouldQuit):
                     break
                else:
                     continue
           elif choice == 2:
                self._advisorController.logOut()
                break
           else:
                Util.sendFeedback("Invalid choice", Color.RED)
                isInvalid = True

    def showStudentPage(self,students):
         isInvalid = False
         while True:
              if not isInvalid:
                   isInvalid = False
                   print(""" Student List
                            **************** """)
                   print(f"{('Number'):>10}    {('Name'):>30}")
                   
                   for i in range(len(students)):
                         print(f"{i}  {students[i].getUserId():>10}    {students[i].getFirstName() + ' '  + students[i].getLastName():>30}")
             
                   print("Select Student:")
                   print("Press b to back")
                   print("Press q to quit")
              else:
                   isInvalid = False

              selectedCoursesThatStatusIsPending = []

              choice = input()

              
              choice2 = input()
              if(self.check_integer(choice2)):
                 if(choice2 > 0 and choice2 < len(students)):
                      for i in len(students[choice2-1].getSelectedCourses()):
                           if((students[choice2-1].getSelectedCourses())[i].getStatus() == CourseStatus.PENDING):
                                selectedCoursesThatStatusIsPending.append((students[choice2-1].getSelectedCourses())[i])
                     
                      shouldQuit = self.coursesOfStudentPage(students[choice2-1])         
                      if(shouldQuit):
                           return True
                 else:
                      Util.sendFeedback("Invalid choice. Try again.",Color.RED)
                      isInvalid = True
              
              
              else:
                   if(choice == "b"):
                        return False
                   elif(choice == "q"):
                        self._advisorController.logOut()
                        return True
                   else:
                        Util.sendFeedback("Invalid choice. Try again.",Color.RED)
                        isInvalid = True
   
              
    def check_integer(input_string):
       try:
             integer_value = int(input_string)
             return True

       except ValueError:
            return False   



    def coursesOfStudentPage(self,student):

        isInvalid = False    
        courses = student.fetchSelectedCoursesForAdvisor();
        
        while True:
           if not isInvalid:
                 isInvalid = False
                 Util.clearScreen()

                 print(" Student: " + student.getFirstName() + " " + student.getLastName() + "'s TimeTable \n")
                 Util.printTimeTable(student.createTimeTable())

                 print(" Courses Of The Student ")
                 print("***************************")

                 print(f"    {'Code':>10}    {'Name':>50}    {'Section':>15}    {'Status':>15}")
                 print(f"    {'-----':>10}    {'------':>50}    {'--------':>15}    {'------':>15}")
                 
                 for i in len(courses):
                         print(f"{i}  {courses[i].getCourse().getCourseCode():>10}    {courses[i].getCourse().getCourseName():>50}    {courses[i].getCourse().getSectionCode():>15}")

                         if(courses[i].getStatus == CourseStatus.APPROVED):
                              Util.paintText(courses[i].getStatus() + "\n", Color.GREEN )
                         elif(courses[i].getStatus() == CourseStatus.PENDING):
                             Util.paintText(courses[i].getStatus() + "\n", Color.YELLOW)
                         elif (courses[i].getStatus() == CourseStatus.DENIED):
                             Util.paintText(courses[i].getStatus() + "\n", Color.RED)
                         else:
                             print(courses[i].getStatus() + "\n");          


                 print("Press b to back")
                 print("Press q to quit")
                 print("Select Course: ")
            
           else:
             isInvalid = False

           choice = input()    
           if(self.check_integer(choice)):
                if(choice > 0 and choice < len(courses)):
                    print("Press a to approve")
                    print("Press d to deny")
                
                    choice2 = input()

                    if choice2 == "a":
                        self._advisorController.approveCourse(student, courses[choice - 1])
                        print("\n")
                    elif choice2 == "d":
                        self._advisorController.denyCourse(student, courses[choice - 1])
                    elif choice == "b":
                        return False
                    elif choice == "q":
                        self._advisorController.logOut()
                        return True
                    else:
                        Util.sendFeedback("Invalid choice. Try again", Color.RED)
                        isInvalid = True
                    
                else:
                    Util.sendFeedback("Invalid choice!", Color.RED)
                
           else: 
                if choice == "b":
                    return False
                elif choice=="q":
                    self._advisorController.logOut()
                    return True
                else:
                    Util.sendFeedback("Invalid choice. Try again", Color.RED)
                    isInvalid = True 