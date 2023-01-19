package main.core;

import org.javatuples.*;
import java.util.ArrayList;

public class Cell {
    protected Pair<Integer,Integer> coordinates;
    protected boolean alive;
    protected ArrayList<Cell> neighbors;
    protected int maxNeighbors;

    public Cell(Pair<Integer,Integer> coordinates, boolean alive, int maxNeighbors) {
        this.coordinates = coordinates;
        this.alive = alive;
        this.neighbors = new ArrayList<Cell>();
        this.maxNeighbors = maxNeighbors;
    }

    public void setState(boolean state) {
        this.alive = state;
    }
    public boolean isAlive() {
        return this.alive;
    }

    public Integer getCoordinateX() {
        return this.coordinates.getValue0();
    }

    public int getCoordinateY() {
        return this.coordinates.getValue1();
    }

    public ArrayList<Cell> getNeighbors(int radius) {
        return this.neighbors;
    }

    public int getMaxNeighbors() {
        return this.maxNeighbors;
    }

    public void setMaxNeighbors(int maxNeighbors) {
        this.maxNeighbors = maxNeighbors;
    }

}
