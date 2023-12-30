import logging
from utils.DatabaseManager import DatabaseManager
from models.User import User

class AuthenticateService(object):
    
    def authenticateUser(self, userID, password):
        

        databaseManager = DatabaseManager.getInstance()
        users: [User] = []
        users.extend(databaseManager.getAdmins())
        users.extend(databaseManager.getAdvisors())
        users.extend(databaseManager.getStudents())

        for user in users:
              if user.checkCredentials(userID, password):
                   logging.log(logging.INFO, "User: "+ userID +" logged in")
                   return user
        
        logging.log(logging.INFO, "User: "+ userID +" faild to log in")
        return None
