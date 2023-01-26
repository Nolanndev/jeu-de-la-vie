package main;

import main.core.*;
import main.utils.Tuple;
import java.util.Random;

public class Gol {
    public static void main(String[] args) {
        System.out.println("Début du projet");

        NormalCell cell = new NormalCell(new Tuple(0,0));
        System.out.println("Coordonnées: (" + cell.getCoordinateX() + "," + cell.getCoordinateY() + ")");
        System.out.println("En vie ? " + (cell.isAlive() ? "oui" : "non"));
        System.out.println("Nombre max de voisins: " + cell.getMaxNeighbors());

        System.out.println("-----------");

        Random obj = new Random();
        int rows = obj.nextInt(20);
        int columns = obj.newInt(20);

        Grid g = new Grid(rows, columns);
        g.getColumns();
        g.getRows();
        System.out.println("--------");
        g.showGrid();
    }
}
