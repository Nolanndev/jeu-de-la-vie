package tests.core;

import main.core.HashLife;
import main.core.Quadtree;
import main.core.Cell;

import java.nio.file.FileAlreadyExistsException;

import javax.swing.text.html.HTMLDocument.RunElement;


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
    
        //assert quadtree1.equals(result) : "Erreur avec centre";
        
        assert quadtree1 != centre : "L'objet retourné est le même que l'entrée";
    
        assert quadtree1.getNw().getNe().getCell() == cell : "Les cellules ne sont pas les mêmes";
        assert quadtree1.getNe().getNe().getCell() == cell : "Les cellules ne sont pas les mêmes";
        assert quadtree1.getSw().getNe().getCell() == cell : "Les cellules ne sont pas les mêmes";
        assert quadtree1.getSe().getNw().getCell() == cell : "Les cellules ne sont pas les mêmes";
        
        assert quadtree1.getNw().getDepth() == centre.getNw().getDepth() + 1 : "1Profondeur incorrecte";
        assert quadtree1.getNe().getDepth() == centre.getNe().getDepth() + 1 : "2Profondeur incorrecte";
        assert quadtree1.getSw().getDepth() == centre.getSw().getDepth() + 1 : "3Profondeur incorrecte";
        assert quadtree1.getSe().getDepth() == centre.getSe().getDepth() + 1 : "4Profondeur incorrecte";
    
        return true;
    }


    public boolean testLife(){
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
    

    public boolean testLife4x4(){
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
    
     
    public boolean testSuccessor(){
        System.out.println("Test: successor() ");
        
        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);
    
        Quadtree nw = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;
    
        Quadtree m = new Quadtree(nw, ne, se, sw, 1, 0, cell);
        Quadtree quadtree = hashlife.successor(m, 1);
        assert quadtree == m.getNe() : "Erreur avec successor pour le cas 1"; 
   
        Quadtree m1 = new Quadtree(nw, ne, se, sw, 1, 1, cell);
        Quadtree quadtree1 = hashlife.successor(m1, 1);
        Quadtree result = hashlife.life_4x4(hashlife.centre(m1));
    
        assert quadtree1.equals(result) : "Erreur avec successor pour le cas 2";
    
        Quadtree m2 = new Quadtree(m, m, m, m, 2, 1, cell);
        Quadtree quadtree2 = hashlife.successor(m2, 1);
        Quadtree result1 = hashlife.life_4x4(m2);
    
        assert quadtree2.equals(result1) : "Erreur avec successor pour le cas 3";
    
  
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
        System.out.println("Test: pad()");
        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);
    
        Quadtree nw = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;

        Quadtree pad = new Quadtree(nw, ne, sw, se);
        Quadtree pad1 = new Quadtree(pad, pad, pad, pad);

        Quadtree quadtree = new Quadtree(pad1, pad1, pad1, pad1);
        Quadtree paddedQuadtree = hashlife.pad(quadtree);

        assert !hashlife.isPadded(quadtree) : "Erreur avec isPadded (quadtree initial)";
        assert hashlife.isPadded(paddedQuadtree) : "Erreur avec isPadded (quadtree padded)";
        assert paddedQuadtree != quadtree : "Erreur avec pad (quadtree initial)";

        return true;
    }

    public boolean testCrop(){
        System.out.println("Test: crop()");
    
        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);

        Quadtree nw = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;
        Quadtree quadtree = new Quadtree(nw, ne, sw, se);

        Quadtree croppedQuadtree = hashlife.crop(quadtree);
        assert croppedQuadtree == quadtree : "(1)Erreur avec crop() sur un Quadtree non-padded";
    
        Quadtree paddedQuadtree = hashlife.pad(quadtree);
    
        croppedQuadtree = hashlife.crop(paddedQuadtree);
        assert croppedQuadtree != paddedQuadtree : "(2) Erreur avec crop() sur un Quadtree padded";
        assert croppedQuadtree.getDepth() != paddedQuadtree.getDepth() - 2 : "(3)Erreur avec crop() sur la profondeur du Quadtree cropped";
    
        Quadtree leaf = new Quadtree(null, null, null, null, 0, 1, cell);
        croppedQuadtree = hashlife.crop(leaf);
        assert croppedQuadtree == leaf : "(4)Erreur avec crop() sur un Quadtree ayant une profondeur <= 3";
    
        return true;
    }
    
    public boolean testAdvance(){
        System.out.println("Test: advance() ");
    
        Cell cell = new Cell(true);
        HashLife hashlife = new HashLife(cell);
    
        Quadtree nw = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree ne = nw;
        Quadtree se = nw;
        Quadtree sw = nw;
        Quadtree quadtree = new Quadtree(nw, ne, sw, se);
    
        assert hashlife.advance(nw, 0) == nw : "Erreur avec la fonction advance() sur le premier if";
        assert hashlife.advance(quadtree, 0) == quadtree : "Erreur avec la fonction advance() sur le premier if";
        assert hashlife.advance(quadtree, 1) != quadtree : "Erreur avec la fonction advance()";
    
        
        Quadtree successor = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree quadtree2 = new Quadtree(successor, successor, successor, successor);
        Quadtree result = hashlife.advance(quadtree2, 1);

        
        quadtree2 = hashlife.centre(quadtree2);
        quadtree2 = hashlife.centre(quadtree2);

        assert quadtree2.equals(result) : "(1) Erreur avec la fonction advance()";
    
        Quadtree result2 = new Quadtree(nw, ne, sw, result);
        assert hashlife.advance(quadtree, 2).equals(result2) : "(2) Erreur avec la fonction advance() ";
    
        Quadtree result3 = new Quadtree(nw, nw, nw, nw);
        assert hashlife.advance(quadtree, 100).equals(result3) : "(3) Erreur avec la fonction advance() pour le test 3";

        return true;
    }
    


}

