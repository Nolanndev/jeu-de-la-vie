package main.core;

import java.util.ArrayList;
import java.util.Random;

import main.utils.Tuple;

public class Grid {
    private int rows;
    private int columns;
    private ArrayList<ArrayList<Cell>> board;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < this.rows; i++) {
            this.board.add(new ArrayList<Cell>());
        }
    }

    public Grid(int size) {
        this(size, size);
    }

    public Grid() {
        this(15,15);
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public String getCellInfos(int row, int column) {
        return this.board.get(row).get(column).infos();
    }

    public void displayGrid() {
        for (ArrayList<Cell> row : this.board) {
            for (Cell cell : row) {
                System.out.print(cell.getCoordinates().toString() + " ");
            }
            System.out.print("\n");
        }
    }

    public Cell getCell(Tuple coordinates) {
        Cell cell = this.board.get(coordinates.getValue1()).get(coordinates.getValue2());
        if (Cell.class.isInstance(cell)) {
            return cell;
        }
        return null;
    }

    public void setCell(int x, int y, Cell cell) {
        Cell c = this.board.get(x).get(y);
        c = cell;
    }

    public ArrayList<Cell> getNeighbors(Cell cell, int radius) {
        int x = cell.getCoordinateX();
        int y = cell.getCoordinateY();
        int diameter = 2 * radius + 1;
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        
        for (int i=0; i < diameter; i++) {
            for (int j=0; j < diameter; j++) {
                if (Cell.class.isInstance(this.board.get(i).get(j))) {
                    System.out.print(this.board.get(i).get(j).getCoordinates().toString());

                }
            }
            System.lineSeparator();
        }
        
        return neighbors;
    }
}