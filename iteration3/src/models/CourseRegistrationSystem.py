from iteration3.src.CommandLineInterface.CLILogin import CLILogin
from iteration3.src.utils.Util import Util

class CourseRegistrationSystem:

    def start(self):
        user = None

        while True:
            # Create command line interface for login page and start it
            login = CLILogin()
            user = login.loginPage()

            Util.clearScreen()
            # Create command line interface for the first menu page and start it
            user.getMyPage()