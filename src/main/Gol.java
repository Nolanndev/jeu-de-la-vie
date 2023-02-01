package main;

import main.core.*;
import main.utils.Tuple;

public class Gol {

    
    public static void main(String[] args) {
        System.out.println("DEBUT");
        System.lineSeparator();

        NormalCell cell = new NormalCell();
        System.out.println(cell.infos());
        System.out.println("-----------------");
        cell.setState(true);

        Grid grid = new Grid(3);
        grid.setCell(0, 0, new NormalCell(true));
        grid.setCell(2, 2, new NormalCell(true));
        grid.setCell(1, 1, new NormalCell(true));
        grid.setCell(0, 2, new NormalCell(true));

        grid.getCell(new Tuple(0, 0)).setState(false);;

        System.out.println("Nombre de voisins de (1,2) : " + grid.countNeighbors(1, 2));
        
        for (int i = 1; i <= 3; i++) {
            System.out.println("-----------------");
            System.out.println("Gen " + i + " : ");
            grid.displayGrid();
            grid.nextGen();
        }


        System.out.println("FIN");
    }
}
