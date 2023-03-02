package tests.utils;

import main.utils.Tuple;


/* 
Ce fichier servira juste à 
tester le fichier Tuple.java

*/

public class TestTuple{
    
    private static int a = 1;
    private static int b = 2;
    private static Tuple tuple1 = new Tuple(a,b);
    private static Tuple tuple2 = new Tuple(a,b);
    private static Tuple tuple3 = new Tuple(b,a);

    public static void startTest(){
        System.out.println("TESTS: main.utils.Tuple");
        testGetValue1();
        testGetValue2();
        testEquals();
        testToString();

        System.out.println("Tous les tests sont passes");
    }

    public static void testGetValue1(){
        System.out.println("Test: getValue1()");
        assert tuple1.getValue1() == a : "La valeur retournee n'est pas correcte";
        assert tuple2.getValue1() == a : "La valeur retournee n'est pas correcte";
        assert tuple3.getValue1() == b : "La valeur retournee n'est pas correcte";
    }

    public static void testGetValue2(){
        System.out.println("Test: getValue2()");
        assert tuple1.getValue2() == b : "La valeur retournee n'est pas correcte";
        assert tuple2.getValue2() == b : "La valeur retournee n'est pas correcte";
        assert tuple3.getValue2() == a : "La valeur retournee n'est pas correcte";
    }
    
    public static void testEquals(){
        System.out.println("Test: equals()");
        assert tuple1.equals(tuple2) : "Les deux tuples ne sont pas egaux";
        assert !tuple1.equals(tuple3) : "Les deux tuples sont egaux";
    }
    
    public static void testToString(){
        System.out.println("Test: toString()");
        assert tuple1.toString().equals("(1,2)") : "Probleme avec toString";
        assert tuple2.toString().equals("(1,2)") : "Probleme avec toString";
        assert tuple3.toString().equals("(2,1)") : "Probleme avec toString";
    }
}



