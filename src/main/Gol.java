package main;


import java.io.IOException;
import java.util.HashMap;

import java.awt.Dimension;

import main.core.*;
import main.utils.ProfileManager;
import main.utils.Quadtree;
import main.gui.*;

public class Gol {

    
    public static void main(String[] args) {
        // System.out.println("DEBUT");
        // System.lineSeparator();

        Cell cell = new Cell(false);
        System.out.println(cell.info());
        System.out.println("-----------------");

        Grid grid = new Grid(new Dimension(20,20));
        grid.setCell(4, 1, new Cell(true));
        grid.setCell(4, 2, new Cell(true));
        grid.setCell(5, 1, new Cell(true));
        grid.setCell(4, 4, new Cell(true));
        grid.setCell(5, 4, new Cell(true));
        grid.setCell(6, 3, new Cell(true));
        grid.setCell(6, 5, new Cell(true));
        grid.setCell(6, 6, new Cell(true));

        System.out.println(grid.getCell(new Dimension(4,1)) == grid.getCell(4,1));
        System.out.println("Nombre de voisins de (1,2) : " + grid.countNeighbors(1, 2));
        
        for (int i = 1; i <= 10; i++) {
            System.out.println("-----------------");
            System.out.println("Gen " + i + " : ");
            grid.displayGrid();
            grid.nextGen();
        }


        HashLife hashLife = new HashLife();
        Quadtree q1 = hashLife.join(HashLife.on, HashLife.on, HashLife.off, HashLife.on);
        Quadtree q2 = hashLife.join(HashLife.off, HashLife.on, HashLife.on, HashLife.off);
        Quadtree q3 = hashLife.join(HashLife.off, HashLife.off, HashLife.on, HashLife.on);
        Quadtree q4 = hashLife.join(HashLife.on, HashLife.off, HashLife.off, HashLife.on);
        
        Quadtree q = hashLife.join(q1, q2, q3, q4);
        System.out.println(q1);
        System.out.println(hashLife.advance(q,389));
        System.out.println("FIN");

        new Window("Test");

        

    }
}
