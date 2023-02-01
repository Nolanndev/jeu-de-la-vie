package main.core;


public interface Cell {
    public void setState(boolean newState);

    public boolean isAlive();

    public int getRadius();

    public void setMinNeighbors(int newMin);

    public int getMinNeighbors();

    public void setMaxNeighbors(int newMax);

    public int getMaxNeighbors();

    public String infos();

    @Override
    public int hashCode();
}
