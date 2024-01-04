from enum import Enum

class ApprovalStatus(Enum):
    DONE = "DONE"
    PENDING = "PENDING"
    FINALIZED_REGISTRATION = "FINALIZED_REGISTRATION"


    @staticmethod
    def dictToEnum(dict):
        if dict == "DONE":
            return ApprovalStatus.DONE
        elif dict == "PENDING":
            return ApprovalStatus.PENDING
        elif dict == ApprovalStatus.FINALIZED_REGISTRATION:
            return ApprovalStatus.FINALIZED_REGISTRATION
        else:
            return None