package tests.utils;

import main.core.Quadtree;
import main.core.Cell;

/* 
Ce fichier sert à tester la classe Quadtree.
*/

public class TestQuadtree{

    private static Quadtree leaf;
    private static Quadtree quad1;
    private static Quadtree quad2;
    private static Quadtree quad3;
    private static Quadtree quad4;
    private static Cell cell; 

    public boolean testGetters(){
        System.out.println("Test: constructors X getters");
        cell = new Cell(true);

        leaf = new Quadtree(null, null, null, null, 0, 1, cell);

        quad1 = new Quadtree(leaf, leaf, leaf, leaf, 2, 1, cell);
        quad2 = new Quadtree(quad1, quad1, quad1, quad1, 3, 0, cell);
        quad3 = new Quadtree(quad1, quad1, quad2, quad2, 3, 0, cell);
        
        assert leaf.getDepth() == 0 : "Probleme avec leaf,  getDeph";
        assert leaf.getNumberAlive() == 1 : "Probleme avec leaf,  getNumberAlive";
        assert leaf.isLeaf() == true : "Probleme avec leaf,  isLeaf";
        
        assert quad1.getDepth() == 2 : "Probleme avec getDepth Quadtree de profondeur 2";
        assert quad1.getNumberAlive() == 1 : "Probleme avec getNumberAlive de Quadtree de profondeur 2";
        assert quad1.isLeaf() == false : "Probleme avec quad1, isLeaf";
        
        assert quad2.getDepth() == 3 : "Probleme avec getDepth de Quadtree de profondeur 3";
        assert quad2.getNumberAlive() == 0 : "Probleme avec getNumberAlive de Quadtree de profondeur 3";
        assert quad2.isLeaf() == false : "Probleme avec quad2, isLeaf";
        
        assert quad3.getDepth() == 3 : "Probleme avec getDepth de Quadtree de profondeur 3";
        assert quad3.getNumberAlive() == 0 : "Probleme avec getNumberAlive de Quadtree de profondeur 3";
        assert quad3.isLeaf() == false : "Probleme avec quad3, isLeaf";

    
        // Test sur les enfants
        assert quad1.getNw() == leaf : "Probleme avec le constructeur de Quadtree de profondeur 2";
        assert quad1.getNe() == leaf : "Probleme avec le constructeur de Quadtree de profondeur 2";
        assert quad1.getSw() == leaf : "Probleme avec le constructeur de Quadtree de profondeur 2";
        assert quad1.getSe() == leaf : "Probleme avec le constructeur de Quadtree de profondeur 2";
    
        assert quad2.getNw() == quad1 : "Probleme avec le constructeur de Quadtree de profondeur 3";
        assert quad2.getNe() == quad1 : "Probleme avec le constructeur de Quadtree de profondeur 3";
        assert quad2.getSw() == quad1 : "Probleme avec le constructeur de Quadtree de profondeur 3";
        assert quad2.getSe() == quad1 : "Probleme avec le constructeur de Quadtree de profondeur 3";
    
        assert quad3.getNw() == quad1 : "Probleme avec le constructeur de Quadtree de profondeur 3";
        assert quad3.getNe() == quad1 : "Probleme avec le constructeur de Quadtree de profondeur 3";
        assert quad3.getSw() == quad2 : "Probleme avec le constructeur de Quadtree de profondeur 3";
        assert quad3.getSe() == quad2 : "Probleme avec le constructeur de Quadtree de profondeur 3";

        return true;
    }
    

