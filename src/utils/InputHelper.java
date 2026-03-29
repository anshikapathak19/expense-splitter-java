package utils;

import java.util.Scanner;

public class InputHelper {
    private static Scanner sc = new Scanner(System.in);

    public static String getString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public static double getDouble(String msg) {
        System.out.print(msg);
        return Double.parseDouble(sc.nextLine());
    }

    public static int getInt(String msg) {
        System.out.print(msg);
        return Integer.parseInt(sc.nextLine());
    }
}
