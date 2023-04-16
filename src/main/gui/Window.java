package main.gui;

import main.core.Grid;
import main.core.HashLife;
import main.core.Quadtree;
import main.core.Cell;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Window implements ComponentListener, KeyListener{

    JFrame window;
    VueGrid vueGrid;
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


        this.grid = new Grid(new Dimension(500,300));
        
        Dimension dimGrid = new Dimension((int) (this.window.getSize().getWidth()), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.vueGrid = new VueGrid(this.grid, dimGrid,true);

        this.window.add(this.vueGrid);

        this.window.setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(this.vueGrid != null){
            this.vueGrid.setVueDimension(new Dimension((int) (this.window.getSize().getWidth()), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        System.out.println("moved");
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        ;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.grid.nextGen();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }


}
