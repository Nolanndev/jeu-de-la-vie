package main.core;

public abstract class BasicCell implements Cell {
    protected boolean alive;
    protected int minNeighbors;
    protected int maxNeighbors;
    protected int radius;

    /*
    BasicCell prend 4 parametres dans son constructeur : 
        le nombre mimimum de voisin pour vivre
        le nombre maximum de voision pour mourir
        la portée prise en compte pour compter les cellules
        son statut de vie : mort ou en vie
     */

    public BasicCell(int minNeighbors, int maxNeighbors, int radius, boolean alive) {
        this.alive = alive;
        this.minNeighbors = minNeighbors;
        this.maxNeighbors = maxNeighbors;
        this.radius = radius;
    }

    public BasicCell(boolean alive) {
        this(2, 3, 1, alive);
    }

    public BasicCell() {
        this(2, 3, 1, false);
    }

    @Override
    public void setState(boolean state) {
        this.alive = state;
    }
    
    public boolean isAlive() {
        return this.alive;
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
        return "Status: " + this.alive + "\nNeighbors\n\tmin: "
                + this.minNeighbors + "\n\tmax: " + this.maxNeighbors;
    }
}
