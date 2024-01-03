import logging
from utils.DatabaseManager import DatabaseManager
from models.User import User

class AuthenticateService(object):
    
    def authenticateUser(self, userID, password):
        
        databaseManager = DatabaseManager.getInstance()
        users: [User] = []
        users.extend(databaseManager.getAdminList())
        users.extend(databaseManager.getAdvisorList())
        users.extend(databaseManager.getStudentList())

        for user in users:
              if user.checkCredentials(userID, password):
                   logging.log(logging.INFO, "User: "+ userID +" logged in")
                   return user
        
        logging.log(logging.WARNING, "User: "+ userID +" faild to log in")
        return None
