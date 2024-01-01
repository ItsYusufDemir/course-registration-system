from enum import Enum

class ApprovalStatus(str,Enum):
    DONE = "DONE"
    PENDING = "PENDING"
