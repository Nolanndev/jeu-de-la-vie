package main.core;

import java.util.ArrayList;
import java.awt.Dimension;

/**
 * Class which represent Grid in game of life.
 * Grid is defined by :
 * <ul>
 * <li>Is size.</li>
 * <li>Is content.</li>
 * </ul>
 * @author Parcheminer Nolann
 * @author Le Bris Ilan
 * @author Marcheron Bastien
 * @author David Matthias
 * 
 * @see Cell
 */
public class Grid{

    /**
     * The dimension of grid.
     */
    private Dimension size;

    /**
     * The content of grid, matrix of cell.
     * @see Cell
     */
    private Cell[][] board;

    /**
     * Array of objects which listens this grid 
     * @see GridListener     
    */
    private ArrayList<GridListener> listeners = new ArrayList<GridListener>();

    /**
     * Constructor which produce new <b>Grid</b> of given <b>size</b>.
     * Each <b>Cell</b> is initialize to the given <b>Cell</b>.
     * 
     * @param size Dimension of grid.
     * @param cell Cell use to initialize the grid.
     */
    public Grid(Dimension size, Cell cell){
        setSize(size);
        setBoard(new Cell[this.getHeight()][this.getWidth()]);
        for (int i = 0; i < getWidth(); i++) {  
            for (int j=0; j < getHeight(); j++){
                setCell(i, j, new Cell(cell.getBornMinNeighbors(), cell.getBornMaxNeighbors(), cell.getDieMinNeighbors(), cell.getDieMaxNeighbors(), cell.getRadius(), cell.isAlive()));
            }  
        }  
    }

    /**
     * Constructor which produce new <b>Grid</b> of given <b>size</b>.
     * Each cell is initialize to default dead cell
     * @param size Dimension of <b>Grid</b>.
     */
    public Grid(Dimension size){
        this(size, new Cell(false));
    }

    /**
     * Constructor which produce new <b>Grid</b> with given matrix of <b>Cell</b>.
     * Dimension of grid his initialize to Dimesion of matrix
     * @param board Matrix of <b>Cell</b>.
     */
    public Grid(Cell[][] board){
        this.board = board;
        this.size = new Dimension(board.length, board[0].length);
    }

    public Grid(Quadtree tree){
        this(quadtreeToMatrix(tree));
    }

    private static Cell[][] quadtreeToMatrix(Quadtree tree){
        if(tree == null){
            return null;
        }

        Cell[][] board = new Cell[(int)Math.pow(2, tree.getDepth())][(int)Math.pow(2, tree.getDepth())];

        if(tree.isLeaf()){
            Cell cell = tree.getCell();
            Boolean alive = (tree.getNumberAlive() == 1) ? true : false; 
            board[0][0] = new Cell(cell.getBornMinNeighbors(), cell.getBornMaxNeighbors(), cell.getDieMinNeighbors(), cell.getDieMaxNeighbors(), cell.getRadius(), alive);
            return board; 
        }
        else{
            board = fusion(quadtreeToMatrix(tree.getNw()), quadtreeToMatrix(tree.getNe()), quadtreeToMatrix(tree.getSw()), quadtreeToMatrix(tree.getSe())); 
            return board; 
        }
        
    }

    private static Cell[][] fusion(Cell[][] nw, Cell[][] ne, Cell[][] sw, Cell[][] se){
        if(nw == null || ne == null || sw == null || se == null){
            return null;
        }

        Dimension size = new Dimension(nw[0].length, nw.length);

        Cell[][] board = new Cell[nw.length + sw.length][nw[0].length + sw[0].length];

        for (int i = 0; i < size.getHeight(); i++){
            for (int j = 0; j < size.getWidth(); j++){
                board[i][j] = nw[i][j];
                board[(int)size.getHeight()+i][j] = sw[i][j];
                board[i][j+(int)size.getWidth()] = ne[i][j];
                board[i+(int)size.getHeight()][j+(int)size.getWidth()] = se[i][j];
            }
        }
        return board;
    }

    /**
     * Accessor to the <b>Cell</b> matrix oh this <b>Grid</b>.
     * @return <b>Cell</b> matrix of this <b>Grid</b>.
     */
    public Cell[][] getBoard(){
        return board;
    }

    /**
     * Defined the <b>Cell</b> matrix for this <b>Grid</b> by the given matrix.
     * Call the function{@link GridListener#changeOccured()} for all{@link #listeners}
     * @param board new <b>Cell</b> matrix of this <b>Grid</b>.
     */
    public void setBoard(Cell[][] board){
        this.board = board;
        this.gridChange();
    }

