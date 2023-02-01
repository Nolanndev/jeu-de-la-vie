package main.core;

import main.utils.Tuple;

public class Grid {
    private Tuple size; 
    private Cell[][] board;

    public Grid(Tuple size) {
        this.size = size;
        this.board = new Cell[this.getHeight()][this.getWidth()];
        for (int i = 0; i < getHeight(); i++)  {  
            for (int j=0; j < getWidth(); j++){
                this.board[i][j] = new NormalCell(new Tuple(i, j));
            }  
        }  
    }

    public Grid(int size) {
        this(new Tuple(size, size));
    }

    public Grid() {
        this(15);
    }

    public Tuple getSize(){
        return this.size;
    }

    public int getWidth(){
        return getSize().getValue1();
    }

    public int getHeight(){
        return getSize().getValue2();
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

    public String getCellInfos(int row, int column) {
        return this.board[row][column].infos();
    }

    public void displayGrid() {
        for (Cell[] row : this.board) {
            for (Cell cell : row) {
                System.out.print(cell.getCoordinates().toString() + " ");
            }
            System.out.print("\n");
        }
    }

    public Cell getCell(Tuple coordinates) {
        Cell cell = this.board[coordinates.getValue1()][coordinates.getValue2()];
        if (Cell.class.isInstance(cell)) {
            return cell;
        }
        return null;
    }

    public void setCell(int x, int y, Cell cell) {
        this.board[x][y] = cell;
    }

    public int countNeighbors(Cell cell) {
        int x = cell.getCoordinateX();
        int y = cell.getCoordinateY();
        int radius = cell.getRadius();
        
        int countNeighbors = 0;

        int firstRow = (x-radius < 0) ? 0 : x-radius;
        int lastRow = (x+radius > getHeight()-1) ? getHeight() : x+radius;  
        int firstColumn = (y-radius < 0) ? 0 : y-radius;
        int lastColumn= (y+radius > getWidth()-1) ? getWidth() : y+radius;  

        for(int i = firstRow; i < lastRow; i++){
            for(int j = firstColumn; j < lastColumn; j++){
                countNeighbors += (this.board[i][j].isAlive()) ? 1 : 0;
            }
        }

        return countNeighbors-1;
    }
}