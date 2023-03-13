package main.utils;

import java.util.*;
import java.io.*;
import java.io.IOException;


public class FileManager {


    // CHARGEMENT DU FICHIER
    private static void parseLine(String line, HashMap<String,String> map) {
        boolean key = true;
        String keyContent = "", valueContent = "";
        for (int i=0; i<line.length(); i++) {
            switch (line.charAt(i)) {
                case ':' :
                    key = false;
                    break;
                case ' ' :
                    break;
                default :
                    if (key) {
                        keyContent += line.charAt(i);
                        break;
                    }
                    valueContent += line.charAt(i);
                    break;
            }
        }
        if (!( keyContent.isEmpty() || valueContent.isEmpty() )) {
            map.put(keyContent, valueContent);
        }
    }

    public static HashMap<String,String> parse(String filePath) {
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fr);
            HashMap<String,String> map  = new HashMap<>();
            if (reader != null) {
                while (reader.ready()) {
                    parseLine(reader.readLine(), map);
                }
            }
            reader.close();
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<String,String>();
        }
    }

    // ECRITURE DU FICHIER

    public static void write(HashMap<String, String> map, String filename) {
        System.out.println("WRITE");
        try {
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + filename);
            String str = "";
            for (String key : map.keySet()) {
                str += key + ": " + map.get(key) + "\n";
            }
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }
}
