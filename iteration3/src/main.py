import os
import sys
from dotenv import load_dotenv

#set python path
load_dotenv()
sys.path.insert(0, os.getenv("PYTHONPATH"))

from iteration3.src.utils.DatabaseManager import DatabaseManager

print(DatabaseManager.getInstance().constraintList[0].minRequiredECTSForTermProject)
DatabaseManager.getInstance().constraintList[0].minRequiredECTSForTermProject = 32
DatabaseManager.getInstance().saveConstraintsList()
print("saved to db")