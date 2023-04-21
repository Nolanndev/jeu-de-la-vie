package main.gui;

import main.core.*;

public class Action implements Runnable {

    public Grid grid;
    public int time;
    public int typeIt;
    
    public Action(Grid grid, int time, int typeIt){
        this.grid = grid;
        this.time = time;
        this.typeIt = typeIt;
    }

    public void run() {
        try {
            if(typeIt!=0){
            for(int i = 0; i<=typeIt; i++){
                this.grid.nextGen();
                Thread.sleep(time*500);
                System.out.println(Thread.currentThread().getName());
            }
            } else {
                while(true){
                    this.grid.nextGen();
                    Thread.sleep(time);
                    System.out.println(Thread.currentThread().getName());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

}
