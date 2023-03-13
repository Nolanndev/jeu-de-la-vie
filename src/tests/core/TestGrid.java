package tests.core;

import java.util.Arrays;
import java.awt.Dimension;

import main.core.Cell;
import main.core.Grid;
import main.core.NormalCell;
import main.core.BasicCell;


/* test la classe Grid */

public class TestGrid{

    private static Grid grid1;
    private static Grid grid2;
    private static Grid grid3;
    private static Grid grid4;
    private static Grid grid5;

    private static Cell[] row1;
    private static Cell[] row2;

    private static Dimension tuple1;
    private static Dimension tuple2;
    private static Dimension tuple3;
    private static Dimension tuple4;
    private static Dimension tuple5;
    private static Dimension tuple6;


    public static void startTest(){
        System.out.println("TESTS: main.core.Grid");

        testConstructor();
        testGetSize();
        testGetWidth();
        testGetHeight();
        testGetRows();
        testGetColumns();
        testCopyBoard();
        testCountNeighbors();
        testGetCell();

        System.out.println("Tous les tests sont passes");
    }

    public static void testConstructor(){
        System.out.println("Test: constructor");
        tuple1 = new Dimension(5, 10);
        tuple2 = new Dimension(10, 5);

        grid1 = new Grid(tuple1);
        grid2 = new Grid(tuple2);

        assert grid1.getSize().equals(new Dimension(5, 10)) : "Probleme avec la taille de la grille";
        assert grid2.getSize().equals(new Dimension(10, 5)) : "Probleme avec la taille de la grille";

    }

    public static void testGetSize(){
        System.out.println("Test: getSize()");
        assert grid1.getSize().equals(new Dimension(5, 10)) : "Probleme avec la taille de la grille";
        assert grid2.getSize().equals(new Dimension(10, 5)) : "Probleme avec la taille de la grille";

    }

    public static void testGetWidth(){
        System.out.println("Test: getWidth()");
        assert grid1.getWidth() == 5 : "Probleme avec la largeur de la grille";
        assert grid2.getWidth() == 10 : "Probleme avec la largeur de la grille";

    }

    public static void testGetHeight(){
        System.out.println("Test: getHeight()");
        assert grid1.getHeight() == 10 : "Probleme avec la hauteur de la grille";
        assert grid2.getHeight() == 5 : "Probleme avec la hauteur de la grille";

    }

    public static void testGetRows(){
        System.out.println("Test: getRows()");
        grid3 = grid1;
        grid4 = grid2;

        for (int i = 0; i < 10 ;i++){
            for (int j = 0; j < 5; j++){
                grid3.setCell(i, j, new NormalCell(true));
            }
        }

        for (int i = 0; i < 5 ;i++){
            for (int j = 0; j < 10; j++){
                grid4.setCell(i, j, new NormalCell(true));
            }
        }

        row1 = grid3.getRows(3);
        row2 = grid4.getRows(1);

        assert row1.length == 5 : "Probleme avec le nombre de lignes retournees";
        assert row1.length == 10 : "Le test est bon";

        assert row2.length == 10 : "Probleme avec le nombre de lignes retournees";
        assert row2.length == 5 : "Le test est bon";

    }

    public static void testGetColumns(){
        System.out.println("Test: getColumns()");
        row1 = grid1.getColumns(2);
        row2 = grid2.getColumns(4);

        assert row1.length == 10 : "Le test est bon";
        assert row1.length == 5 : "Probleme avec le nombre de colonnes retournees";

        assert row2.length == 5 : "Le test est bon";
        assert row2.length == 10 : "Probleme avec le nombre de colonnes retournees";
    }


    public static void testCopyBoard(){
        System.out.println("Test: copyBoard()");
        tuple3 = new Dimension(3, 3);
        Grid grid = new Grid(tuple3);

        // ajout de cellule à grid 
        grid.setCell(0, 0, new NormalCell(true)); 
        grid.setCell(1, 1, new NormalCell(true));
        grid.setCell(2, 2, new NormalCell(true));

        Cell[][] copy = grid.copyBoard();
        boolean is_equal = true;

        for (int i = 0; i < grid.getHeight(); i++){
            for (int j = 0; j < grid.getWidth(); j++){
                tuple4 = new Dimension(i, j);
                if (grid.getCell(tuple4).isAlive() != copy[i][j].isAlive()){
                    is_equal = false;
                    break;
                }
            }
            if (!is_equal){
                break;
            }
        }
        assert is_equal : "copie de la grille incorrecte";
    }

    public static void testCountNeighbors(){
        System.out.println("Test: countNeighbors()");
        tuple5 = new Dimension(2,2);

        grid5 = new Grid(tuple5);
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 2; j++){
                grid5.setCell(i, j, new NormalCell(true));
            }
        }

        NormalCell cell = new NormalCell(0, 3, 10, true);
        grid5.setCell(0, 0, cell);

        assert grid5.countNeighbors(1, 1) == 10 : "Le nombre de voisins n'est pas correct";
        assert grid5.countNeighbors(1, 1) == 3 : "Le nombre de voisins est correct";
        assert grid5.countNeighbors(0, 0) == 10 : "Le rayon est grand mais le nombre de voisins est bon";
    }

    public static void testGetCell(){
        System.out.println("Test: getCell()");
        tuple5 = new Dimension(2,2);

        grid5 = new Grid(tuple5);
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 2; j++){
                grid5.setCell(i, j, new NormalCell(0, 3, 4, true));
            }
        }

        tuple6 = new Dimension(0, 0);

        assert grid5.getCell(tuple6) == new NormalCell(0, 3, 4, true): "La cellule est correcte";
    
        assert grid5.getCell(tuple6) == new NormalCell(1, 3, 4, true): "minNeighbors est incorrecte";
        assert grid5.getCell(tuple6) == new NormalCell(0, 5, 4, true): "maxNeighbors est incorrecte";
        assert grid5.getCell(tuple6) == new NormalCell(0, 3, 5, true): "radius est incorrecte";
        assert grid5.getCell(tuple6) == new NormalCell(0, 3, 5, false): "alive est incorrecte";
    }
}    
