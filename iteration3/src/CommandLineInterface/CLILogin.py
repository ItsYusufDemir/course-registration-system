from src.controllers.UserController import UserController
from src.models.User import User
from src.utils.Util import Util

class CLILogin(object):
     
     def __init__(self,user):
        self._user = user


     def loginPage(self):
        while True:
           Util.clearScreen()
           print(" Login ")
           print(" ******** ")

           username = input("Username: ")
           password = input("Password: ")

           userController = UserController()
           self.user = userController.login(username, password)
       
           if self._user is None:
             print("Invalid username or password.")

           else:
              break
           
           return self._user