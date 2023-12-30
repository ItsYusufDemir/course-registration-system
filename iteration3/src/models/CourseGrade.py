#beyza
class CourseGrade:
    def __init__(self, course, letterGrade, courseResult):
        self.course = course
        self.letterGrade = letterGrade
        self.courseResult = courseResult
        
    def convertLetterGradeToScore(self):
        match self.letterGrade:
            case "AA":
                score = 4.0
            case "BA":
                score = 3.5
            case "BB":
                score = 3.0
            case "CB":
                score = 2.5
            case "CC":
                score = 2.0
            case "DC":
                score = 1.5
            case "DD":
                score = 1.0
            case "FD":
                score = 0.5
            case "FF":
                score = 0.0
            case _:
                score = 0.0      
        return score
                

        
    