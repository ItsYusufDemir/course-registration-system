package iteration2.src.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import iteration2.src.interfaces.Color;

public class Util {

    private static Logger logger;

    public static void initLogger() {
        LogManager logManager = LogManager.getLogManager();
        try {
            FileInputStream fis = new FileInputStream("iteration2/logging.properties");
            logManager.readConfiguration(fis);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void clearScreen() {
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
        /*
         * try{
         * final String os = System.getProperty("os.name");
         * if (os.contains("Windows") || os.contains("windows")){
         * Runtime.getRuntime().exec("clr");
         * }else {
         * Runtime.getRuntime().exec("clear");
         * }
         * }catch (Exception e){
         * e.printStackTrace();
         * }
         */

        System.out.print("\033\143");
    }

    public static void paintText(String message, String color) {
        System.out.print(color + message + Color.DEFAULT);
    }

    public static void paintTextln(String message, String color) {
        System.out.println(color + message + Color.DEFAULT);
    }

    public static void sendFeedback(String message, String color) {
        paintTextln(message, color);
        animationTimer(1);
        clearScreen();
    }

    public static void sendFeedback(String message) {
        System.out.println(message);
        animationTimer(1);
        clearScreen();
    }

    public static String getRowNumberFromInput(String str) {

        if (str.contains(" ")) {
            return str.substring(0, str.indexOf(" "));
        } else if (str.contains(",")) {
            return str.substring(0, str.indexOf(","));
        } else if (str.contains(".")) {
            return str.substring(0, str.indexOf("."));
        } else {

            return str;
        }

    }

    public static boolean validateNumber(String str, Object[] list) {
        if (isValidNumber(str)) {
            if (checkIfValidRowNumber(str, list)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidNumber(String str) {
        int strLength = getRowNumberFromInput(str).length();
        for (int i = 0; i < strLength; i++) {
            if (str.charAt(i) < 48 || str.charAt(i) > 57) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkIfValidRowNumber(String rowNumber, Object[] list) {
        int rowNumberInt = Integer.parseInt(getRowNumberFromInput(rowNumber));
        if (rowNumberInt > list.length || rowNumberInt < 1) {
            return false;
        }
        return true;
    }

    public static ArrayList<String> makeArrayList(String splitPattern, String textToSplit) {
        String[] splitArray = textToSplit.split(splitPattern);
        ArrayList<String> newlist = new ArrayList<String>(Arrays.asList(splitArray));
        for (int i = 0; i < newlist.size(); i++) {
            newlist.set(i, newlist.get(i).trim());
        }
        return newlist;
    }

    public static boolean isInputFormatTrueForDay(ArrayList<String> list) {
        int length = list.size();
        for (int i = 0; i < length; i++) {
            if (!("Monday".equalsIgnoreCase(list.get(i)) ||
                    "Tuesday".equalsIgnoreCase(list.get(i)) ||
                    "Wednesday".equalsIgnoreCase(list.get(i)) ||
                    "Thursday".equalsIgnoreCase(list.get(i)) ||
                    "Friday".equalsIgnoreCase(list.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInputFormatTrueForTime(ArrayList<String> list) { // ["08:30-09:20", "09:30-10:30",
                                                                             // "08:-09:20"]
        int length = list.size();
        int hour;
        int minute;
        String[] timeComponentList;
        String[] timeList;

        for (int i = 0; i < length; i++) {

            if (list.get(i).length() != 11) {
                return false;
            }

            if (!(list.get(i).charAt(2) == ':' && list.get(i).charAt(5) == '-' && list.get(i).charAt(8) == ':')) { // format
                                                                                                                   // control
                return false;
            }
            timeList = list.get(i).split("-"); // ["08:30", "09:20]

            if (timeList.length > 2) {
                return false;
            }

            for (int j = 0; j < 2; j++) {
                timeComponentList = timeList[j].split(":");
                try {
                    hour = Integer.parseInt(timeComponentList[0]);
                    minute = Integer.parseInt(timeComponentList[1]);
                } catch (Exception e) {
                    return false;
                }
                if (0 > hour || hour > 23) {
                    return false;
                }

                if (0 > minute || minute > 59) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void animationTimer(int duration) {

        paintTextln("3", Color.RED);
        try {
            Thread.sleep(duration * 1000);
        } catch (Exception e) {
            paintTextln("Thread sleep error", Color.RED);
        }

        paintTextln("2", Color.RED);
        try {
            Thread.sleep(duration * 1000);
        } catch (Exception e) {
            paintTextln("Thread sleep error", Color.RED);
        }

        paintTextln("1", Color.RED);
        try {
            Thread.sleep(duration * 1000);
        } catch (Exception e) {
            paintTextln("Thread sleep error", Color.RED);
        }

    }

    public static void printTimeTable(String timeTable[][]) {

        List<String> monCourses;
        List<String> tueCourses;
        List<String> wedCourses;
        List<String> thrCourses;
        List<String> friCourses;

        String[] times = { "08:30-09:20", "09:30-10:20", "10:30-11:20", "11:30-12:20", "12:30-13:20", "13:30-14:20",
                "14:30-15:20", "15:30-16:20" };

        for (int i = 0; i < 8; i++) { // 8 is how many hours we have as an option
            System.out.println();
            System.out.print(times[i] + "    "); // pritns the time

            // getting the courses for each day at the current hour we are printing
            monCourses = divideIntoCourses(timeTable[0][i]);
            tueCourses = divideIntoCourses(timeTable[1][i]);
            wedCourses = divideIntoCourses(timeTable[2][i]);
            thrCourses = divideIntoCourses(timeTable[3][i]);
            friCourses = divideIntoCourses(timeTable[4][i]);

            int monCoursesSize = monCourses.size();
            int tueCoursesSize = tueCourses.size();
            int wedCoursesSize = wedCourses.size();
            int thrCoursesSize = thrCourses.size();
            int friCoursesSize = friCourses.size();
            // traversing through the days and printing the courses line by line until all
            // of the courses are printed
            for (int j = 0; j < monCoursesSize || j < tueCoursesSize || j < wedCoursesSize || j < thrCoursesSize
                    || j < friCoursesSize; j++) {

                if (monCoursesSize > j && monCourses.get(j) != "") {
                    if (monCoursesSize > 1) {
                        Util.paintText(monCourses.get(j) + " ", Color.RED);
                    } else {
                        Util.paintText(monCourses.get(j) + " ", Color.DEFAULT);
                    }
                } else {
                    System.out.print("          ");
                }

                if (tueCoursesSize > j && tueCourses.get(j) != "") {
                    if (tueCoursesSize > 1) {
                        Util.paintText(tueCourses.get(j) + "  ", Color.RED);
                    } else {
                        Util.paintText(tueCourses.get(j) + "  ", Color.DEFAULT);
                    }
                } else {
                    System.out.print("           ");
                }

                if (wedCoursesSize > j && wedCourses.get(j) != "") {
                    if (wedCoursesSize > 1) {
                        Util.paintText(wedCourses.get(j) + "    ", Color.RED);
                    } else {
                        Util.paintText(wedCourses.get(j) + "    ", Color.DEFAULT);
                    }
                } else {
                    System.out.print("             ");
                }

                if (thrCoursesSize > j && thrCourses.get(j) != "") {
                    if (thrCoursesSize > 1) {
                        Util.paintText(thrCourses.get(j) + "   ", Color.RED);
                    } else {
                        Util.paintText(thrCourses.get(j) + "   ", Color.DEFAULT);
                    }
                } else {
                    System.out.print("            ");
                }

                if (friCoursesSize > j && friCourses.get(j) != "") {
                    if (friCoursesSize > 1) {
                        Util.paintText(friCourses.get(j) + " ", Color.RED);
                    } else {
                        Util.paintText(friCourses.get(j) + " ", Color.DEFAULT);
                    }
                } else {
                    System.out.print("          ");
                }

                System.out.print("\n               ");
            }
            System.out.println(
                    "\n__________________________________________________________________________________________");

        }

    }

    // divides the courses string which is separated with "-" into a courses list
    private static List<String> divideIntoCourses(String str) {
        List<String> courses = new ArrayList<>();
        String temp = "";
        int strLength = str.length();
        for (int i = 0; i < strLength; i++) {
            if (str.charAt(i) != '-') { // TODO: according to selin we have at the end also "-" this might cause a
                                        // problem for alignment
                temp += str.charAt(i); // fix this if it causes a problem
            } else {
                courses.add(temp);
                temp = "";
            }
        }

        return courses;
    }

}
