from controllers.AdvisorController import AdvisorController
from enums.CourseStatus import CourseStatus
from interfaces.Color import Color
from models.SelectedCourse import SelectedCourse
from models.Student import Student
from utils.Util import Util
from models.Constraint import Constraint

class CLIAdvisor(object):

     def __init__(self, advisorController):
        self._advisorController = advisorController


     def menuPage(self):
          while True:

               Util.clearScreen()

               if self._advisorController.getNotifications() is not None:
                    Util.paintTextln(" Notifications\n " +
                                "**************", Color.YELLOW)
                    for string in self._advisorController.getNotifications():
                         Util.paintTextln(string, Color.RED)
                    self._advisorController.clearNotifications()

               print("\n\n")
               print(" Menu ")
               print("********")
               print("    1. Student List")
               print("    2. Log out")
               
               
               shouldQuit = False

               choice: str = input()

               if choice == "1":
                    students = self._advisorController.getStudentListOrderByStatus()
                    Util.clearScreen()
                    shouldQuit = self._showStudentPage(students)

                    if(shouldQuit):
                         break
                    else:
                         continue
               elif choice == "2":
                    self._advisorController.logOut()
                    break
               else:
                    Util.sendFeedback("Invalid choice", Color.RED)
                    isInvalid = True

     def _showStudentPage(self,students):
          isInvalid = False
          while True:
               
               print("Student List")
               print("************")
               print(f"{('Number'):>10}    {('Name'):>30}")
                    
               for i in range(len(students)):
                    print(f"{i + 1}  {students[i].getUserId():>10}    {students[i].getFirstName() + ' '  + students[i].getLastName():>30}")
               
               
               print("\nPress b to back")
               print("Press q to quit\n")   
               print("Select Student:")       
               
               selectedCoursesThatStatusIsPending = []

               choice = input()

               try: 
                    if(choice == "b"):
                         return False
                    elif(choice == "q"):
                         self._advisorController.logOut()
                         return True
                    elif(Util.validateNumber(choice, students)):
                         for i in range(len(students[int(choice)-1].getSelectedCourses())):
                                   if((students[int(choice)-1].getSelectedCourses())[i].getStatus() == CourseStatus.PENDING):
                                        selectedCoursesThatStatusIsPending.append((students[int(choice)-1].getSelectedCourses())[i])
                              
                         shouldQuit = self._coursesOfStudentPage(students[int(choice)-1])         
                         if(shouldQuit):
                              return True
                    
                    else:
                         Util.sendFeedback("Invalid choice. Try again.",Color.RED)
                         isInvalid = True
               except Exception as e:
                    Util.sendFeedback(str(e), Color.RED)
   

     def _coursesOfStudentPage(self,student):

          isInvalid = False    
          courses = student.fetchSelectedCoursesForAdvisor()
          isFinalizable = False
          
          while True:
               
               Util.clearScreen()

               print(" Student: " + student.getFirstName() + " " + student.getLastName() + "'s TimeTable \n")
               Util.printTimeTable(student.createTimeTable())

               print("\n\n Courses Of The Student ")
               print("***************************")

               print(f"    {'Code':>10}    {'Name':>50}    {'Section':>15}    {'Status':>15}")
               print(f"    {'-----':>10}    {'------':>50}    {'--------':>15}    {'------':>15}")

               for i in range(len(courses)):
                    print(f"{i+1}  {courses[i].getCourse().getCourseCode():>10}    {courses[i].getCourse().getCourseName():>50}    {courses[i].getCourseSection().getSectionCode():>15}   " , end="           ")

                    if courses[i].getStatus() == CourseStatus.APPROVED:
                         Util.paintText(courses[i].getStatus() + "\n", Color.GREEN)
                    elif courses[i].getStatus() == CourseStatus.PENDING:
                         Util.paintText(courses[i].getStatus() + "\n", Color.YELLOW)
                    elif courses[i].getStatus() == CourseStatus.DENIED:
                         Util.paintText(courses[i].getStatus() + "\n", Color.RED)
                    elif courses[i].getStatus() == CourseStatus.APPROVED_FINALIZED:
                         Util.paintText("APPROVED FINALIZED" + "\n", Color.GREEN)
                    elif courses[i].getStatus() == CourseStatus.DENIED_FINALIZED:
                         Util.paintText("DENIED FINALIZED" + "\n", Color.RED)
                    else:
                         print(courses[i].getStatus() + "\n")

               print("\nPress s to show timetable")
               print("Press f to finalize registration")
               print("Press b to back")
               print("Press q to quit\n")
               print("Select Course: ")
              
              

              
               choice = input()

               try:
                    if choice == "b":
                         return False
                    elif choice=="q":
                         self._advisorController.logOut()
                         return True
                    elif choice == "f":
                         if(len(student.getSelectedCourses()) == 0):
                              print("Student has not selected any course yet. Are you sure to finalize registration? (y/n)")
                              choice2 = input()
                              if choice2 == "y":
                                   self._advisorController.finalizeRegistration(student)
                                   Util.sendFeedback("Registration is finalized.", Color.GREEN)
                              elif choice2 == "n":
                                   continue
                              else:
                                   raise Exception("Invalid choice")
                         else:
                              self._advisorController.finalizeRegistration(student)
                              Util.sendFeedback("Registration is finalized.", Color.GREEN)
                    elif choice == "s":
                         Util.printTimeTable(student.createTimeTable())
                    elif(Util.validateNumber(choice, courses)):
                              print("Press a to approve")
                              print("Press d to deny")
                         
                              choice2 = input()

                              if choice2 == "a" :                                        
                                        self._advisorController.approveCourse(student, courses[int(choice) - 1])
                                        Util.sendFeedback("Course is approved", Color.GREEN)
                              elif choice2 == "d":                                   
                                             self._advisorController.denyCourse(student, courses[int(choice) - 1])
                                             Util.sendFeedback("Course is denied", Color.GREEN)
                              else:
                                   raise Exception("Invalid choice. Try again")    
                    else:
                         raise Exception("Invalid choice. Try again")
               except Exception as e:
                    Util.sendFeedback(str(e), Color.RED)
                    