from utils.AuthenticateService import AuthenticateService
from models.User import User

class UserController:
    def __init__(self):
        self.currentUser = None

    def login(self, userId, password)->User:
        authenticateService = AuthenticateService()
        self.currentUser = authenticateService.authenticateUser(userId, password)
        return self.currentUser


    