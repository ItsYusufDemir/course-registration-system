from enum import Enum

class CourseResult(str,Enum):
    PASSED = "PASSED"
    FAILED = "FAILED"
    ACTIVE = "ACTIVE"

    @staticmethod
    def dictToEnum(dict):
        if dict == "PASSED":
            return CourseResult.PASSED
        elif dict == "FAILED":
            return CourseResult.FAILED
        elif dict == "ACTIVE":
            return CourseResult.ACTIVE
        else:
            return None
