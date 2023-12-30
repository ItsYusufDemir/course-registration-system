import os
import sys
from dotenv import load_dotenv

#set python path
load_dotenv()
sys.path.insert(0, os.getenv("PYTHONPATH"))

from iteration3.src.models.CourseRegistrationSystem import CourseRegistrationSystem
from iteration3.src.utils.Util import Util

class Main:
    @staticmethod
    def main():
        # Create the course registration system and start it
        courseRegistrationSystem = CourseRegistrationSystem()
        Util.getLogger().info("Course registration system starting.")
        courseRegistrationSystem.start()

if __name__ == "__main__":
    Main.main()
