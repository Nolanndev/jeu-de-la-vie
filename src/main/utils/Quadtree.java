package main.utils;

import java.text.MessageFormat;

public class Quadtree {

    private Quadtree nw;
    private Quadtree ne;
    private Quadtree se;
    private Quadtree sw;
    private int depth;
    private int numberAlive;
    
    public Quadtree(Quadtree nw, Quadtree ne, Quadtree sw, Quadtree se, int depth, int numberAlive) {
        this.nw = nw;
        this.ne = ne;
        this.se = se;
        this.sw = sw;
        this.depth = depth;
        this.numberAlive = numberAlive;
    }

    public Quadtree(Quadtree nw, Quadtree ne, Quadtree se, Quadtree sw) {
        this(nw, ne, sw, se, nw.getDepth()+1, 0);
        this.numberAlive = nw.getNumberAlive() + ne.getNumberAlive() + se.getNumberAlive() + sw.getNumberAlive();
    }

    public Quadtree(){
        this(null, null, null, null, 1, 0);
    }

    public Quadtree getNe() {
        return ne;
    }

    public Quadtree getNw() {
        return nw;
    }

    public Quadtree getSe() {
        return se;
    }

    public Quadtree getSw() {
        return sw;
    }

    public int getDepth() {
        return depth;
    }

    public int getNumberAlive() {
        return numberAlive;
    }

    public boolean isLeaf(){
        return getDepth() == 0;
    }

    @Override
    public int hashCode() {
        if(this.numberAlive == 1){
            return 678;
        }
        if(this.numberAlive == 0){
            return 98;
        }
        return (this.ne.hashCode() * this.nw.hashCode())/this.sw.hashCode() - this.se.hashCode() + this.getNumberAlive()*10; 
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }

        if (!(obj instanceof Quadtree)){
            return false;
        }

        Quadtree q = (Quadtree) obj;
        return this.getNw() == q.getNw() && this.getNe() == q.getNe() && this.getSe() == q.getSe() && this.getSw() == q.getSw();
    }

    @Override
    public String toString() {
        return MessageFormat.format("depth : {0},  nbAlive: {1}",
            this.getDepth(),
            this.getNumberAlive()    
        );
    }
}