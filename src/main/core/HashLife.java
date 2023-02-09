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

    public Quadtree join(Quadtree no, Quadtree ne, Quadtree se, Quadtree so){
        if(no == null || ne == null || so == null | se == null){
            return null;
        }
        int totalNUmberAlive = no.getNumberAlive()+ne.getNumberAlive()+so.getNumberAlive()+se.getNumberAlive();
        return new Quadtree(no, ne, se, so, no.getDepth()+1, totalNUmberAlive);
    }

    public Quadtree centre(Quadtree centre){
        Quadtree zero = getZero(centre.getDepth());
        return this.join( join(zero, zero, centre.getNw(), zero), join(zero, zero, zero, centre.getNe()), join(centre.getSe(), zero, zero, zero), join(zero, centre.getSw(), zero, zero));
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
        Quadtree nw = life(m.getNw().getNw(), m.getNw().getNe(), m.getNe().getNw(), m.getNw().getSw(), m.getNw().getSe(), m.getNe().getSw(), m.getSw().getNw(), m.getSw().getNe(), m.getSe().getNw());  
        Quadtree ne = life(m.getNw().getNe(), m.getNe().getNw(), m.getNe().getNe(), m.getNw().getSe(), m.getNe().getSw(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNw(), m.getSe().getNe());  
        Quadtree sw = life(m.getNw().getSw(), m.getNw().getSe(), m.getNe().getSw(), m.getSw().getNw(), m.getSw().getNe(), m.getSe().getNw(), m.getSw().getSw(), m.getSw().getSe(), m.getSe().getSw()); 
        Quadtree se = life(m.getNw().getSe(), m.getNe().getSw(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNw(), m.getSe().getNe(), m.getSw().getSe(), m.getSe().getSw(), m.getSe().getSe());  
        return join(nw, ne, se, sw);
    }

    public Quadtree successor(Quadtree m, int j){
        Quadtree res;
        if(m == null){
            return null;
        }
        if(m.getNumberAlive() == 0){
            return m.getNe();
        }
        else if (m.getDepth() == 2){
            res = life_4x4(m);
        }

        else{
            j = (j<=0) ? (m.getDepth() - 2) : Math.min(j, m.getDepth()-2);
        }

        Quadtree c1 = successor(join(m.getNe().getNe(), m.getNe().getNw(), m.getNe().getSw(), m.getNe().getSe()), j);
        Quadtree c2 = successor(join(m.getNe().getNw(), m.getNw().getNe(), m.getNe().getSe(), m.getNw().getSw()), j);
        Quadtree c3 = successor(join(m.getNw().getNe(), m.getNw().getNw(), m.getNw().getSw(), m.getNw().getSe()), j);
        Quadtree c4 = successor(join(m.getNe().getSw(), m.getNe().getSe(), m.getSw().getNe(), m.getSw().getNw()), j);
        Quadtree c5 = successor(join(m.getNe().getSe(), m.getNw().getSw(), m.getSw().getNw(), m.getSe().getNe()), j);
        Quadtree c6 = successor(join(m.getNw().getSw(), m.getNw().getSe(), m.getSe().getNe(), m.getSe().getNw()), j);
        Quadtree c7 = successor(join(m.getSw().getNe(), m.getSw().getNw(), m.getSw().getSw(), m.getSw().getSe()), j);
        Quadtree c8 = successor(join(m.getSw().getNw(), m.getSe().getNe(), m.getSw().getSe(), m.getSe().getSw()), j);
        Quadtree c9 = successor(join(m.getSe().getNe(), m.getSe().getNw(), m.getSe().getSw(), m.getSe().getSe()), j);

        if(j<m.getDepth()-2){
            res = join(
                    (join(c1.getSe(), c2.getSw(), c4.getNw(), c5.getNe())),
                    (join(c2.getSe(), c3.getSw(), c5.getNw(), c6.getNe())),
                    (join(c4.getSe(), c5.getSw(), c7.getNw(), c8.getNe())),
                    (join(c5.getSe(), c6.getSw(), c8.getNw(), c9.getNe()))
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

        return res;
    }

}