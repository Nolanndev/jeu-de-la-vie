package main.core;

import main.utils.Tuple;

public class Grid{
    private int rows;
    private int columns;
    private Cell grid[][];

    public Grid(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.grid = new Cell[rows][columns];
        
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                grid[i][j] = new Cell(new Tuple(i,j), 0, 0); 
            }
        }
    }

    public int getRows(){
        return this.rows;
    }

    public int getColumns(){
        return this.columns;
    }

    public void showGrid(){
        for (int i = 0; i < getRows(); i++){
            for (int j = 0; j < getColumns(); j++){
                System.out.println(grid[i][j]);
            }
        }
    }
}