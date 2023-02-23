package main.core;

import java.util.HashMap;

import main.utils.Quadtree;

public class HashLife extends Quadtree{

    private HashMap<Quadtree, Quadtree> cache; 
    public static Quadtree on = new Quadtree(null, null, null, null, 0, 1);
    public static Quadtree off = new Quadtree(null, null, null, null, 0, 0);


    public HashLife() {
        super();
    }

    public Quadtree getZero(int depth){
        return (depth <= 0) ? HashLife.off : this.join(getZero(depth-1), getZero(depth-1),getZero(depth-1), getZero(depth-1));
    }

    public Quadtree join(Quadtree no, Quadtree ne, Quadtree se, Quadtree so){
        if(so == null || se == null || ne == null || no == null){
            return null;
        }
        int numberAlive = no.getNumberAlive() + ne.getNumberAlive() + se.getNumberAlive() + so.getNumberAlive();  
        return new Quadtree(no, ne, se, so, no.getDepth()+1, numberAlive );
    }

    public Quadtree centre(Quadtree centre){
        Quadtree zero = getZero(centre.getNe().getDepth());
        return this.join(join(zero, zero, zero, centre.getNw()), join(zero, zero, centre.getNe(), zero), join(zero, centre.getSw(), zero, zero), join(centre.getSe(), zero, zero, zero));
        
    }

    public Quadtree life(Quadtree centre ,Quadtree... neighboors){
        int neighboorsAlive = 0;
        for (Quadtree quadtree : neighboors) {
            if(quadtree != null){
                neighboorsAlive += quadtree.getNumberAlive();
            }
        }
        return ((neighboorsAlive == 2 && centre.getNumberAlive() == 1) || neighboorsAlive==3) ? on : off;
    }

    public Quadtree life_4x4(Quadtree m){
        Quadtree nw = life(m.getNw().getSe(), m.getNw().getNw(), m.getNw().getNe(), m.getNe().getNw(), m.getNw().getSw(), m.getNe().getSw(), m.getSw().getNw(), m.getSw().getNe(), m.getSe().getNw());  
        Quadtree ne = life(m.getNe().getSw(), m.getNw().getNe(), m.getNe().getNw(), m.getNe().getNe(), m.getNw().getSe(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNw(), m.getSe().getNe());  
        Quadtree sw = life(m.getSw().getNe(), m.getNw().getSw(), m.getNw().getSe(), m.getNe().getSw(), m.getSw().getNw(), m.getSe().getNw(), m.getSw().getSw(), m.getSw().getSe(), m.getSe().getSw()); 
        Quadtree se = life(m.getSe().getNw(), m.getNw().getSe(), m.getNe().getSw(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNe(), m.getSw().getSe(), m.getSe().getSw(), m.getSe().getSe());  
        return join(nw, ne, sw, se);
    }

    public Quadtree successor(Quadtree m, Integer j){
        Quadtree res;
        if(m == null){
            return null;
        }
        
        if(m.getNumberAlive() == 0){
            return m.getNe();
        }

        if(m.getDepth() == 1){
            return life_4x4(centre(m));
        }

        if (m.getDepth() == 2){
            return life_4x4(m);
        }

        else{
            j = (j == null) ? (m.getDepth() - 2) : Math.min(j, m.getDepth()-2);

            Quadtree c1 = successor(join(m.getNw().getNw(), m.getNw().getNe(), m.getNw().getSw(), m.getNw().getSe()), j);
            Quadtree c2 = successor(join(m.getNw().getNe(), m.getNe().getNw(), m.getNw().getSe(), m.getNe().getSw()), j);
            Quadtree c3 = successor(join(m.getNe().getNw(), m.getNe().getNe(), m.getNe().getSw(), m.getNe().getSe()), j);
            Quadtree c4 = successor(join(m.getNw().getSw(), m.getNw().getSe(), m.getSw().getNw(), m.getSw().getNe()), j);
            Quadtree c5 = successor(join(m.getNw().getSe(), m.getNe().getSw(), m.getSw().getNe(), m.getSe().getNw()), j);
            Quadtree c6 = successor(join(m.getNe().getSw(), m.getNe().getSe(), m.getSe().getNw(), m.getSe().getNe()), j);
            Quadtree c7 = successor(join(m.getSw().getNw(), m.getSw().getNe(), m.getSw().getSw(), m.getSw().getSe()), j);
            Quadtree c8 = successor(join(m.getSw().getNe(), m.getSe().getNw(), m.getSw().getSe(), m.getSe().getSw()), j);
            Quadtree c9 = successor(join(m.getSe().getNw(), m.getSe().getNe(), m.getSe().getSw(), m.getSe().getSe()), j);
            
            if(j < (m.getDepth()-2)){
                res = join(
                        (join(c1.getSe(), c2.getSw(), c4.getNe(), c5.getNw())),
                        (join(c2.getSe(), c3.getSw(), c5.getNe(), c6.getNw())),
                        (join(c4.getSe(), c5.getSw(), c7.getNe(), c8.getNw())),
                        (join(c5.getSe(), c6.getSw(), c8.getNe(), c9.getNw()))
                    );
            }
            else{
                res = join(
                        successor(join(c1, c2, c4, c5), j),
                        successor(join(c2, c3, c5, c6), j),
                        successor(join(c4, c5, c7, c8), j),
                        successor(join(c5, c6, c8, c9), j)
                    );
            }
        }

        return res;
    }

}