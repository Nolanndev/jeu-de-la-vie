package main.core;

public class NormalCell extends BasicCell{

    public NormalCell(Boolean alive) {
        super(alive);
    }

    public NormalCell(int minNeighbors, int maxNeighbors, int radius, boolean alive){
        super(minNeighbors, maxNeighbors, radius, alive);
    }

    public NormalCell() {
        super();
    }
}
