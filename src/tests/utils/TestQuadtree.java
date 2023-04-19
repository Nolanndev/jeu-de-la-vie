package tests.utils;

import main.core.Quadtree;
import main.core.Cell;

import java.util.Objects; 

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
        
        Quadtree leaf = new Quadtree(null, null, null, null, 0, 1, cell);
        assert leaf.hashCode() == 709 : "Premier if en erreur sur hashCode()";
    
        Quadtree leaf1 = new Quadtree(null, null, null, null, 0, 0, cell);
        assert leaf1.hashCode() == 129 : "Deuxieme if echec sur hashCode()";

        Quadtree quadtree = new Quadtree(leaf, leaf, leaf, leaf, 1, 4, cell);
        Quadtree quadtree1 = quadtree;

        assert quadtree.hashCode() == 40 : "(1) le hashcode genere n est pas le bon";
        assert quadtree1.hashCode() == quadtree.hashCode() : "(2) le hashcode genere n est pas le bon";

        Quadtree quadtree2 = new Quadtree(leaf1, leaf1, leaf1, leaf1, 1, 2, cell);
        Quadtree quadtree3 = quadtree2;
        
        assert quadtree2.hashCode() == 20 : "(3) le hashcode genere n est pas le bon";
        assert quadtree3.hashCode() == quadtree2.hashCode() : "(4) le hashcode genere est mauvais";

        Quadtree quadtreeX = quadtree; 
        
        Quadtree quadtree4 = new Quadtree(quadtree1, quadtreeX, quadtreeX, quadtree);
        Quadtree quadtree5 = new Quadtree(quadtree1, quadtree1, quadtree1, quadtree1);
        
        assert quadtree4.hashCode() == 160 : "(5) le hashcode genere n est pas le bon";
        assert quadtree5.hashCode() == 160 : "(6) le hashcode genere est mauvais";
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
