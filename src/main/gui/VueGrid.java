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

    private int posUX = 0;
    private int posUY = 0;

    Integer lastX;
    Integer lastY;


    private int sizeCase;




    public VueGrid(Grid grid, Dimension dimension, Boolean drawLine){
        addMouseListener(this);
        addMouseMotionListener(this);


        this.grid = grid;
        this.dimension = dimension;
        this.drawLine = drawLine;
        super.setPreferredSize(dimension);
        this.setSizeCase(15);


        this.grid.addListener(this);
    }

    public VueGrid(Dimension dimension){
        this(new Grid(), dimension, true);
    }

    public void setSizeCase(int sizeCase) {
        if(sizeCase <= 1){
            this.drawLine = false;
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
        else if (posUX >= this.grid.getWidth() - (this.dimension.getWidth()/this.sizeCase)){
            this.posUX = this.grid.getWidth() - (int)(this.dimension.getWidth()/this.sizeCase);
        }
        else{
            this.posUX = posUX;
        }
    }

    private void setPosUY(int posUY) {
        if(posUY <= 0){
            this.posUY = 0;
        }

        else if (posUY >= this.grid.getHeight() - (int)this.dimension.getHeight()/this.sizeCase ){
            this.posUY = this.grid.getHeight() - ((int)this.dimension.getHeight()/this.sizeCase) ;
        }

        else{
            this.posUY = posUY;
        }
    }




    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, (int)this.dimension.getWidth(), (int)this.dimension.getHeight()); // Background

        this.drawGrid(g);
        
    }

    private void drawGrid(Graphics g){
        for(int x = 0; x<Math.min((int)this.dimension.getWidth()/this.sizeCase,this.grid.getWidth()); x++){
            for(int y = 0; y<Math.min((int)this.dimension.getHeight()/this.sizeCase, this.grid.getHeight()); y++){
                
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
            if(posX <= this.grid.getWidth() && posY <= this.grid.getHeight()){

                int posCellX = posX + this.posUX;
                int posCellY = posY + this.posUY;


                if(SwingUtilities.isLeftMouseButton(e)){ //Left click
                    if(!this.grid.getCell(posCellX ,posCellY).isAlive()){
                        this.grid.setCell(posCellX, posCellY, new NormalCell(true));
                    }
                }
                
                if(SwingUtilities.isRightMouseButton(e)){ //Left click
                    if(this.grid.getCell(posCellX,posCellY).isAlive()){
                        this.grid.setCell(posCellX, posCellY, new NormalCell(false));
                    }
                }

            }

            
        } 
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX()<=this.dimension.getWidth() && e.getY()<=this.dimension.getHeight()){
            if(SwingUtilities.isMiddleMouseButton(e)){
                this.lastX = (int)(e.getX()/this.sizeCase);
                this.lastY = (int)(e.getY()/this.sizeCase);
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getX()<=this.dimension.getWidth() && e.getY()<=this.dimension.getHeight()){
            if(SwingUtilities.isMiddleMouseButton(e)){
                this.lastX = null;
                this.lastY = null;
            }

        }
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

                int posCellX = posX + this.posUX;
                int posCellY = posY + this.posUY;

                if(SwingUtilities.isLeftMouseButton(e)){ //posX/Y is use to not change the same Cell during the drag
                    if(!this.grid.getCell(posCellX,posCellY).isAlive()){
                        this.grid.setCell(posCellX, posCellY, new NormalCell(true));
                    }
                }

                if(SwingUtilities.isRightMouseButton(e)){ //Left click
                    if(this.grid.getCell(posCellX,posCellY).isAlive()){
                        this.grid.setCell(posCellX, posCellY, new NormalCell(false));
                    }
                }

                if(SwingUtilities.isMiddleMouseButton(e)){
                    
                        setPosUX(this.posUX - (posX - this.lastX));
                        setPosUY(this.posUY - (posY - this.lastY));

                        this.lastX = posX;
                        this.lastY = posY;

                        this.drawGrid(getGraphics());
                    
                }
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
