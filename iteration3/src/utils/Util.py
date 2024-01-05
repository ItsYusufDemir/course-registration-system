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
        list = string.split("-")
        # if a empty string is present in the list, remove the comment below
        # list.remove("")
        return list
    
    def printTimeTable(time_table: [[str]]):
        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]

        times = ["08:30-09:20", "09:30-10:20", "10:30-11:20", "11:30-12:20", "13:00-13:50", "14:00-14:50",
                "15:00-15:50", "16:00-16:50"]

        for i, current_time in enumerate(times):
            print()
            print(f"{current_time}    ", end="")

            for day in days:
                courses = Util.divideIntoCourses(time_table[days.index(day)][i])
                courses_size = len(courses)

                if courses_size > 0:
                    if courses_size > 1:
                        Util.paintText(f"{courses[0]} ", Color.RED)
                    else:
                        print(f"{courses[0]} ", end="")
                else:
                    print("          ", end="")

                print("  ", end="")

            print("\n               ")
