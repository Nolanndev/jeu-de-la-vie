package tests;

import main.core.*;

import tests.utils.TestQuadtree;

import tests.core.TestGrid;
import tests.core.TestNormalCell;
import tests.core.TestHashLife;

/*
Ce fichier sert a tester toutes les fonctions du projet, il sera donc
MAJ regulierement 

ant compil
java -cp bin/ tests.Test
*/


public class Test{

    public static void main(String[] args){

        TestNormalCell.startTest();
        TestGrid.startTest();
        TestQuadtree.startTest(); // TestQuadtree à modifier

        // --- test ok au dessus, en dessous plus trop =')
        TestHashLife.startTest();    
    }
    
}
