package main.utils;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.regex.*;

public class ProfileManager {

    private static boolean isUUID(String line) {
        Pattern regex = Pattern
                .compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }

    public static HashMap<String, HashMap<String, String>> load() {
        try {
            String path = System.getProperty("user.dir") + "\\src\\main\\assets\\profiles.gol.profile";
            File f = new File(path);
            if (f.exists() && !f.isDirectory()) {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                HashMap<String, HashMap<String, String>> map = new HashMap<>();

                if (reader != null) {

                    String uuid = "", keyContent, keyValue;
                    boolean key = true;

                    while (reader.ready()) {
                        String line = reader.readLine();
                        key = true;
                        keyContent = "";
                        keyValue = "";
                        if (isUUID(line)) {
                            uuid = line;
                            map.put(uuid, new HashMap<>());
                        } else {
                            for (int i = 0; i < line.length(); i++) {
                                if (line.charAt(i) == ':') {
                                    key = false;
                                } else if (line.charAt(i) == ' ' || line.charAt(i) == '_' ||line.charAt(i) == '\n') {
                                    ;
                                } else {
                                    if (key) {
                                        keyContent += line.charAt(i);
                                    } else {
                                        keyValue += line.charAt(i);
                                    }
                                }
                            }
                            if (map.containsKey(uuid) && !keyContent.isBlank() && !keyValue.isBlank()) {
                                map.get(uuid).put(keyContent, keyValue);
                                keyContent = "";
                                keyValue = "";
                            }
                        }

                    }

                }
                reader.close();

                return map;
            } else {
                throw new IOException("Le fichier n'existe pas");
            }
        } catch (IOException e) {
            System.err.println("Erreur : null") ;
            return null;
        }
    }

    public static boolean save(HashMap<String, HashMap<String, String>> map) {
        try {
            String path = System.getProperty("user.dir") + "\\src\\main\\assets\\profiless.gol.profile";
            FileWriter writer = new FileWriter(path);
            String file = "";
            for (String uuid : map.keySet()) {
                file += uuid + "\n";
                // System.out.println(uuid);
                HashMap<String, String> key_val = map.get(uuid);
                for (String key : key_val.keySet()) {
                    String var = "_" + key + ": " + key_val.get(key) + "\n";
                    file += var;
                    // System.out.print(var);
                }
                file += "\n";
            }
    
            writer.write(file);
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

// b2dcdde9-794d-4a9e-bc3c-837207e33778
// _RADIUS: 1
// _NUMBER-OF-ITERATION: 10
// _BEGIN-EVOLUTION-TO-ITERATION: 0
// _NEIGHBORS-BIRTH-MIN: 2
// _NEIGHBORS-DEATH-MIN: 2
// _DELAY: 500
// _INFINITE-EVOLUTION: true
// _NAME: default
// _NEIGHBORS-BIRTH-MAX: 3
// _NEIGHBORS-DEATH-MAX: 3

// f381ed37-0845-45c2-bcaf-4bb7270734d6
// _RADIUS: 2
// _NUMBER-OF-ITERATION: 50
// _BEGIN-EVOLUTION-TO-ITERATION: 35
// _NEIGHBORS-BIRTH-MIN: 3
// _NEIGHBORS-DEATH-MIN: 3
// _DELAY: 50
// _INFINITE-EVOLUTION: true
// _NAME: custom1
// _NEIGHBORS-BIRTH-MAX: 4
// _NEIGHBORS-DEATH-MAX: 4