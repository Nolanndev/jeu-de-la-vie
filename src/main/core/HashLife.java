package main.core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to compute the next gen of Quadtree in Game of life by using the hashLife algorithm
 * @see Quadtree
 */
public class HashLife{

    /**
     * Cell to use for the default Quadtree (on and off)
     */
    private Cell cell;

    /**
     * Cache to avoid calculating the same thing several times
     */
    private HashMap<Quadtree, Quadtree> cache = new HashMap<Quadtree, Quadtree>(); 

    /**
     * Quadtree which represents an alive Cell
     */
    public Quadtree on;

    /**
     * Quadtree which represents a dead Cell
     */
    public Quadtree off;

    /**
     * Constructor which produce a new Hashlife and defined {@link HashLife#on} and {@link HashLife#off} with the given Cell 
     * @param cell Cell which defined {@link HashLife#on} and {@link HashLife#off}
     */
    public HashLife(Cell cell){
        this.cell = cell;
        this.on = new Quadtree(null, null, null, null, 0, 1, this.cell);
        this.off = new Quadtree(null, null, null, null, 0, 0, this.cell);

    }


    /**
     * Produce a new Quadtree with the given depth full of {@link HashLife#off} 
     * @param depth depth of the new Quadtree
     * @return new Quadtree full of {@link HashLife#off} 
     */
    public Quadtree getZero(int depth){
        return (depth <= 0) ? this.off : new Quadtree(getZero(depth-1), getZero(depth-1), getZero(depth-1), getZero(depth-1));
    }

    /**
     * Produce a new Quadtree center on the given Quadtree
     * @param tree The tree that will centered
     * @return tree centered
     */
    public Quadtree center(Quadtree tree){
        Quadtree zero = getZero(tree.getNe().getDepth());
        return new Quadtree(new Quadtree(zero, zero, zero, tree.getNw()), new Quadtree(zero, zero, tree.getNe(), zero), new Quadtree(zero, tree.getSw(), zero, zero), new Quadtree(tree.getSe(), zero, zero, zero)); 
    }

    /**
     * Compute the next gen for a 3x3 collection of Cell, where center is the Center
     * @param center the Center of the 3x3 collection
     * @param neighboors Other Quadtree of the 3x3 collection
     * @return new Quadtree at next gen
     */
    private Quadtree life(Quadtree center ,Quadtree... neighboors){
        int neighboorsAlive = 0;
        for (Quadtree quadtree : neighboors){
            if(quadtree != null){
                neighboorsAlive += quadtree.getNumberAlive();
            }
        }
        return ((center.getNumberAlive() == 1 && neighboorsAlive >= center.getCell().getDieMinNeighbors() && neighboorsAlive <= center.getCell().getDieMaxNeighbors()) 
                || (center.getNumberAlive() == 0 
                && neighboorsAlive >= center.getCell().getBornMinNeighbors() && neighboorsAlive <= center.getCell().getBornMaxNeighbors())) ? on : off;
    }

    /**
     * Compute the next gen for a Quadtree of depth 1 
     * @param tree The quadtree which will be compute
     * @return New Quadtree a gen +1
     */
    private Quadtree life_2x2(Quadtree tree){
        Quadtree nw = life(tree.getNw().getSe(), tree.getNw().getNw(), tree.getNw().getNe(), tree.getNe().getNw(), tree.getNw().getSw(), tree.getNe().getSw(), tree.getSw().getNw(), tree.getSw().getNe(), tree.getSe().getNw());  
        Quadtree ne = life(tree.getNe().getSw(), tree.getNw().getNe(), tree.getNe().getNw(), tree.getNe().getNe(), tree.getNw().getSe(), tree.getNe().getSe(), tree.getSw().getNe(), tree.getSe().getNw(), tree.getSe().getNe());  
        Quadtree sw = life(tree.getSw().getNe(), tree.getNw().getSw(), tree.getNw().getSe(), tree.getNe().getSw(), tree.getSw().getNw(), tree.getSe().getNw(), tree.getSw().getSw(), tree.getSw().getSe(), tree.getSe().getSw()); 
        Quadtree se = life(tree.getSe().getNw(), tree.getNw().getSe(), tree.getNe().getSw(), tree.getNe().getSe(), tree.getSw().getNe(), tree.getSe().getNe(), tree.getSw().getSe(), tree.getSe().getSw(), tree.getSe().getSe());  
        return new Quadtree(nw, ne, sw, se);
    }

