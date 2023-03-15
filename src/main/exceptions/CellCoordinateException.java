package main.exceptions;

public class CellCoordinateException extends Exception{
    public CellCoordinateException() {
        System.out.println("Il y a une erreur dans les coordonnées de la cellule");
    }
}
