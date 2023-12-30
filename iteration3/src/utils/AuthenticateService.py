import logging
from src.utils.DatabaseManager import DatabaseManager

class AuthenticateService(object):
    
    def authenticateUser(self, userID, password):
        self._userID = userID
        self._password = password

        self.databaseManager = DatabaseManager.getInstance()
        self.users = list()
        self.users.append(self.databaseManager.getAdmins())
        self.users.append(self.databaseManager.getAdvisors())
        self.users.append(self.databaseManager.getStudents())

        for self.user in self.users:
              if self.user.checkCredentials(self.userID, self.password):
                   logging.log(logging.INFO, "User: "+self.userId+" logged in")
                   return self.user
        
        logging.log(logging.INFO, "User: "+self.userId+" faild to log in")
        return None
