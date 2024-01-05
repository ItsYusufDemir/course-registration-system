from iteration3.src.interfaces.Color import Color
from iteration3.src.CommandLineInterface.CLILogin import CLILogin
from iteration3.src.models.User import User
from iteration3.src.utils.Util import Util

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
            Util.sendFeedback("Logged out successfully", Color.GREEN)