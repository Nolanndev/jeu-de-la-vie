package tests;

import main.utils.Tuple;
import main.core.*;

import tests.utils.TestTuple;
import tests.core.TestGrid;
import tests.core.TestNormalCell;

/*
Ce fichier sert a tester toutes les fonctions du projet, il sera donc
MAJ regulierement 

ant compil
java -cp bin/ tests.Test
*/


public class Test{

    public static void main(String[] args){

        TestTuple.startTest();
        TestNormalCell.startTest();
        // --- test ok, en dessous plus trop =')
        TestGrid.startTest();
    }
    
}
