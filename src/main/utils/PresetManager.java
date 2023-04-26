package main.utils;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.regex.*;

import java.awt.Dimension;

public class PresetManager {

    /**
     * check if a string is a .gol.preset name
     * @param line string to check
     * @return true if string is a name, false else
     */
    public static boolean isName(String line) {
        if (line == null || line.isBlank()) {
            return false;
        }
        Pattern regex = Pattern.compile("^[0-9a-zA-Z ]+$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }

    /**
     * check if a string is a .gol.preset grid size
     * @param line string to check
     * @return true if string is grid size, false else
     */
    public static boolean isGridSize(String line) {
        if (line == null || line.isBlank()) {
            return false;
        }
        Pattern regex = Pattern.compile("^\\([0-9]+,[0-9]+\\)$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }
    
    /**
     * check if a string is a .gol.preset coordinate
     * @param line string to check
     * @return true if string is a coordinate, false else
     */
    public static boolean isCoordinate(String line) {
        if (line == null || line.isBlank()) {
            return false;
        }
        Pattern regex = Pattern.compile("^[0-9]+:[0-9]+$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }

    /**
     * convert string to a dimension
     * @param line string to dimension
     * @requires PresetManager.isGridSize() == true
     * @return a dimension with line values
     */
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
    
    /**
     * convert string to a dimension
     * @param line string to dimension
     * @requires PresetManager.isCoordinate() == true
     * @return a dimension with line values
     */
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
    
    /**
     * load presets from the default files
     * @return all presets of the files
     */
    public static HashMap<String, HashMap<String, Object>> load() {
        char fs = File.separatorChar;
        return load(fs + "src" + fs + "main" + fs + "assets" + fs + "presets.gol.preset");
    }

    /**
     * load presets from the file
     * @param filepath file where we get the presets
     * @ensures return null if there is an error during file loading
     * @return presets loads
     */
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

    /**
     * save presets to the default file
     * @param map presets to save
     * @return true if save has been successfull
     */
    public static boolean save(HashMap<String, HashMap<String, Object>> map) {
        char fs = File.separatorChar;
        return save(map, fs + "src" + fs + "main" + fs + "assets" + fs + "presets.gol.preset");
    }

    /**
     * save presets to a file
     * @param map presets to save
     * @param filepath file where presets will be write
     * @requires map to be loaded and update by ProfileManager functions
     * @return
     */
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

    /**
     * delete a preset from a name
     * @param name name of the preset we want to delete
     */
    public static void delete (String name) {
        char fs = File.separatorChar;
        delete(fs + "src" + fs + "main" + fs + "assets" + fs + "presets.gol.preset", name);
    }

    /**
     * delete a preset from a file
     * @param filepath path of the file in wich we want to delete the preset
     * @param name name of the preset we want to delete
     */
    public static void delete(String filepath, String name) {
        HashMap<String, HashMap<String, Object>> map = PresetManager.load(filepath);
        map.remove(name);
        PresetManager.save(map, filepath);
    }

    /**
     * get all presets names
     * @return an ArrayList<String> containing all presets names
     */
    public static ArrayList<String> getNames() {
        char fs = File.separatorChar;
        return getNames(fs + "src" + fs + "main" + fs + "assets" + fs + "presets.gol.preset");
    }
    
    /**
     * get all presets names
     * @param filepath path of the file wich we want to get all presets names
     * @return an ArrayList<String> containing all presets names
     */
    public static ArrayList<String> getNames(String filepath) {
        ArrayList<String> presets = new ArrayList<>();
        HashMap<String, HashMap<String, Object>> names = PresetManager.load(filepath);
        if (names != null) {
            for (String name : PresetManager.load(filepath).keySet()) {
                presets.add(name);
            }
            return presets;
        }
        return null;
    }

    /**
     * get name preset
     * @param name name of the preset
     * @ensures return null if the preset doesn't exist
     * @return preset
     */
    public static HashMap<String, Object> getPreset(String name){
        char fs = File.separatorChar;
        return getPreset(name, fs + "src" + fs + "main" + fs + "assets" + fs + "presets.gol.preset");
    }
    
    /**
     * get preset name
     * @param name name of the preset
     * @param filepath path of the file in wich we get the preset
     * @ensures return null if the preset doesn't exist
     * @return preset
     */
    public static HashMap<String, Object> getPreset(String name, String filepath) {
        HashMap<String, HashMap<String, Object>> map = PresetManager.load(filepath);
        for (String presetName : map.keySet()) {
            if (presetName.equals(name)) {
                return map.get(presetName);
            }
        }
        return null;
    }

    /**
     * check if a name is valid to be added
     * @param name new name we want to check
     * @return true if the name is valid to be added, false else
     */
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