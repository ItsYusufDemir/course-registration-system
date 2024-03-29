from iteration3.src.controllers.UserController import UserController
from iteration3.src.interfaces.Color import Color
from iteration3.src.models.User import User
from iteration3.src.utils.Util import Util

class CLILogin(object):
     
     def __init__(self):
        self._user = None


     def loginPage(self):
         while True:
            Util.clearScreen()
            print(" Login ")
            print(" ******** ")

            username = input("Username: ")
            password = input("Password: ")

            userController = UserController()
            self._user = userController.login(username, password)
            
            if self._user is None:
               Util.sendFeedback("Invalid username or password", Color.RED)

            else:
               break
            
         return self._user