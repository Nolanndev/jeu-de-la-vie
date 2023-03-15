package main.core;

/**
 * Class which represent a Cell in game of life.
 * Cell is defined by : 
 * <ul>
 * <li> The minimum required neighbors to be alive </li>
 * <li> The maximum required neighbors to be alive </li>
 * <li> The radius of neighbors considered </li>
 * <li> Is state, alive or not</li>
 * 
 * </ul>
 * 
 * 
 * @author Parcheminer Nolann
 * @author David Matthias
 */
public class Cell {


    /**
     * the minimum required Neighbors to be alive
     */
    protected Integer minNeighbors;

    /**
     * the maximum required Neighbors to be alive
     */
    protected Integer maxNeighbors;

    /**
     * the radius of neighbors considered
     */
    protected int radius;

    /**
     * State of Cell, alive or not
     */
    protected boolean alive;

    
    /**
     * Constructor which produce new Cell with specified value.
     * 
     * @param minNeighbors  value of <b>minNeighbors</b>
     * @param maxNeighbors  value of <b>maxNeighbors</b>
     * @param radius  value of <b>radius</b>
     * @param alive   value of <b>alive</b>
     * 
     * @see Cell
     * 
     * @author Parcheminer Nollan
    */
    public Cell(int minNeighbors, int maxNeighbors, int radius, boolean alive) {
        setState(alive);
        setMinNeighbors(minNeighbors);
        setMaxNeighbors(maxNeighbors);
        setRadius(radius);
    }

    /**
     * Constructor which produce new default Cell of game of life.
     * Cell have for value:
     * <ul>
     * <li><b>minNeighbors</b> = 2</li>
     * <li><b>maxNeighbors</b> = 3</li>
     * <li><b>radius</b> = 1</li>
     * </ul>
     * @param alive value of <b>alive</b>
     */
    public Cell(boolean alive){
        this(2,3,1,alive);
    }


    /**
     * Accessor to the value of <b>minNeighbors</b>.
     * @return value of <b>minNeighbors</b>.
    */
    public int getMinNeighbors() {
        return this.minNeighbors;
    }

    /**
     * Defined <b>minNeighbors</b> value of a cell.
     * @param newMin new value of <b>minNeighbors</b>.
     * @throws ExceptionInInitializerError Occured if <b>newMin</b> is less than <b>0</b> or <b>newMin</b> are greater than <b>maxNeigbors</b>.
     */
    public void setMinNeighbors(int newMin) throws ExceptionInInitializerError {
        if(newMin < 0){
            throw new ExceptionInInitializerError("minNeighbors must be positive number.");
        }
        else if(this.maxNeighbors!=null && newMin > this.maxNeighbors){
            throw new ExceptionInInitializerError("minNeighbors must be less than maxNeighbors.");
        }
        this.minNeighbors = newMin;
    }

    
    /**
     * Accessor to the value of <b>maxNeighbors</b>.
     * @return value of <b>maxNeighbors</b>.
    */
    public int getMaxNeighbors() {
        return this.maxNeighbors;
    }


    /**
     * Defined <b>maxNeighbors</b> value of a cell.
     * @param newMax new value of <b>maxNeighbors</b>.
     * @throws ExceptionInInitializerError Occured if maxNeighbors are less than minNeigbors.
    */
    public void setMaxNeighbors(int newMax) throws ExceptionInInitializerError {
        if(this.minNeighbors != null &&  newMax < this.minNeighbors){
            throw new ExceptionInInitializerError("maxNeighbors must be superior to minNeighbors");
        }
        this.maxNeighbors = newMax;
    }


    /**
     * Accessor to the value of <b>radius</b>.
     * @return value of <b>radius</b>.
    */
    public int getRadius() {
        return radius;
    }
    
    /**
     * Defined <b>radius</b> value of a cell.
     * @param radius new value of <b>radius</b>.
     * @throws ExceptionInInitializerError Occured if <b>radius</b> are less than <b>0</b>.
     */
    public void setRadius(int radius) throws ExceptionInInitializerError{
        if(radius < 0){
            throw new ExceptionInInitializerError("radius must be a positive number.");
        }
        this.radius = radius;
    }
    
    
    /**
     * Accessor to state of cell.
     * @return <b>true</b> if cell is <b>alive</b>, <b>else</b> return <b>false</b>
    */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Defined state of a cell. 
     * @param newState alive or not.
    */
    public void setState(boolean newState) {
        this.alive = newState;
    }



    /**
     * Access to representation of Cell in String.
     * @return representation of cell
     */
    public String infos() {
        return "Status: " + this.alive + "\nNeighbors\n\tmin: "
                + this.minNeighbors + "\n\tmax: " + this.maxNeighbors + "\nradius : " + this.radius;
    }
}
