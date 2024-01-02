from enum import Enum

class ApprovalStatus(Enum):
    DONE = "DONE"
    PENDING = "PENDING"

    @staticmethod
    def dictToEnum(dict):
        if dict == "DONE":
            return ApprovalStatus.DONE
        elif dict == "PENDING":
            return ApprovalStatus.PENDING
        else:
            return None