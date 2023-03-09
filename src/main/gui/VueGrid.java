package main.gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.*;

import main.core.GridListener;
import main.core.Grid;
import main.core.NormalCell;

public class VueGrid extends JPanel implements MouseListener, MouseMotionListener, GridListener{
    
    private Grid grid;
    public Dimension dimension;
    public Boolean drawLine; // Draw line to delimit each Cell

    private Integer lastX = 0; //use to drawing with drag mouse
    private Integer lastY = 0;

    private int posUX = 0;
    private int posUY = 0;
    private int posDX = 100;
    private int posDY = 100;

    private int sizeCase = 20;




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

    public void setSizeCase(int sizeCase) {
        if(sizeCase <= 1){
            this.sizeCase = 1;
        }
        else if (sizeCase >= Math.min(this.dimension.getWidth(), this.dimension.getHeight())){
            this.sizeCase = (int) Math.min(this.dimension.getWidth(), this.dimension.getHeight());
        }

        this.sizeCase = sizeCase;
    }

    private void setPosUX(int posUX) {
        if(posUX <= 0){
            this.posUX = 0;
        }
        else{
            this.posUX = posUX;
        }
        // System.out.println(this.posUX);
    }

    private void setPosUY(int posUY) {
        if(posUY <= 0){
            this.posUY = 0;
        }
        else{
            this.posUY = posUY;
        }
        // System.out.println(this.posUY);

    }

    private void setPosDX(int posDX) {
        if(posDX <= 0){
            this.posDX = 0;
        }
        else{
            this.posDX = posDX;
        }
        // System.out.println(this.posDX);

    }

    private void setPosDY(int posDY) {
        if(posDY <= 0){
            this.posDY = 0;
        }
        else{
            this.posDY = posDY;
        }
        // System.out.println(this.posDY);

    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, (int)this.dimension.getWidth(), (int)this.dimension.getHeight()); // Background

        this.drawGrid(g);
        
    }

    private void drawGrid(Graphics g){
        for(int x = 0; x<100; x++){
            for(int y = 0; y<100; y++){
                
                Color c = (this.grid.getCell(x+this.posUX, y+this.posUY).isAlive()) ? Color.red : Color. white; // Red for Alive Cell, White for dead Cell
                g.setColor(c);

                if((x+1)*this.sizeCase <= this.dimension.getWidth() && (y+1)*this.sizeCase <= this.dimension.getHeight()){ // 20 is size of one pixel
                    if(this.drawLine){
                        g.fillRect(x*this.sizeCase, y*this.sizeCase, this.sizeCase-1, this.sizeCase-1);
                    }
                    else{
                        g.fillRect(x*this.sizeCase, y*this.sizeCase, this.sizeCase, this.sizeCase);
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
            int posX = (int)(e.getX()/this.sizeCase);   //Convert position of mouse in position in grid
            int posY = (int)(e.getY()/this.sizeCase);

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
                int posX = (int)(e.getX()/this.sizeCase);
                int posY = (int)(e.getY()/this.sizeCase);

                if(SwingUtilities.isLeftMouseButton(e) && (posX != this.lastX || posY!=this.lastY)){ //posX/Y is use to not change the same Cell during the drag
                    if(this.grid.getCell(posX,posY).isAlive()){
                        this.grid.setCell(posX, posY, new NormalCell(false));
                    }
                    else{
                        this.grid.setCell(posX, posY, new NormalCell(true));
                    }
                    
                }

                if(SwingUtilities.isRightMouseButton(e)){
                    int posUX = this.posUX + (posX-this.lastX); 
                    this.setPosUX(posUX);

                    int posDX = this.posDX - (posX-this.lastX); 
                    this.setPosDX(posDX);

                    int posUY = this.posUY - (posY-this.lastY); 
                    this.setPosUY(posUY);

                    int posDY = this.posDY - (posY-this.lastY); 
                    this.setPosDY(posDY);

                    System.out.print(this.posUX);
                    System.out.print("   ");

                    System.out.println(this.posUY);
                }
            
                this.lastX = posX;
                this.lastY = posY;
            } 
        }
        catch(java.lang.ArrayIndexOutOfBoundsException error){  //Mouse released outside the window
            ; //do nothing
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ;
    }

    

    

}
