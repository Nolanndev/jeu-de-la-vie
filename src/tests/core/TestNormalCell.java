package tests.core;

import main.core.NormalCell;

/* 
Ce fichier servira juste à 
tester le fichier NormalCell.java
*/

public class TestNormalCell{
    NormalCell cell = new NormalCell(0,3,1,true);

    public boolean testIsAliveTrue(){
        return cell.isAlive() == true;
    }

    public boolean testIsAliveFalse(){
        NormalCell cell2 = new NormalCell(0,3,1,false);
        return cell2.isAlive() == false;
    }
    public boolean testGetRadius(){
        return cell.getRadius() == 1;
    }
    
    public boolean testGetMinNeighbors(){
        return cell.getMinNeighbors() == 0;
    }
    
    public boolean testGetMaxNeighbors(){
        return cell.getMaxNeighbors() == 3;
    }
    
    public boolean testInfos(){
        return cell.infos().equals("Status: true\nNeighbors\n\tmin: 0\n\tmax: 3");
    }
}