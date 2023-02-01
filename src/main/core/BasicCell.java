package main.core;

import main.utils.Tuple;

public abstract class BasicCell implements Cell {
    protected Tuple coordinates;
    protected boolean alive;
    protected int minNeighbors;
    protected int maxNeighbors;
    protected int radius;

    public BasicCell(Tuple coordinates, int minNeighbors, int maxNeighbors, int radius) {
        this.coordinates = coordinates;
        this.alive = true;
        this.minNeighbors = minNeighbors;
        this.maxNeighbors = maxNeighbors;
        this.radius = radius;
    }

    public BasicCell(Tuple coordinates) {
        this(coordinates, 2, 3,1);
    }

    @Override
    public void setState(boolean state) {
        this.alive = state;
    }
    
    public boolean isAlive() {
        return this.alive;
    }
    
    public void setCoordinates(Tuple newCoordinates) {
        this.coordinates = newCoordinates;
    }
    
    public Tuple getCoordinates() {
        return this.coordinates;
    }

    public int getCoordinateX() {
        return this.coordinates.getValue1();
    }

    public int getCoordinateY() {
        return this.coordinates.getValue2();
    }

    public int getRadius() {
        return radius;
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

    public String infos() {
        return "Coordinates: " + this.coordinates.toString() + "\nStatus: " + this.alive + "\nNeighbors\n\tmin: "
                + this.minNeighbors + "\n\tmax: " + this.maxNeighbors;
    }

    public void viewCoordinates() {
        System.out.println(this.coordinates.toString());
    }
}
