package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SideMenu extends JPanel implements ActionListener{
    
    private JPanel menu;
    private JButton playBtn;
    private Dimension dimension;

    public SideMenu(Dimension dimension){

        this.dimension = dimension;
        super.setPreferredSize(dimension);
    }

    public void setDimension(Dimension dimension) {
        if(dimension.getWidth() <= 0 || dimension.getHeight() <= 0){
            this.dimension = new Dimension(0, 0);
        }
        else{
            this.dimension = dimension;
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor(Color.white);
        g.fillRect(0, 0, (int)this.dimension.getWidth(), (int)this.dimension.getHeight()); // Background
     
        menu = new JPanel();

        playBtn = new JButton("▶");
        playBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        playBtn.addActionListener(this);
        menu.add(playBtn);
        menu.setSize(this.dimension);
        this.add(menu);
        

    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==playBtn){
            System.out.println("Bouton Lancer");
        }
    }
}