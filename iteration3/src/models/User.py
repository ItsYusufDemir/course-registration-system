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

    def getUserId(self):
        return self.userId
    
    def getNotifications(self):
        return self.notifications
    
    def addNotification(self, notification):
        self.notifications.append(notification)