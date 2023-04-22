package main.utils;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.regex.*;

public class ProfileManager{

    /**
     * Retourne vrai si la chaîne de caractères passée en paramètre est un uuid
     * @param line une chaine de caractère
     * @ensures return true si line est générée par java.util.UUID.randomUUID();
     * @return vrai si le paramètre line est un uuid
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
    
    public static HashMap<String, HashMap<String,String>> load() {
	    return load("\\src\\main\\assets\\profiles.gol.profile");
    }

    /**
     * Charge les profiles du jeu de la vie
     * @requires les profiles doivent être écrits avec la fonction ProfileManager.load()
     * @ensures return null s'il y a une erreur lors du chargement des données
     * @return une HashMap<String, HashMap<String, String>> contenant tous les profiles
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
     * 
     * @param map est une HashMap<String, HashMap<String,String>> contenant les différents profiles
     * @requires map doit être une valeur retournée par la fonction ProfileManager.load()
     * @return vrai si les données ont bien été sauvegardées et faux sinon
     */

    public static boolean save(HashMap<String, HashMap<String,String>> map) {
        return save(map, "\\src\\main\\assets\\profiles.gol.profile");
    }

    public static boolean save(HashMap<String, HashMap<String, String>> map, String filepath) {
        try {
            String path = System.getProperty("user.dir") + filepath;
            FileWriter writer = new FileWriter(path);
            String file = "";
            for (String uuid : map.keySet()){
                file += uuid + "\n";
                System.out.println(uuid);
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

    public static ArrayList<String> getNames() {
        HashMap<String, HashMap<String,String>> map = load();
        ArrayList<String> names = new ArrayList<>();
        for (String id : map.keySet()) {
            names.add(map.get(id).get("NAME"));
        }
        return names;
    }

    public static HashMap<String,String> getProfile(String profileName) {
        HashMap<String, HashMap<String,String>> profiles = load();
        for (String id : profiles.keySet()) {
            if (profileName.equals(profiles.get(id).get("NAME"))) {
                return profiles.get(id);
            }
        }
        return null;
    }

    public static boolean isValidName(String profileName) {
        if (profileName == null || profileName.isBlank()){
            return false;
        }
        Pattern regex = Pattern.compile("^[0-9a-zA-Z ]+$");
        Matcher matcher = regex.matcher(profileName);
        return matcher.find();
    }
}