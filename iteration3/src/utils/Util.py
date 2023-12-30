import os

from interfaces.Color import Color


class Util:
    
    def clearScreen():
        os.system('cls' if os.name == 'nt' else 'clear')

    def paintText(message : str, color: Color):
        print(color.value + message + Color.DEFAULT.value, end="")
    
    def paintTextln(message : str, color: Color):
        print(color.value + message + Color.DEFAULT.value)

    def sendFeedback(message : str, color: Color):
        print(color.value + message + Color.DEFAULT.value)
        Util.animationTimer(3)
        Util.clearScreen()

    def sendFeedback(message : str):
        print(message)
        Util.animationTimer(3)

        Util.clearScreen()


    def animationTimer(time : int):
        import time
        for i in range(time):
            Util.paintTextln(".", Color.YELLOW)
            time.sleep(1)

    def isValidNumber(string : str) -> int:
        try:
            return int(string)
        except ValueError:
            raise("Invalid Input - Expecting a number between 0-9")
        

    def validateNumber(string : str, list: []):
        if Util.isValidNumber(string) in range(0, len(list)):
            return True
        return False

    def makeArrayList(pattern: str, string: str)->[str]:
        return string.split(pattern)
    

    def isInputFormatTrueForDay(list:[str])->bool:
        for(day) in list:
            if day not in ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]:
                return False
        return True
    
    def isInputFormatTrueForTime(list:[str])->bool:
        for(time) in list:
            if time != 11:
                return False
            
            if time[2] != ":" or time[5] != "-" or time[8] != ":":
                return False
            
            timeList = time.split("-")
            if len(timeList) != 2:
                return False
            
            for(timeHours) in timeList:
                if timeHours[0:2] not in range(0, 24) or timeHours[3:5] not in range(0, 60):
                    return False
                
        return True
    

    def divideIntoCourses(string:str)->[str]:
        list = string.split("-")
        # if a empty string is present in the list, remove the comment below
        # list.remove("")
        return list
    
    def printTimeTable(time_table: [[str]]):
        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]

        times = ["08:30-09:20", "09:30-10:20", "10:30-11:20", "11:30-12:20", "12:30-13:20", "13:30-14:20",
                "14:30-15:20", "15:30-16:20"]

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
