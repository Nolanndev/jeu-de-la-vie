package tests.core;

import main.core.Cell;

/* 
Ce fichier servira juste à 
tester le fichier NormalCell.java
*/

public class TestCell{

    public boolean testIsAlive(){

        System.out.println("Test: isAlive()");
        Cell cell = new Cell(true);
        Cell cell1 = new Cell(false);

        assert cell.isAlive() == true: "La cellule devrait être vivante";
        assert cell.isAlive() != false: "La cellule devrait être vivante";

        assert cell1.isAlive() == false: "La cellule devrait être morte";
        assert cell1.isAlive() != true: "La cellule devrait être vivante";
        return true;
    }

    public boolean testGetRadius(){
        System.out.println("Test: getRadius()");
        Cell cell = new Cell(1, 1, 0, 3, 1, true);
        Cell cell2 = new Cell(1, 1, 0, 1, 2, true);
        Cell cell3 = new Cell(1, 1, 0, 1, 5, true);
        Cell cell4 = new Cell(1, 1, 0, 1, 1, true);


        assert cell.getRadius() == 1 : "Le rayon est correct (=1)";
        assert cell.getRadius() != 3 : "Le rayon n'est pas correct (=3)";

        assert cell2.getRadius() == 2 : "Le rayon est correct (=2)";
        assert cell3.getRadius() == 5 : "Le rayon est correct (=5)";
        assert cell4.getRadius() == 1 : "Le rayon est correct (=0)";

        return true;
    }
    
    
    public boolean testGetBornMinNeighbors(){
        //maxNeighbors > minNeighbors => avoid triggering setMaxNeighbors
        System.out.println("Test: getBornMinNeighbors()");

        Cell cell1 = new Cell(0, 1, 0, 2, 1, true);
        Cell cell2 = new Cell(1, 2, 0, 3, 0, true); 
        Cell cell3 = new Cell(2, 3, 0, 4, 0, true); 
        
        assert cell1.getBornMinNeighbors() == 0 : "minNeighbors est correct (=0)";
        assert cell2.getBornMinNeighbors() == 1 : "minNeighbors est correct (=1)";
        assert cell3.getBornMinNeighbors() == 2 : "minNeighbors est correct (=2)";
    
        return true;
    }
    
    
    public boolean testGetBornMaxNeighbors(){
        System.out.println("Test: getBornMaxNeighbors()");
        
        Cell cell1 = new Cell(0, 3, 0, 2, 1, true);
        assert cell1.getBornMaxNeighbors() == 3 : "maxNeighbors est correct (=3)";
    
        Cell cell2 = new Cell(0, 1, 0, 2, 1, true);
        assert cell2.getBornMaxNeighbors() == 1 : "maxNeighbors est correct (=1)";
    
        Cell cell3 = new Cell(0, 5, 0, 2, 1, true);
        assert cell3.getBornMaxNeighbors() == 5 : "maxNeighbors est correct (=5)";
    
        return true;
    }

    public boolean testGetDieMinNeighbors(){
        System.out.println("Test: dieMinNeighbors()");
        
        Cell cell1 = new Cell(0, 0, 3, 5, 1, true);
        assert cell1.getDieMinNeighbors() == 3 : "maxNeighbors est correct (=3)";
    
        Cell cell2 = new Cell(0, 1, 1, 2, 1, true);
        assert cell2.getDieMinNeighbors() == 1 : "maxNeighbors est correct (=1)";
    
        Cell cell3 = new Cell(0, 0, 5, 6, 1, true);
        assert cell3.getDieMinNeighbors() == 5 : "maxNeighbors est correct (=5)";
    
        return true;
    }

    public boolean testGetDieMaxNeighbors(){
        System.out.println("Test: dieMaxNeighbors()");
        
        Cell cell1 = new Cell(0, 0, 3, 5, 1, true);
        assert cell1.getDieMaxNeighbors() == 5 : "maxNeighbors est correct (=3)";
    
        Cell cell2 = new Cell(0, 1, 1, 2, 1, true);
        assert cell2.getDieMaxNeighbors() == 2 : "maxNeighbors est correct (=1)";
    
        Cell cell3 = new Cell(0, 0, 5, 6, 1, true);
        assert cell3.getDieMaxNeighbors() == 6 : "maxNeighbors est correct (=5)";
    
        return true;
    }
    
    
}