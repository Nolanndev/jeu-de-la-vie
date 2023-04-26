package main;

import java.awt.Dimension;

import main.core.Cell;
import main.core.Grid;
import main.core.HashLife;
import main.core.Quadtree;
import main.gui.*;

public class Gol{

    
    public static void main(String[] args){
        Cell cell = new Cell(3,3,2,3,1,false);

        Grid grid = new Grid(new Dimension(4, 4));
        grid.getCell(0, 0).setState(true);
        grid.getCell(1, 0).setState(true);
        grid.getCell(2, 0).setState(true);
        grid.getCell(3, 0).setState(true);
        
        grid.getCell(1, 1).setState(true);
        grid.getCell(3, 1).setState(true);
        
        grid.getCell(1, 3).setState(true);
        grid.getCell(3, 3).setState(true);

        grid.getCell(0, 2).setState(true);
        grid.getCell(1, 2).setState(true);
        grid.getCell(2, 2).setState(true);
        grid.getCell(3, 2).setState(true);
        
        System.out.println("Exemple de hashlife avec des Quadtree");
        System.out.println("----------");
        System.out.println("Generation 0 : ");
        grid.displayGrid();

        HashLife hash = new HashLife(cell);
        Quadtree tree = new Quadtree(grid);
        tree = hash.advance(tree, 60);
        System.out.println("Generation 60 : ");
        new Grid(tree).displayGrid();


        new Window("Game Of Life");
    }
}
