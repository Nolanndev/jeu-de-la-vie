package main.core;

import main.utils.Tuple;

public interface Cell {
    public void setState(boolean newState);

    public boolean isAlive();

    public Tuple getCoordinates();

    public int getCoordinateX();

    public int getCoordinateY();

    public void setMinNeighbors(int newMin);

    public int getMinNeighbors();

    public void setMaxNeighbors(int newMax);

    public int getMaxNeighbors();

    public String infos();

    public void viewCoordinates();
}
