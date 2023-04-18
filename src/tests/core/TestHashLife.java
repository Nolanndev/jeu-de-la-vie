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

        //test un carré de 2x2. Au bout de la Xeme etape, il n'aura pas changé
        assert hashlife.advance(on, 0) == on : "Erreur avec le cas de base";
        assert hashlife.advance(quadtree, 0) == quadtree : "Erreur avec le cas de base";

        assert hashlife.advance(quadtreeOn, 50) == quadtreeOn : "Erreur avec advance() sur quadtreeOn";
        assert hashlife.advance(quadtreeOn, 10) == quadtreeOn : "Erreur avec la fonction advance() sur quadtreeOn";
        
        Quadtree infinite = new Quadtree(new Quadtree(off, on, on, off),
        new Quadtree(on, off, off, on),
        new Quadtree(on, off, off, on),
        new Quadtree(off, on, on, off));
        
        assert hashlife.advance(infinite, 0) == infinite : "probleme avec la fonction advance() sur infinite ";
        assert hashlife.advance(infinite, 20) == infinite : "probleme avec la fonction advance() sur infinite";

        Quadtree infinite2 = new Quadtree(quadtreeOff, quadtreeOff, new Quadtree(off, on, off, off), new Quadtree(off, on, on, off));
        
        assert hashlife.advance(infinite2, 0) == infinite2 : "probleme avec la fonction advance() sur infinite2";
        assert hashlife.advance(infinite2, 10) == infinite2 : "probleme avec la fonction advance() sur infinite2";

        Quadtree infinite3 = new Quadtree(quadtreeOn, quadtreeOff, quadtreeOff, quadtreeOn);
        
        Quadtree expected_infinite3 = new Quadtree(new Quadtree(on, on, on, off), quadtreeOff, quadtreeOff, new Quadtree(off, on, on, on));

        for (int i = 1; i <= 30; i++){
            if (i == 0){
                assert hashlife.advance(infinite3, i) == infinite3 : "probleme avec la fonction advance() sur infinite3";
            }
            if (i % 2 != 0){
                assert hashlife.advance(infinite3, i) == expected_infinite3 : "probleme avec la fonction advance() sur infinite3";   
            }
            if (i % 2 == 0){
                assert hashlife.advance(infinite3, i) == infinite : "probleme avec la fonction advance() sur infinite3";  
            }
        }

        Quadtree infinite4 = new Quadtree(infinite3, infinite3, infinite3, infinite3);

        Quadtree step1_1_infinite4 = new Quadtree(new Quadtree(on, on, on, off), quadtreeOff, quadtreeOff, quadtreeOff);
        Quadtree step2_1_infinite4 = new Quadtree(new Quadtree(on, on, off, off), quadtreeOff, quadtreeOff, new Quadtree(off, on, off, on));
        Quadtree step3_1_infinite4 = new Quadtree(new Quadtree(on, off, on, off), quadtreeOff, quadtreeOff, new Quadtree(off, off, on, on));
        Quadtree step4_1_infinite4 = new Quadtree(quadtreeOff, quadtreeOff, quadtreeOff, new Quadtree(off, on, on, on));

        Quadtree expected_infinite4 = new Quadtree(step1_1_infinite4, step2_1_infinite4, step3_1_infinite4, step4_1_infinite4);
        assert hashlife.advance(infinite4, 1) == expected_infinite4 : "probleme avec la fonction advance() sur infinite4"; 

        Quadtree step1_2_infinite4 = new Quadtree(quadtreeOn, quadtreeOff, quadtreeOff, quadtreeOff);
        Quadtree step2_2_infinite4 = new Quadtree(quadtreeOn, quadtreeOff, quadtreeOff, quadtreeOff);
        Quadtree step3_2_infinite4 = new Quadtree(quadtreeOn, quadtreeOff, quadtreeOff, quadtreeOff);
        Quadtree step4_2_infinite4 = new Quadtree(quadtreeOn, quadtreeOff, quadtreeOff, quadtreeOn);

        Quadtree result_infinite4 = new Quadtree(step1_2_infinite4, step2_2_infinite4, step3_2_infinite4, step4_2_infinite4);

        for (int k = 2; k <= 30; k++){
            assert hashlife.advance(infinite4, k) == result_infinite4 : "probleme avec la fonction advance() sur infinite4 avec le resultat"; 
        }

        Quadtree infinite5 = new Quadtree(new Quadtree(off, off, on, off), 
        (new Quadtree(off, off, off, on)), 
        (new Quadtree(off, on, off, off)), 
        (new Quadtree(on, off, off, off)));


        assert hashlife.advance(infinite5, 0) == infinite5 : "probleme avec la fonction advance() sur infinite5"; 
        assert hashlife.advance(infinite5, 2) == quadtreeOn : "probleme avec la fonction advance() sur infinite5 quadtreeOn"; 

        assert hashlife.advance(infinite4, 2) == expected_infinite4 : "probleme avec la fonction advance() sur expected_infinite4"; 

        
        
        Quadtree disappear = new Quadtree(off, on, on, off);
        assert hashlife.advance(disappear, 0) == disappear : "probleme avec la fonction advance() sur disappear ";
        assert hashlife.advance(disappear, 1) == quadtreeOff : "probleme avec la fonction advance() sur disappear quadtreeOff";

        Quadtree disappear2 = new Quadtree(new Quadtree(on, off, off, on), quadtreeOff, quadtreeOff, new Quadtree(on, off, off, on));

        Quadtree expected_disappear2 = new Quadtree(new Quadtree(off, off, off, on), quadtreeOff, quadtreeOff, new Quadtree(on, off, off, off));

        assert hashlife.advance(disappear2, 1) == expected_disappear2 : "Il doit ressembler a expected_disappear2 a cette etape";  
        assert hashlife.advance(disappear2, 2) == off : "Il devrait disparaitre au bout de 2 etapes";  

        Quadtree disappear3 = new Quadtree(new Quadtree(off, off, off, on),
        new Quadtree(off, off, off, on),
        new Quadtree(off, off, off, on),
        new Quadtree(off, off, off, on));
        
        assert hashlife.advance(disappear3, 0) == disappear3 : "Il doit ressembler a expected_disappear3 a cette etape";  
        assert hashlife.advance(disappear3, 1) == off : "Il devrait disparaitre au bout de 2 etapes";  
        
        return true;
    }
}

