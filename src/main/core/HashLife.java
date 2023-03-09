package main.core;

import java.util.ArrayList;
import java.util.HashMap;

import main.utils.Quadtree;

public class HashLife extends Quadtree{

    private HashMap<Quadtree, Quadtree> cache; 
    public static Quadtree on = new Quadtree(null, null, null, null, 0, 1);
    public static Quadtree off = new Quadtree(null, null, null, null, 0, 0);


    public HashLife() {
        super();
    }
    
<<<<<<< HEAD
    /* 
    fonction recursive
    si la profondeur est inferieure ou egale à 0, on retourne un quadtree null
    sinon on retourne un noeud vide au niveau k
    */
=======
    /*
    si la profondeur est inferieure ou egale à 0, on appelle le constructeur de la classe mere
    sinon on verifie le quadtree en diminuant la profondeur de -1
     */

>>>>>>> da918629ed327c16c3032d1348c52e17f1349553
    public Quadtree getZero(int depth){
        return (depth <= 0) ? HashLife.off : this.join(getZero(depth-1), getZero(depth-1), getZero(depth-1), getZero(depth-1));
    }

    /* 
    combine quatre enfants au niveau k-1 à un nouveau noeud au niveau k. 
    Si c’est mis en cache, retourne le noeud mis en cache. 
    Sinon, créez un nouveau noeud et ajoutez-le au cache
    */

    public Quadtree join(Quadtree no, Quadtree ne, Quadtree se, Quadtree so){
        if(so == null || se == null || ne == null || no == null){
            return null;
        }
        int numberAlive = no.getNumberAlive() + ne.getNumberAlive() + se.getNumberAlive() + so.getNumberAlive();  
        return new Quadtree(no, ne, se, so, no.getDepth()+1, numberAlive );
    }

    /*
    renvoie un noeud au niveau k+1, qui est centré sur le noeud quadtree donné :
    */
    
    public Quadtree centre(Quadtree centre){
        Quadtree zero = getZero(centre.getNe().getDepth());
        return this.join(join(zero, zero, zero, centre.getNw()), join(zero, zero, centre.getNe(), zero), join(zero, centre.getSw(), zero, zero), join(centre.getSe(), zero, zero, zero)); 
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
        return ((neighboorsAlive == 2 && centre.getNumberAlive() == 1) || neighboorsAlive==3) ? on : off;
    }

    public Quadtree life_4x4(Quadtree m){
        Quadtree nw = life(m.getNw().getSe(), m.getNw().getNw(), m.getNw().getNe(), m.getNe().getNw(), m.getNw().getSw(), m.getNe().getSw(), m.getSw().getNw(), m.getSw().getNe(), m.getSe().getNw());  
        Quadtree ne = life(m.getNe().getSw(), m.getNw().getNe(), m.getNe().getNw(), m.getNe().getNe(), m.getNw().getSe(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNw(), m.getSe().getNe());  
        Quadtree sw = life(m.getSw().getNe(), m.getNw().getSw(), m.getNw().getSe(), m.getNe().getSw(), m.getSw().getNw(), m.getSe().getNw(), m.getSw().getSw(), m.getSw().getSe(), m.getSe().getSw()); 
        Quadtree se = life(m.getSe().getNw(), m.getNw().getSe(), m.getNe().getSw(), m.getNe().getSe(), m.getSw().getNe(), m.getSe().getNe(), m.getSw().getSe(), m.getSe().getSw(), m.getSe().getSe());  
        return join(nw, ne, sw, se);
    }

    /*
    retourne le successeur central d’un noeud au moment t+2**k-2.
    */
    public Quadtree successor(Quadtree m, Integer j){
        Quadtree res;
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