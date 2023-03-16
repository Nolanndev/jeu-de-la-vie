package main.utils;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.util.regex.*;
import main.exceptions.*;

public class ProfileManager {

    private static void parseLine(String line, HashMap<String, String> map) {
        boolean key = true;
        String keyContent = "", valueContent = "";
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case ':':
                    key = false;
                    break;
                case ' ':
                    break;
                default:
                    if (key) {
                        keyContent += line.charAt(i);
                        break;
                    }
                    valueContent += line.charAt(i);
                    break;
            }
        }
        if (!(keyContent.isEmpty() || valueContent.isEmpty())) {
            map.put(keyContent, valueContent);
        }
    }

    /**
     * load file with '.gol.profile' extension
     * 
     * @param filename name of the file without extension
     * @return the map of (key,value) contains in the file
     * @throws IOException file doesn't exist
     */
    public static HashMap<String, String> load(String filename) throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\main\\assets\\profiles\\" + filename + ".gol.profile";
        File f = new File(path);
        if (f.exists() && !f.isDirectory()) {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            HashMap<String, String> map = new HashMap<>();
            if (reader != null) {
                while (reader.ready()) {
                    parseLine(reader.readLine(), map);
                }
            }
            reader.close();
            return map;
        } else {
            throw new IOException("Le fichier n'existe pas");
        }
    }

    /**
     * load the default profile
     * 
     * @return the default map of (key,value) contains in the file
     * @throws IOException default file not found
     */
    public static HashMap<String, String> load() throws IOException {
        return load("default");
    }

    public static boolean validProfileName(String profileName) throws ProfileNameException {
        Pattern regex = Pattern.compile("\\W+");
        Matcher matcher = regex.matcher(profileName);
        if (profileName.isEmpty() || matcher.find() || profileName == "default") {
            return false;
        }
        return true;
    }

    /**
     * 
     * @param map
     * @param filename
     */
    public static void save(HashMap<String, String> map, String filename) throws IOException, ProfileNameException {
        if (!validProfileName(filename)) {
            throw new ProfileNameException("profile name is not valid");
        }

        String path = System.getProperty("user.dir") + "\\src\\main\\assets\\profiles\\" + filename + ".gol.profile";
        FileWriter writer = new FileWriter(path);
        String str = "";
        for (String key : map.keySet()) {
            str += key + ": " + map.get(key) + "\n";
        }
        writer.write(str);
        writer.close();
    }
}
