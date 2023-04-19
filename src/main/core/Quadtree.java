package main.core;

import java.text.MessageFormat;

public class Quadtree{

    private Quadtree nw;
    private Quadtree ne;
    private Quadtree se;
    private Quadtree sw;
    private int depth;
    private int numberAlive;
    private Cell cell;
    
    public Quadtree(Quadtree nw, Quadtree ne, Quadtree sw, Quadtree se, int depth, int numberAlive, Cell cell){
        this.nw = nw;
        this.ne = ne;
        this.se = se;
        this.sw = sw;
        this.depth = depth;
        this.numberAlive = numberAlive;
        this.cell = cell;
    }

    public Quadtree(Quadtree nw, Quadtree ne, Quadtree sw, Quadtree se){
        this(nw, ne, sw, se, nw.getDepth()+1, (nw.getNumberAlive() + ne.getNumberAlive() + se.getNumberAlive() + sw.getNumberAlive()), nw.getCell());
    }

    // public Quadtree(Grid grid){
    //     (Math.log(x) / Math.log(2))
    //    int depth =Math.max((int)grid.getHeight(), (int)grid.getWidth());
    // }


    public Quadtree getNe(){
        return ne;
    }

    public Quadtree getNw(){
        return nw;
    }

    public Quadtree getSe(){
        return se;
    }

    public Quadtree getSw(){
        return sw;
    }

    public int getDepth(){
        return depth;
    }

    public int getNumberAlive(){
        return numberAlive;
    }

    public Cell getCell(){
        return cell;
    }

    public boolean isLeaf(){
        return getDepth() == 0;
    }


    public Quadtree deepCopy(){
        if(this.isLeaf()){
            return new Quadtree(this.nw, this.ne, this.se, this.sw, 0, this.numberAlive, this.cell);
        }
        return new Quadtree( this.nw.deepCopy(), this.ne.deepCopy(), this.se.deepCopy(), this.sw.deepCopy(), this.depth, this.numberAlive, this.cell);
    }

    @Override
    public int hashCode(){
        if(this.numberAlive == 1){
            return 709;
        }
        if(this.numberAlive == 0){
            return 129;
        }
        return (this.ne.hashCode() * this.nw.hashCode())/this.sw.hashCode() - this.se.hashCode() + this.getNumberAlive()*10; 
    }

    @Override
    public boolean equals(Object obj){
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
    public String toString(){
        return MessageFormat.format("depth : {0},  nbAlive: {1}",
            this.getDepth(),
            this.getNumberAlive()
        );
    }
}