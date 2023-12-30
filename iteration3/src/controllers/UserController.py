from iteration3.src.utils.AuthenticateService import AuthenticateService

class UserController:
    def __init__(self):
        self.currentUser = None

    def login(self, userId, password):
        authenticateService = AuthenticateService()
        return authenticateService.authenticateUser(userId, password)


    