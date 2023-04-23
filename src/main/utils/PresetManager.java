package main.utils;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.regex.*;
import java.awt.Dimension;

public class PresetManager {

    public static boolean isName(String line) {
        if (line == null || line.isBlank()) {
            return false;
        }
        Pattern regex = Pattern.compile("^[0-9a-zA-Z ]+$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }

    // public static boolean isDimension(String line) {
    //     if (line == null || line.isBlank()) {
    //         return false;
    //     }
    //     Pattern regex = Pattern.compile("^_([0-9]+,[0-9]+)$");
    //     Matcher matcher = regex.matcher(line);
    //     return matcher.find();
    // }

    public static HashMap<String, ArrayList<Dimension>> load() {
        return load("\\src\\main\\assets\\presets.gol.preset");
    }

    public static HashMap<String, ArrayList<Dimension>> load(String filepath) {
        try {
            String path = System.getProperty("user.dir") + filepath;
            File f = new File(path);
            if (f.exists() && !f.isDirectory()) {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                HashMap<String, ArrayList<Dimension>> map = new HashMap<>();
                if (reader != null) {
                    String name = "", keyContent, keyValue;
                    boolean key = true;

                    while (reader.ready()) {
                        String line = reader.readLine();
                        key = true;
                        keyContent = "";
                        keyValue = "";
                        if (isName(line)) {
                            name = line;
                            map.put(name, new ArrayList<>());
                        } else {
                            for (int i = 0; i < line.length(); i++) {
                                if (line.charAt(i) == ':') {
                                    key = false;
                                } else if (line.charAt(i) == ' ' || line.charAt(i) == '_' || line.charAt(i) == '\n') {
                                    ;
                                } else {
                                    if (key) {
                                        keyContent += line.charAt(i);
                                    } else {
                                        keyValue += line.charAt(i);
                                    }
                                }
                            }
                            if (map.containsKey(name) && !keyContent.isBlank() && !keyValue.isBlank()) {
                                // map.get(name).add(new Dimension(Integer.parseInt(keyContent), Integer.parseInt(keyValue)));
                                map.get(name).add(new Dimension((int)Integer.parseInt(keyContent), (int)Integer.parseInt(keyValue)));
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
            System.err.println("Erreur : PresetManager.load()");
            return null;
        }
    }

    public static boolean save(HashMap<String, ArrayList<Dimension>> map) {
        return save(map, "\\src\\main\\assets\\presets.gol.preset");
    }

    public static boolean save(HashMap<String, ArrayList<Dimension>> map, String filepath) {
        try {
            String path = System.getProperty("user.dir") + filepath;
            FileWriter writer = new FileWriter(path);
            String file = "";
            for (String name : map.keySet()) {
                file += name + "\n";
                for (Dimension dim : map.get(name)) {
                    file += "_" + (int)dim.getWidth() + ":" + (int)dim.getHeight() + "\n";
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

    public static ArrayList<String> getNames() {
        ArrayList<String> presets = new ArrayList<>();
        HashMap<String, ArrayList<Dimension>> names = PresetManager.load();
        if (names != null) {
            for (String name : PresetManager.load().keySet()) {
                presets.add(name);
            }
            return presets;
        }
        return null;
    }

    public static ArrayList<Dimension> getPreset(String name) {
        HashMap<String, ArrayList<Dimension>> map = PresetManager.load();
        for (String presetName : map.keySet()) {
            if (presetName.equals(name)) {
                return map.get(presetName);
            }
        }
        return null;
    }

    public static boolean isValidName(String name) {
        if (name == null || name.isBlank()){
            return false;
        }
        for (String presetName : PresetManager.getNames()) {
            if (name.equals(presetName)) {
                return false;
            }
        }
        Pattern regex = Pattern.compile("^[0-9a-zA-Z ]+$");
        Matcher matcher = regex.matcher(name);
        return matcher.find();
    }
}
