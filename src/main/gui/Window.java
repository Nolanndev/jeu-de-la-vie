package main.gui;

import main.core.Grid;
import main.core.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window implements ComponentListener{

    JFrame window;
    VueGrid vueGrid;

    public Window(String title) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.window = new JFrame();

        this.window.addComponentListener(this); //resize Event listener

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.window.setTitle(title);
        this.window.setSize(new Dimension( (int)(screenSize.getWidth()*0.8) ,(int)(screenSize.getHeight()*0.8))); // 80% of screen size
        this.window.setLocation((int)(screenSize.getWidth()-this.window.getWidth())/2, (int)(screenSize.getHeight()-this.window.getHeight())/2); // Center on screen
        this.window.setResizable(true);
        this.window.setVisible(true);
        

        Grid grid = new Grid(new Dimension(200, 150)); // grille de 20 x 20 = 400 cases
        grid.setCell(0, 0, new Cell(true));
        grid.setCell(4, 2, new Cell(true));
        grid.setCell(5, 1, new Cell(true));
        grid.setCell(4, 4, new Cell(true));
        grid.setCell(5, 4, new Cell(true));
        grid.setCell(6, 3, new Cell(true));
        grid.setCell(6, 5, new Cell(true));
        // grid.setCell(1, 149, new NormalCell(true));
        // grid.setCell(199, 149, new NormalCell(true));



        Dimension dim = new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.vueGrid = new VueGrid(grid, dim,true);

        this.window.add(this.vueGrid, BorderLayout.CENTER);

    }

    public Window() {
        this("Met un putain de titre");
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(this.vueGrid != null){
            this.vueGrid.setDimension(new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        ;
    }

    @Override
    public void componentShown(ComponentEvent e) {
        ;
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        ;
    }


}
