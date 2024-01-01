from enum import Enum

class CourseResult(str,Enum):
    PASSED = "PASSED"
    FAILED = "FAILED"
    ACTIVE = "ACTIVE"
