package tests;

import java.io.IOException;

import tests.utils.*;

import tests.core.*;



public class Test {

    public static void main(String[] args) throws IOException{

        boolean ok = true;
        System.out.println("-----TestProfileManager()----");

        TestProfileManager profileManagerTester = new TestProfileManager();
        //ok = ok && profileManagerTester.testSave();
        ok = ok && profileManagerTester.testLoad();

        System.out.println("-----TestPresetManager----");
        TestPresetManager preset = new TestPresetManager();
        ok = ok && preset.testIsName();
        ok = ok && preset.testLoad();
        ok = ok && preset.testSave();
        ok = ok && preset.testGetPreset();
        
        System.out.println("----TestQuadtree()-----");
     
        // All test OK
        TestQuadtree quadtree = new TestQuadtree();

        ok = ok && quadtree.testGetters();
        ok = ok && quadtree.testHashCode();
        ok = ok && quadtree.testEquals();
        ok = ok && quadtree.testToString();

        System.out.println("-----TestGrid()------");

       
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

        System.out.println("----TestCell()-----");

        TestCell cell = new TestCell();
        ok = ok && cell.testIsAlive();
        ok = ok && cell.testGetRadius();
        ok = ok && cell.testGetBornMaxNeighbors();
        ok = ok && cell.testGetBornMinNeighbors();
        ok = ok && cell.testGetDieMinNeighbors();
        ok = ok && cell.testGetDieMaxNeighbors();

        System.out.println("-----TestHashLife()-----");

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