    /**
     * Accessor to the <b>size</b> of this <b>Grid</b>.
     * @return <b>size</b> of this <b>Grid</b>.
     */
    public Dimension getSize(){
        return this.size;
    }

    /**
     * Accesor to the width of this <b>Grid</b>.
     * @return Width of this <b>Grid</b>.
     */
    public int getWidth(){
        return (int) getSize().getWidth();
    }

    /**
     * Accesor to the height of this <b>Grid</b>.
     * @return Heigth of this <b>Grid</b>.
     */
    public int getHeight(){
        return (int) getSize().getHeight();
    }

    /**
     * Defined <b>size</b> of the <b>Grid</b> by the given Dimension.
     * @param size new value of <b>size</b>
     * @throws ExceptionInInitializerError if size have negative value.
     * @throws ExceptionInInitializerError if size values is superior to size of Cell matrix.
     */
    public void setSize(Dimension size){
        if(size.getWidth() > 0 && size.getHeight()>0){
            if(this.board == null || (this.board.length>=size.getHeight() && this.board[0].length>=size.getWidth())){
                this.size = size;
            }
            else{
                throw new ExceptionInInitializerError("Dimension values must be less or equal to dimension of board.");
            }
        }
        else{
            throw new ExceptionInInitializerError("Dimension values must be positive integer.");
        }
    }

    /**
     * Accessor to the given row of matrix
     * @param row index in the matrix
     * @return asked row of the matrix
     * @throws IndexOutOfBoundsException if row given is greater than the height of his <b>Grid</b>
     * @throws IndexOutOfBoundsException if row given is less than 0
     */
    public Cell[] getRows(int row){
        if(row > this.getHeight()){
            throw new IndexOutOfBoundsException("row given is greater than the height of Grid");
        }
        if(row < 0){
            throw new IndexOutOfBoundsException("row must be positive integer");
        }
        return this.board[row];
    }

    /**
     * Accessor to the given column of matrix
     * @param column index in the matrix
     * @return asked column of the matrix
     * @throws IndexOutOfBoundsException if column given is greater than the width of his <b>Grid</b>
     * @throws IndexOutOfBoundsException if column given is less than 0
     */
    public Cell[] getColumns(int column){
        if(column > this.getWidth()){
            throw new IndexOutOfBoundsException("column given is greater than the height of Grid");
        }
        if(column < 0){
            throw new IndexOutOfBoundsException("column must be positive integer");
        }
        Cell[] result = new Cell[getHeight()];
        for (int i = 0; i < getHeight(); i++){
            result[i] = getCell(column,i);
        }
        return result;
    }

    /**
     * Accessor to <b>Cell</b> which are in the given coordinates in the matrix. 
     * @param coordinates coordinates in matrix of the <b>Cell</b>
     * @return <b>Cell</b> at the given coordinates.
     */
    public Cell getCell(Dimension coordinates){
    
        Cell cell = this.board[(int) coordinates.getHeight()][(int) coordinates.getWidth()];
        if (Cell.class.isInstance(cell)){
            return cell;
        }
        return null;
    }

    /**
     * Accessor to <b>Cell</b> which are in the given coordinates in the matrix.
     * @param x Horizontal axis of the <b>Cell</b>
     * @param y Vertical axis of the <b>Cell</b>
     * @return <b>Cell</b> at the given coordinates.
     */
    public Cell getCell(int x, int y){
        return this.getCell(new Dimension(x, y));
    }

    /**
     * Defined given <b>Cell</b> at the given coordinates in the matrix of this <b>Grid</b>.
     * Call the function{@link GridListener#changeCell(x, y)} for all{@link #listeners}
     * @param x Horizontal axis of the new <b>Cell</b>
     * @param y Vertical axis of the new <b>Cell</b>
     * @param cell new Cell at the given coordinates
     */
    public void setCell(int x, int y, Cell cell){
        this.board[y][x] = cell;
        this.changeCell(x, y);  
    }

    /**
     * Defined given <b>Cell</b> at the given coordinates in the matrix of this <b>Grid</b>.
     * @param coordinates coordinates in matrix of the new <b>Cell</b>
     * @param cell new Cell at the given coordinates
     */
    public void setCell(Dimension coordinates, Cell cell){
        this.setCell((int)getHeight(), (int)getWidth(), cell);
    }

