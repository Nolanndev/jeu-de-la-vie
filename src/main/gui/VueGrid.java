package main.gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.*;

import main.core.GridListener;
import main.core.Grid;
import main.core.Cell;

public class VueGrid extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, GridListener{
    
    private Grid grid;
    public Dimension dimension;
    public Boolean drawLine; // Draw line to delimit each Cell

    private int posUX = 0;
    private int posUY = 0;

    Integer lastX;
    Integer lastY;

    private Integer sizeCase;


    public VueGrid(Grid grid, Dimension dimension, Boolean drawLine){
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);


        this.grid = grid;
        this.setDimension(dimension);
        this.drawLine = drawLine;
        super.setPreferredSize(dimension);
        this.setSizeCase(10);


        this.grid.addListener(this);
    }

    public VueGrid(Dimension dimension){
        this(new Grid(), dimension, true);
    }

    public void setDimension(Dimension dimension) {
        if(dimension.getWidth() <= 0 || dimension.getHeight() <= 0){
            this.dimension = new Dimension(0, 0);
        }
        else{
            this.dimension = dimension;
        }
    }

    public void setSizeCase(int sizeCase) {
        if(this.sizeCase == null){
            this.sizeCase = sizeCase;
        }
        int posX = Math.min((int)((this.dimension.getWidth()/this.sizeCase) + this.posUX),this.grid.getWidth())/2;   //Convert position of mouse in position in grid
        int posY = Math.min((int)((this.dimension.getHeight()/this.sizeCase) + this.posUY),this.grid.getHeight())/2;

        if(sizeCase*(this.grid.getWidth()-this.posUX+1) < (int)this.dimension.getWidth() && sizeCase*(this.grid.getHeight()-this.posUY+1) < (int)this.dimension.getHeight()){
            this.sizeCase = Math.min((int)this.dimension.getWidth()/this.grid.getWidth()+1 , (int)this.dimension.getHeight()/this.grid.getHeight()+1);
        }
        
        else if(sizeCase <= 1){
            this.sizeCase = 1;
        }

        else if (sizeCase >= 100){
            this.sizeCase = 100;
        }
        else{
            this.sizeCase = sizeCase;
        }

        int posX2 = Math.min((int)((this.dimension.getWidth()/this.sizeCase) + this.posUX),this.grid.getWidth())/2;   //Convert position of mouse in position in grid
        int posY2 = Math.min((int)((this.dimension.getHeight()/this.sizeCase) + this.posUY),this.grid.getHeight())/2;

        setPosUX(this.posUX + posX-posX2);
        setPosUY(this.posUY + posY-posY2);

        System.out.println(this.sizeCase );
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
        if(this.drawLine && this.sizeCase>3){
            g.fillRect(0, 0, (int)this.dimension.getWidth(), (int)this.dimension.getHeight()); // Background
        }

        this.drawGrid(g);
        
    }

    private void drawGrid(Graphics g){
        for(int x = 0; x<Math.min((int)this.dimension.getWidth()/this.sizeCase,this.grid.getWidth()); x++){
            for(int y = 0; y<Math.min((int)this.dimension.getHeight()/this.sizeCase, this.grid.getHeight()); y++){
                
                try{
                    Color c = (this.grid.getCell(x+this.posUX, y+this.posUY).isAlive()) ? Color.red : Color. white; // Red for Alive Cell, White for dead Cell
                    g.setColor(c);

                    if((x+1)*this.sizeCase <= this.dimension.getWidth() && (y+1)*this.sizeCase <= this.dimension.getHeight()){ // 20 is size of one pixel
                        if(this.drawLine && this.sizeCase>3){
                            g.fillRect(x*this.sizeCase, y*this.sizeCase, this.sizeCase-1, this.sizeCase-1);
                        }
                        else{
                            g.fillRect(x*this.sizeCase, y*this.sizeCase, this.sizeCase, this.sizeCase);
                        }
                    }
                }
                catch(java.lang.ArrayIndexOutOfBoundsException e){
                    break;
                }
                
            }
        }   
    }

    @Override
    public void changeOccured() {
        this.drawGrid(getGraphics());
    }

    @Override
    public void changeCell(int x, int y) {
        System.out.print(x);
        System.out.print("   ");
        System.out.println(y);


        Graphics g = getGraphics();
        if(x >= this.posUX && x<this.posUX + (int)(this.dimension.getWidth()/this.sizeCase) && y >= this.posUY && y<    this.posUY + (int)(this.dimension.getHeight()/this.sizeCase)){
            Color c = (this.grid.getCell(x, y).isAlive()) ? Color.red : Color.white; // Red for Alive Cell, White for dead Cell
            g.setColor(c);
            
            if(this.drawLine && this.sizeCase>3){
                g.fillRect((x-this.posUX)*this.sizeCase, (y-this.posUY)*this.sizeCase, this.sizeCase-1, this.sizeCase-1);
            }
            else{
                g.fillRect(x*this.sizeCase, y*this.sizeCase, this.sizeCase, this.sizeCase);
            }
        }
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
                        this.grid.setCell(posCellX, posCellY, new Cell(true));
                    }
                }
                
                if(SwingUtilities.isRightMouseButton(e)){ //Left click
                    if(this.grid.getCell(posCellX,posCellY).isAlive()){
                        this.grid.setCell(posCellX, posCellY, new Cell(false));
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
                        this.grid.setCell(posCellX, posCellY, new Cell(true));
                    }
                }

                if(SwingUtilities.isRightMouseButton(e)){ //Left click
                    if(this.grid.getCell(posCellX,posCellY).isAlive()){
                        this.grid.setCell(posCellX, posCellY, new Cell(false));
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        switch (e.getWheelRotation()) {
            case 1:    //Wheel down
                this.setSizeCase(this.sizeCase-1);
                break;
        
            case -1: //Wheel up
                this.setSizeCase(this.sizeCase+1);
                break;

            default:
                break;
        }
        this.paintComponent(getGraphics());
    }



    

}
