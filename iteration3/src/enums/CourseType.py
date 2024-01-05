from enum import Enum

class CourseType(str,Enum):
    COMPULSORY = "COMPULSORY"
    NONTECHNICAL_ELECTIVE = "NONTECHNICAL_ELECTIVE"
    TECHNICAL_ELECTIVE = "TECHNICAL_ELECTIVE"
    UNIVERSITY_ELECTIVE = "UNIVERSITY_ELECTIVE"
    FACULTY_ELECTIVE = "FACULTY_ELECTIVE"

    @staticmethod
    def dictToEnum(dict):
        if dict == "COMPULSORY":
            return CourseType.COMPULSORY
        elif dict == "NONTECHNICAL_ELECTIVE":
            return CourseType.NONTECHNICAL_ELECTIVE
        elif dict == "TECHNICAL_ELECTIVE":
            return CourseType.TECHNICAL_ELECTIVE
        elif dict == "UNIVERSITY_ELECTIVE":
            return CourseType.UNIVERSITY_ELECTIVE
        elif dict == "FACULTY_ELECTIVE":
            return CourseType.FACULTY_ELECTIVE
        else:
            return None