package main;

import main.core.*;
import main.utils.Tuple;

public class Gol {
    public static void main(String[] args) {
        System.out.println("Début du projet");

        NormalCell cell = new NormalCell(new Tuple(0,0));
        System.out.println("Coordonnées: (" + cell.getCoordinateX() + "," + cell.getCoordinateY() + ")");
        System.out.println("En vie ? " + (cell.isAlive() ? "oui" : "non"));
        System.out.println("Nombre max de voisins: " + cell.getMaxNeighbors());
    }
}