    /**
     * Return the central successor of a Quadtree at time 2**j
     * @param tree 
     * @param j
     * @return central successor of a tree at time 2**j
     */
    private Quadtree successor(Quadtree tree, Integer j){
        Quadtree res;

        if(cache.containsKey(tree)){
            return cache.get(tree);
        }

        if(tree.getNumberAlive() == 0){
            return tree.getNe();
        }

        if(tree.getDepth() == 1){
            return life_2x2(center(tree));
        }

        if (tree.getDepth() == 2){
            return life_2x2(tree);
        }

        else{
            j = (j == null) ? (tree.getDepth() - 2) : Math.min(j, tree.getDepth()-2);

            Quadtree c1 = successor(new Quadtree(tree.getNw().getNw(), tree.getNw().getNe(), tree.getNw().getSw(), tree.getNw().getSe()), j);
            Quadtree c2 = successor(new Quadtree(tree.getNw().getNe(), tree.getNe().getNw(), tree.getNw().getSe(), tree.getNe().getSw()), j);
            Quadtree c3 = successor(new Quadtree(tree.getNe().getNw(), tree.getNe().getNe(), tree.getNe().getSw(), tree.getNe().getSe()), j);
            Quadtree c4 = successor(new Quadtree(tree.getNw().getSw(), tree.getNw().getSe(), tree.getSw().getNw(), tree.getSw().getNe()), j);
            Quadtree c5 = successor(new Quadtree(tree.getNw().getSe(), tree.getNe().getSw(), tree.getSw().getNe(), tree.getSe().getNw()), j);
            Quadtree c6 = successor(new Quadtree(tree.getNe().getSw(), tree.getNe().getSe(), tree.getSe().getNw(), tree.getSe().getNe()), j);
            Quadtree c7 = successor(new Quadtree(tree.getSw().getNw(), tree.getSw().getNe(), tree.getSw().getSw(), tree.getSw().getSe()), j);
            Quadtree c8 = successor(new Quadtree(tree.getSw().getNe(), tree.getSe().getNw(), tree.getSw().getSe(), tree.getSe().getSw()), j);
            Quadtree c9 = successor(new Quadtree(tree.getSe().getNw(), tree.getSe().getNe(), tree.getSe().getSw(), tree.getSe().getSe()), j);
            
            if(j < (tree.getDepth()-2)){
                res = new Quadtree(
                        (new Quadtree(c1.getSe(), c2.getSw(), c4.getNe(), c5.getNw())),
                        (new Quadtree(c2.getSe(), c3.getSw(), c5.getNe(), c6.getNw())),
                        (new Quadtree(c4.getSe(), c5.getSw(), c7.getNe(), c8.getNw())),
                        (new Quadtree(c5.getSe(), c6.getSw(), c8.getNe(), c9.getNw()))
                    );
            }
            else{
                res = new Quadtree(
                        successor(new Quadtree(c1, c2, c4, c5), j),
                        successor(new Quadtree(c2, c3, c5, c6), j),
                        successor(new Quadtree(c4, c5, c7, c8), j),
                        successor(new Quadtree(c5, c6, c8, c9), j)
                    );
            }
        }
        cache.put(tree, res);
        return res;
    }

    /**
     * Lets know if the given Quadtree is padded or not
     * A Quadtree is padded when it is surronded by Quadtree full of off
     * @param tree Tree to test
     * @return True if it is padded else False
     */
    public boolean isPadded(Quadtree tree){
        if(tree.getDepth()<3){
            return true;
        }
        return(
            tree.getNw().getNumberAlive() == tree.getNw().getSe().getSe().getNumberAlive() &&
            tree.getNe().getNumberAlive() == tree.getNe().getSw().getSw().getNumberAlive() &&
            tree.getSw().getNumberAlive() == tree.getSw().getNe().getNe().getNumberAlive() &&
            tree.getSe().getNumberAlive() == tree.getSe().getNw().getNw().getNumberAlive()
        );
    }

    /**
     * Return the given tree padded
     * @param tree Quadtree will be padded
     * @return Given Quadtree padded
     */
    public Quadtree pad(Quadtree tree){
        if (tree.getDepth() <= 3 || !isPadded(tree)){
            return pad(center(tree));
        }
        return tree;
    }

    /**
     * Return the center of the given Quadtree
     * @param tree 
     * @return Center of tree
     */
    public Quadtree inner(Quadtree tree){
        if (tree.getDepth() < 2){
            return null;
        }
        return new Quadtree(tree.getNw().getSe(), tree.getNe().getSw(), tree.getSw().getNe(), tree.getSe().getNw());
    }

    /**
     * Repeatedly take the inner node, until all pading is removed
     * @param tree 
     * @return tree where all pading is removed
     */
    public Quadtree crop(Quadtree tree){
        if(tree.getDepth() < 2){
            return tree; 
        }
        if(!isPadded(tree)){
            return inner(tree);
        }

        return crop(inner(tree));
    }

   /**
    * Advance tree by exactly n generations
    * @param tree Tree to advance
    * @param n Number of generations
    * @return tree at the n generation
    */
    public Quadtree advance(Quadtree tree, int n){
        if(n == 0){
            return tree;
        }

        ArrayList<Integer> bits = new ArrayList<Integer>();

        while(n>0){
            bits.add(n & 1);
            n = n >> 1;
            tree = center(tree);
        }

        tree = center(tree);

        for (int i = 0; i < bits.size(); i++){
            int bit = bits.get(bits.size() - i - 1);
            int j = bits.size() - i - 1;
            if(bit >=1){
                tree = center(successor(tree, j));
            }
        }
        return crop(tree);
    }
}