    /**
     * Display grid in terminal.
     * Alive <b>Cell</b> are represent by ■
     * Dead <b>Cell</b> are represent by blank space
     */
    public void displayGrid(){
        for (Cell[] row : this.board){
            for (Cell cell : row){
                String res = (cell.isAlive()) ? "■" : " ";
                System.out.print(res + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Count alive neigbors of <b>Cell</b> at the given coordinates.
     * Radius of Neigbors depend on radius of <b>Cell</b>.
     * @param x Horizontal axis of the new <b>Cell</b>
     * @param y Vertical axis of the new <b>Cell</b>
     * @return Number of alive neighbors.
     * 
     * @see Cell#radius
     */
    public int countNeighbors(int x, int y){
        int radius = getCell(x,y).getRadius();
        
        int countNeighbors = 0;

        int firstRow = (x-radius < 0) ? 0 : x-radius;
        int lastRow = (x+radius > getHeight()-1) ? getHeight()-1 : x+radius;  
        int firstColumn = (y-radius < 0) ? 0 : y-radius;
        int lastColumn= (y+radius > getWidth()-1) ? getWidth()-1 : y+radius;  
        
        for(int i = firstRow; i <= lastRow; i++){
            for(int j = firstColumn; j <= lastColumn; j++){   
                countNeighbors += (this.getCell(i, j).isAlive()) ? 1 : 0;
            }
        }
        
        countNeighbors -= (getCell(x, y).isAlive()) ? 1 : 0;

        return countNeighbors;
    }

    /**
     * Compute the next generation of this Grid using the Game of Life rules.
     * matrix of this Grid is change to matrix of next generation.
     * 
     * Call the function{@link GridListener#changeCell(int, int)} for all listener in{@link #listeners}
     */
    public void nextGen(){
        Grid copyGrid = new Grid(this.copyBoard());
        
        for (int i = 0; i < getWidth() ; i++){
            for (int j = 0; j < getHeight(); j++){
                
                int neighbors = copyGrid.countNeighbors(i,j);
                Cell cell = copyGrid.getCell(i, j);

                Boolean nextState = ((cell.isAlive() && neighbors>=cell.getDieMinNeighbors() && neighbors<=cell.getDieMaxNeighbors()) || //respecte les conditions de mort déinie dans la Cellule cell
                (!cell.isAlive() && neighbors>=cell.getBornMinNeighbors() && neighbors<=cell.getBornMaxNeighbors())); //ou respecte les conditions de naissance déinie dans la Cellule cell
                
                if(nextState != this.getCell(i, j).isAlive()){ //si l'etat de la cellule a la prochaine génération est différent de l'état actuelle, on change la cellule. 
                    this.setCell(i, j, new Cell(cell.getBornMinNeighbors(), cell.getBornMaxNeighbors(), cell.getDieMinNeighbors(), cell.getDieMaxNeighbors(),cell.getRadius(), nextState));
                    this.changeCell(i, j);
                }
            }
        }
    }

    /**
     * Produce a deep copy of matrix of this <b>Grid</b>
     * @return copy of matrix
     */
    public Cell[][] copyBoard(){
        Cell[][] copyBoard = new Cell[this.getHeight()][this.getWidth()];
        for (int i = 0; i < getHeight() ; i++){
            for (int j = 0; j < getWidth(); j++){
                Cell cell = this.getCell(j, i);
                copyBoard[i][j] = new Cell(cell.getBornMinNeighbors(), cell.getBornMaxNeighbors(), cell.getDieMinNeighbors(), cell.getDieMaxNeighbors(),cell.getRadius(), cell.isAlive());
            }
        }
        return copyBoard;
    }

    /**
     * add GridListener to{@link Grid#listeners} 
     * @param e GridListener to add
     */
    public void addListener(GridListener e){
        this.listeners.add(e);
    }

    /**
     * remove GridListener from{@link Grid#listeners} 
     * @param e GridListener to remove
     */
    public void removeListener(GridListener e){
        this.listeners.remove(e);
    }

    private void gridChange(){
        for (GridListener listener : this.listeners){
            listener.changeOccured();
        }
    }

    private void changeCell(int x, int y){
        for (GridListener listener : this.listeners){
            listener.changeCell(x, y);
        }
    }

    public void clearGrid(){
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                cell.setState(false);
            }
        }
        this.gridChange();
    }

}