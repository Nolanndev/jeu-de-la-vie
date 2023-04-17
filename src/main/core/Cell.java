package main.core;

/**
 * Class which represent a Cell in game of life.
 * Cell is defined by : 
 * <ul>
 * <li> The minimum required neighbors to born if Cell are dead </li>
 * <li> The maximum required neighbors to born if Cell are dead</li>
 * <li> The minimum required neighbors before die if Cell are alive </li>
 * <li> The maximum required neighbors before die if Cell are alive</li>
 * <li> The radius of neighbors considered </li>
 * <li> Is state, alive or not</li>
 * 
 * </ul>
 * 
 * 
 * @author Parcheminer Nolann
 * @author David Matthias
 */
public class Cell{


    /**
     * the minimum required Neighbors to born if <b>Cell</b> are dead
     */
    protected Integer bornMinNeighbors;

    /**
     * the maximum required Neighbors to born if <b>Cell</b> are dead
     */
    protected Integer bornMaxNeighbors;

    /**
     * the minimum required Neighbors before die if <b>Cell</b> are alive
     */
    protected Integer dieMinNeighbors;

    /**
     * the maximum required Neighbors before die if <b>Cell</b> are alive
     */
    protected Integer dieMaxNeighbors;


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
     * @param bornMinNeighbors  value of <b>bornMinNeighbors</b>
     * @param bornMaxNeighbors  value of <b>bornMaxNeighbors</b>
     * @param dieMinNeighbors value of <b>dieMinNeighbors</b>
     * @param dieMaxNeighbors value of <b>dieMaxNeighbors</b>
     * @param radius  value of <b>radius</b>
     * @param alive   value of <b>alive</b>
     * 
     * @see Cell
     * 
     * @author Parcheminer Nollan
     * @author David Matthias
    */
    public Cell(int bornMinNeighbors, int bornMaxNeighbors, int dieMinNeighbors, int dieMaxNeighbors, int radius, boolean alive){
        setState(alive);
        setBornMinNeighbors(bornMinNeighbors);
        setBornMaxNeighbors(bornMaxNeighbors);
        setDieMinNeighbors(dieMinNeighbors);
        setDieMaxNeighbors(dieMaxNeighbors);
        setRadius(radius);
    }

    /**
     * Constructor which produce new default Cell of game of life.
     * Cell have for value:
     * <ul>
     * <li><b>bornMinNeighbors</b> = 3</li>
     * <li><b>bornMaxNeighbors</b> = 3</li>
     * <li><b>dieMaxNeighbors</b> = 2</li>
     * <li><b>dieMaxNeighbors</b> = 3</li>
     * <li><b>radius</b> = 1</li>
     * </ul>
     * @param alive value of <b>alive</b>
     */
    public Cell(boolean alive){
        this(3,3,2,3,1,alive);
    }

    /**
     * Accessor to the value of <b>bornMinNeighbors</b>.
     * @return value of <b>bornMinNeighbors</b>.
    */
    public int getBornMinNeighbors(){
        return this.bornMinNeighbors;
    }

    /**
     * Defined <b>bornMinNeighbors</b> value of a cell.
     * @param newBornMin new value of <b>bornMinNeighbors</b>.
     * @throws ExceptionInInitializerError Occured if <b>newBornMin</b> is less than <b>0</b> or <b>newBornMin</b> are greater than <b>bornMaxNeigbors</b>.
     */
    private void setBornMinNeighbors(int newBornMin) throws ExceptionInInitializerError{
        if(newBornMin < 0){
            throw new ExceptionInInitializerError("bornMinNeighbors must be positive number.");
        }
        else if(this.bornMaxNeighbors!=null && newBornMin > this.bornMaxNeighbors){
            throw new ExceptionInInitializerError("bornMinNeighbors must be less than bornMaxNeighbors.");
        }
        this.bornMinNeighbors = newBornMin;
    }
    
    /**
     * Accessor to the value of <b>bornMaxNeighbors</b>.
     * @return value of <b>bornMaxNeighbors</b>.
    */
    public int getBornMaxNeighbors(){
        return this.bornMaxNeighbors;
    }

