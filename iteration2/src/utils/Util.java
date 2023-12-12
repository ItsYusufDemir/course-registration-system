package iteration2.src.utils;

import iteration2.src.enums.Color;

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

}
