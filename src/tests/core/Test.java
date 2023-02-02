package tests.core;


/*
Ce fichier servira à tester toutes
les fonctions du projet, il sera donc
MAJ regulièrement 
*/

public class Test {

    public static void main(String[] args) {
    
        boolean ok = true;

        TestTuple tupleTest = new TestTuple();
        ok = ok && tupleTest.testGetValue1();
        ok = ok && tupleTest.testGetValue2();
        ok = ok && tupleTest.testToString();

        //all is good ------------------

        TestNormalCell normalCell = new TestNormalCell();
        ok = ok && normalCell.testIsAliveTrue();
        ok = ok && normalCell.testIsAliveFalse();
        ok = ok && normalCell.testGetRadius();
        ok = ok && normalCell.testGetMinNeighbors();
        ok = ok && normalCell.testGetMaxNeighbors();
        ok = ok && normalCell.testInfos();



        System.out.println(ok ? "All␣tests␣OK" : "At␣least␣one␣test␣KO");
    }
    
}
