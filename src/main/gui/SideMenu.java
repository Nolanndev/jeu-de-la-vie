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
        createSideMenu();
    }

    private void createSideMenu(){
        menu = new JPanel();

        playBtn = new JButton("Lancer");
        playBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        playBtn.addActionListener(this);
        menu.add(playBtn);
        menu.setSize(this.dimension);
    }

    // @Override
    // public void paintComponent(Graphics g){
    //     super.paintComponent(g);
        
    //     g.setColor(Color.red);
    //     g.fillRect(0, 0, (int)this.dimension.getWidth(), (int)this.dimension.getHeight()); // Background
        
    // }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==playBtn){
            System.out.println("Bouton Lancer");
        }
    }
}