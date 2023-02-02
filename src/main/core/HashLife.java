package main.core;

import java.util.HashMap;

import main.utils.Quadtree;

public class HashLife extends Quadtree{

    private HashMap<Quadtree, Quadtree> cache; 
    private Quadtree on = new Quadtree(null, null, null, null, 0, 1);
    private Quadtree off = new Quadtree(null, null, null, null, 0, 0);


    public HashLife() {
        super();
    }

    public Quadtree getZero(int depth){
        return (depth == 0) ? this.off : this.join(getZero(depth-1), getZero(depth-1),getZero(depth-1), getZero(depth-1));
    }

    public Quadtree join(Quadtree no, Quadtree ne, Quadtree so, Quadtree se){
        int totalNUmberAlive = no.getNumberAlive()+ne.getNumberAlive()+so.getNumberAlive()+se.getNumberAlive();
        return new Quadtree(no, ne, se, so, no.getDepth()+1, totalNUmberAlive);
    }

}