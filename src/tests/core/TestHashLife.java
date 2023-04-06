package tests.core;

import main.core.HashLife;
import main.core.Quadtree;

import java.nio.file.FileAlreadyExistsException;

import main.core.Cell;


public class TestHashLife{
   
    public boolean testGetZero(){
        System.out.println("Test: getZero() ");

        Cell cell = new Cell(true);
        Cell cell1 = new Cell(0, 1, 0, 2, 1, true);
        
        HashLife hashlife = new HashLife(cell);
        HashLife hashLife1 = new HashLife(cell1);

        Quadtree quadtree = hashlife.getZero(1);
        Quadtree quadtree1 = hashLife1.getZero(0);

        assert quadtree != null : "Erreur: getZero() a renvoyé null." ;
        assert quadtree.getDepth() == 1 : "Erreur: la profondeur devrait être de 1.";
        assert quadtree.getNumberAlive() == 0 : "Erreur: nombre de cellules vivantes !=  0.";

        assert quadtree1 != null : "Erreur: getZero() a renvoyé null." ;
        assert quadtree1.getDepth() == 0 : "Erreur: la profondeur devrait être de 0.";
        assert quadtree1.getNumberAlive() == 0 : "Erreur: nombre de cellules vivantes !=  0.";
    
        return true;
    }
    
    

