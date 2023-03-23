package main.gui;

import main.core.Grid;
import main.core.Cell;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class Window implements ComponentListener, KeyListener{

    JFrame window;
    VueGrid vueGrid;
    SideMenu sideMenu;
    Grid grid;

    public Window(String title) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.window = new JFrame();

        this.window.addComponentListener(this); //resize Event listener
        this.window.addKeyListener(this);

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.window.setTitle(title);
        this.window.setSize(new Dimension( (int)(screenSize.getWidth()*0.8) ,(int)(screenSize.getHeight()*0.8))); // 80% of screen size
        this.window.setLocation((int)(screenSize.getWidth()-this.window.getWidth())/2, (int)(screenSize.getHeight()-this.window.getHeight())/2); // Center on screen
        this.window.setResizable(true);
        this.window.setVisible(true);
        

        this.grid = new Grid(new Dimension(500, 500)); // grille de 20 x 20 = 400 cases
        grid.setCell(0, 0, new Cell(true));
        grid.setCell(4, 2, new Cell(true));
        grid.setCell(5, 1, new Cell(true));
        grid.setCell(4, 4, new Cell(true));
        grid.setCell(5, 4, new Cell(true));
        grid.setCell(6, 3, new Cell(true));
        grid.setCell(6, 5, new Cell(true));
        grid.setCell(1, 149, new Cell(true));
        grid.setCell(199, 149, new Cell(true));

        Dimension dimGrid = new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.vueGrid = new VueGrid(this.grid, dimGrid,true);
        Dimension dimMenu = new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.sideMenu = new SideMenu(dimMenu);
        JScrollPane scrollMenu = new JScrollPane(this.sideMenu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.window.add(this.vueGrid, BorderLayout.WEST);
        this.window.add(scrollMenu, BorderLayout.EAST);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(this.vueGrid != null){
            this.vueGrid.setDimension(new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
        }
        if(this.sideMenu != null){
            this.sideMenu.setDimension(new Dimension((int) (this.window.getSize().getWidth()*0.25), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        System.out.println("moved");
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        System.out.println("Shown");
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        ;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.vueGrid.grid.nextGen();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }


}
