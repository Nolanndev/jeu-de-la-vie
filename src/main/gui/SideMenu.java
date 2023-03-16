package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SideMenu extends JPanel implements ActionListener{
    
    private JPanel menu, commands, m1, profile, settings, m2, cell, m3;
    private JButton nextBtn, resetBtn, clearBtn, photoBtn, videoBtn, loadBtn;
    private Dimension dimension;
    private JLabel commandsLablel, profileLablel, settingsLablel, cellLablel;
    private Icon play, stop, next, reset, clear, photo, video;
    private JToggleButton playStopBtn;

    public SideMenu(Dimension dimension){

        this.dimension = dimension;
        createSideMenu();
    }

    public void setDimension(Dimension dimension) {
        if(dimension.getWidth() <= 0 || dimension.getHeight() <= 0){
            this.dimension = new Dimension(0, 0);
        }
        else{
            this.dimension = dimension;
        }
    }

    private void createSideMenu(){
        menu = new JPanel();

        menu.setLayout(new GridLayout(4, 1));
        
        Dimension dimBtn = new Dimension(40,40);

        //Commands section

        commands = new JPanel();

        commands.setLayout(new GridLayout(2, 1));
        commandsLablel = new JLabel("Commands");
        commandsLablel.setFont(new Font("Segoe UI Black", Font.BOLD, 30));

        commands.add(commandsLablel);

        m1 = new JPanel();

        play = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\play.png");
        stop = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\stop.png");
        playStopBtn = new JToggleButton(play);
        playStopBtn.setPreferredSize(dimBtn);
        playStopBtn.addActionListener(this);
        m1.add(playStopBtn);

        next = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\next.png");
        nextBtn = new JButton(next);
        nextBtn.setPreferredSize(dimBtn);
        nextBtn.addActionListener(this);
        m1.add(nextBtn);

        reset = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\reset.png");
        resetBtn = new JButton(reset);
        resetBtn.setPreferredSize(dimBtn);
        resetBtn.addActionListener(this);
        m1.add(resetBtn); 

        clear = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\clear.png");
        clearBtn = new JButton(clear);
        clearBtn.setPreferredSize(dimBtn);
        clearBtn.addActionListener(this);
        m1.add(clearBtn);

        photo = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\photo.png");
        photoBtn = new JButton(photo);
        photoBtn.setPreferredSize(dimBtn);
        photoBtn.addActionListener(this);
        m1.add(photoBtn);

        video = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\video.png");
        videoBtn = new JButton(video);
        videoBtn.setPreferredSize(dimBtn);
        videoBtn.addActionListener(this);
        m1.add(videoBtn);
        
        commands.add(m1);

        //Profile section

        profile = new JPanel();

        profile.setLayout(new GridLayout(2, 1));
        profileLablel = new JLabel("Profile");
        profileLablel.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
        profile.add(profileLablel);

        m2 = new JPanel();

        loadBtn = new JButton("Load");
        loadBtn.setPreferredSize(dimBtn);
        JFileChooser loadBtn = new JFileChooser();
        loadBtn.addActionListener(this);
        m2.add(loadBtn);

        profile.add(m2);

        //Settings section

        settings = new JPanel();

        settings.setLayout(new GridLayout(2, 1));
        settingsLablel = new JLabel("Settings");
        settingsLablel.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
        settings.add(settingsLablel);
        
        //Cell section

        cell = new JPanel();

        cell.setLayout(new GridLayout(2, 1));
        cellLablel = new JLabel("Cell");
        cellLablel.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
        cell.add(cellLablel);
        
        m3 = new JPanel();

        cell.add(m3);

        menu.add(commands);
        menu.add(profile);
        menu.add(settings);
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
        if(e.getSource()==playStopBtn){
            if (playStopBtn.isSelected()){
                playStopBtn.setIcon(stop);
                System.out.println("Bouton Play");
            }
            else{
                playStopBtn.setIcon(play);
                System.out.println("Bouton Stop");
            }
        }
        if (e.getSource()==nextBtn){
            System.out.println("Bouton Next");
        }
        if (e.getSource()==resetBtn){
            System.out.println("Bouton Reset");
        }
        if (e.getSource()==clearBtn){
            System.out.println("Bouton Clear");
        }
        if (e.getSource()==photoBtn){
            System.out.println("Bouton Photo");
        }
        if (e.getSource()==videoBtn){
            System.out.println("Bouton Video");
        }
        if (e.getSource()==loadBtn){
            System.out.println("Bouton Load");
        }
    }
}