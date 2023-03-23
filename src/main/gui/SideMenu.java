package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SideMenu extends JPanel implements ActionListener{
    
    private JPanel menu, commands, m1, profile, settings, m2, cell, iterationP, timeItP, numberItP, startItP, cellMaxP, cellMinP, radiusP;
    private JButton nextBtn, resetBtn, clearBtn, photoBtn, videoBtn, loadBtn, saveBtn;
    private Dimension dimension;
    private JLabel commandsLablel, profileLablel, settingsLablel, cellLablel, iteration, timeIt, numberIt, startIt, cellMax, cellMin, radius;
    private Icon play, stop, next, reset, clear, photo, video;
    private JToggleButton playStopBtn;
    private JTextArea timeItT, numberItT, startItT, cellMaxT, cellMinT, radiusT;
    private JCheckBox infinite, finite ;

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
        Font fontTitle = new Font("Verdana", Font.BOLD, 30);
        Font fontBtn = new Font("Verdana", Font.BOLD, 20);
        Font fontText = new Font("Verdana", Font.PLAIN, 15);

        //Commands section

        commands = new JPanel();

        commands.setLayout(new GridLayout(2, 1));
        commandsLablel = new JLabel("Commands");
        commandsLablel.setFont(fontTitle);

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
        profileLablel.setFont(fontTitle);
        profile.add(profileLablel);

        m2 = new JPanel();
        loadBtn = new JButton("Load");
        loadBtn.setFont(fontBtn);
        loadBtn.addActionListener(this);
        m2.add(loadBtn);

        saveBtn = new JButton("Save");
        saveBtn.setFont(fontBtn);
        saveBtn.addActionListener(this);
        m2.add(saveBtn);

        profile.add(m2);

        //Settings section

        settings = new JPanel();
        settings.setLayout(new GridLayout(6, 1));
        settingsLablel = new JLabel("Settings");
        settingsLablel.setFont(fontTitle);
        
        iterationP = new JPanel();
        iteration = new JLabel("Type of iteration :");
        iteration.setFont(fontText);
        infinite = new JCheckBox("Infinite");
        infinite.addActionListener(this);
        finite = new JCheckBox("Finite");
        finite.addActionListener(this);
        
        iterationP.add(iteration);
        iterationP.add(infinite);
        iterationP.add(finite);

        timeItP = new JPanel();
        timeIt = new JLabel("Time between iteration :");
        timeIt.setFont(fontText);
        timeItT = new JTextArea(1,4);

        timeItP.add(timeIt);
        timeItP.add(timeItT);

        numberItP = new JPanel();
        numberIt = new JLabel("Number of iteration :");
        numberIt.setFont(fontText);
        numberItT = new JTextArea(1,4);

        numberItP.add(numberIt);
        numberItP.add(numberItT);

        startItP = new JPanel();
        startIt = new JLabel("Start to iteration :");
        startIt.setFont(fontText);
        startItT = new JTextArea(1,4);

        startItP.add(startIt);
        startItP.add(startItT);
        
        settings.add(settingsLablel);
        settings.add(iterationP);
        settings.add(timeItP);
        settings.add(numberItP);
        settings.add(startItP);
        
        //Cell section
        
        cell = new JPanel();
        cell.setLayout(new GridLayout(4, 1));
        cellLablel = new JLabel("Cell");
        cellLablel.setFont(fontTitle);
        
        cellMaxP = new JPanel();
        cellMax = new JLabel("Neighboring cell max :");
        cellMax.setFont(fontText);
        cellMaxT = new JTextArea(1,4);

        cellMaxP.add(cellMax);
        cellMaxP.add(cellMaxT);
        
        cellMinP = new JPanel();
        cellMin = new JLabel("Neighboring cell min :");
        cellMin.setFont(fontText);
        cellMinT = new JTextArea(1,4);
        
        cellMinP.add(cellMin);
        cellMinP.add(cellMinT);
        
        radiusP = new JPanel();
        radius = new JLabel("Radius :");
        radius.setFont(fontText);
        radiusT = new JTextArea(1,4);
        
        radiusP.add(radius);
        radiusP.add(radiusT);
        
        cell.add(cellLablel);
        cell.add(cellMaxP);
        cell.add(cellMinP);
        cell.add(radiusP);
    
        //add to main panel
        
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
                if (infinite.isSelected() == false && finite.isSelected() == false){
                    System.out.println("unselect");
                    JOptionPane.showMessageDialog(this,"Unselect type");
                }
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
        if (e.getSource()==saveBtn){
            System.out.println("Bouton Save");
        }
        if(e.getSource() == finite){
            System.out.println("Bouton finite");
            infinite.setSelected(false);
        }
        if (e.getSource() == infinite){
            System.out.println("Bouton infinite");
            finite.setSelected(false);
        }
    }
}