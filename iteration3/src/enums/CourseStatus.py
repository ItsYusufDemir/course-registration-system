from enum import Enum

class CourseStatus(str, Enum):
    DRAFT = "DRAFT"
    PENDING = "PENDING"
    APPROVED = "APPROVED"
    DENIED = "DENIED"
    APPROVED_FINALIZED = "APPROVED_FINALIZED"
    DENIED_FINALIZED = "DENIED_FINALIZED"
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
        elif dict == "APPROVED_FINALIZED":
            return CourseStatus.APPROVED_FINALIZED
        elif dict == "DENIED_FINALIZED":
            return CourseStatus.DENIED_FINALIZED
        else:
            return None
