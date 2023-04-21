package main.utils;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.regex.*;

import main.core.Cell;
import main.core.Grid;

import java.awt.Point;

public class PresetManager{

    public static boolean isUUID(String line){
        if (line == null){
            return false;
        }
        Pattern regex = Pattern
                .compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }
    
    public static HashMap<String, HashMap<String,String>> load() {
	    return load("src\\main\\assets\\profiles.gol.profile");
    }

    public static HashMap<String, HashMap<String, String>> load(String filepath){
        try{
            String path = System.getProperty("user.dir") + filepath;
            File f = new File(path);
            if (f.exists() && !f.isDirectory()){
                BufferedReader reader = new BufferedReader(new FileReader(path));
                HashMap<String, HashMap<String, String>> map = new HashMap<>();

                if (reader != null){

                    String uuid = "", keyContent, keyValue;
                    boolean key = true;

                    while (reader.ready()){
                        String line = reader.readLine();
                        key = true;
                        keyContent = "";
                        keyValue = "";
                        if (isUUID(line)){
                            uuid = line;
                            map.put(uuid, new HashMap<>());
                        } else{
                            for (int i = 0; i < line.length(); i++){
                                if (line.charAt(i) == ':'){
                                    key = false;
                                } else if (line.charAt(i) == ' ' || line.charAt(i) == '_' ||line.charAt(i) == '\n'){
                                    ;
                                } else{
                                    if (key){
                                        keyContent += line.charAt(i);
                                    } else{
                                        keyValue += line.charAt(i);
                                    }
                                }
                            }
                            if (map.containsKey(uuid) && !keyContent.isBlank() && !keyValue.isBlank()){
                                map.get(uuid).put(keyContent, keyValue);
                                keyContent = "";
                                keyValue = "";
                            }
                        }

                    }

                }
                reader.close();

                return map;
            } else{
                throw new IOException("Le fichier n'existe pas");
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean save(HashMap<String, Grid> map) {
        return save(map, "\\src\\main\\assets\\preset.gol.profile");
    }

    public static boolean save(HashMap<String, Grid> map, String filepath) {
        try {
            String path = System.getProperty("user.dir") + filepath;
            FileWriter writer = new FileWriter(path);
            String file = "";
            for (String uuid : map.keySet()){
                file += uuid + "\n";
                System.out.println(uuid);
                
                Grid grid = map.get(uuid);
                Cell[][] board = map.get(uuid).getBoard();
                for (int i = 0; i<grid.getWidth(); i++) {
                    for (int j = 0; j<grid.getHeight(); j++) {
                        if (board[i][j].isAlive()) {
                            file += "_" + i + ":" + j + "\n";
                        }
                    }
                }
                file += "\n";
            }
            writer.write(file);
            writer.close();
            return true;
        } catch (IOException e){
            return false;
        }
    }
}
