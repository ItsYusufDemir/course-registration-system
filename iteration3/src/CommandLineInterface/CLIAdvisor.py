from controllers.AdvisorController import AdvisorController
from enums.CourseStatus import CourseStatus
from interfaces.Color import Color
from models.SelectedCourse import SelectedCourse
from models.Student import Student
from utils.Util import Util

class CLIAdvisor(object):

     def __init__(self, advisorController):
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
               print(" Menu ")
               print("********")
               print("    1. Student List")
               print("    2. Log out")
               
               
               shouldQuit = False

               choice: str = input()

               if choice == "1":
                    students = self._advisorController.getStudentListOrderByStatus()
                    Util.clearScreen()
                    shouldQuit = self.showStudentPage(students)

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

     def showStudentPage(self,students):
          isInvalid = False
          while True:
               
               print("Student List")
               print("************")
               print(f"{('Number'):>10}    {('Name'):>30}")
                    
               for i in range(len(students)):
                    print(f"{i + 1}  {students[i].getUserId():>10}    {students[i].getFirstName() + ' '  + students[i].getLastName():>30}")
               
               print("\nSelect Student:")
               print("Press b to back")
               print("Press q to quit")          
               
               selectedCoursesThatStatusIsPending = []

               choice = input()
               if(Util.validateNumber(choice, students)):
                    for i in range(len(students[int(choice)-1].getSelectedCourses())):
                              if((students[int(choice)-1].getSelectedCourses())[i].getStatus() == CourseStatus.PENDING):
                                   selectedCoursesThatStatusIsPending.append((students[int(choice)-1].getSelectedCourses())[i])
                         
                    shouldQuit = self.coursesOfStudentPage(students[int(choice)-1])         
                    if(shouldQuit):
                         return True
               else:
                    if(choice == "b"):
                         return False
                    elif(choice == "q"):
                         self._advisorController.logOut()
                         return True
                    else:
                         Util.sendFeedback("Invalid choice. Try again.",Color.RED)
                         isInvalid = True
   

     def coursesOfStudentPage(self,student):

          isInvalid = False    
          courses = student.fetchSelectedCoursesForAdvisor()
          
          while True:
               
               Util.clearScreen()

               print(" Student: " + student.getFirstName() + " " + student.getLastName() + "'s TimeTable \n")
               Util.printTimeTable(student.createTimeTable())

               print(" Courses Of The Student ")
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
                    else:
                         print(courses[i].getStatus() + "\n")

               print("\nPress b to back")
               print("Press q to quit")
               print("Select Course: ")

               choice = input()    
               
               if(Util.validateNumber(choice, courses)):
                         print("Press a to approve")
                         print("Press d to deny")
                    
                         choice2 = input()

                         if choice2 == "a":
                              result = self._advisorController.approveCourse(student, courses[int(choice) - 1])
                              #TODO: throw error, and catch it here
                              if result:
                                   Util.sendFeedback("Course is approved", Color.GREEN)
                              else:
                                   Util.sendFeedback("Course is not approved", Color.RED)
                         elif choice2 == "d":
                              result = self._advisorController.denyCourse(student, courses[int(choice) - 1])
                              if result:
                                   Util.sendFeedback("Course is denied", Color.GREEN)
                              else:
                                   Util.sendFeedback("Course is not denied", Color.RED)
                         elif choice == "b":
                              return False
                         elif choice == "q":
                              self._advisorController.logOut()
                              return True
                         else:
                              Util.sendFeedback("Invalid choice. Try again", Color.RED)
                              isInvalid = True   
               else: 
                    if choice == "b":
                         return False
                    elif choice=="q":
                         self._advisorController.logOut()
                         return True
                    else:
                         Util.sendFeedback("Invalid choice. Try again", Color.RED)
                         isInvalid = True 