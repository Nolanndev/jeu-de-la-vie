package main.core;

import java.util.ArrayList;
import java.awt.Dimension;

public class Grid {
    private Dimension size; 
    private Cell[][] board; // grille de cellules
    private ArrayList<GridListener> listeners = new ArrayList<GridListener>();

    public Grid(Dimension size) {
        this.size = size;
        this.board = new Cell[this.getHeight()][this.getWidth()];
        for (int i = 0; i < getHeight(); i++)  {  
            for (int j=0; j < getWidth(); j++){
                this.board[i][j] = new NormalCell();
            }  
        }  
    }

    //grille initialisée de dimension size * size
    public Grid(int size) {
        this(new Dimension(size, size));
    }

    // taille de base de la grille : 15 par 15
    public Grid() {
        this(15);
    }

    public Grid(Cell[][] board) {
        this.board = board;
        this.size = new Dimension(board.length, board[0].length);
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
        this.gridChange();
    }

    public Dimension getSize(){
        return this.size;
    }

    public int getWidth(){
        return (int) getSize().getWidth();
    }

    public int getHeight(){
        return (int) getSize().getHeight();
    }

    public Cell[] getRows(int row) {
        return this.board[row];
    }

    public Cell[] getColumns(int column) {
        Cell[] result = new Cell[getHeight()];
        for (int i = 0; i < getHeight(); i++) {
            result[i] = this.board[i][column];
        }
        return result;
    }

    /* retourne les informations d'une cellule de coordonnées x = row et y = column */
    public String getCellInfos(int row, int column) {
        return this.board[row][column].infos();
    }

    public void displayGrid() {
        for (Cell[] row : this.board) {
            for (Cell cell : row) {
                String res = (cell.isAlive()) ? "1" : "0";
                System.out.print(res + " ");
            }
            System.out.print("\n");
        }
    }

    public Cell getCell(Dimension coordinates) {
        Cell cell = this.board[(int) coordinates.getWidth()][(int) coordinates.getHeight()];
        if (Cell.class.isInstance(cell)) {
            return cell;
        }
        return null;
    }

    public Cell getCell(int x, int y){
        return this.getCell(new Dimension(y, x));
    }

    /* initialise une cellule aux coordonnées x et y dans la grille */
    public void setCell(int x, int y, Cell cell) {
        this.board[y][x] = cell;
        this.gridChange();  
    }

    /* compte les voisins d'une cellule coordonnées x et y dans la grille */
    public int countNeighbors(int x, int y) {
        int radius = getCell(new Dimension(x, y)).getRadius();
        
        int countNeighbors = 0;

        int firstRow = (x-radius < 0) ? 0 : x-radius;
        int lastRow = (x+radius > getHeight()-1) ? getHeight()-1 : x+radius;  
        int firstColumn = (y-radius < 0) ? 0 : y-radius;
        int lastColumn= (y+radius > getWidth()-1) ? getWidth()-1 : y+radius;  

        for(int i = firstRow; i <= lastRow; i++){
            for(int j = firstColumn; j <= lastColumn; j++){                
                countNeighbors += (this.board[i][j].isAlive()) ? 1 : 0;
            }
        }

        countNeighbors -= (this.board[x][y].isAlive()) ? 1 : 0;

        return countNeighbors;
    }

    /* initialise la prochaine generation de cellule dans une nouvelle grille copyBoard*/
    public void nextGen(){
        Cell[][] copyBoard = copyBoard();
        for (int i = 0; i < getHeight() ; i++) {
            for (int j = 0; j < getWidth(); j++) {
                Boolean res = (countNeighbors(i, j) == 3 || (countNeighbors(i, j) == 2 && this.board[i][j].isAlive()));
                copyBoard[i][j].setState(res);
            }
        }
        setBoard(copyBoard);
        this.gridChange();
    }

    /* copie la grille actuelle dans copyBoard*/
    public Cell[][] copyBoard(){
        Cell[][] copyBoard = new Cell[this.getHeight()][this.getWidth()];
        for (int i = 0; i < getHeight() ; i++) {
            for (int j = 0; j < getWidth(); j++) {
                copyBoard[i][j] = new NormalCell(this.board[i][j].isAlive());
            }
        }
        return copyBoard;
    }

    public void addListener(GridListener e){
        this.listeners.add(e);
    }

    public void removeListener(GridListener e){
        this.listeners.remove(e);
    }

    private void gridChange(){
        for (GridListener listener : this.listeners) {
            listener.changeOccured();
        }
    }

}