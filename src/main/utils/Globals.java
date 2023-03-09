package main.util;

public class Globals {
    public static String currentPath = System.getProperty("user.dir");

    public static void viewGlobals() {
        System.out.println("GLOBALS VARIABLES ");
        System.out.println("\t------");
        System.out.println("Current directory path: " + currentPath);
    }
}