    public  boolean testInner(){
        System.out.println("Test: inner() ");
        Cell cell = new Cell(true);

        HashLife hashlife = new HashLife(cell);

        Quadtree nw = new Quadtree(null, null, null, null,0 ,1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;
 
        Quadtree quadtree = new Quadtree(nw, ne, sw, se);
        Quadtree null1 = hashlife.inner(quadtree);

        assert null1 == null : "Erreur avec inner()";

        Quadtree quadtree1 = new Quadtree(quadtree, quadtree, quadtree, quadtree);
        Quadtree compare = new Quadtree(quadtree1, quadtree1, quadtree1, quadtree1);

        Quadtree result_quadtree = hashlife.inner(compare);
        Quadtree quadtree2 = new Quadtree(compare.getNw().getSe(), compare.getNe().getSw(), compare.getSw().getNe(), compare.getSe().getNw());

        assert quadtree2.equals(result_quadtree) : "Erreur avec inner()";
    
        return true;
    }
 
     
    public boolean testCentre(){
        System.out.println("Test: centre()");

        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);

        Quadtree nw = new Quadtree(null, null, null, null,0 ,1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;

        Quadtree centre = new Quadtree(nw, ne, se, sw);

        Quadtree zero = hashlife.getZero(centre.getNe().getDepth());
        Quadtree result =  new Quadtree(new Quadtree(zero, zero, zero, centre.getNw()),  
                                        new Quadtree(zero, zero, centre.getNe(), zero),  
                                        new Quadtree(zero, centre.getSw(), zero, zero), 
                                        new Quadtree(centre.getSe(), zero, zero, zero)); 

        
        Quadtree quadtree1 = hashlife.centre(centre);

        assert quadtree1.equals(result) : "Erreur avec centre";

        return true;
    }


    public boolean testLife() {
        System.out.println("Test: life()");
    
        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);

        Quadtree nw = new Quadtree(null, null, null, null,0 ,1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;

        Quadtree on = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree off = new Quadtree(null, null, null, null, 0, 0, cell);
    

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
        
        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);

        Quadtree nw = new Quadtree(null, null, null, null,0 ,1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;

        Quadtree quadtree = new Quadtree(nw, ne, sw, se);
        Quadtree m = new Quadtree(quadtree, quadtree, quadtree, quadtree);

        Quadtree nw1 = hashlife.life(m.getNw().getSe(), m.getNw().getNw(), m.getNw().getNe(), m.getNe().getNw(), m.getNw().getSw(), m.getNe().getSw(), m.getSw().getNw(), m.getSw().getNe(), m.getSe().getNw());  
        Quadtree ne1 = hashlife.life(m.getNe().getSw(), m.getNw().getNe(), m.getNe().getNw(), m.getNe().getNe(), m.getNw().getSe(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNw(), m.getSe().getNe());  
        Quadtree sw1 = hashlife.life(m.getSw().getNe(), m.getNw().getSw(), m.getNw().getSe(), m.getNe().getSw(), m.getSw().getNw(), m.getSe().getNw(), m.getSw().getSw(), m.getSw().getSe(), m.getSe().getSw()); 
        Quadtree se1 = hashlife.life(m.getSe().getNw(), m.getNw().getSe(), m.getNe().getSw(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNe(), m.getSw().getSe(), m.getSe().getSw(), m.getSe().getSe());  
        
        Quadtree result = new Quadtree(nw1, ne1, sw1, se1);
        Quadtree quadtree1 = hashlife.life_4x4(m);

        assert result.equals(quadtree1) : "Erreur avec life_4x4";
        assert m.getNw().getSe() == nw : "Erreur sur la creation du quadtree";
        return true;
    }
    
     
    public boolean testSuccessor() {
        System.out.println("Test: successor() ");
        
        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);

        Quadtree nw = new Quadtree(null, null, null, null,0 ,1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;

        Quadtree m = new Quadtree(nw, ne, se, sw, 1, 0, cell);
        Quadtree quadtree = hashlife.successor(m, 1);
        assert quadtree == m.getNe() : "Erreur avec successor"; 

        Quadtree m1 = new Quadtree(nw, ne, se, sw, 1, 1, cell);
        Quadtree quadtree1 = hashlife.successor(m, 1);
        Quadtree result = hashlife.life_4x4(hashlife.centre(m1));

        assert quadtree1.equals(result) : "erreur avec successor";

        Quadtree m2 = new Quadtree(nw, ne, se, sw, 2, 1, cell);
        Quadtree quadtree2 = hashlife.successor(m, 1);
        Quadtree result1 = hashlife.life_4x4(m2);

        assert quadtree2.equals(result1) : "erreur avec successor";

        return true;
    }
    


    public boolean testIsPadded(){ 
        System.out.println("Test: isPadded() ");

        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);

        Quadtree nw = new Quadtree(null, null, null, null,0 ,1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;
        
        Quadtree quadtree = new Quadtree(nw, ne, se, sw, 1 ,1, cell);
        Quadtree quadtree1 = new Quadtree(quadtree, quadtree, quadtree, quadtree, 2 ,1, cell);
        Quadtree quadtree2 = new Quadtree(quadtree1, quadtree1, quadtree1, quadtree1, 3 ,1, cell);

        boolean result = hashlife.isPadded(quadtree2);

        assert quadtree2.getNw().getNumberAlive() == quadtree2.getNw().getSe().getSe().getNumberAlive() : "1erreur avec isPadded";
        assert quadtree2.getNe().getNumberAlive() == quadtree2.getNe().getSw().getSw().getNumberAlive() : "2erreur avec isPadded";
        assert quadtree2.getSw().getNumberAlive() == quadtree2.getSw().getNe().getNe().getNumberAlive() : "3erreur avec isPadded";
        assert quadtree2.getSe().getNumberAlive() == quadtree2.getSe().getNw().getNw().getNumberAlive() : "4erreur avec isPadded";

        assert result == true : "erreur avec isPadded";
        return true;
    }
    
    public boolean testPad(){
        System.out.println("Test: isPadded() ");
        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);

        Quadtree nw = new Quadtree(null, null, null, null,0 ,1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;

        Quadtree quadtree = new Quadtree(nw, ne, sw, se);
        Quadtree result = hashlife.pad(quadtree);

        //trouver un cas ou un quadtree n'est pas padded
        
        assert !hashlife.isPadded(result) == false : "erreur avec isPadded";
        //assert result == quadtree : "Erreur avec pad";


        return true;
    }

    public boolean testAdvance(){
        System.out.println("Test: advance() ");

        return true;
    }

    public boolean testCrop(){
        System.out.println("Test: crop() ");

        return true; 
    }

}

