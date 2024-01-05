import logging
from models.CourseRegistrationSystem import CourseRegistrationSystem

logging.basicConfig(filename='./iteration3/logs/logs.log', level=logging.DEBUG, format='%(asctime)s - %(levelname)s - %(message)s')


# Create the course registration system and start it
courseRegistrationSystem = CourseRegistrationSystem()
courseRegistrationSystem.start()
