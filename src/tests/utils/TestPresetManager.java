package tests.utils;
import main.utils.PresetManager;

import java.util.*;
import java.awt.Dimension;


public class TestPresetManager{

    public boolean testIsName(){
        System.out.println("isName()");
        assert PresetManager.isName("9876543210") == true : "erreur, les chiffres sont acceptés";
        assert PresetManager.isName("Ca Passe Normalement") == true : "erreur, les espace et majuscules sont acceptés";
        assert PresetManager.isName("Test12        ") == true : "erreur, les espace, majuscules et nombres sont acceptés ";

        assert PresetManager.isName("XXX_XXX") == false : "le nom ne peut pas contenir d'underscore";
        assert PresetManager.isName("!!") == false : "le nom ne peut pas contenir !!";
        assert PresetManager.isName("??") == false : "le nom ne peut pas contenir ??";
        assert PresetManager.isName("") == false : "le nom ne peut pas etre vide";
        assert PresetManager.isName("test@") == false : "@ n'est pas valide";
        assert PresetManager.isName("$100") == false : "$ n'est pas valide";

        return true;
    }

    public boolean testIsGridSize(){
        System.out.println("isGridSize()");
        assert PresetManager.isGridSize("(10,10)") == true : "la dimension est acceptée 10x10";
        assert PresetManager.isGridSize("(100,100)") == true : "la dimension est acceptée 100x100";
        assert PresetManager.isGridSize("(1000,1000)") == true : "la dimension est acceptée 1000x1000";

        assert PresetManager.isGridSize("(1a,1a)") == false : "les lettres ne sont pas acceptées";
        assert PresetManager.isGridSize("(OO,OO)") == false : "OO n'est pas accepté";
        assert PresetManager.isGridSize("(,)") == false : "il faut mettre des nombres";

        return true;
    }

    
    public boolean testisCoordinate(){
        System.out.println("isCoordinate()");
        assert PresetManager.isCoordinate("9:9") == true : "la dimension 9x9 est acceptée";
        assert PresetManager.isCoordinate("100:100") == true : "la dimension 100x100 est acceptée";
        assert PresetManager.isCoordinate("1:1") == true : "la dimension 1x1 est acceptée";

        assert PresetManager.isCoordinate("1:") == false : "la dimension n'est pas bonne 1:";
        assert PresetManager.isCoordinate("") == false : "la coordonnée n'est pas bonne (vide)";
        assert PresetManager.isCoordinate("vide") == false : "la coordonnée n'est pas bonne (vide)";
        assert PresetManager.isCoordinate("(10,10)") == false : "la coordonnée n'est pas bonne 10x10";
        assert PresetManager.isCoordinate("(100,100)") == false : "la coordonnée n'est pas bonne 100x100";
        assert PresetManager.isCoordinate("(1000,1000)") == false : "la coordonnée n'est pas bonne 1000x1000";

        return true;
    }

    public boolean testParseSize(){
        System.out.println("isParseSize()");
        assert PresetManager.parseSize("(100,200)").width == 100 : "100 est censé etre la largeur";
        assert PresetManager.parseSize("(100,200)").height == 200 : "200 est censé etre la hauteur";
        assert PresetManager.parseSize("(-50,-52)").width == -50 : "-50 est censé etre la largeur";
        assert PresetManager.parseSize("(-50,-52)").height == -52 : "-52 est censé etre la hauteur";

        assert PresetManager.parseSize("(c,a)").width == 99 : "-50 est censé etre la largeur";
        assert PresetManager.parseSize("(c,a)").height == 97 : "-52 est censé etre la hauteur";
        return true;
    }

    public boolean testParseCoordinate(){
        assert PresetManager.parseCoordinate("100:200").width == 100 : "100 est censé etre la largeur";
        assert PresetManager.parseCoordinate("100:200").height == 200 : "200 est censé etre la hauteur";
        assert PresetManager.parseCoordinate("-50:-52").width == -50 : "-50 est censé etre la largeur";
        assert PresetManager.parseCoordinate("-50:-52").height == -52 : "-52 est censé etre la hauteur";

        assert PresetManager.parseCoordinate("a:b").width == 97 : "a est censé etre la hauteur";
        assert PresetManager.parseCoordinate("a:b").height == 98 : "b est censé etre la hauteur";

        return true;
    }


