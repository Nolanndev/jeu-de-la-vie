package main.core;

import java.util.ArrayList;
import main.utils.Tuple;

public class Cell {
    protected Tuple coordinates;
    protected boolean alive;
    protected ArrayList<Cell> neighbors;
    protected int minNeighbors;
    protected int maxNeighbors;

    public Cell(Tuple coordinates, int minNeighbors, int maxNeighbors) {
        this.coordinates = coordinates;
        this.alive = true;
        this.neighbors = new ArrayList<Cell>();
        this.minNeighbors = minNeighbors;
        this.maxNeighbors = maxNeighbors;
    }

    public Cell(Tuple coordinates) {
        this(coordinates, 2, 3);
    }

    public Cell() {
        this(new Tuple(0,0), 2, 3);
    }

    public void setState(boolean state) {
        this.alive = state;
    }
    public boolean isAlive() {
        return this.alive;
    }

    public Integer getCoordinateX() {
        return this.coordinates.getValue1();
    }

    public int getCoordinateY() {
        return this.coordinates.getValue2();
    }

    public ArrayList<Cell> getNeighbors(int radius) {
        return this.neighbors;
    }

    public void setMinNeighbors(int newMin) {
        this.minNeighbors = newMin;
    }

    public int getMinNeighbors() {
        return this.minNeighbors;
    }

    public int getMaxNeighbors() {
        return this.maxNeighbors;
    }

    public void setMaxNeighbors(int maxNeighbors) {
        this.maxNeighbors = maxNeighbors;
    }

}
