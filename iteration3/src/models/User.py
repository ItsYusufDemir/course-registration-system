import logging
from iteration3.src.interfaces.Color import Color
from iteration3.src.interfaces.Showable import Showable
from iteration3.src.utils.DatabaseManager import DatabaseManager
from iteration3.src.utils.Util import Util

class User(Showable):
    def __init__(self, userId, password, firstName, lastName, status, notifications):
        self.userId = userId
        self.password = password
        self.firstName = firstName
        self.lastName = lastName
        self.status = status
        self.notifications = notifications

    @classmethod
    def dictToObject(self, dict):
        return User(
            dict['userId'],
            dict['password'],
            dict['firstName'],
            dict['lastName'],
            dict['status'],
            dict['notifications']
        )

    def logout(self):
        Util.sendFeedback("You have been logged out.", Color.GREEN)
        logging.log(logging.INFO, "User: "+self.userId+" logged out")
        DatabaseManager().getInstance().saveToDatabase()
   
    def checkCredentials(self, userId, password)->bool:
        if self.userId == userId and self.password == password:
            return True
        else:
            return False
    
    def clearNotifications(self):
        self.notifications = []
        logging.log(logging.INFO, "Notifications seen for user: "+self.userId)

    def getUserId(self):
        return self.userId
    
    def getNotifications(self):
        return self.notifications
    
    def addNotification(self, notification):
        self.notifications.append(notification)