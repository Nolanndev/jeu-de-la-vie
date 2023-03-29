// package tests.core;

// import main.core.Quadtree;

// /* 
// Ce fichier sert à tester la classe Quadtree.
// */

// public class TestQuadtree{

//     private static Quadtree leaf;
//     private static Quadtree quad1;
//     private static Quadtree quad2;
//     private static Quadtree quad3;
//     private static Quadtree quad4;

//     public static void startTest(){
//         System.out.println("TESTS: main.utils.Quadtree");

//         testConstructor();
//         testGetters();
//         testHashCode();
//         testEquals();
//         testToString();

//         System.out.println("Tous les tests sont passes");
//     }

//     public static void testConstructor(){
//         System.out.println("Test: constructors()");
//         // leaf = new Quadtree();
//         quad1 = new Quadtree(leaf, leaf, leaf, leaf);
//         quad2 = new Quadtree(quad1, quad1, quad1, quad1);
//         quad3 = new Quadtree(quad1, quad1, quad2, quad2);
//         quad4 = new Quadtree(quad2, quad3, quad3, quad2);

//         assert leaf.getDepth() == 1 : "Probleme avec le constructeur";
//         assert quad1.getNumberAlive() == 0 : "Probleme avec le constructeur";
//         assert quad2.getNumberAlive() == 0 : "Probleme avec le constructeur";
//         assert quad3.getNumberAlive() == 0 : "Probleme avec le constructeur";
//         assert quad4.getNumberAlive() == 0 : "Probleme avec le constructeur";
//     }

//     public static void testGetters(){
//         System.out.println("Test: getters()");
//         assert quad4.getNw() == quad2 : "Probleme avec les getters";
//         assert quad4.getNe() == quad3 : "Probleme avec les getters";
//         assert quad4.getSw() == quad3 : "Probleme avec les getters";
//         assert quad4.getSe() == quad2 : "Probleme avec les getters";
//     }

//     public static void testHashCode(){
//         System.out.println("Test: hashCode()");
//         Quadtree quad1_1 = new Quadtree(leaf, leaf, leaf, leaf);
//         Quadtree quad2_1 = new Quadtree(quad1_1, quad1_1, quad1_1, quad1_1);
//         Quadtree quad3_1 = new Quadtree(quad1_1, quad1_1, quad2_1, quad2_1);
//         Quadtree quad4_1 = new Quadtree(quad2_1, quad3_1, quad3_1, quad2_1);

//         assert quad1.hashCode() == quad1_1.hashCode() : "Probleme avec hashCode";
//         assert quad2.hashCode() == quad2_1.hashCode() : "Probleme avec hashCode";
//         assert quad3.hashCode() == quad3_1.hashCode() : "Probleme avec hashCode";
//         assert quad4.hashCode() == quad4_1.hashCode() : "Probleme avec hashCode";
//     }
//     public static void testEquals(){
//         System.out.println("Test: equals()");
    
//         assert quad1.equals(quad1) : "Probleme avec equals";
//         assert quad2.equals(quad2) : "Probleme avec equals";
//         assert quad3.equals(quad3) : "Probleme avec equals";
//         assert quad4.equals(quad4) : "Probleme avec equals";
    
//         Quadtree quad1Clone = new Quadtree(leaf, leaf, leaf, leaf);
//         Quadtree quad2Clone = new Quadtree(quad1Clone, quad1Clone, quad1Clone, quad1Clone);
//         Quadtree quad3Clone = new Quadtree(quad1Clone, quad1Clone, quad2Clone, quad2Clone);
//         Quadtree quad4Clone = new Quadtree(quad2Clone, quad3Clone, quad3Clone, quad2Clone);
    
//         assert quad1.equals(quad1Clone) : "Probleme avec equals";
//         assert quad2.equals(quad2Clone) : "Probleme avec equals";
//         assert quad3.equals(quad3Clone) : "Probleme avec equals";
//         assert quad4.equals(quad4Clone) : "Probleme avec equals";
    
//         assert !quad1.equals(leaf) : "Probleme avec equals";
//         assert !quad2.equals(quad1) : "Probleme avec equals";
//         assert !quad3.equals(quad2) : "Probleme avec equals";
//         assert !quad4.equals(quad3) : "Probleme avec equals";
//     }
    
//     public static void testToString(){
//         System.out.println("Test: toString()");
    
//         assert leaf.toString().equals("O") : "Probleme avec toString";
//         assert quad1.toString().equals("_") : "Probleme avec toString";
//         assert quad2.toString().equals("_") : "Probleme avec toString";
//         assert quad3.toString().equals("_") : "Probleme avec toString";
//         assert quad4.toString().equals("_") : "Probleme avec toString";
//     }
    
// }
