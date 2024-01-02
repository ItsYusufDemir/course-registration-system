from enum import Enum

class CourseStatus(str, Enum):
    DRAFT = "DRAFT"
    PENDING = "PENDING"
    APPROVED = "APPROVED"
    DENIED = "DENIED"
    ACTIVE = "ACTIVE"

    @classmethod
    def dictToEnum(cls, dict):
        if dict == "DRAFT":
            return CourseStatus.DRAFT
        elif dict == "PENDING":
            return CourseStatus.PENDING
        elif dict == "APPROVED":
            return CourseStatus.APPROVED
        elif dict == "DENIED":
            return CourseStatus.DENIED
        elif dict == "ACTIVE":
            return CourseStatus.ACTIVE
        else:
            return None
