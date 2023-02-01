package main;

import main.core.*;
import main.utils.Tuple;

public class Gol {

    public static Grid genGrid(int size) {
        Grid grid = new Grid(size);

        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                grid.setCell(i,j, new NormalCell(new Tuple(i,j)));
            }
        }

        return grid;
    }
    
    public static void main(String[] args) {
        System.out.println("DEBUT");
        System.lineSeparator();

        NormalCell cell = new NormalCell(new Tuple(2, 3));
        System.out.println(cell.infos());
        System.out.println("-----------------");
        System.lineSeparator();
        cell.setState(false);

        Grid grid = new Grid(3);
        grid.displayGrid();
        // grid.getCell(new Tuple(2,2)).viewCoordinates();


        System.lineSeparator();
        System.out.println("FIN");
    }
}
