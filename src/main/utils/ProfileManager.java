package main.utils;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.regex.*;

public class ProfileManager{

    public static boolean isUUID(String line){
        if (line == null){
            return false;
        }
        Pattern regex = Pattern
                .compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }
    

    public static HashMap<String, HashMap<String, String>> load(){
        try{
            String path = System.getProperty("user.dir") + "\\src\\main\\assets\\profiles.gol.profile";
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

    public static boolean save(HashMap<String, HashMap<String, String>> map) {
        try {
            String path = System.getProperty("user.dir") + "\\src\\main\\assets\\profiles.gol.profile";
            FileWriter writer = new FileWriter(path);
            String file = "";
            for (String uuid : map.keySet()){
                file += uuid + "\n";
                System.out.println(uuid);
                HashMap<String, String> key_val = map.get(uuid);
                for (String key : key_val.keySet()){
                    file += "_" + key + ": " + key_val.get(key) + "\n";
                    System.out.println(uuid + "." + key + ":" + key_val.get(key));
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