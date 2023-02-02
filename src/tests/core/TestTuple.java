package tests.core;

import main.utils.Tuple;


/* 
Ce fichier servira juste à 
tester le fichier Tuple.java

*/

public class TestTuple {

    public boolean testGetValue1() {
        Tuple tuple = new Tuple(4,5);
        return tuple.getValue1() == 4;
    }

    public boolean testGetValue2() {
        Tuple tuple = new Tuple(4,5);
        return tuple.getValue2() == 5;
    }

    public boolean testToString(){
        Tuple tuple = new Tuple(4,5);
        return tuple.toString().equals("(4,5)"); // on compare le contenu et non l'objet
    }
}



