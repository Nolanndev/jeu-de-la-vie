package main.gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.*;

import main.core.GirdListener;
import main.core.Grid;
import main.core.NormalCell;

public class VueGrid extends JPanel implements MouseListener, MouseMotionListener, GirdListener{
    
    private Grid grid;
    public Dimension dimension;
    public Boolean drawLine; // Draw line to delimit each Cell

    private Integer lastX = 0; //use to drawing with drag mouse
    private Integer lastY = 0;

    public VueGrid(Grid grid, Dimension dimension, Boolean drawLine){
        addMouseListener(this);
        addMouseMotionListener(this);

        this.grid = grid;
        this.dimension = dimension;
        this.drawLine = drawLine;
        super.setPreferredSize(dimension);

        this.grid.addListener(this);
    }

    public VueGrid(Dimension dimension){
        this(new Grid(), dimension, true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, (int)this.dimension.getWidth(), (int)this.dimension.getHeight()); // Background

        this.drawGrid(g);
        
    }

    private void drawGrid(Graphics g){
        for(int x = 0; x<this.grid.getHeight(); x++){
            for(int y = 0; y<this.grid.getWidth(); y++){
                
                Color c = (this.grid.getCell(x,y).isAlive()) ? Color.red : Color. white; // Red for Alive Cell, White for dead Cell
                g.setColor(c);

                if((x+1)*20 <= this.dimension.getWidth() && (y+1)*20 <= this.dimension.getHeight()){ // 20 is size of one pixel
                    if(this.drawLine){
                        g.fillRect(x*20, y*20, 20-1, 20-1);
                    }
                    else{
                        g.fillRect(x*20, y*20, 20, 20);
                    }
                }
            }
        }   
    }

    @Override
    public void changeOccured() {
        this.drawGrid(getGraphics());
    }

    

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX()<=this.dimension.getWidth() && e.getY()<=this.dimension.getHeight()){ // Click on grid
            int posX = (int)(e.getX()/20);                                                 //Convert position of mouse in position in grid
            int posY = (int)(e.getY()/20);

            if(SwingUtilities.isLeftMouseButton(e)){ //Left click
                if(this.grid.getCell(posX,posY).isAlive()){
                    this.grid.setCell(posX, posY, new NormalCell(false));
                }
                else{
                    this.grid.setCell(posX, posY, new NormalCell(true));
                }
            }
        } 
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ; //do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try{
            if(e.getX()<=this.dimension.getWidth() && e.getY()<=this.dimension.getHeight()){
                int posX = (int)(e.getX()/20);
                int posY = (int)(e.getY()/20);

                if(SwingUtilities.isLeftMouseButton(e) && (posX != this.lastX || posY!=this.lastY)){ //posX/Y is use to not change the same Cell during the drag
                    if(this.grid.getCell(posX,posY).isAlive()){
                        this.grid.setCell(posX, posY, new NormalCell(false));
                    }
                    else{
                        this.grid.setCell(posX, posY, new NormalCell(true));
                    }
                    this.lastX = posX;
                    this.lastY = posY;
                }
            } 
        }
        catch(java.lang.ArrayIndexOutOfBoundsException error){  //Mouse released outside the window
            ; //do nothing
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.print("");
    }

    

    

}
