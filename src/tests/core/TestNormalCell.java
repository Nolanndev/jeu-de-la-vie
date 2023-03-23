package tests.core;

import main.core.Cell;

/* 
Ce fichier servira juste à 
tester le fichier NormalCell.java
*/

public class TestNormalCell{
    private static Cell cell = new Cell(true);

    public static void startTest(){
        System.out.println("TESTS: main.core.NormalCell");

        testIsAlive();
        testGetRadius();
        // testGetMinNeighbors();
        // testGetMaxNeighbors();
        // testGetMinNeighbors();

        System.out.println("Tous les tests sont passes");
    }

    public static void testIsAlive(){
        System.out.println("Test: isAlive() //true");
        assert cell.isAlive() == true: "Le test est correcte (cellule vivante)";
        assert cell.isAlive() == false: "Le test est incorrecte (cellule morte alors que vivante)";

    }

    public static void testGetRadius(){
        System.out.println("Test: getRadius");
        assert cell.getRadius() == 1 : "Le rayon est correct (=1)";
        assert cell.getRadius() == 3 : "Le rayon n'est pas correct (=3)";
    }
    
    // public static void testGetMinNeighbors(){
    //     System.out.println("Test: getMinNeighbors()");
    //     assert cell.getMinNeighbors() == 0 : "minNeighbors est correct (=0)";
    //     assert cell.getMinNeighbors() == 1 : "minNeighbors n'est pas correct (=1)";
    // }
    
    // public static void testGetMaxNeighbors(){
    //     System.out.println("Test: getMaxNeighbors()");
    //     assert cell.getMaxNeighbors() == 3 : "maxNeighbors est correct (=3)";
    //     assert cell.getMaxNeighbors() == 1 : "maxNeighbors n'est pas correct (=1)";
    // }
}