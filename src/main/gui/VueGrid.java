package main.gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.*;

import main.core.GridListener;
import main.core.Grid;
import main.core.Cell;

public class VueGrid extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, GridListener{
    
    public Grid grid;
    public Dimension vueDimension;
    public Boolean drawLine; // Draw line to delimit each Cell

    public Dimension dimGrid;

    private int posUX = 0;
    private int posUY = 0;

    Integer lastX;
    Integer lastY;

    private Integer sizeCase;


    public VueGrid(Grid grid, Dimension vueDimension, Boolean drawLine){
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);


        this.grid = grid;
        this.setVueDimension(vueDimension);
        this.drawLine = drawLine;
        super.setPreferredSize(vueDimension);
        this.setSizeCase(10);

        this.grid.addListener(this);
        
    }

    public void setVueDimension(Dimension vueDimension) {
        if(vueDimension.getWidth() <= 0 || vueDimension.getHeight() <= 0){
            this.vueDimension = new Dimension(0, 0);
        }
        else{
            this.vueDimension = vueDimension;
        }

        setDimGrid();
    }

    public void setSizeCase(int sizeCase){
        if(this.sizeCase == null){
            this.sizeCase = sizeCase;
        }
        
        int sizeCaseBeforeChange = this.sizeCase;

        int posX = Math.min((int)((this.vueDimension.getWidth()/this.sizeCase) + this.posUX),this.grid.getWidth())/2;   //Convert position of mouse in position in grid
        int posY = Math.min((int)((this.vueDimension.getHeight()/this.sizeCase) + this.posUY),this.grid.getHeight())/2;

        if(sizeCase*(this.grid.getWidth()-this.posUX) < (int)this.vueDimension.getWidth() || sizeCase*(this.grid.getHeight()-this.posUY) < (int)this.vueDimension.getHeight()){
            ;
        }
        else{
            if(sizeCase <= 1){
                this.sizeCase = 1;
            }
    
            else if (sizeCase >= 100){
                this.sizeCase = 100;
            }
            else{
                this.sizeCase = sizeCase;
            }
            int posX2 = Math.min((int)((this.vueDimension.getWidth()/this.sizeCase) + this.posUX),this.grid.getWidth())/2;   //Convert position of mouse in position in grid
            int posY2 = Math.min((int)((this.vueDimension.getHeight()/this.sizeCase) + this.posUY),this.grid.getHeight())/2;
    
            setPosUX(this.posUX + posX-posX2);
            setPosUY(this.posUY + posY-posY2);
        }

        if(this.sizeCase != sizeCaseBeforeChange){
            setDimGrid();
            this.paintComponent(getGraphics());
        }

    }

    private void setPosUX(int posUX){
        if(posUX <= 0){
            this.posUX = 0;
        }
        else if (posUX >= this.grid.getWidth() - (this.vueDimension.getWidth()/this.sizeCase)){
            this.posUX = this.grid.getWidth() - (int)(this.vueDimension.getWidth()/this.sizeCase);
        }
        else{
            this.posUX = posUX;
        }
    }

    private void setPosUY(int posUY){
        if(posUY <= 0){
            this.posUY = 0;
        }

        else if (posUY >= this.grid.getHeight() - (int)this.vueDimension.getHeight()/this.sizeCase ){
            this.posUY = this.grid.getHeight() - ((int)this.vueDimension.getHeight()/this.sizeCase) ;
        }

        else{
            this.posUY = posUY;
        }
    }


    public void setDimGrid() {
        if(this.sizeCase != null){
            int dimX = Math.min(this.grid.getWidth(), Math.floorDiv((int)this.vueDimension.getWidth(), this.sizeCase));
            int dimY = Math.min(this.grid.getHeight(), Math.floorDiv((int)this.vueDimension.getHeight(), this.sizeCase));
    
            this.dimGrid = new Dimension(dimX, dimY);
            System.out.println(this.dimGrid);
        }
            
    }



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.black);
        if(this.drawLine && this.sizeCase>3){
            for(int i = 0; i<=this.dimGrid.height; i++){
                g.fillRect(0, i*sizeCase-1,this.dimGrid.width*this.sizeCase, 1);
            }
            for(int i = 0; i<=this.dimGrid.width; i++){
                g.fillRect(i*sizeCase-1, 0, 1, this.dimGrid.height*this.sizeCase);
            }
        }

        this.drawGrid(g);
        
    }

    private void drawGrid(Graphics g){
        for(int x = 0; x<this.dimGrid.width; x++){
            for(int y = 0; y<this.dimGrid.height; y++){
    
                Color c = (this.grid.getCell(x+this.posUX, y+this.posUY).isAlive()) ? Color.red : Color. white; // Red for Alive Cell, White for dead Cell
                g.setColor(c);

                
                if(this.drawLine && this.sizeCase>3){
                    g.fillRect(x*this.sizeCase, y*this.sizeCase, this.sizeCase-1, this.sizeCase-1);
                }
                else{
                    g.fillRect(x*this.sizeCase, y*this.sizeCase, this.sizeCase, this.sizeCase);
                }
                
            }
        }   
    }

    @Override
    public void changeOccured(){
        this.drawGrid(getGraphics());
    }

    @Override
    public void changeCell(int x, int y){
        Graphics g = getGraphics();
        if(x >= this.posUX && x<this.posUX + this.dimGrid.width && y >= this.posUY && y < this.posUY + this.dimGrid.height){
            Color c = (this.grid.getCell(x, y).isAlive()) ? Color.red : Color.white; // Red for Alive Cell, White for dead Cell
            g.setColor(c);
            
            if(this.drawLine && this.sizeCase>3){
                g.fillRect((x-this.posUX)*this.sizeCase, (y-this.posUY)*this.sizeCase, this.sizeCase-1, this.sizeCase-1);
            }
            else{
                g.fillRect((x-this.posUX)*this.sizeCase, (y-this.posUY)*this.sizeCase, this.sizeCase, this.sizeCase);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX()<=this.vueDimension.getWidth() && e.getY()<=this.vueDimension.getHeight()){ // Click on grid
            int posX = (int)(e.getX()/this.sizeCase);   //Convert position of mouse in position in grid
            int posY = (int)(e.getY()/this.sizeCase);
            if(posX <= this.grid.getWidth() && posY <= this.grid.getHeight()){

                int posCellX = posX + this.posUX;
                int posCellY = posY + this.posUY;


                if(SwingUtilities.isLeftMouseButton(e)){ //Left click
                    this.grid.getCell(posCellX, posCellY).setState(true);
                }
                
                if(SwingUtilities.isRightMouseButton(e)){ //Left click
                    this.grid.setCell(posCellX, posCellY, new Cell(false));
                }

            }

            
        } 
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX()<=this.vueDimension.getWidth() && e.getY()<=this.vueDimension.getHeight()){
            if(SwingUtilities.isMiddleMouseButton(e)){
                this.lastX = (int)(e.getX()/this.sizeCase);
                this.lastY = (int)(e.getY()/this.sizeCase);
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getX()<=this.vueDimension.getWidth() && e.getY()<=this.vueDimension.getHeight()){
            if(SwingUtilities.isMiddleMouseButton(e)){
                this.lastX = null;
                this.lastY = null;
            }

        }
    }

    @Override
    public void mouseEntered(MouseEvent e){
        ;
    }

    @Override
    public void mouseExited(MouseEvent e){
        ;
    }

    @Override
    public void mouseDragged(MouseEvent e){
        try{
            if(e.getX()<=this.vueDimension.getWidth() && e.getY()<=this.vueDimension.getHeight()){
                int posX = (int)(e.getX()/this.sizeCase);
                int posY = (int)(e.getY()/this.sizeCase);

                int posCellX = posX + this.posUX;
                int posCellY = posY + this.posUY;

                if(SwingUtilities.isLeftMouseButton(e)){ //posX/Y is use to not change the same Cell during the drag
                    if(!this.grid.getCell(posCellX,posCellY).isAlive()){
                        Cell newCell = this.grid.getCell(posCellX,posCellY).copyCell();
                        newCell.setState(true);
                        this.grid.setCell(posCellX, posCellY, newCell);
                    }
                }

                if(SwingUtilities.isRightMouseButton(e)){ //Left click
                    if(this.grid.getCell(posCellX,posCellY).isAlive()){
                        Cell newCell = this.grid.getCell(posCellX,posCellY).copyCell();
                        newCell.setState(false);
                        this.grid.setCell(posCellX, posCellY, newCell);
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
    public void mouseMoved(MouseEvent e){
        ;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        switch (e.getWheelRotation()){
            case 1:    //Wheel down
                this.setSizeCase(this.sizeCase-1);
                break;
        
            case -1: //Wheel up
                this.setSizeCase(this.sizeCase+1);
                break;

            default:
                break;
        }
    }

}
