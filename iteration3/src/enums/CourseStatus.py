from enum import Enum

class CourseStatus(Enum):
    DRAFT = 1
    PENDING = 2
    APPROVED = 3
    DENIED = 4
    ACTIVE = 5