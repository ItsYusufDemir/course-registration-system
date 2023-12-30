import logging
from interfaces.Showable import Showable

class User(Showable):
    def __init__(self, userId, password, firstName, lastName, status, notifications):
        self.userId = userId
        self.password = password
        self.firstName = firstName
        self.lastName = lastName
        self.status = status
        self.notifications = notifications

    def logout(self):
        print("User logged out")
        logging.log(logging.INFO, "User: "+self.userId+" logged out")
   
    def checkCredentials(self, userId, password)->bool:
        if self.userId == userId and self.password == password:
            return True
        else:
            return False
    
    def clearNotifications(self):
        self.notifications = []
        logging.log(logging.INFO, "Notifications seen for user: "+self.userId)