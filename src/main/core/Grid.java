package main.core;

public class Grid{
    private int rows;
    private int columns;
    private int grid[][];

    public Grid(int rows, int columns, int grid[][]){
        this.rows = rows;
        this.columns = columns;
        this.grid = new int[rows][columns];

        for (int i = 0; i <= rows; i++){
            for (int j = 0; j <= columns; j++){
                grid[i][j] = 0; // on initialise une grille vide de 0. On ajoutera des cellules dedans plutard
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
        for (int i = 0; i <= getRows(); i++){
            for (int j = 0; j <= getColumns(); j++){
                System.out.println(grid[i][j]);
            }
        }
    }
}