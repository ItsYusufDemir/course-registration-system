class CourseSection:

    def __init__(self, studentCapacity, lecturerName, sectionTime, sectionDay, classroom, sectionCode, studentCountInsideCourseSection):
        self._studentCapacity = studentCapacity
        self._lecturerName = lecturerName
        self._sectionTime = sectionTime
        self._sectionDay = sectionDay
        self._classroom = classroom
        self._sectionCode = sectionCode
        self._studentCountInsideCourseSection = studentCountInsideCourseSection

    def checkAvailability(self):
        if self._studentCapacity >= self._studentCountInsideCourseSection:
            return True
        else:
            return False

        