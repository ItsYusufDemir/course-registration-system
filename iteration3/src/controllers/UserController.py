from iteration3.src.utils.AuthenticateService import AuthenticateService
from iteration3.src.models.User import User

class UserController:
    def __init__(self):
        self._currentUser = None

    def login(self, userId, password)->User:
        authenticateService = AuthenticateService()
        self._currentUser = authenticateService.authenticateUser(userId, password)
        return self._currentUser


    