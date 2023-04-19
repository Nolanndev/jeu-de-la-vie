package tests.core;

import main.core.HashLife;
import main.core.Quadtree;
import main.core.Cell;

public class TestHashLife{

    private Cell cell = new Cell(true);
    private HashLife hashlife = new HashLife(cell);
    private Quadtree on = new Quadtree(null, null, null, null, 0, 1, cell);
    private Quadtree off = new Quadtree(null, null, null, null, 0, 0, cell);

    
    public boolean testAdvance(){
        System.out.println("Test: advance() ");

        Quadtree quadtreeOn = new Quadtree(on, on, on, on);
        Quadtree quadtreeOff = new Quadtree(off, off, off, off);
        Quadtree quadtree = new Quadtree(quadtreeOn, quadtreeOn, quadtreeOn, quadtreeOn);

        assert hashlife.advance(on, 0) == on : "Erreur avec le cas de base";
        assert hashlife.advance(quadtree, 0) == quadtree : "Erreur avec le cas de base";

        // On compare le hashcode car l'opérateur "==" et la methode equals ne donnent pas le bon resultat
        Quadtree hashcode10 = hashlife.advance(quadtreeOn, 10);
        Quadtree hashcode50 = hashlife.advance(quadtreeOn, 50);


        assert hashcode10.hashCode() == quadtreeOn.hashCode() == true : "erreur avec quadtreeOn";
        assert hashcode50.hashCode() == quadtreeOn.hashCode() == true : "erreur avec quadtreeOn";



        // ------------------------------
        Quadtree step1 = new Quadtree(quadtreeOff, new Quadtree(off, off, off, on), new Quadtree(off, off, off, on), new Quadtree(on, off, off, off));
        Quadtree step2 = new Quadtree(new Quadtree(off, off, on, off), quadtreeOff, new Quadtree(off, on, off, off), new Quadtree(off, off, on, off));
        Quadtree step3 = new Quadtree(new Quadtree(off, on, off, off), new Quadtree(off, off, on, off), quadtreeOff, new Quadtree(off, on, off, off));
        Quadtree step4 = new Quadtree(new Quadtree(off, off, off, on), new Quadtree(on, off, off, off), new Quadtree(on, off, off, off), quadtreeOff);

        Quadtree expected_quadtree = new Quadtree(step1, step2, step3, step4);
        Quadtree result_quadtree = hashlife.advance(quadtree, 1);

        assert result_quadtree.hashCode() == expected_quadtree.hashCode() : "Erreur avec advance() avec quadtree";
        

        Quadtree infinite = new Quadtree(new Quadtree(off, on, on, off),
        new Quadtree(on, off, off, on),
        new Quadtree(on, off, off, on),
        new Quadtree(off, on, on, off));

        Quadtree result = hashlife.advance(infinite, 20);

        assert result.hashCode() == infinite.hashCode() : "probleme avec la fonction advance() sur infinite";

        
        assert hashlife.advance(infinite, 0) == infinite : "probleme avec la fonction advance() sur infinite 1er if ";

        // ------------------------------
        Quadtree infinite2 = new Quadtree(quadtreeOff, quadtreeOff , new Quadtree(off, on, off, off), new Quadtree(on, on, off, off));
        Quadtree expected_infinite2 = new Quadtree(quadtreeOff, new Quadtree(off, off, on, off), quadtreeOff, new Quadtree(on, off, on, off));
        assert hashlife.advance(infinite2, 0) == infinite2 : "probleme avec la fonction advance() sur infinite2 (premier if)";

        Quadtree result_odd = hashlife.advance(infinite2, 1);
        Quadtree result_even = hashlife.advance(infinite2, 2);

        assert result_even.hashCode() == infinite2.hashCode() : "advance provoque une erreur, even";
        assert result_odd.hashCode() == expected_infinite2.hashCode() : "advance provoque une erreur, odd";

    

        // ------------------------------
        Quadtree infinite3 = new Quadtree(quadtreeOn, quadtreeOff, quadtreeOff, quadtreeOn);
        Quadtree expected_infinite3 = new Quadtree(new Quadtree(on, on, on, off), quadtreeOff, quadtreeOff, new Quadtree(off, on, on, on));

        Quadtree odd = hashlife.advance(infinite3, 1);
        Quadtree even = hashlife.advance(infinite3, 2);

        for (int i = 1; i <= 30; i++){
            if (i == 0){
                assert hashlife.advance(infinite3, i) == infinite3 : "probleme avec la fonction advance() sur infinite3";
            }
            if (i % 2 != 0){
                assert odd.hashCode() == expected_infinite3.hashCode() : "probleme avec la fonction advance() sur infinite3 odd";   
            }
            if (i % 2 == 0){
                assert even.hashCode() == infinite3.hashCode()  : "probleme avec la fonction advance() sur infinite3 even";  
            }
        }

        // ------------------------------
        Quadtree infinite4 = new Quadtree(infinite3, infinite3, infinite3, infinite3);

        Quadtree step1_1_infinite4 = new Quadtree(new Quadtree(on, on, on, off), quadtreeOff, quadtreeOff, quadtreeOff);
        Quadtree step2_1_infinite4 = new Quadtree(new Quadtree(on, on, off, off), quadtreeOff, quadtreeOff, new Quadtree(off, on, off, on));
        Quadtree step3_1_infinite4 = new Quadtree(new Quadtree(on, off, on, off), quadtreeOff, quadtreeOff, new Quadtree(off, off, on, on));
        Quadtree step4_1_infinite4 = new Quadtree(quadtreeOff, quadtreeOff, quadtreeOff, new Quadtree(off, on, on, on));

        Quadtree expected_infinite4 = new Quadtree(step1_1_infinite4, step2_1_infinite4, step3_1_infinite4, step4_1_infinite4);

        Quadtree result_infinite4_1 = hashlife.advance(infinite4,1);

        assert result_infinite4_1.hashCode() == expected_infinite4.hashCode() : "probleme avec la fonction advance() sur infinite4"; 
                
        Quadtree step1_2_infinite4 = new Quadtree(quadtreeOn, quadtreeOff, quadtreeOff, quadtreeOff);
        Quadtree step2_2_infinite4 = new Quadtree(quadtreeOff, quadtreeOff, quadtreeOff, quadtreeOff);
        Quadtree step3_2_infinite4 = new Quadtree(quadtreeOff, quadtreeOff, quadtreeOff, quadtreeOff);
        Quadtree step4_2_infinite4 = new Quadtree(quadtreeOff, quadtreeOff, quadtreeOff, quadtreeOn);

        Quadtree result_infinite4_2 = new Quadtree(step1_2_infinite4, step2_2_infinite4, step3_2_infinite4, step4_2_infinite4);

        result = hashlife.advance(infinite4,2);

        assert result_infinite4_2.hashCode() == result.hashCode() : "erreur sur infinite4";


        // ------------------------------

        Quadtree infinite5 = new Quadtree(new Quadtree(off, off, on, off), 
        (new Quadtree(off, off, off, on)), 
        (new Quadtree(off, on, off, off)), 
        (new Quadtree(on, off, off, off)));
        assert hashlife.advance(infinite5, 0) == infinite5 : "probleme avec la fonction advance() sur infinite5";
        
        result = hashlife.advance(infinite5, 2);
        assert quadtreeOn.hashCode() == result.hashCode() : "probleme avec la fonction advance() sur infinite5 quadtreeOn"; 

        // ------------------------------
        
        Quadtree disappear = new Quadtree(off, on, on, off);
        assert hashlife.advance(disappear, 0) == disappear : "probleme avec la fonction advance() sur disappear ";

        result = hashlife.advance(disappear, 1);
        assert result.hashCode() == quadtreeOff.hashCode() : "probleme avec la fonction advance() sur disappear quadtreeOff";

        // ------------------------------

        Quadtree disappear2 = new Quadtree(new Quadtree(on, off, off, on), quadtreeOff, quadtreeOff, new Quadtree(on, off, off, on));
        Quadtree expected_disappear2 = new Quadtree(new Quadtree(off, off, off, on), quadtreeOff, quadtreeOff, new Quadtree(on, off, off, off));

        result = hashlife.advance(disappear2, 1);

        //System.out.println("result : " + hash_result + " expected : " + hash_disappear2);
        assert result.hashCode() == expected_disappear2.hashCode() : "Il doit ressembler a expected_disappear2 a cette etape";  

        result = hashlife.advance(disappear2, 2);

        //System.out.println("result : " + hash_result + " expected : " + hash_quadtreeOff);
        assert result.hashCode() == quadtreeOff.hashCode() : "Il devrait disparaitre au bout de 2 etapes";  

        // ------------------------------

        Quadtree disappear3 = new Quadtree(new Quadtree(off, off, off, on),
        new Quadtree(off, off, off, on),
        new Quadtree(off, off, off, on),
        new Quadtree(off, off, off, on));

        
        result = hashlife.advance(disappear3, 1);
        
        //System.out.println("result : " + hash_result + " expected : " + hash_quadtreeOff);
        assert hashlife.advance(disappear3, 0) == disappear3 : "Il doit ressembler a expected_disappear3 a cette etape";  
        assert result.hashCode() == quadtreeOff.hashCode() : "Il devrait disparaitre au bout de 2 etapes";  
        
        return true;
    }
}

