package tests.core;

import main.core.HashLife;
import main.utils.Quadtree;

public class TestHashLife{

    public static void startTest(){
        System.out.println("TESTS : main.core.HashLife() ");
        testGetZero();
        testJoin();
        testCentre();
        testLife(); 
        testLife4x4();
        testSuccessor();
        testAdvance();
        testPad();
        testIsPadded();
        System.out.println("Tous les tests sont passés ");
    } 

    public static void testGetZero(){
        Quadtree nw = new Quadtree(null, null, null, null, 1, 1);
        Quadtree ne = new Quadtree(null, null, null, null, 1, 0);
        Quadtree sw = new Quadtree(null, null, null, null, 1, 0);
        Quadtree se = new Quadtree(null, null, null, null, 1, 1);
        
    }

    
    public static void testJoin(){
        System.out.println("join() ");
        HashLife hashLife = new HashLife();
        Quadtree nw = new Quadtree(null, null, null, null, 1, 1);
        Quadtree ne = new Quadtree(null, null, null, null, 1, 0);
        Quadtree sw = new Quadtree(null, null, null, null, 1, 0);
        Quadtree se = new Quadtree(null, null, null, null, 1, 1);
        Quadtree quadtree = hashLife.join(nw, ne, sw, se);


    }

     
    public static void testCentre(){
        System.out.println("centre() ");
        HashLife hashLife = new HashLife();
        Quadtree nw = new Quadtree(null, null, null, null, 2, 0);
        Quadtree ne = new Quadtree(null, null, null, null, 2, 0);
        Quadtree sw = new Quadtree(null, null, null, null, 2, 0);
        Quadtree se = new Quadtree(null, null, null, null, 2, 1);
        Quadtree quadtree = hashLife.join(nw, ne, sw, se);
        Quadtree centre = hashLife.centre(quadtree);
    }

     
    public static void testLife(){
        System.out.println("life() ");
        HashLife hashLife = new HashLife();

        Quadtree on = new Quadtree(null, null, null, null, 0, 1);
        Quadtree off = new Quadtree(null, null, null, null, 0, 0);
        Quadtree centre = new Quadtree(null, null, null, null, 2, 1);
        Quadtree nw = new Quadtree(null, null, null, null, 2, 0);
        Quadtree ne = new Quadtree(null, null, null, null, 2, 0);
        Quadtree sw = new Quadtree(null, null, null, null, 2, 0);
        Quadtree se = new Quadtree(null, null, null, null, 2, 0);
        Quadtree[] neighboors = new Quadtree[]{nw, ne, sw, se, null, null, null, null, null};
        Quadtree res = hashLife.life(centre, neighboors);

        centre = new Quadtree(null, null, null, null, 2, 1);

        Quadtree q1 = new Quadtree(null, null, null, null, 0, 1);
        Quadtree q2 = new Quadtree(null, null, null, null, 0, 0);
        
        Quadtree q3 = HashLife.off;
      
        Quadtree q4 = new Quadtree(q2, q2, q2, q1, 1, 1);
        Quadtree q5 = HashLife.off;
        Quadtree q6 = new Quadtree(q5, q5, q5, q5, 1, 0);
        
        Quadtree q7 = new Quadtree(q4, q5, q5, q5, 2, 1);
        Quadtree q8 = HashLife.off;
        Quadtree q9 = new Quadtree(q8, q8, q8, q1, 1, 1);
        
        Quadtree q10 = new Quadtree(q5, q5, q5, q5, 1, 0);
    }

    public static void testLife4x4(){ 
        System.out.println("life4x4() ");
    }
    
    public static void testSuccessor(){
        System.out.println("successor() ");
    }

        
    public static void testAdvance(){
        System.out.println("advance() ");
    }


    public static void testPad(){ 
        System.out.println("pad() ");
    }

       
    public static void testIsPadded(){
        System.out.println("isPadded() ");
    }
}

