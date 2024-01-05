import os

from iteration3.src.interfaces.Color import Color


class Util:
    
    def clearScreen():
        os.system('cls' if os.name == 'nt' else 'clear')

    def paintText(message : str, color: Color):
        print(color.value + message + Color.DEFAULT.value, end="", flush=True)
    
    def paintTextln(message : str, color: Color):
        print(color.value + message + Color.DEFAULT.value)

    def sendFeedback(message, color=None):
        if color is not None:
            print(color.value + message + Color.DEFAULT.value, end="", flush=True)
        else:
            print(message, end="", flush=True)
        Util.animationTimer(2)
        Util.clearScreen()

    def animationTimer(duration : int):
        import time
        for i in range(duration):
            Util.paintText(".", Color.YELLOW)
            time.sleep(1)

    def isValidNumber(string : str) -> int:
        try:
            return int(string)
        except ValueError:
            raise ValueError("Invalid number")

    def validateNumber(string : str, list: []):
        try:
            number = Util.isValidNumber(string)
        except ValueError:
            raise ValueError("Invalid number")
        if number is not None and 0 < number <= len(list):
            return True
        raise Exception("Number out of range")

    def makeArrayList(pattern: str, string: str)->[str]:
        return string.split(pattern)
    

    def isInputFormatTrueForDay(list:[str])->bool:
        valid_days = ["monday", "tuesday", "wednesday", "thursday", "friday"]

        for day in list:
            cleaned_day = day.strip().lower()
            if cleaned_day not in valid_days:
                raise Exception("Invalid day format")
        return True
    
    def isInputFormatTrueForTime(lst: [str]) -> bool:
        for time in lst:
            time = time.strip()

            if len(time) != 11:
                raise Exception("Invalid time format")

            if time[2] != ":" or time[5] != "-" or time[8] != ":":
                raise Exception("Invalid time format")

            timeList = time.split("-")

            if len(timeList) != 2:
                raise Exception("Only one dash is allowed in time")

            for timeHours in timeList:
                try:
                    hour = int(timeHours[0:2])
                    minute = int(timeHours[3:5])
                except ValueError:
                    raise Exception("Invalid time format")

                if not (0 <= hour <= 23) or not (0 <= minute <= 59):
                    raise Exception("Invalid time format")

        return True
    

    def divideIntoCourses(string:str)->[str]:
        courses = []
        temp = ""
        strLength = len(string)
        
        for i in range(strLength):
            if string[i] != "-":
                temp += string[i]
            else:
                courses.append(temp)
                temp = ""

        return courses
    
    def printTimeTable(time_table: [[str]]):

        times = ["08:30-09:20", "09:30-10:20", "10:30-11:20", "11:30-12:20", "12:30-13:20", "13:30-14:20",
                "14:30-15:20", "15:30-16:20"]
        
        print(" Timetable\n" +
                    "***********\n" +
                    " Hours/Days    Monday    Tuesday    Wednesday    Thursday    Friday\n" +
                    " __________    ______    _______    _________    ________    ______\n")
        
        for i in range(8):
            print("\n" + times[i] + "    ", end="")

            monCourses = Util.divideIntoCourses(time_table[0][i])
            tueCourses = Util.divideIntoCourses(time_table[1][i])
            wedCourses = Util.divideIntoCourses(time_table[2][i])
            thuCourses = Util.divideIntoCourses(time_table[3][i])
            friCourses = Util.divideIntoCourses(time_table[4][i])

            monCoursesSize = len(monCourses)
            tueCoursesSize = len(tueCourses)
            wedCoursesSize = len(wedCourses)
            thuCoursesSize = len(thuCourses)
            friCoursesSize = len(friCourses)
            j = 0
            while(j<monCoursesSize or j<tueCoursesSize or j<wedCoursesSize or j<thuCoursesSize or j<friCoursesSize ):
                if (monCoursesSize > j and monCourses[j] != ""):
                    if(monCoursesSize>1):
                        Util.paintText(monCourses[j] + " ", Color.RED)
                    else:
                        Util.paintText(monCourses[j] + " ", Color.DEFAULT)
                else:
                    print("           ", end="")

                if (tueCoursesSize > j and tueCourses[j] != ""):
                    if(tueCoursesSize>1):
                        Util.paintText(tueCourses[j] + "  ", Color.RED)
                    else:
                        Util.paintText(tueCourses[j] + "  ", Color.DEFAULT)
                else:
                    print("           ", end="")
                
                
                if (wedCoursesSize > j and wedCourses[j] != ""):
                    if(tueCoursesSize>1):
                        Util.paintText(wedCourses[j] + "    ", Color.RED)
                    else:
                        Util.paintText(wedCourses[j] + "    ", Color.DEFAULT)
                else:
                    print("             ", end="")

                if (thuCoursesSize > j and thuCourses[j] != ""):
                    if(thuCoursesSize>1):
                        Util.paintText(thuCourses[j] + "   ", Color.RED)
                    else:
                        Util.paintText(thuCourses[j] + "   ", Color.DEFAULT)
                else:
                    print("            ", end="")

                if (friCoursesSize > j and friCourses[j] != ""):
                    if(friCoursesSize>1):
                        Util.paintText(friCourses[j] + " ", Color.RED)
                    else:
                        Util.paintText(friCourses[j] + " ", Color.DEFAULT)
                else:
                    print("          ", end="")
                
                print("\n               ", end="")

                j += 1

            print("\n__________________________________________________________________________________________\n")

        

        
