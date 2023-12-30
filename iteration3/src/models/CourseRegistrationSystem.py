from CommandLineInterface.CLILogin import CLILogin
from models.User import User
from utils.Util import Util

class CourseRegistrationSystem:

    def __init__(self):
        pass

    def start(self):
        user: User = None

        while True:
            # Create command line interface for login page and start it
            login = CLILogin()
            user = login.loginPage()

            Util.clearScreen()
            # Create command line interface for the first menu page and start it
            user.getMyPage()