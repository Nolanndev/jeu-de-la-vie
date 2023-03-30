package tests.core;

import main.core.HashLife;
import main.core.Quadtree;


public class TestHashLife{
    
    public boolean testGetZero(){
        System.out.println("Test: getZero() ");
    /* 
        HashLife hashlife = new HashLife();
    
        Quadtree quadtree = hashlife.getZero(1);
        assert quadtree != null : "Erreur: getZero() a renvoyé null." ;
        assert quadtree.getDepth() == 1 : "Erreur: la profondeur devrait être de 1.";
        assert quadtree.getNumberAlive() == 0 : "Erreur: nombre de cellules vivantes !=  0.";
*/    
        return true;
    }
    
    
  /*  
    public  boolean testJoin() {
        System.out.println("Test: join() ");

        HashLife hashlife = new HashLife();

        Quadtree nw = new Quadtree();
        Quadtree ne = new Quadtree();
        Quadtree se = new Quadtree();
        Quadtree sw = new Quadtree();
    
        Quadtree quadtree = hashlife.join(nw, ne, se, sw);

        assert quadtree != null : "Erreur: join() a renvoyé null.";
        assert quadtree.getDepth() == 2 : "Erreur: la profondeur de quadtree devrait etre 2.";
        assert quadtree.getNumberAlive() == 0 : "Erreur: nombre de cellules vivantes = 0.";
        assert quadtree.getNumberAlive() != 1 : "Erreur: nombre de cellules vivantes != 1.";

    
        return true;
    }
     
    public boolean testCentre() {
        System.out.println("Test: centre()");
        HashLife hashlife = new HashLife();

        Quadtree nw1 = new Quadtree();
        Quadtree ne1 = new Quadtree();
        Quadtree se1 = new Quadtree();
        Quadtree sw1 = new Quadtree();
        Quadtree center1 = new Quadtree(nw1, ne1, se1, sw1);
        Quadtree quadtree1 = hashlife.centre(center1);
        Quadtree result1 = new Quadtree(new Quadtree(), new Quadtree(), new Quadtree(), new Quadtree(), 2, 0);
        assert quadtree1.equals(result1) : "Erreur sur centre (Test 1)";        
    
        Quadtree nw2 = new Quadtree(new Quadtree(), new Quadtree(), new Quadtree(), new Quadtree(), 1, 1);
        Quadtree ne2 = new Quadtree();
        Quadtree se2 = new Quadtree();
        Quadtree sw2 = new Quadtree();
        Quadtree center2 = new Quadtree(nw2, ne2, se2, sw2);
        Quadtree quadtree2 = hashlife.centre(center2);
        Quadtree result2 = new Quadtree(new Quadtree(), new Quadtree(), new Quadtree(), nw2, 2, 1);
        assert quadtree2.equals(result2) : "Erreur sur centre (Test 2)";
    
        Quadtree nw3 = new Quadtree(new Quadtree(), new Quadtree(), new Quadtree(), new Quadtree(), 1, 1);
        Quadtree ne3 = new Quadtree();
        Quadtree se3 = new Quadtree();
        Quadtree sw3 = new Quadtree();
        Quadtree center3 = new Quadtree(sw3, se3, ne3, nw3);
        Quadtree quadtree3 = hashlife.centre(center3);
        Quadtree result3 = new Quadtree(new Quadtree(), new Quadtree(), nw3, new Quadtree(), 2, 1);
        assert quadtree3.equals(result3) : "Erreur sur centre (Test 3)";
        
        return true;
    }
     
    public boolean testLife() {
        System.out.println("Test: life()");
    
        HashLife hashlife = new HashLife();
        Quadtree nw = new Quadtree();
        Quadtree ne = new Quadtree();
        Quadtree se = new Quadtree();
        Quadtree sw = new Quadtree();

        Quadtree on = new Quadtree(null, null, null, null, 0, 1);
        Quadtree off = new Quadtree(null, null, null, null, 0, 0);

        Quadtree center = new Quadtree(nw, ne, se, sw);
    
        Quadtree result1 = hashlife.life(off, nw, ne, se, sw, center);
        assert result1.equals(off) : "Erreur sur life (Test 1)";
    
        Quadtree result2 = hashlife.life(on, nw, ne, se, sw, center);
        assert result2.equals(off) : "Erreur sur life (Test 2)";
    
        Quadtree result3 = hashlife.life(on, on, on, off, center);
        assert result3.equals(on) : "Erreur sur life (Test 3)";
    
        Quadtree result4 = hashlife.life(on, on, on, on, center);
        assert result4.equals(on) : "Erreur sur life (Test 4)";
    
        Quadtree result5 = hashlife.life(on, on, on, on, on, center);
        assert result5.equals(off) : "Erreur sur life (Test 5)";
        
        return true;
    }
    

    public boolean testLife4x4() {
        System.out.println("Test: life4x4() ");

        HashLife hashlife = new HashLife();
        Quadtree nw = new Quadtree();
        Quadtree ne = new Quadtree();
        Quadtree se = new Quadtree();
        Quadtree sw = new Quadtree();

        Quadtree m = new Quadtree(nw, ne, se, sw, 0, 1);

        Quadtree nw1 = hashlife.life(m.getNw().getSe(), m.getNw().getNw(), m.getNw().getNe(),m.getNe().getNw(), m.getNw().getSw(), m.getNe().getSw(), m.getSw().getNw(), m.getSw().getNe(), m.getSe().getNw());  
        Quadtree ne1 = hashlife.life(m.getNe().getSw(), m.getNw().getNe(), m.getNe().getNw(), m.getNe().getNe(), m.getNw().getSe(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNw(), m.getSe().getNe());  
        Quadtree sw1 = hashlife.life(m.getSw().getNe(), m.getNw().getSw(), m.getNw().getSe(), m.getNe().getSw(), m.getSw().getNw(), m.getSe().getNw(), m.getSw().getSw(), m.getSw().getSe(), m.getSe().getSw()); 
        Quadtree se1 = hashlife.life(m.getSe().getNw(), m.getNw().getSe(), m.getNe().getSw(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNe(), m.getSw().getSe(), m.getSe().getSw(), m.getSe().getSe()); 

        Quadtree result = hashlife.join(nw1, ne1, sw1, se1);

        Quadtree quadtree = new Quadtree(nw, ne , se, sw, 1, 2);
        Quadtree quadtree1 = hashlife.life_4x4(quadtree);

        assert quadtree1.equals(result) : "erreur sur life_4x4";

        return true;
    }
    
    
    public boolean testSuccessor() {
        System.out.println("Test: successor() ");
    
        Quadtree nw = new Quadtree();
        Quadtree ne = new Quadtree();
        Quadtree se = new Quadtree();
        Quadtree sw = new Quadtree();
        HashLife hashlife = new HashLife();
    
        Quadtree quadtree = new Quadtree();
        Quadtree result = hashlife.successor(quadtree, 0);
        assert result == null : "erreur sur quadtree vide";
    
        Quadtree quadtree1 = new Quadtree(nw, ne, se, sw, 1, 0);
        Quadtree centre = hashlife.centre(quadtree1);
        Quadtree result1 = hashlife.life_4x4(centre);
        Quadtree expected1 = new Quadtree(new Quadtree(), new Quadtree(), new Quadtree(), new Quadtree(), 0, 1);
        assert result1.equals(expected1) : "erreur avec la profondeur = 1";
    
        Quadtree quadtree2 = new Quadtree(nw, ne, se, sw, 2, 0);
        Quadtree life = hashlife.life_4x4(quadtree2);
        Quadtree result2 = hashlife.successor(quadtree2, 0);
        assert result2.equals(life) : "erreur avec la profondeur = 2";
    
        Quadtree copie = new Quadtree(nw, ne, sw, sw, 1, 0);
        Quadtree quadtree3 = new Quadtree(
                copie,
                copie,
                copie,
                copie,
                3, 0
        );

        Quadtree copie2 = new Quadtree(new Quadtree(), new Quadtree(), new Quadtree(), new Quadtree(), 0, 2);
        Quadtree expected3 = new Quadtree(
                copie2,
                copie2,
                copie2,
                copie2,
                3, 0
        );
        Quadtree result3 = hashlife.successor(quadtree3, 0);
        assert result3.equals(expected3) : "erreur profondeur > 2.";

        return true;
    }
    


    public boolean testIsPadded(){ 
        System.out.println("Test: pad() ");

        Quadtree nw = new Quadtree(null, null, null, null, 0, 1);
        Quadtree ne = new Quadtree(null, null, null, null, 0, 1);
        Quadtree se = new Quadtree(null, null, null, null, 0, 1);
        Quadtree sw = new Quadtree(null, null, null, null, 0, 1);
        
        HashLife hashlife = new HashLife();
    
        Quadtree quadtree = new Quadtree(nw, ne, se, sw, 0, 1);
        boolean result = haslife.isPadded(quadtree);

        return true;
    }
   
    public boolean testPad(){
        System.out.println("Test: isPadded() ");

        HashLife hashlife = new HashLife();

        Quadtree nw = new Quadtree(null, null, null, null, 0, 1);
        Quadtree ne = new Quadtree(null, null, null, null, 0, 1);
        Quadtree se = new Quadtree(null, null, null, null, 0, 1);
        Quadtree sw = new Quadtree(null, null, null, null, 0, 1);

        Quadtree fusion = new Quadtree(nw, ne, se, sw, 4, 1);
        
        Quadtree quadtree = new Quadtree(fusion, fusion, fusion, fusion, 4, 1);
        Quadtree pad = hashlife.pad(quadtree);


        Quadtree quadtree1 = new Quadtree(nw, ne, se, sw, 0, 1);
        Quadtree centre = hashlife.centre(quadtree1);
        Quadtree result = hashlife.pad(centre);

        //assert quadtree.equals(pad) == false: "erreur sur pad";
        //assert quadtree1.equals(result) : "erreur sur pad";
        
        return true;
    }

    public boolean testAdvance(){
        System.out.println("Test: advance() ");

        return true;
    }
*/
}

