package main;


import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.RunElement;

import main.core.*;
import main.gui.*;
import main.utils.ProfileManager;

public class Gol{

    
    public static void main(String[] args){
        // System.out.println("DEBUT");
        // System.lineSeparator();

        Cell cell = new Cell(true);
        // System.out.println(cell.info());
        // System.out.println("-----------------");

        // Grid grid = new Grid(new Dimension(10,20));
        // grid.setCell(0, 0, new Cell(true));
        // grid.setCell(4, 1, new Cell(true));
        // grid.setCell(4, 2, new Cell(true));
        // grid.setCell(5, 1, new Cell(true));
        // grid.setCell(4, 4, new Cell(true));
        // grid.setCell(5, 4, new Cell(true));
        // grid.setCell(6, 3, new Cell(true));
        // grid.setCell(6, 5, new Cell(true));
        // grid.setCell(6, 6, new Cell(true));
        // grid.setCell(9, 19, new Cell(true));

        // System.out.println(grid.getCell(new Dimension(4,1)) == grid.getCell(4,1));
        // System.out.println("Nombre de voisins de (4,2) : " + grid.countNeighbors(0, 2));
        
        // Quadtree on = new Quadtree(null, null, null, null, 0, 1, new Cell(true));
        // Quadtree off = new Quadtree(null, null, null, null, 0, 0, new Cell(false));


        // Quadtree q1 = new Quadtree(on, on, off, on);
        // Quadtree q2 = new Quadtree(off, on, on, off);
        // Quadtree q3 = new Quadtree(off, off, on, on);
        // Quadtree q4 = new Quadtree(on, off, off, on);
        
        // Quadtree q = new Quadtree(q1, q2, q3, q4);

        // HashLife hash = new HashLife(cell);

        
        // new Grid(q).displayGrid();
        // Quadtree advance = hash.advance(q, 260);

        // Grid fr = new Grid(advance);
        // fr.displayGrid();

        // new Window("Game Of Life");

        // HashMap<String, HashMap<String, String>> mapLoad = ProfileManager.load();
        // System.out.println(mapLoad);
        // for (String id: mapLoad.keySet()) {
        //         System.out.println(id);
        //         for (String key: mapLoad.get(id).keySet()) {
        //                 System.out.println(key + " : " + mapLoad.get(id).get(key));
        //             }
        //             System.out.println();
        // }


        Quadtree on = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree off = new Quadtree(null, null, null, null, 0, 0, new Cell(false));

        Quadtree q1 = new Quadtree(on, on, off, on);
        Quadtree q2 = new Quadtree(off, on, on, off);
        Quadtree q3 = new Quadtree(off, off, on, on);
        Quadtree q4 = new Quadtree(on, off, off, on);
        
        // HashMap<String, HashMap<String, String>> mapSave = new HashMap<>();
        // HashMap<String,String> values = new HashMap<>();
        // values.put("RADIUS","1");
        // values.put("NUMBER-OD-ITERATION","10");
        // values.put("BEGIN-EVOLUTION-TO-ITERATION","0");
        // values.put("NEIGHBORS-BIRTH-MIN","2");
        // values.put("NEIGHBORS-DEATH-MIN","2");
        // values.put("DELAY","500");
        // values.put("INFINITE-EVOLUTION","true");
        // values.put("NAME","default");
        // values.put("NEIGHBORS-BIRTH-MAX","3");
        // values.put("NEIGHBORS-DEATH-MAX","3");
        // mapSave.put("b2dcdde9-794d-4a9e-bc3c-837207e33778",values);

        // System.out.println(mapSave);
        // System.out.println(mapLoad.equals(mapSave));
        // System.out.println(Boolean.toString(true));
        // System.out.println("ECRITURE");
        // ProfileManager.save(mapSave);

       //ProfileManager.save(ProfileManager.load());

        new Window("Game Of Life");
    }
}
