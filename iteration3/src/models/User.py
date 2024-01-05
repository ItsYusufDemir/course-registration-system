import logging
from iteration3.src.interfaces.Color import Color
from iteration3.src.interfaces.Showable import Showable
from iteration3.src.utils.DatabaseManager import DatabaseManager
from iteration3.src.utils.Util import Util

class User(Showable):
    def __init__(self, userId, password, firstName, lastName, status, notifications):
        self._userId = userId
        self._password = password
        self._firstName = firstName
        self._lastName = lastName
        self._status = status
        self._notifications = notifications

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
        logging.log(logging.INFO, "User: "+self._userId+" logged out")
        DatabaseManager().getInstance().saveToDatabase()
   
    def checkCredentials(self, userId, password)->bool:
        if self._userId == userId and self._password == password:
            return True
        else:
            return False
    
    def clearNotifications(self):
        self._notifications = []
        logging.log(logging.INFO, "Notifications seen for user: "+self._userId)

    def getUserId(self):
        return self._userId
    
    def getNotifications(self):
        return self._notifications
    
    def addNotification(self, notification):
        self._notifications.append(notification)