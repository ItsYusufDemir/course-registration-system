package iteration2.src.utils;

import iteration2.src.enums.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class Util {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void paintText(String message, String color) {
        System.out.println(color + message + Color.DEFAULT);
    }

    public static void sendFeedback(String message, String color, int duration) {
        paintText(message, color);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendFeedback(String message, int duration) {
        System.out.println(message);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public static ArrayList<String> makeArrayList(String splitPattern, String textToSplit){
        String[] splitArray = textToSplit.split(splitPattern);
        ArrayList<String> newlist = new ArrayList<String>(Arrays.asList(splitArray));
        for(int i = 0; i < newlist.size(); i++){
            newlist.set(i, newlist.get(i).trim());
        }
        return newlist;
    }

    public static boolean isInputFormatTrueForDay(ArrayList<String> list){
        int length = list.size();
        for(int i = 0; i < length; i++){
            if(!("Monday".equalsIgnoreCase(list.get(i)) ||
                    "Tuesday".equalsIgnoreCase(list.get(i)) ||
                    "Wednesday".equalsIgnoreCase(list.get(i)) ||
                    "Thursday".equalsIgnoreCase(list.get(i)) ||
                    "Friday".equalsIgnoreCase(list.get(i)))){
                return false;
            }
        }
        return true;
    }
    public static boolean isInputFormatTrueForTime(ArrayList<String> list){
        int length = list.size();
        int hour;
        int minute;
        String[] timeList;
        for(int i = 0; i < length; i++){
            if(!list.get(i).contains(":")){
                return false;
            }

            timeList = list.get(i).split(":");
            try{
                hour = Integer.parseInt(timeList[0]);
                minute = Integer.parseInt(timeList[0]);
            }catch (Exception e){
                return false;
            }

            if(0 < hour && hour > 23){
                return false;
            }


            if(0 < minute && minute > 59){
                return false;
            }

        }
        return true;
    }

}
