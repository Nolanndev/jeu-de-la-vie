package main.core;

import java.util.ArrayList;
import java.util.HashMap;

public class HashLife{


    private Cell cell;
    private HashMap<Quadtree, Quadtree> cache = new HashMap<Quadtree, Quadtree>(); 
    public Quadtree on;
    public Quadtree off;


    public HashLife(Cell cell) {
        this.cell = cell;
        this.on = new Quadtree(null, null, null, null, 0, 1, cell);
        this.off = new Quadtree(null, null, null, null, 0, 0, cell);

    }

    /* 
    fonction recursive
    si la profondeur est inferieure ou egale à 0, on retourne un quadtree null
    sinon on retourne un noeud vide au niveau k
    */
    public Quadtree getZero(int depth){
        return (depth <= 0) ? this.off : new Quadtree(getZero(depth-1), getZero(depth-1), getZero(depth-1), getZero(depth-1));
    }

    /*
    renvoie un noeud au niveau k+1, qui est centré sur le noeud quadtree donné :
    */
    
    public Quadtree centre(Quadtree centre){
        Quadtree zero = getZero(centre.getNe().getDepth());
        return new Quadtree(new Quadtree(zero, zero, zero, centre.getNw()), new Quadtree(zero, zero, centre.getNe(), zero), new Quadtree(zero, centre.getSw(), zero, zero), new Quadtree(centre.getSe(), zero, zero, zero)); 
    }

    /* 
    ... : tableau de Quadtree  
    retourne la prochaine génération d’une cellule k=2. 
    Pour mettre fin à la récursion, au niveau de la base, 
    si nous avons un bloc k=2 4x4, nous pouvons calculer les 2x2 
    successeurs centraux en itérant sur les 3x3 sous-quartiers de 1x1 
    cellules en utilisant la règle de vie standard (ou toute autre 
    règle d’automate cellulaire binaire de quartier Moore que nous choisissons)
    */
    public Quadtree life(Quadtree centre ,Quadtree... neighboors){
        int neighboorsAlive = 0;
        for (Quadtree quadtree : neighboors) {
            if(quadtree != null){
                neighboorsAlive += quadtree.getNumberAlive();
            }
        }
        return ((centre.getNumberAlive() == 1 && neighboorsAlive >= cell.getDieMinNeighbors() && neighboorsAlive <= cell.getDieMaxNeighbors()) || (centre.getNumberAlive() == 0 && neighboorsAlive >= cell.getBornMinNeighbors() && neighboorsAlive <= cell.getBornMaxNeighbors())) ? on : off;
    }

    private Quadtree life_4x4(Quadtree m){
        Quadtree nw = life(m.getNw().getSe(), m.getNw().getNw(), m.getNw().getNe(), m.getNe().getNw(), m.getNw().getSw(), m.getNe().getSw(), m.getSw().getNw(), m.getSw().getNe(), m.getSe().getNw());  
        Quadtree ne = life(m.getNe().getSw(), m.getNw().getNe(), m.getNe().getNw(), m.getNe().getNe(), m.getNw().getSe(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNw(), m.getSe().getNe());  
        Quadtree sw = life(m.getSw().getNe(), m.getNw().getSw(), m.getNw().getSe(), m.getNe().getSw(), m.getSw().getNw(), m.getSe().getNw(), m.getSw().getSw(), m.getSw().getSe(), m.getSe().getSw()); 
        Quadtree se = life(m.getSe().getNw(), m.getNw().getSe(), m.getNe().getSw(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNe(), m.getSw().getSe(), m.getSe().getSw(), m.getSe().getSe());  
        return new Quadtree(nw, ne, sw, se);
    }

    /*
    retourne le successeur central d’un noeud au moment t+2**k-2.
    */
    private Quadtree successor(Quadtree m, Integer j){
        Quadtree res;

        if(cache.containsKey(m)){
            return cache.get(m);
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

            Quadtree c1 = successor(new Quadtree(m.getNw().getNw(), m.getNw().getNe(), m.getNw().getSw(), m.getNw().getSe()), j);
            Quadtree c2 = successor(new Quadtree(m.getNw().getNe(), m.getNe().getNw(), m.getNw().getSe(), m.getNe().getSw()), j);
            Quadtree c3 = successor(new Quadtree(m.getNe().getNw(), m.getNe().getNe(), m.getNe().getSw(), m.getNe().getSe()), j);
            Quadtree c4 = successor(new Quadtree(m.getNw().getSw(), m.getNw().getSe(), m.getSw().getNw(), m.getSw().getNe()), j);
            Quadtree c5 = successor(new Quadtree(m.getNw().getSe(), m.getNe().getSw(), m.getSw().getNe(), m.getSe().getNw()), j);
            Quadtree c6 = successor(new Quadtree(m.getNe().getSw(), m.getNe().getSe(), m.getSe().getNw(), m.getSe().getNe()), j);
            Quadtree c7 = successor(new Quadtree(m.getSw().getNw(), m.getSw().getNe(), m.getSw().getSw(), m.getSw().getSe()), j);
            Quadtree c8 = successor(new Quadtree(m.getSw().getNe(), m.getSe().getNw(), m.getSw().getSe(), m.getSe().getSw()), j);
            Quadtree c9 = successor(new Quadtree(m.getSe().getNw(), m.getSe().getNe(), m.getSe().getSw(), m.getSe().getSe()), j);
            
            if(j < (m.getDepth()-2)){
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
        cache.put(m, res);
        return res;
    }

    private boolean isPadded(Quadtree q){
        return(
            q.getNw().getNumberAlive() == q.getNw().getSe().getSe().getNumberAlive() &&
            q.getNe().getNumberAlive() == q.getNe().getSw().getSw().getNumberAlive() &&
            q.getSw().getNumberAlive() == q.getSw().getNe().getNe().getNumberAlive() &&
            q.getSe().getNumberAlive() == q.getSe().getNw().getNw().getNumberAlive()
        );
    }

    public Quadtree pad(Quadtree q){
        if (q.getDepth() <= 3 || !isPadded(q)){
            return pad(centre(q));
        }
        return q;
    }

    public Quadtree inner(Quadtree q){
        return new Quadtree(q.getNw().getSe(), q.getNe().getSw(), q.getSw().getNe(), q.getSe().getNw());
    }

    public Quadtree crop(Quadtree q){
        if(q.getDepth() <= 3 || !isPadded(q)){
            return q; 
        }

        return crop(inner(q));
    }

    /*
    trouve le nième successeur d’un noeud donné. 
    Depuis que nous calculons n’importe quelle étape avancée de 2**j, jusqu’à 2**k-2, de 
    un noeud de niveau k, on peut avancer par n trouver son successeur
    en utilisant les conversions binaires.
    */
    public Quadtree advance(Quadtree q, int n){
        if(n == 0){
            return q;
        }

        ArrayList<Integer> bits = new ArrayList<Integer>();

        while(n>0){
            bits.add(n & 1);
            n = n >> 1;
            q = centre(q);
        }

        q = centre(q);

        for (int i = 0; i < bits.size(); i++) {
            int bit = bits.get(bits.size() - i - 1);
            int j = bits.size() - i - 1;
            if(bit >=1){
                q = successor(q, j);
            }
        }
        return q;
    }
}