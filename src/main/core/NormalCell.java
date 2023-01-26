package main.core;

import main.utils.*;
import java.util.ArrayList;

public class NormalCell extends Cell {
    
    public NormalCell(Tuple coordinates) {
        super(coordinates);
    }

    @Override
    public ArrayList<Cell> getNeighbors(int radius) {
        return this.neighbors;
    }

    
}
