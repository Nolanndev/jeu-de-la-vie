package tests.utils;

import main.utils.ProfileManager;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import main.exceptions.ProfileNameException;

public class TestProfileManager {

    private HashMap<String, String> createMap() {
        HashMap<String, String> base = new HashMap<>();
        base.put("NAME", "test");
        base.put("RADIUS", "5");
        base.put("NEIGHBORS_MIN", "1");
        base.put("NEIGHBORS_MAX", "3");
        base.put("DELAY", "3000");
        base.put("STATES", "15");
        base.put("DIRECTION", "ALL");
        base.put("EVOLUTION", "null");
        return base;
    }

    public boolean testLoad() throws IOException {
        System.out.print("Test : TestProfileManager.testLoad()");
        HashMap<String, String> map = createMap();

        HashMap<String, String> res = ProfileManager.load("testBase");

        assert map.equals(res) : "Profile loading went wrong";
        System.out.println(" - OK");
        return true;
    }

    public boolean testValidProfileName() throws ProfileNameException {
        System.out.print("Test : TestProfileManager.testValidProfileName()");
        
        assert ProfileManager.validProfileName("DARK_SASUKE!!!") == false : "DARK_SASUKE!!! n'est pas un nom de profil valide";
        assert ProfileManager.validProfileName("DARK_ITACHI14") == true : "DARK_ITACHI14 est un nom de profil valide";
        assert ProfileManager.validProfileName("TERREUR964") == true : "TERREUR964 est un nom de profil valide";
        assert ProfileManager.validProfileName("DARKSLIPEUR93!!") == false : "DARKSLIPEUR93!! n'est pas un nom de profil valide";
        assert ProfileManager.validProfileName("SAKURA EST FAIBLE") == false : "SAKURA EST FAIBLE n'est pas un nom de profil valide";
        assert ProfileManager.validProfileName("") == false : "Le profil ne peut pas être vide";
        assert ProfileManager.validProfileName(" ") == false : "Le profil ne peut pas etre un espace";
        assert ProfileManager.validProfileName("default") == false : "Default est un nom réservé";
        return true;
    }

    public boolean testSave() throws IOException, ProfileNameException {
        System.out.print("Test : TestProfileManager.testSave()");
        HashMap<String, String> map = createMap();
        ProfileManager.save(map, "testSave");
        File testFile = new File("src/main/assets/profiles/testSave.gol.profile");

        assert testFile.exists() == true : "le fichier n'existe pas";
        return true;
    }
}
