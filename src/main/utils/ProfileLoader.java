package main.utils;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

// code inspiré de https://www.tutorialspoint.com/how-can-we-read-a-json-file-in-java#:~:text=The%20json.,the%20path%20to%20execute%20it.

public class ProfileLoader {
    
    public static void main(String[] args) {
        String path = ".\\src\\main\\ressources\\data\\config.json";
        JSONParser parser = new JSONParser();

        try {
            FileReader file = new FileReader(path);
            try {
                Object obj = parser.parse(file);
                JSONObject myObject = (JSONObject) obj;
                String window = (String) myObject.get("window");
                System.out.println(window);
            } catch (Exception e) {
                System.err.println("Error: profile loading");
                e.printStackTrace(System.out);
            }
        } catch (Exception e) {
            System.err.println("Error: file loading");
            e.printStackTrace(System.out);
        }
    }
}