    public boolean testLoad() {
        System.out.println("load()");
        String filepath = "\\src\\main\\assets\\presets.gol.preset";
    
        HashMap<String, HashMap<String,Object>> result = PresetManager.load(filepath);
        assert result != null : "le fichier chargé est null";
        assert result.containsKey("Le dernier") : "Le fichier doit contenir le preset 'le dernier'";
        assert result.containsKey("un autre") : "Le fichier doit contenir le preset 'un autre'";
        assert result.containsKey("default") : "Le fichier doit contenir le preset 'default'";

    
        assert result.size() == 3 : "Le nombre de presets doit etre de 3";
  
        ArrayList<Dimension> dernierDimensions = (ArrayList<Dimension>) result.get("Le dernier").get("CELLS");
        assert dernierDimensions != null && dernierDimensions.size() == 4 : "Le nombre de dimensions pour 'le dernier' doit etre de 4";

        ArrayList<Dimension> defaultDimensions = (ArrayList<Dimension>) result.get("default").get("CELLS");
        assert defaultDimensions != null && defaultDimensions.size() == 4 : "Le nombre de dimensions pour 'default' doit etre de 4";

        ArrayList<Dimension> autreDimensions = (ArrayList<Dimension>) result.get("un autre").get("CELLS");
        assert autreDimensions != null && autreDimensions.size() == 4 : "Le nombre de dimensions pour 'un autre' doit etre de 4";
      
    
        assert ((Dimension)defaultDimensions.get(0)).width == 10 : "La largeur de la dimension 0 de 'default' doit etre de 10";
        assert ((Dimension)defaultDimensions.get(0)).height == 15 : "La hauteur de la dimension 0 de 'default' doit etre de 15";
        assert ((Dimension)defaultDimensions.get(1)).width == 10 : "La largeur de la dimension 1 de 'default' doit etre de 10";
        assert ((Dimension)defaultDimensions.get(1)).height == 16 : "La hauteur de la dimension 1 de 'default' doit etre de 16";
    
        assert ((Dimension)autreDimensions.get(2)).width == 11 : "La largeur de la dimension 2 de 'un autre' doit etre de 11";
        assert ((Dimension)autreDimensions.get(2)).height == 15 : "La hauteur de la dimension 2 de 'un autre' doit etre de 15";
    
        assert ((Dimension)dernierDimensions.get(3)).width == 11 : "La largeur de la dimension 3 de 'le dernier' doit etre de 11";
        assert ((Dimension)dernierDimensions.get(3)).height == 16 : "La hauteur de la dimension 3 de 'le dernier' doit etre de 16";
    
        return true;
    }
    


    public boolean testSave() {
        System.out.println("save()");

        HashMap<String, HashMap<String, Object>> map = new HashMap<String, HashMap<String, Object>>();

        HashMap<String, Object> preset1 = new HashMap<String, Object>();
        ArrayList<Dimension> dimensions1 = new ArrayList<Dimension>();
        dimensions1.add(new Dimension(10, 15));
        dimensions1.add(new Dimension(10, 16));
        dimensions1.add(new Dimension(11, 15));
        dimensions1.add(new Dimension(11, 16));

        
        
        preset1.put("SIZE", new Dimension(10, 10));
        preset1.put("CELLS", dimensions1);
        
        HashMap<String, Object> preset2 = new HashMap<String, Object>();
        ArrayList<Dimension> dimensions2 = new ArrayList<Dimension>();
        dimensions2.add(new Dimension(10, 15));
        dimensions2.add(new Dimension(10, 16));
        dimensions2.add(new Dimension(11, 15));
        dimensions2.add(new Dimension(11, 16));

        preset2.put("SIZE", new Dimension(10, 10));
        preset2.put("CELLS", dimensions2);

        HashMap<String, Object> preset3 = new HashMap<String, Object>();
        ArrayList<Dimension> dimensions3 = new ArrayList<Dimension>();
        dimensions3.add(new Dimension(10, 15));
        dimensions3.add(new Dimension(10, 16));
        dimensions3.add(new Dimension(11, 15));
        dimensions3.add(new Dimension(11, 16));

        preset3.put("SIZE", new Dimension(10, 10));
        preset3.put("CELLS", dimensions3);

        map.put("default", preset1);
        map.put("un autre", preset2);
        map.put("Le dernier", preset3);

        String filepath2 = "/src/main/assets/test.gol.preset";

        assert PresetManager.save(map, filepath2) : "erreur avec la fonction save()";

        return true;
    }

