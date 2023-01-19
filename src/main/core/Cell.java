package main.core;


public class Cell {
    
    protected boolean alive;
    

    public Cell(boolean alive) {
        this.alive = alive;
    }

    public void setState(boolean state) {
        this.alive = state;
    }
    public boolean isAlive() {
        return this.alive;
    }

    
    
}
