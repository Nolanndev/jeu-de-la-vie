package main.core;

import java.text.MessageFormat;

/**
 * Class which represent Quadtree.
 * Quadtree is defined by :
 * <ul>
 * <li>Is depth</li>
 * <li>Is number of Cell alive</li>
 * <li>Is Cell associated to the Quadtree</li>
 * <li>Is four children nw, ne, sw, se</li>
 * 
 * </ul>
 * @author Marcheron Bastien
 * @author David Matthias
 * 
 * @see Cell
 */
public class Quadtree{

    /**
     * North West of the Quadtree
     */
    private Quadtree nw;
    
    /**
     * North East of the Quadtree
     */
    private Quadtree ne;

    /**
     * Sout West of the Quadtree
     */
    private Quadtree sw;

    /**
     * South East of the Quadtree
     */
    private Quadtree se;

    /**
     * Depth of the Quadtree
     */
    private int depth;

    /**
     * Number of Cell alive in the Quadtree
     */
    private int numberAlive;

    /**
     * Properity of cell into the quadtree
     */
    private Cell cell;
    
    /**
     * Construct a new Quadtree defined by the given Quadtree
     * @param nw North West of the Quadtree
     * @param ne North East of the Quadtree
     * @param sw South West of the Quadtree
     * @param se South East of the Quadtree
     * @param depth Depth of the Quadtree
     * @param numberAlive Number of Cell alive in the Quadtree
     * @param cell Properity of cell into the Quadtree
     */
    public Quadtree(Quadtree nw, Quadtree ne, Quadtree sw, Quadtree se, int depth, int numberAlive, Cell cell){
        this.nw = nw;
        this.ne = ne;
        this.se = se;
        this.sw = sw;
        this.depth = depth;
        this.numberAlive = numberAlive;
        this.cell = cell;
    }

    /**
     * Construct new Quadtree defined by 4 other quadtree with depth - 1
     * @param nw North West of the Quadtree
     * @param ne North East of the Quadtree
     * @param sw South West of the Quadtree
     * @param se South East of the Quadtree
     */
    public Quadtree(Quadtree nw, Quadtree ne, Quadtree sw, Quadtree se){
        this(nw, ne, sw, se, nw.getDepth()+1, (nw.getNumberAlive() + ne.getNumberAlive() + se.getNumberAlive() + sw.getNumberAlive()), nw.getCell());
    }

    /**
     * Constructor wich produce new Quadtree from a Grid
     * @param grid Grid will be convert
     * @see Grid
     */
    public Quadtree(Grid grid){
        int gridMaxSize = Math.max((int)grid.getHeight(), (int)grid.getWidth());  
        int depth = (int) Math.ceil((Math.log(gridMaxSize) / Math.log(2)));
        grid.convertForQuadtree(depth);

        if(depth == 0){
            this.nw = null;
            this.ne = null;
            this.se = null;
            this.sw = null;
            this.depth = 0;
            this.cell = grid.getCell(0, 0);
            this.numberAlive = (this.cell.isAlive()) ? 1 : 0;;
        }

        else{
            this.nw = new Quadtree(new Grid(grid.getNw()));
            this.ne = new Quadtree(new Grid(grid.getNe()));
            this.sw = new Quadtree(new Grid(grid.getSw()));
            this.se = new Quadtree(new Grid(grid.getSe()));
            this.depth = depth;
            this.cell = nw.getCell();
            this.numberAlive = (nw.getNumberAlive() + ne.getNumberAlive() + se.getNumberAlive() + sw.getNumberAlive());
        }

    }

    /**
     * Accessor to the North West part of the Quadtree
     * @return North West part of the Quadtree
     */
    public Quadtree getNw(){
        return nw;
    }

    /**
     * Accessor to the North East part of the Quadtree
     * @return North East part of the Quadtree
     */
    public Quadtree getNe(){
        return ne;
    }

    /**
     * Accessor to the South West part of the Quadtree
     * @return South West part of the Quadtree
     */
    public Quadtree getSw(){
        return sw;
    }

    /**
     * Accessor to the South East part of the Quadtree
     * @return South East part of the Quadtree
     */
    public Quadtree getSe(){
        return se;
    }

    /**
     * Accessor to the depth of the Quadtree
     * @return depth of the Quadtree
     */
    public int getDepth(){
        return depth;
    }

    /**
     * Accessor to the number Of Cell alive into the Quadtree
     * @return number Of Cell alive into the Quadtree
     */
    public int getNumberAlive(){
        return numberAlive;
    }

    /**
     * Accessor to the Cell into the Quadtree
     * @return The Cell into the Quadtree
     */
    public Cell getCell(){
        return cell;
    }


    /**
     * Lets know if this Quadtree is a leaf or not
     * @return true if the Quadtree is a leaf else retrun false
     */
    public boolean isLeaf(){
        return getDepth() == 0;
    }


    /**
     * Produce a copy of the Quadtree
     * @return copy of the Quadtree
     */
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