package main.utils;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;


public class WindowsSettingsLoader {
    public String TITLE = "Projet - Jeu de la Vie";
    public int WIDTH = 1200;
    public int HEIGHT = 700;
    public boolean RESIZABLE = true;
    public String ICONPATH;
    public boolean FILEOVERRIDEPREVENTION = true;

    public void load(String path) {
        try {
            FileReader file = new FileReader(path);
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(file);
                JSONObject myObject = (JSONObject) obj;

                this.TITLE = (String) myObject.get("title");
                this.WIDTH = ((Long) myObject.get("width")).intValue();
                this.HEIGHT = ((Long) myObject.get("height")).intValue();
                this.RESIZABLE = (boolean) myObject.get("resizable");
                this.FILEOVERRIDEPREVENTION = (boolean) myObject.get("fileOverridePreventionMessage");
            } catch (Exception e) {
                System.err.println("Erreur: chargement du profile");
                e.printStackTrace(System.out);
            }
        } catch (Exception e) {
            System.err.println("Erreur: chargement du fichier");
            e.printStackTrace(System.out);
        }
    }

    public void display() {
        System.out.println("Title: " + this.TITLE);
        System.out.println("Width: " + this.WIDTH);
        System.out.println("Height: " + this.HEIGHT);
        System.out.println("Resizable: " + this.RESIZABLE);
    }
}