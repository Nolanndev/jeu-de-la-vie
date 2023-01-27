package main;

import main.core.*;
import main.utils.Tuple;

public class Gol {
    public static void main(String[] args) {
        System.out.println("DEBUT");
        System.lineSeparator();

        NormalCell cell = new NormalCell(new Tuple(2, 3));
        System.out.println(cell.infos());
        System.out.println("-----------------");
        System.lineSeparator();

        Grid grid = new Grid(3, 3);
        grid.displayGrid();

        System.lineSeparator();
        System.out.println("FIN");
    }
}
