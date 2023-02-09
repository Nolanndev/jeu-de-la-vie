package main;

import main.core.*;
import main.utils.Quadtree;

public class Gol {

    
    public static void main(String[] args) {
        System.out.println("DEBUT");
        System.lineSeparator();

        NormalCell cell = new NormalCell();
        System.out.println(cell.infos());
        System.out.println("-----------------");
        cell.setState(true);

        Grid grid = new Grid(20);
        grid.setCell(4, 1, new NormalCell(true));
        grid.setCell(4, 2, new NormalCell(true));
        grid.setCell(5, 1, new NormalCell(true));
        grid.setCell(4, 4, new NormalCell(true));
        grid.setCell(5, 4, new NormalCell(true));
        grid.setCell(6, 3, new NormalCell(true));
        grid.setCell(6, 5, new NormalCell(true));
        grid.setCell(6, 6, new NormalCell(true));


        System.out.println("Nombre de voisins de (1,2) : " + grid.countNeighbors(1, 2));
        
        for (int i = 1; i <= 10; i++) {
            System.out.println("-----------------");
            System.out.println("Gen " + i + " : ");
            grid.displayGrid();
            grid.nextGen();
        }


        HashLife hashLife = new HashLife();
        Quadtree on = new Quadtree(null, null, null, null, 0, 1);
        Quadtree off = new Quadtree(null, null, null, null, 0, 0);
 
        Quadtree q = new Quadtree(off, on ,on , on, 1, 3);
        System.out.println(q);
        Quadtree res = hashLife.centre(q);
        System.out.println(hashLife.life_4x4(res));

        System.out.println("FIN");
    }
}
