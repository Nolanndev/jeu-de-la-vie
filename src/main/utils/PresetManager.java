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

    public static boolean isGridSize(String line) {
        if (line == null || line.isBlank()) {
            return false;
        }
        Pattern regex = Pattern.compile("^\\([0-9]+,[0-9]+\\)$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }
    
    public static boolean isCoordinate(String line) {
        if (line == null || line.isBlank()) {
            return false;
        }
        Pattern regex = Pattern.compile("^[0-9]+:[0-9]+$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }

    public static Dimension parseSize(String line){
        String[] words = line.replaceAll("[\\(\\)]", "").split(",");
        int width, height;
    
        try{
            width = Integer.parseInt(words[0]);
            height = Integer.parseInt(words[1]);
        } 
        catch (NumberFormatException e) {
            width = (int) words[0].charAt(0); // code ASCII
            height = (int) words[1].charAt(0);
        }
    
        return new Dimension(width, height);
    }
    
    
    public static Dimension parseCoordinate(String line) {
        String[] words = line.split(":");
        int x, y;
        try {
            x = Integer.parseInt(words[0]);
            y = Integer.parseInt(words[1]);
        } catch (NumberFormatException e) {
            x = (int) words[0].charAt(0); 
            y = (int) words[1].charAt(0);
        }
    
        return new Dimension(x, y);
    }
    

    public static HashMap<String, HashMap<String, Object>> load() {
        char fs = File.separatorChar;
        return load(fs + "src" + fs + "main" + fs + "assets" + fs + "presets.gol.preset");
    }

    public static HashMap<String,HashMap<String,Object>> load(String filepath) {
        try {
            String path = System.getProperty("user.dir") + filepath;
            File f = new File(path);
            if (f.exists() && !f.isDirectory()) {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                HashMap<String,HashMap<String,Object>> map = new HashMap<>();
                if (reader != null) {
                    String name = "";
                    while (reader.ready()) {
                        String line = reader.readLine();
                        if (isName(line)) {
                            name = line;
                            map.put(name, new HashMap<>());
                            map.get(name).put("CELLS", new ArrayList<>());
                        } else if (isGridSize(line)) {
                            map.get(name).put("SIZE", parseSize(line));
                        } else if (isCoordinate(line)) {
                            ((ArrayList<Dimension>)map.get(name).get("CELLS")).add(parseCoordinate(line));
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

    public static boolean save(HashMap<String, HashMap<String, Object>> map) {
        char fs = File.separatorChar;
        return save(map, fs + "src" + fs + "main" + fs + "assets" + fs + "presets.gol.preset");
    }

    public static boolean save(HashMap<String, HashMap<String, Object>> map, String filepath) {
        try {
            String path = System.getProperty("user.dir") + filepath;
            FileWriter writer = new FileWriter(path);
            String file = "";
            for (String name : map.keySet()) {
                file += name + "\n";
                Dimension dim = (Dimension)map.get(name).get("SIZE");
                file += "(" + Integer.toString((int)dim.getWidth()) + "," + Integer.toString((int)dim.getHeight()) + ")\n";
                ArrayList<Dimension> cells = (ArrayList<Dimension>) map.get(name).get("CELLS");
                for (Dimension dimi : cells) {
                    file += Integer.toString((int)dimi.getWidth()) + ":" + Integer.toString((int)dimi.getHeight())+ "\n";
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

    public static void delete (String name) {
        char fs = File.separatorChar;
        delete(fs + "src" + fs + "main" + fs + "assets" + fs + "presets.gol.preset", name);
    }

    public static void delete(String filepath, String name) {
        HashMap<String, HashMap<String, Object>> map = PresetManager.load(filepath);
        map.remove(name);
        PresetManager.save(map,filepath);
    }

    public static ArrayList<String> getNames() {
        ArrayList<String> presets = new ArrayList<>();
        HashMap<String, HashMap<String, Object>> names = PresetManager.load();
        if (names != null) {
            for (String name : PresetManager.load().keySet()) {
                presets.add(name);
            }
            return presets;
        }
        return null;
    }

    public static HashMap<String, Object> getPreset(String name) {
        HashMap<String, HashMap<String, Object>> map = PresetManager.load();
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