    /**
     * Defined <b>bornMaxNeighbors</b> value of a cell.
     * @param newBornMax new value of <b>bornMaxNeighbors</b>.
     * @throws ExceptionInInitializerError Occured if bornMaxNeighbors are less than bornMinNeigbors.
    */
    private void setBornMaxNeighbors(int newBornMax) throws ExceptionInInitializerError{
        if(this.bornMinNeighbors != null &&  newBornMax < this.bornMinNeighbors){
            throw new ExceptionInInitializerError("bornMaxNeighbors must be superior to bornMinNeighbors");
        }
        this.bornMaxNeighbors = newBornMax;
    }

    /**
     * Accessor to the value of <b>dieMinNeighbors</b>.
     * @return value of <b>dieMinNeighbors</b>.
    */
    public int getDieMinNeighbors(){
        return this.dieMinNeighbors;
    }
    
    /**
     * Defined <b>dieMinNeighbors</b> value of a cell.
     * @param newDieMin new value of <b>dieMinNeighbors</b>.
     * @throws ExceptionInInitializerError Occured if <b>newDieMin</b> is less than <b>0</b> or <b>newDieMin</b> are greater than <b>dieMaxNeigbors</b>.
     */
    private void setDieMinNeighbors(int newDieMin) throws ExceptionInInitializerError{
        if(newDieMin < 0){
            throw new ExceptionInInitializerError("dieMinNeighbors must be positive number.");
        }
        else if(this.dieMaxNeighbors!=null && newDieMin > this.dieMaxNeighbors){
            throw new ExceptionInInitializerError("dieMinNeighbors must be less than dieMaxNeighbors.");
        }
        this.dieMinNeighbors = newDieMin;
    }
    
    
    /**
     * Accessor to the value of <b>dieMaxNeighbors</b>.
     * @return value of <b>dieMaxNeighbors</b>.
    */
    public int getDieMaxNeighbors(){
        return this.dieMaxNeighbors;
    }

    /**
     * Defined <b>dieMaxNeighbors</b> value of a cell.
     * @param newDieMax new value of <b>dieMaxNeighbors</b>.
     * @throws ExceptionInInitializerError Occured if dieMaxNeighbors are less than dieMinNeigbors.
    */
    private void setDieMaxNeighbors(int newDieMax) throws ExceptionInInitializerError{
        if(this.dieMinNeighbors != null &&  newDieMax < this.dieMinNeighbors){
            throw new ExceptionInInitializerError("dieMaxNeighbors must be superior to dieMinNeighbors");
        }
        this.dieMaxNeighbors = newDieMax;
    }

    /**
     * Accessor to the value of <b>radius</b>.
     * @return value of <b>radius</b>.
    */
    public int getRadius(){
        return radius;
    }
    
    /**
     * Defined <b>radius</b> value of a cell.
     * @param radius new value of <b>radius</b>.
     * @throws ExceptionInInitializerError Occured if <b>radius</b> are less than <b>0</b>.
     */
    private void setRadius(int radius) throws ExceptionInInitializerError{
        if(radius < 0){
            throw new ExceptionInInitializerError("radius must be a positive number.");
        }
        this.radius = radius;
    }
    
    /**
     * Accessor to state of cell.
     * @return <b>true</b> if cell is <b>alive</b>, <b>else</b> return <b>false</b>
    */
    public boolean isAlive(){
        return this.alive;
    }

    /**
     * Defined state of a cell. 
     * @param newState alive or not.
    */
    public void setState(boolean newState){
        this.alive = newState;
    }

    /**
     * Access to representation of Cell in String.
     * @return representation of cell
     */
    public String info(){
        return "State: " + this.alive + "\nNeighbors\n\tmin to born: " + this.getBornMinNeighbors() + "\n\tmax to born: " + this.getBornMaxNeighbors() + 
        "\n\tmin before die : " + this.getDieMinNeighbors() + "\n\tmax before die : " + this.getDieMaxNeighbors() +"\nradius : " + this.radius;
    }
}
