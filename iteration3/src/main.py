
from dotenv import load_dotenv
import os
import sys

load_dotenv()
sys.path.append(os.getenv("ROOT_PATH"))
import logging
from iteration3.src.models.CourseRegistrationSystem import CourseRegistrationSystem

logging.basicConfig(filename='./iteration3/logs/logs.log', level=logging.DEBUG, format='%(asctime)s - %(levelname)s - %(message)s')


# Create the course registration system and start it
courseRegistrationSystem = CourseRegistrationSystem()
courseRegistrationSystem.start()
