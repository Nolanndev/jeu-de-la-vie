package main.gui;

import main.core.Grid;
import main.core.NormalCell;
import main.utils.Tuple;

import javax.swing.*;
import java.awt.*;

public class Window{

    JFrame window;
    VueGrid vueGrid;

    public Window(String title) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.window = new JFrame();
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.window.setTitle(title);
        this.window.setSize(new Dimension( (int)(screenSize.getWidth()) ,(int)screenSize.getHeight()-50));
        this.window.setVisible(true);
        this.window.setResizable(false);
        

        Grid grid = new Grid(new Tuple(200, 200)); // grille de 20 x 20 = 400 cases
        grid.setCell(0, 0, new NormalCell(true));
        grid.setCell(4, 2, new NormalCell(true));
        grid.setCell(5, 1, new NormalCell(true));
        grid.setCell(4, 4, new NormalCell(true));
        grid.setCell(5, 4, new NormalCell(true));
        grid.setCell(6, 3, new NormalCell(true));
        grid.setCell(6, 5, new NormalCell(true));
        grid.setCell(80, 9, new NormalCell(true));
        grid.setCell(180, 180, new NormalCell(true));

        Dimension dim = new Dimension((int) (screenSize.getWidth()*0.75), (int)screenSize.getHeight()-50);
        VueGrid vueGrid = new VueGrid(grid, dim,true);

        this.window.add(vueGrid, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        new Window("Tes");
    }

}
