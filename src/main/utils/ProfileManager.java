package main.utils;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.regex.*;

public class ProfileManager{

    /**
     * Return if line is a UUID
     * @param line from a .gol.profile file
     * @ensures return true if line if generate by java.util.UUID.randomUUID();
     * @return vrai if line is a UUID
     */
    public static boolean isUUID(String line){
        if (line == null){
            return false;
        }
        Pattern regex = Pattern
                .compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        Matcher matcher = regex.matcher(line);
        return matcher.find();
    }
    
    /**
     * Return load with the default path
     * @return profiles 
     */
    public static HashMap<String, HashMap<String,String>> load() {
	    // return load("/src/main/assets/profiles.gol.profile");
	    char fs = File.separatorChar;
        return load(fs + "src" + fs + "main" + fs + "assets" + fs + "profiles.gol.profile");
    }

    /**
     * load profiles of game of life
     * @param filepath file of the .gol.profile we want to load
     * @requires the filepath must have been write with ProfileManager.save()
     * @ensures return null if there is an error during data loading
     * @return une HashMap<String, HashMap<String, String>> containing all profiles
     */
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

    /**
     * save profiles in the default file
     * @param map a map containing profiles
     * @requires a map created by ProfileManager.load()
     * @return true if data have been successfully write, else 0
     */

    public static boolean save(HashMap<String, HashMap<String,String>> map) {
        char fs = File.separatorChar;
        return save(map, fs + "src" + fs + "main" + fs + "assets" + fs + "profiles.gol.profile");
    }

    /**
     * save profiles in a file
     * @param map all profiles
     * @param filepath the file of the target file
     * @return true if data have been successfully write, else 0
     */
    public static boolean save(HashMap<String, HashMap<String, String>> map, String filepath) {
        try {
            String path = System.getProperty("user.dir") + filepath;
            FileWriter writer = new FileWriter(path);
            String file = "";
            for (String uuid : map.keySet()){
                file += uuid + "\n";
                HashMap<String, String> key_val = map.get(uuid);
                for (String key : key_val.keySet()){
                    file += "_" + key + ": " + key_val.get(key) + "\n";
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

    /**
     * get all profiles names
     * @return an ArrayList<String> containing all profiles names
     */
    public static ArrayList<String> getNames() {
        HashMap<String, HashMap<String,String>> map = load();
        ArrayList<String> names = new ArrayList<>();
        for (String id : map.keySet()) {
            names.add(map.get(id).get("NAME"));
        }
        return names;
    }

    /**
     * get a profile from a name
     * @param profileName name of the profile
     * ensures return null if the profiles doesn't exist
     * @return profile
     */
    public static HashMap<String,String> getProfile(String profileName) {
        HashMap<String, HashMap<String,String>> profiles = load();
        for (String id : profiles.keySet()) {
            if (profileName.equals(profiles.get(id).get("NAME"))) {
                return profiles.get(id);
            }
        }
        return null;
    }

    /**
     * check is a name is valid to be added in profiles
     * @param profileName new profile we want to check
     * @return true if we can add the name, false else
     */
    public static boolean isValidName(String profileName) {
        if (profileName == null || profileName.isBlank()){
            return false;
        }
        for (String name : ProfileManager.getNames()) {
            if (profileName.equals(name)) {
                return false;
            }
        }
        Pattern regex = Pattern.compile("^[0-9a-zA-Z ]+$");
        Matcher matcher = regex.matcher(profileName);
        return matcher.find();
    }

    /**
     * return id of a name
     * @param profileName name of the profile
     * @ensures return null if name is not a profile
     * @return a string containing a uuid of the profile
     */
    public static String getId(String profileName) {
        HashMap<String, HashMap<String,String>> map = ProfileManager.load();
        for (String id : map.keySet()) {
            if (map.get(id).get("NAME").equals(profileName)) {
                return id;
            }
        }
        return null;
    }
}