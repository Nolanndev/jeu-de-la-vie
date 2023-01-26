package main;

import main.core.*;

public class Gol{
    public static void main(String [] args){
        System.out.println("DEBUT");
        System.out.println("-----------");
        Grid g = new Grid(5, 5);
        System.out.println(g.getColumns());
        System.out.println(g.getRows());
        g.showGrid();
    }
}
