package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SideMenu extends JPanel implements ActionListener{
    
    private JPanel menu, commande, m1, situation, m2, cell, m3;
    private JButton playBtn, stopBtn, clearBtn;
    private Dimension dimension;
    private JLabel commandeLablel, situationLablel, cellLablel;

    public SideMenu(Dimension dimension){

        this.dimension = dimension;
        createSideMenu();
    }

    private void createSideMenu(){
        menu = new JPanel();

        menu.setLayout(new GridLayout(3, 1));

        commande = new JPanel();

        commande.setLayout(new GridLayout(2, 1));
        commandeLablel = new JLabel("Commande");
        commandeLablel.setFont(new Font("Segoe UI Black", Font.BOLD, 30));

        commande.add(commandeLablel);

        m1 = new JPanel();

        playBtn = new JButton("Play");
        playBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        playBtn.addActionListener(this);
        m1.add(playBtn);

        stopBtn = new JButton("Stop");
        stopBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        stopBtn.addActionListener(this);
        m1.add(stopBtn);

        clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        clearBtn.addActionListener(this);
        m1.add(clearBtn);    
        
        commande.add(m1);

        situation = new JPanel();

        situation.setLayout(new GridLayout(2, 1));
        situationLablel = new JLabel("Situation");
        situationLablel.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
        situation.add(situationLablel);
        
        m2 = new JPanel();

        playBtn = new JButton("||");
        playBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        playBtn.addActionListener(this);
        m2.add(playBtn);

        stopBtn = new JButton("Stop");
        stopBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        stopBtn.addActionListener(this);
        m2.add(stopBtn);

        clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        clearBtn.addActionListener(this);
        m2.add(clearBtn);

        situation.add(m2);

        cell = new JPanel();

        cell.setLayout(new GridLayout(2, 1));
        cellLablel = new JLabel("Cellule");
        cellLablel.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
        cell.add(cellLablel);
        
        m3 = new JPanel();

        playBtn = new JButton("Play");
        playBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        playBtn.addActionListener(this);
        m3.add(playBtn);

        stopBtn = new JButton("Stop");
        stopBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        stopBtn.addActionListener(this);
        m3.add(stopBtn);

        clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        clearBtn.addActionListener(this);
        m3.add(clearBtn);

        cell.add(m3);

        menu.add(commande);
        menu.add(situation);
        menu.add(cell);

        menu.setSize(this.dimension);
        this.add(menu);
    }

    // @Override
    // public void paintComponent(Graphics g){
    //     super.paintComponent(g);
        
    //     g.setColor(Color.red);
    //     g.fillRect(0, 0, (int)this.dimension.getWidth(), (int)this.dimension.getHeight()); // Background
        
    // }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==playBtn){
            System.out.println("Bouton Play");
        }
        if (e.getSource()==stopBtn){
            System.out.println("Bouton Stop");
        }
        if (e.getSource()==clearBtn){
            System.out.println("Bouton Clear");
        }
    }
}