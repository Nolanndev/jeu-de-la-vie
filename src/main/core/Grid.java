package main.core;

import java.util.ArrayList;
import java.util.Random;

import main.utils.Tuple;

public class Grid {
    private int rows;
    private int columns;
    private ArrayList<ArrayList<BasicCell>> board;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new ArrayList<ArrayList<BasicCell>>();
        for (int i = 0; i < this.rows; i++) {
            this.board.add(new ArrayList<BasicCell>());
        }

        Random rand = new Random();
        // Initialisation des valeurs de la grille
        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<this.rows; j++) {
                this.board.get(i).add(new NormalCell(new Tuple(rand.nextInt(0, 10), rand.nextInt(0, 10))));
            }
        }
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
        for (ArrayList<BasicCell> row : this.board) {
            for (BasicCell cell : row) {
                System.out.print(cell.getCoordinates().toString() + " ");
            }
            System.out.print("\n");
        }
    }
}