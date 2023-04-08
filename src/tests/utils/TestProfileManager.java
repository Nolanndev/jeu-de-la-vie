package tests.utils;

import main.utils.ProfileManager;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.plaf.TreeUI;

public class TestProfileManager{

    public boolean testIsUUID(){
        System.out.print("Test : isUUID()");

        String valid = "123e4567-e89b-12d3-a456-426655440000";
        String invalid = "123e4567-e89b-12d3-a456-42665544";

    
        assert ProfileManager.isUUID(valid) : "erreur sur le nom valide";
        assert !ProfileManager.isUUID(invalid) : "erreur sur le nom invalide";
        assert !ProfileManager.isUUID("") : "erreur le nom ne peut pas être vide";
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
    /* 

    public boolean testSave() throws IOException, ProfileNameException{
        System.out.print("Test : TestProfileManager.testSave()");
        HashMap<String, String> map = createMap();
        ProfileManager.save(map, "testSave");
        File testFile = new File("src/main/assets/profiles/testSave.gol.profile");

        assert testFile.exists() == true : "le fichier n'existe pas";
        return true;
    }

*/

}
