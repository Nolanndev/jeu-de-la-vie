package tests.utils;

import main.utils.Quadtree;

public class TestQuadtree{
    private static Quadtree ne = new Quadtree();
    private static Quadtree nw = new Quadtree();
    private static Quadtree se = new Quadtree();
    private static Quadtree sw = new Quadtree();

    private static Quadtree quadtree1 = new Quadtree(ne, nw, se, sw, 3, 1);
    private static Quadtree quadtree2 = new Quadtree(ne, nw, se, sw, 1, 0);
    private static Quadtree quadtreeNull = new Quadtree();

    public static void startTest(){
        System.out.println("TESTS: main.utils.Quadtree");
        testGetNe();
        testGetNw();
        testGetSe();
        testGetSw();
        testGetDepth();
        testGetNumberAlive();
        testIsLeaf();
        testHashCode();
        testToString();

        System.out.println("Tous les tests sont passes");
    }

    public static void testGetNe(){
        System.out.println("Test: getNe()");
        String error_message = "Probleme avec le Quadtree Ne";

        assert quadtree1.getNe() == null : error_message;
        assert quadtree2.getNe() == null : error_message;
        assert quadtreeNull.getNe() == null : error_message;
    }


    public static void testGetNw(){
        System.out.println("Test: getNw()");
        String error_message = "Probleme avec le Quadtree Nw";

        assert quadtree1.getNw() == null : error_message;
        assert quadtree2.getNw() == null : error_message;
        assert quadtreeNull.getNw() == null : error_message;     
    }

    public static void testGetSe(){
        System.out.println("Test: getSe()");
        String error_message = "Probleme avec le Quadtree Se";

        assert quadtree1.getSe() == null : error_message;
        assert quadtree2.getSe() == null : error_message;
        assert quadtreeNull.getSe() == null : error_message;
    }

    public static void testGetSw(){
        System.out.println("Test: getSw()");
        String error_message = "Probleme avec le Quadtree Sw";

        assert quadtree1.getSw() == null : error_message;
        assert quadtree2.getSw() == null : error_message;
        assert quadtreeNull.getSw() == null : error_message;
    }

    public static void testGetDepth(){
        System.out.println("Test: getDepth()");
        String error_message = "Probleme avec la profondeur";

        assert quadtree1.getDepth() == 3 : error_message;
        assert quadtree2.getDepth() == 1 : error_message;
        assert quadtreeNull.getDepth() == 1 : error_message;
    }

    public static void testGetNumberAlive(){
        System.out.println("Test: getNumberAlive()");
        String error_message = "Probleme avec getNumberAlive()";

        assert quadtree1.getNumberAlive() == 1 : error_message;
        assert quadtree2.getNumberAlive() == 0 : error_message;
        assert quadtreeNull.getNumberAlive() == 0 : error_message;
    }

    public static void testIsLeaf(){
        System.out.println("Test: isLeaf()");
        String error_message = "Probleme avec isLeaf()";

        assert quadtree1.isLeaf() == false : error_message;
        assert quadtree2.isLeaf() == true : error_message;
        assert quadtreeNull.isLeaf() == true : error_message;
    }

    public static void testHashCode(){
        System.out.println("Test: hashCode()");
        String error_message = "Probleme avec le hashCode()";
        
        assert quadtree1.hashCode() == 678 : error_message;
        assert quadtree1.hashCode() == 678 : error_message;
        assert quadtreeNull.hashCode() == 98 : error_message;
    }

    public static void testToString(){
        System.out.println("Test: toString()");
        String error_message = "Probleme avec toString()";

        assert quadtree1.toString() == "depth : " + quadtree1.getDepth() +  "nbAlive: " + quadtree1.getNumberAlive() : error_message;
        assert quadtree2.toString() == "depth : " + quadtree2.getDepth() +  "nbAlive: " + quadtree2.getNumberAlive() : error_message;
        assert quadtreeNull.toString() == "depth : " + quadtreeNull.getDepth() +  "nbAlive: " + quadtreeNull.getNumberAlive() : error_message;
    }
}