class Prerequisite:
    def __init__ (self, prerequisiteOfCourses):
        self.prerequisiteOfCourses = prerequisiteOfCourses

    @classmethod
    def dictToObject(cls, dict):
        from models.Course import Course
        return cls(
            [Course.dictToObject(course) for course in dict['prerequisiteOfCourses']]
        )

    def checkPrerequisiteCoursePassed(self, student, course):
        isPrerequisiteCoursesPassed = []
        
        for prerequisitecourse in course.getPrerequisiteInformation().prerequisiteOfCourses:
            for passedCourse in student.getTranscript().acquirePassedCourses():
                isPrerequisiteCoursesPassed.append(prerequisitecourse.getCourseCode() == passedCourse.getCourseCode())
            if self._isContainTrue(isPrerequisiteCoursesPassed) == False:
                return False
        return True # if all prerequisite courses passed
            
   
   
    def _isContainTrue(list):
        for i in list:
            if i:
                return True # if there is any true value then return true
        return False # if there is no any true value inside the list


    def getPrerequisiteOfCourses(self):
        return self.prerequisiteOfCourses