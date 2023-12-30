from controllers import UserController
from models import User
from utils import Util

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