    public boolean testHashCode(){
        System.out.println("Test: hashCode()");
        leaf = new Quadtree(null, null, null, null, 0, 1, cell);


        quad1 = new Quadtree(leaf, leaf, leaf, leaf, 2, 1, cell);
        quad2 = new Quadtree(quad1, quad1, quad1, quad1, 3, 0, cell);
        quad3 = new Quadtree(quad1, quad1, quad2, quad2, 3, 0, cell);

        Quadtree quad1_1 = new Quadtree(leaf, leaf, leaf, leaf, 2, 1, cell);
        Quadtree quad2_1 = new Quadtree(quad1_1, quad1_1, quad1_1, quad1_1, 3, 0, cell);
        Quadtree quad3_1 = new Quadtree(quad1_1, quad1_1, quad2_1, quad2_1, 3, 0, cell);
    
        assert quad1.hashCode() == quad1_1.hashCode() : "1 Probleme avec hashCode";
        assert quad2.hashCode() == quad2_1.hashCode() : "2 Probleme avec hashCode";
        assert quad3.hashCode() == quad3_1.hashCode() : "3 Probleme avec hashCode";
    
        Quadtree quad5 = new Quadtree(quad1_1, quad1_1, quad2_1, quad3_1, 0, 1, cell);
        Quadtree quad6 = new Quadtree(quad1_1, quad1_1, quad2_1, quad3_1, 0, 1, cell);

        assert quad5.hashCode() == quad6.hashCode() : "5 Probleme avec hashCode";
    
        Quadtree quad7 = new Quadtree(quad1_1, quad1_1, quad2_1, quad3_1, 0, 1, cell);
        Quadtree quad8 = new Quadtree(quad1_1, quad1_1, quad2_1, quad3_1, 0, 1, cell);
        assert quad7.hashCode() == quad8.hashCode() : "6 Probleme avec hashCode";
    
        return true;
    }
    

    public boolean testEquals(){
        System.out.println("Test: equals()");

        leaf = new Quadtree(null, null, null, null, 0, 1, cell);
        quad1 = new Quadtree(leaf, leaf, leaf, leaf, 0, 1, cell);
        quad2 = new Quadtree(quad1, quad1, quad1, quad1, 0, 1, cell);
        quad3 = new Quadtree(quad1, quad1, quad2, quad2, 0, 1, cell);
        quad4 = new Quadtree(quad2, quad3, quad3, quad2, 0, 1, cell);
    
        assert quad1.equals(quad1) : "Probleme avec equals";
        assert quad2.equals(quad2) : "Probleme avec equals";
        assert quad3.equals(quad3) : "Probleme avec equals";
        assert quad4.equals(quad4) : "Probleme avec equals";
    
        Quadtree quad1Clone = quad1;
        Quadtree quad2Clone = new Quadtree(quad1Clone, quad1Clone, quad1Clone, quad1Clone, 0, 1, cell);
        Quadtree quad3Clone = new Quadtree(quad1Clone, quad1Clone, quad2, quad2, 0, 1, cell);

        Quadtree quad4Clone = new Quadtree(quad2, quad3, quad3, quad2, 0, 1, cell);
    
        assert quad1.equals(quad1Clone) : "Probleme avec equals";
        assert quad2.equals(quad2Clone) : "Probleme avec equals";
        assert quad3.equals(quad3Clone) : "Probleme avec equals";
        assert quad4.equals(quad4Clone) : "Probleme avec equals";
    
        assert !quad1.equals(leaf) : "Probleme avec equals";
        assert !quad2.equals(quad1) : "Probleme avec equals";
        assert !quad3.equals(quad2) : "Probleme avec equals";
        assert !quad4.equals(quad3) : "Probleme avec equals";
        
        return true;
    }
    
    public boolean testToString(){
        System.out.println("Test: toString()");

        leaf = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree quad1 = new Quadtree(leaf, leaf, leaf, leaf, 2, 0, cell);
        Quadtree quad2 = new Quadtree(quad1, quad1, quad1, quad1, 3, 1, cell);
    
        String output1 = "depth : 2,  nbAlive: 0";
        String output2 = "depth : 3,  nbAlive: 1";
    
        assert quad1.toString().equals(output1) : "Probleme avec toString()";
        assert quad2.toString().equals(output2) : "Probleme avec toString()";
    
        return true;
    }
    
 
}
