package tests;

import java.io.IOException;
import main.exceptions.*;


import tests.utils.TestProfileManager;
import tests.utils.TestQuadtree;

import tests.core.TestGrid;
import tests.core.TestHashLife;
import tests.core.TestCell;



public class Test {

    public static void main(String[] args) throws IOException{

        boolean ok = true;
        
        System.out.println("-------------");

        TestProfileManager profileManagerTester = new TestProfileManager();
        //ok = ok && profileManagerTester.testSave();
        ok = ok && profileManagerTester.testLoad();
        
        System.out.println("-------------");
     
        // All test OK
        TestQuadtree quadtree = new TestQuadtree();

        ok = ok && quadtree.testGetters();
        ok = ok && quadtree.testHashCode();
        ok = ok && quadtree.testEquals();
        ok = ok && quadtree.testToString();

        System.out.println("-------------");

       
        //All test OK
        TestGrid grid = new TestGrid();
        ok = ok && grid.testGetters();
        ok = ok && grid.testGetSize();
        ok = ok && grid.testGetWidth();
        ok = ok && grid.testGetHeight();
        ok = ok && grid.testGetRows();
        ok = ok && grid.testGetColumns();
        ok = ok && grid.testCopyBoard();
        ok = ok && grid.testCountNeighbors();
        ok = ok && grid.testGetCell();

        System.out.println("-------------");

        //All test OK
        TestCell cell = new TestCell();
        ok = ok && cell.testIsAlive();
        ok = ok && cell.testGetRadius();
        ok = ok && cell.testGetBornMaxNeighbors();
        ok = ok && cell.testGetBornMinNeighbors();
        ok = ok && cell.testGetDieMinNeighbors();
        ok = ok && cell.testGetDieMaxNeighbors();

        System.out.println("-------------");

        //TestHashLife
        TestHashLife hashLife = new TestHashLife();
        ok = ok && hashLife.testAdvance();

    
        if (ok == false){
            System.out.println("At least one test KO");
        }
        else{
            System.out.println("All test OK");
        }
    }
}

