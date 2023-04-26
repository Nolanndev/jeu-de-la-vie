package tests.utils;
import main.utils.ProfileManager;

import java.io.File;
import java.util.UUID;

import java.util.HashMap;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestProfileManager{

    public boolean testIsUUID(){
        System.out.print("Test : isUUID()");

        String valid = "123e4567-e89b-12d3-a456-426655440000";
        String invalid = "123e4567-e89b-12d3-a456-42665544";

    
        assert ProfileManager.isUUID(valid) : "erreur sur le nom valide";
        assert !ProfileManager.isUUID(invalid) : "erreur sur le nom invalide";
        assert !ProfileManager.isUUID("") : "erreur le nom ne peut pas etre vide";
        assert !ProfileManager.isUUID(null) : "erreur le nom est de type null";
    
        return true;
    }
    
    public boolean testLoad(){
        HashMap<String, HashMap<String, String>> map = ProfileManager.load();
        File f = new File("invalidPath");
        
        assert map != null || !f.exists() : "Erreur : le fichier n'existe pas ou est vide";
        if (map != null){
            assert !map.isEmpty() : "Erreur : le map est vide";
        }
     
        assert !f.exists() : "Erreur : le fichier existe";
        
        String emptyPath = System.getProperty("user.dir") + "\\src\\main\\assets\\emptyFile.txt";
        File emptyFile = new File(emptyPath);
        
        if(emptyFile.exists()){
            assert emptyFile.length() == 0 : "Erreur : le fichier n'est pas vide";
        } 
        else{
            System.out.println("Le fichier emptyFile n'existe pas.");
        }

        return true;
    }


    public boolean testSave(){
        System.out.println("Test: save()");
        String path = System.getProperty("user.dir") + "/src/main/assets/profiles.gol.profile";
        File testFile1 = new File(path);
        assert testFile1.exists() == true : "Le fichier n'existe pas : " + testFile1.getAbsolutePath();
    
        HashMap<String, HashMap<String, String>> emptyMap = new HashMap<>();
        //assert ProfileManager.save(emptyMap) == true : "Erreur avec le ProfileManager - HashMap vide";
    
        HashMap<String, HashMap<String, String>> map = new HashMap<>();

        ArrayList<String> uuidList = new ArrayList<>();

        int j = 100;
        for (int i = 0; i < j; i++){
            String uuid = UUID.randomUUID().toString();
            assert ProfileManager.isUUID(uuid) == true : "erreur sur la generation";
            uuidList.add(uuid);
            assert uuid.equals(uuidList.get(i)) : "l'uuid est different de celui attendu";

            HashMap<String, String> profile = new HashMap<>();

            profile.put("_RADIUS", "1");
            profile.put("_NUMBER-OF-ITERATION", "10");
            profile.put("_BEGIN-EVOLUTION-TO-ITERATION", "3");
            profile.put("_NEIGHBORS-BIRTH-MIN", "2");
            profile.put("_NEIGHBORS-DEATH-MIN", "2");
            profile.put("_DELAY", "500");
            profile.put("_INFINITE-EVOLUTION", "true");
            profile.put("_NAME", Integer.toString(i));
            profile.put("_NEIGHBORS-BIRTH-MAX", "3");
            profile.put("_NEIGHBORS-DEATH-MAX", "3");
            
            map.put(uuid, profile);
            assert ProfileManager.save(map) : "Erreur lors de l'enregistrement de map";
        }
   
        // verifie les UUID dans le fichier
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile1))) {
            int i = 0;
            String line;
            while ((line = reader.readLine()).isEmpty() == false) {
                if (i % 12 == 0) { // recupere les uuid aux lignes qui correspondent
                    String uuidFromFile = line.trim();
                    assert uuidList.contains(uuidFromFile) == true : "uuid n'a pas ete ecrit dans le fichier";
    
                }
                i++;
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("save() : OK");
        return true;
    }
}
