package main;


import java.awt.Dimension;

import main.core.*;
import main.gui.*;

public class Gol {

    
    public static void main(String[] args) {
        // System.out.println("DEBUT");
        // System.lineSeparator();

        Cell cell = new Cell(true);
        System.out.println(cell.info());
        System.out.println("-----------------");

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
        
        // for (int i = 1; i <= 10; i++) {
        //     System.out.println("-----------------");
        //     System.out.println("Gen " + i + " : ");
        //     grid.displayGrid();
        //     grid.nextGen();
        // }


        Quadtree on = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree off = new Quadtree(null, null, null, null, 0, 0, new Cell(false));


        Quadtree q1 = new Quadtree(on, on, off, on);
        Quadtree q2 = new Quadtree(off, on, on, off);
        Quadtree q3 = new Quadtree(off, off, on, on);
        Quadtree q4 = new Quadtree(on, off, off, on);
        
        Quadtree q = new Quadtree(q1, q2, q3, q4);

        HashLife hash = new HashLife(cell);

        new Grid(q).displayGrid();
        Quadtree advance = hash.advance(q, 30);

        Grid fr = new Grid(advance);
        fr.displayGrid();

        // new Window("Game Of Life");
    }
}
