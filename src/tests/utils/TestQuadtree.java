package tests.utils;

import main.utils.Quadtree;

/* 
Ce fichier sert à tester la classe Quadtree.
*/

public class TestQuadtree{

    private static Quadtree leaf;
    private static Quadtree quad1;
    private static Quadtree quad2;
    private static Quadtree quad3;
    private static Quadtree quad4;

    public boolean testGetters() {
        System.out.println("Test: constructors()");
        leaf = new Quadtree();
        quad1 = new Quadtree(leaf, leaf, leaf, leaf);
        quad2 = new Quadtree(quad1, quad1, quad1, quad1);
        quad3 = new Quadtree(quad1, quad1, quad2, quad2);
        quad4 = new Quadtree(quad2, quad3, quad3, quad2);
        
        assert leaf.getDepth() == 1 : "Probleme avec le constructeur de feuille";
        assert leaf.getNumberAlive() == 0 : "Probleme avec le constructeur de feuille";
        
        assert quad1.getDepth() == 2 : "Probleme avec le constructeur de Quadtree de profondeur 2";
        assert quad1.getNumberAlive() == 0 : "Probleme avec le constructeur de Quadtree de profondeur 2";
        
        assert quad2.getDepth() == 3 : "Probleme avec le constructeur de Quadtree de profondeur 3";
        assert quad2.getNumberAlive() == 0 : "Probleme avec le constructeur de Quadtree de profondeur 3";
        
        assert quad3.getDepth() == 3 : "Probleme avec le constructeur de Quadtree de profondeur 3";
        assert quad3.getNumberAlive() == 0 : "Probleme avec le constructeur de Quadtree de profondeur 3";

        assert quad4.getDepth() == 4 : "Probleme avec le constructeur de Quadtree de profondeur 4";
        assert quad4.getNumberAlive() == 0 : "Probleme avec le constructeur de Quadtree de profondeur 4";
    
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
    
        assert quad4.getNw() == quad2 : "Probleme avec le constructeur";
        assert quad4.getNe() == quad3 : "Probleme avec le constructeur";
        assert quad4.getSe() == quad3 : "Probleme avec le constructeur";
        assert quad4.getSw() == quad2 : "Probleme avec le constructeur";

        return true;
    }
    

    public boolean testHashCode(){
        System.out.println("Test: hashCode()");
        Quadtree quad1_1 = new Quadtree(leaf, leaf, leaf, leaf);
        Quadtree quad2_1 = new Quadtree(quad1_1, quad1_1, quad1_1, quad1_1);
        Quadtree quad3_1 = new Quadtree(quad1_1, quad1_1, quad2_1, quad2_1);
        Quadtree quad4_1 = new Quadtree(quad2_1, quad3_1, quad3_1, quad2_1);
    
        assert quad1.hashCode() == quad1_1.hashCode() : "1 Probleme avec hashCode";
        assert quad2.hashCode() == quad2_1.hashCode() : "2 Probleme avec hashCode";
        assert quad3.hashCode() == quad3_1.hashCode() : "3 Probleme avec hashCode";
        assert quad4.hashCode() == quad4_1.hashCode() : "4 Probleme avec hashCode";
    
        Quadtree quad5 = new Quadtree(quad1_1, quad1_1, quad2_1, quad3_1);
        Quadtree quad6 = new Quadtree(quad1_1, quad1_1, quad2_1, quad4_1);
        assert quad5.hashCode() == quad6.hashCode() : "5 Probleme avec hashCode";
    
        Quadtree quad7 = new Quadtree(quad1_1, quad1_1, quad2_1, quad3_1);
        Quadtree quad8 = new Quadtree(quad1_1, quad1_1, quad2_1, quad3_1);
        assert quad7.hashCode() == quad8.hashCode() : "6 Probleme avec hashCode";
    
        return true;
    }
    

    public boolean testEquals(){
        System.out.println("Test: equals()");

        leaf = new Quadtree();
        quad1 = new Quadtree(leaf, leaf, leaf, leaf);
        quad2 = new Quadtree(quad1, quad1, quad1, quad1);
        quad3 = new Quadtree(quad1, quad1, quad2, quad2);
        quad4 = new Quadtree(quad2, quad3, quad3, quad2);
    
        assert quad1.equals(quad1) : "Probleme avec equals";
        assert quad2.equals(quad2) : "Probleme avec equals";
        assert quad3.equals(quad3) : "Probleme avec equals";
        assert quad4.equals(quad4) : "Probleme avec equals";
    
        Quadtree quad1Clone = quad1;
        Quadtree quad2Clone = new Quadtree(quad1Clone, quad1Clone, quad1Clone, quad1Clone);
        Quadtree quad3Clone = new Quadtree(quad1Clone, quad1Clone, quad2, quad2);

        Quadtree quad4Clone = new Quadtree(quad2, quad3, quad3, quad2);
    
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
    
    public boolean testToString() {
        System.out.println("Test: toString()");
    
        Quadtree quad1 = new Quadtree(new Quadtree(), new Quadtree(), new Quadtree(), new Quadtree());
        Quadtree quad2 = new Quadtree(quad1, quad1, quad1, quad1);
    
        String output1 = "depth : 2,  nbAlive: 0";
        String output2 = "depth : 3,  nbAlive: 0";
    
        assert quad1.toString().equals(output1) : "Probleme avec toString()";
        assert quad2.toString().equals(output2) : "Probleme avec toString()";
    
        return true;
    }
    
    
}
