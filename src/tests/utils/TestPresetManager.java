package tests.utils;
import main.utils.PresetManager;

import java.util.*;
import java.awt.Dimension;


public class TestPresetManager{

    public boolean testIsName(){
        System.out.println("isName()");
        assert PresetManager.isName("9876543210") == true : "erreur, les chiffres sont acceptés";
        assert PresetManager.isName("Ca Passe Normalement") == true : "erreur, les espace et majuscules sont acceptés";
        assert PresetManager.isName("John Doe 123        ") == true : "erreur, les espace, majuscules et nombres sont acceptés ";

        assert PresetManager.isName("XXX_XXX") == false : "le nom ne peut pas contenir d'underscore";
        assert PresetManager.isName("!!") == false : "le nom ne peut pas contenir !!";
        assert PresetManager.isName("??") == false : "le nom ne peut pas contenir ??";
        assert PresetManager.isName("") == false : "le nom ne peut pas etre vide";
        assert PresetManager.isName("test@") == false : "@ n'est pas valide";
        assert PresetManager.isName("$100") == false : "$ n'est pas valide";

        return true;
    }

    public boolean testLoad() {
        System.out.println("load()");
        String filepath = "\\src\\main\\assets\\presets.gol.preset";
    
        HashMap<String, ArrayList<Dimension>> result = PresetManager.load(filepath);
        assert result != null : "le fichier chargé est null";
    
        assert result.size() == 3 : "Le nombre de presets doit etre de 3";
        

        ArrayList<Dimension> dernierDimensions = result.get("Le dernier");
        assert dernierDimensions != null && dernierDimensions.size() == 4 : "Le nombre de dimensions pour 'le dernier' doit etre de 4";
        ArrayList<Dimension> defaultDimensions = result.get("default");
        assert defaultDimensions != null && defaultDimensions.size() == 4 : "Le nombre de dimensions pour 'default' doit etre de 4";
        ArrayList<Dimension> autreDimensions = result.get("un autre");
        assert autreDimensions != null && autreDimensions.size() == 4 : "Le nombre de dimensions pour 'un autre' doit etre de 4";


        assert result.containsKey("Le dernier") == true : "Le fichier doit contenir le preset 'le dernier'";
    
        assert defaultDimensions.get(0).width == 10 : "La largeur de la dimension 0 de 'default' doit etre de 10";
        assert defaultDimensions.get(0).height == 15 : "La hauteur de la dimension 0 de 'default' doit etre de 15";
        assert defaultDimensions.get(1).width == 10 : "La largeur de la dimension 1 de 'default' doit etre de 10";
        assert defaultDimensions.get(1).height == 16 : "La hauteur de la dimension 1 de 'default' doit etre de 16";
    
        assert autreDimensions.get(2).width == 11 : "La largeur de la dimension 2 de 'un autre' doit etre de 11";
        assert autreDimensions.get(2).height == 15 : "La hauteur de la dimension 2 de 'un autre' doit etre de 15";
    
        assert dernierDimensions.get(3).width == 11 : "La largeur de la dimension 3 de 'le dernier' doit etre de 11";
        assert dernierDimensions.get(3).height == 16 : "La hauteur de la dimension 3 de 'le dernier' doit etre de 16";
    
        return true;
    }
    
    

    
    public boolean testSave(){
        System.out.println("save()");
        HashMap<String, ArrayList<Dimension>> map = new HashMap<String, ArrayList<Dimension>>();

        ArrayList<Dimension> dimensions1 = new ArrayList<Dimension>();
        dimensions1.add(new Dimension(10, 15));
        dimensions1.add(new Dimension(10, 16));
        dimensions1.add(new Dimension(11, 15));
        dimensions1.add(new Dimension(11, 16));

        ArrayList<Dimension> dimensions2 = new ArrayList<Dimension>();
        dimensions2.add(new Dimension(10, 15));
        dimensions2.add(new Dimension(10, 16));
        dimensions2.add(new Dimension(11, 15));
        dimensions2.add(new Dimension(11, 16));

        ArrayList<Dimension> dimensions3 = new ArrayList<Dimension>();
        dimensions3.add(new Dimension(10, 15));
        dimensions3.add(new Dimension(10, 16));
        dimensions3.add(new Dimension(11, 15));
        dimensions3.add(new Dimension(11, 16));
        map.put("default", dimensions1);
        map.put("un autre", dimensions2);
        map.put("Le dernier", dimensions3);
        
        String filepath = "\\src\\main\\assets\\presets.gol.preset";
        String invalidfilepath = "\\src\\main\\assets\\default.gol.preset";

        assert PresetManager.save(map, filepath) == true : "erreur avec la fonction save()";
        //assert PresetManager.save(map, invalidfilepath) == false : "erreur avec la fonction save()";


        return true;
    }
    
    public boolean testGetPreset(){
        ArrayList<Dimension> default_v = PresetManager.getPreset("default");
        assert default_v != null : "default doit etre != null";
        assert default_v.size() == 4 : "la taile doit etre de 4";
        assert default_v.get(0).width == 10 : "la largeur doit etre de 10";
        assert default_v.get(0).height == 15 : "la hauteur doit etre de 15";

        assert default_v.get(1).width == 10 : "la largeur doit etre de 10";
        assert default_v.get(1).height == 16 : "la hauteur doit etre de 16";

        assert default_v.get(2).width == 11: "la largeur doit etre de 11";
        assert default_v.get(2).height == 15 : "la hauteur doit etre de 15";

        assert default_v.get(3).width == 11: "la largeur doit etre de 11";
        assert default_v.get(3).height == 16 : "la hauteur doit etre de 16";
 


        ArrayList<Dimension> autre = PresetManager.getPreset("un autre");
        assert autre != null : "autre doit etre != null";
        assert autre.size() == 4 : "la taile doit etre de 4";
        assert autre.get(0).width == 10 : "la largeur doit etre de 10";
        assert autre.get(0).height == 15 : "la hauteur doit etre de 15";

        assert autre.get(1).width == 10 : "la largeur doit etre de 10";
        assert autre.get(1).height == 16 : "la hauteur doit etre de 16";

        assert autre.get(2).width == 11: "la largeur doit etre de 11";
        assert autre.get(2).height == 15 : "la hauteur doit etre de 15";

        assert autre.get(3).width == 11: "la largeur doit etre de 11";
        assert autre.get(3).height == 16 : "la hauteur doit etre de 16";

        ArrayList<Dimension> dernier = PresetManager.getPreset("Le dernier");
        assert dernier != null : "dernier doit etre != null";
        assert dernier.size() == 4 : "la taile doit etre de 4";
        assert dernier.get(0).width == 10 : "la largeur doit etre de 10";
        assert dernier.get(0).height == 15 : "la hauteur doit etre de 15";

        assert dernier.get(1).width == 10 : "la largeur doit etre de 10";
        assert dernier.get(1).height == 16 : "la hauteur doit etre de 16";

        assert dernier.get(2).width == 11: "la largeur doit etre de 11";
        assert dernier.get(2).height == 15 : "la hauteur doit etre de 15";

        assert dernier.get(3).width == 11: "la largeur doit etre de 11";
        assert dernier.get(3).height == 16 : "la hauteur doit etre de 16";
    
        // Test avec un preset inexistant
        ArrayList<Dimension> presetInexistant = PresetManager.getPreset("inexistant");
        assert presetInexistant == null;
        return true;
    }
}


