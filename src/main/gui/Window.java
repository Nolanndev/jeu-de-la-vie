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
        
        Cell cell = new Cell(true);

        Quadtree on = new Quadtree(null, null, null, null, 0, 1, cell);
        Quadtree off = new Quadtree(null, null, null, null, 0, 0, new Cell(false));


        Quadtree q1 = new Quadtree(on, on, off, on);


        Quadtree q2 = new Quadtree(off, on, on, off);
        Quadtree q3 = new Quadtree(off, off, on, on);
        Quadtree q4 = new Quadtree(on, off, off, on);
        
        Quadtree q = new Quadtree(q1, q2, q3, q4);

        HashLife hash = new HashLife(cell);

        this.grid = new Grid(hash.advance(q, 220));
        
        Dimension dimGrid = new Dimension((int) (this.window.getSize().getWidth()), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.vueGrid = new VueGrid(this.grid, dimGrid,true);
        Dimension dimMenu = new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.sideMenu = new SideMenu(dimMenu);
        JScrollPane scrollMenu = new JScrollPane(this.sideMenu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.window.add(this.vueGrid, BorderLayout.WEST);
        this.window.add(scrollMenu, BorderLayout.EAST);

        this.window.setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(this.vueGrid != null){
            this.vueGrid.setDimension(new Dimension((int) (this.window.getSize().getWidth()), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
        }
        // if(this.sideMenu != null){
        //     this.sideMenu.setDimension(new Dimension((int) (this.window.getSize().getWidth()*0.25), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
        // }
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