    public boolean testGetNames(){
        System.out.println("getNames()");

        ArrayList<String> names = PresetManager.getNames();
        assert names != null : "la liste des noms n'est pas null";
        assert !names.isEmpty() : "la liste n'est pas vide non plus";
    
        assert names.contains("default") : "Erreur avec la fonction getNames, elle doit contenir default";
        assert names.contains("test") : "Erreur avec la fonction getNames, elle doit contenir test";
        assert names.contains("un autre") : "Erreur avec la fonction getNames, elle doit contenir un autre";
        return true;
    }

    public boolean testDelete(){
        System.out.println("delete()");
        String filepath = "/src/main/assets/test.gol.preset";

        String default_v = "default";
        String un_autre = "un autre";

        ArrayList<String> initialPresets = PresetManager.getNames();
        assert initialPresets.contains(default_v) == true : "la fonction save provoque une erreur defaut";
        assert initialPresets.contains(un_autre) == true : "la fonction save provoque une erreur un autre";

        PresetManager.delete(filepath, un_autre);
        PresetManager.delete(filepath, default_v);
        
        ArrayList<String> updatedPresets = PresetManager.getNames();

        assert updatedPresets.contains(default_v) == false : "la fonction ne supprime pas comme il faut defaut";
        assert updatedPresets.contains(un_autre) == false : "la fonction ne supprime pas comme il faut un autre";
        return true;
    }

    public boolean testGetPreset(){
        System.out.println("getPreset()");

        HashMap<String, Object> default_preset = PresetManager.getPreset("default");
        HashMap<String, Object> un_autre = PresetManager.getPreset("un autre");
        HashMap<String, Object> le_dernier = PresetManager.getPreset("Le dernier");

        assert default_preset != null : "default doit etre != null";
        assert default_preset.containsKey("_10:15") : "Le preset default doit contenir la dimension _10:15";
        assert default_preset.containsKey("_10:16") : "Le preset default doit contenir la dimension _10:16";
        assert default_preset.containsKey("_11:15") : "Le preset default doit contenir la dimension _11:15";
        assert default_preset.containsKey("_11:16") : "Le preset default doit contenir la dimension _11:16";

        assert un_autre != null : "un autre doit etre != null";
        assert un_autre.containsKey("_10:15") : "Le preset un autre doit contenir la dimension _10:15";
        assert un_autre.containsKey("_10:16") : "Le preset un autre doit contenir la dimension _10:16";
        assert un_autre.containsKey("_11:15") : "Le preset un autre doit contenir la dimension _11:15";
        assert un_autre.containsKey("_11:16") : "Le preset un autre doit contenir la dimension _11:16";

        assert le_dernier != null : "Le dernier doit etre != null";
        assert le_dernier.containsKey("_10:15") : "Le preset Le dernier doit contenir la dimension _10:15";
        assert le_dernier.containsKey("_10:16") : "Le preset Le dernier doit contenir la dimension _10:16";
        assert le_dernier.containsKey("_11:15") : "Le preset Le dernier doit contenir la dimension _11:15";
        assert le_dernier.containsKey("_11:16") : "Le preset Le dernier doit contenir la dimension _11:16";

        HashMap<String, Object> inexistant_preset = PresetManager.getPreset("inexistant");
        assert inexistant_preset == null : "Le preset inexistant ne doit pas exister";

        
        return true;
    }
}


