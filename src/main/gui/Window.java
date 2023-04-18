package main.gui;

import main.core.*;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class Window implements ActionListener, KeyListener, ComponentListener{

    JFrame window, setting, cell;
    VueGrid vueGrid;
    JMenuBar menu;
    JMenu commandsMenu, profileMenu;
    JMenuItem play, pause, next, reset, clear, photo, video, load, save, settingsMenu, cellMenu;
    JDialog settingDialog, cellDialog, loadDialog, saveDialog;
    JPanel iterationP, timeItP, numberItP, startItP, cellMaxP, cellMinP, radiusP, icon;
    JLabel iteration, timeIt, numberIt, startIt, cellMax, cellMin, radius;
    JTextArea timeItT, numberItT, startItT, cellMaxT, cellMinT, radiusT;
    JCheckBox infinite, finite;
    JButton confimSetting, confirmCell, nextBtn, resetBtn, clearBtn, photoBtn, videoBtn;
    JToggleButton playPauseBtn;
    Icon playIc, pauseIc, nextIc, resetIc, clearIc, photoIc, videoIc;
    JLayeredPane iconMenu;
    Grid grid;
    
    int radiusValue, numberOfIteration, beginEvolutionToIteration, neighborsBirthMin, neighborsBirthMax, neighborsDeathMin, neighborsDeathMax, delay;
    boolean infiteEvolution;
    


    public Window(String title){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.window = new JFrame();

        this.window.addKeyListener(this);
        this.window.addComponentListener(this); //resize Event listener

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.window.setTitle(title);
        this.window.setSize(new Dimension( (int)(screenSize.getWidth()*0.8) ,(int)(screenSize.getHeight()*0.8))); // 80% of screen size
        this.window.setLocation((int)(screenSize.getWidth()-this.window.getWidth())/2, (int)(screenSize.getHeight()-this.window.getHeight())/2); // Center on screen
        this.window.setResizable(true);
        
        Quadtree on = new Quadtree(null, null, null, null, 0, 1, new Cell(true));
        Quadtree off = new Quadtree(null, null, null, null, 0, 0, new Cell(false));


        Quadtree q1 = new Quadtree(on, on, off, on);
        Quadtree q2 = new Quadtree(off, on, on, off);
        Quadtree q3 = new Quadtree(off, off, on, on);
        Quadtree q4 = new Quadtree(on, off, off, on);
        
        Quadtree q = new Quadtree(q1, q2, q3, q4);

        HashLife hash = new HashLife(new Cell(true));

        Quadtree advance = hash.advance(q, 479);

        Grid grid = new Grid(advance);
        
        Dimension dimGrid = new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.vueGrid = new VueGrid(grid, dimGrid,true);
        
        //Bar Menu
        menu = new JMenuBar();

        menu.setLayout(new GridLayout(1,4));
        
        commandsMenu = new JMenu("Commands");
        profileMenu = new JMenu("Profile");
        settingsMenu = new JMenuItem("Settings");
        cellMenu = new JMenuItem("Cell");
        
        play = new JMenuItem("Play");
        play.addActionListener(this);
        pause = new JMenuItem("Pause");
        pause.addActionListener(this);
        next = new JMenuItem("Next");
        next.addActionListener(this);
        reset = new JMenuItem("Reset");
        reset.addActionListener(this);
        clear = new JMenuItem("Clear");
        clear.addActionListener(this);
        photo = new JMenuItem("Photo");
        photo.addActionListener(this);
        video = new JMenuItem("Video");
        video.addActionListener(this);
        
        commandsMenu.add(play);
        commandsMenu.add(pause);
        commandsMenu.add(reset);
        commandsMenu.add(clear);
        commandsMenu.add(photo);
        commandsMenu.add(video);
        
        load = new JMenuItem("Load");
        load.addActionListener(this);
        save = new JMenuItem("Save");
        save.addActionListener(this);
        
        profileMenu.add(load);
        profileMenu.add(save);


        this.grid = new Grid(new Dimension(500,300));
        
        menu.add(commandsMenu);
        menu.add(profileMenu);
        menu.add(settingsMenu);
        settingsMenu.addActionListener(this);
        menu.add(cellMenu);
        cellMenu.addActionListener(this);

        //Icon Menu

        iconMenu = this.window.getLayeredPane();
        icon = new JPanel();
        int w = 300;
        int h = 50;
        icon.setBounds((this.window.getWidth()-w)/2, (this.window.getHeight()-h*2), w, h);

        playIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/play.png");
        pauseIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/stop.png");
        icon.add(playPauseBtn = new JToggleButton(playIc));
        playPauseBtn.addActionListener(this);

        nextIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/next.png");
        icon.add(nextBtn = new JButton(nextIc));
        nextBtn.addActionListener(this);

        resetIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/reset.png");
        icon.add(resetBtn = new JButton(resetIc));
        resetBtn.addActionListener(this);

        clearIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/clear.png");
        icon.add(clearBtn = new JButton(clearIc));
        clearBtn.addActionListener(this);

        photoIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/photo.png");
        icon.add(photoBtn = new JButton(photoIc));
        photoBtn.addActionListener(this);

        videoIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/video.png");
        icon.add(videoBtn = new JButton(videoIc));
        videoBtn.addActionListener(this);

        icon.setLayout(new GridLayout(1, 7));
        iconMenu.add(icon);

        this.window.add(this.vueGrid, BorderLayout.CENTER);
        this.window.setJMenuBar(menu);
        this.window.setVisible(true);
    }
    
    @Override
    public void componentResized(ComponentEvent e){
        if(this.vueGrid != null){
            this.vueGrid.setVueDimension(new Dimension((int) (this.window.getSize().getWidth()), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
        }
    }
    
    @Override
    public void componentMoved(ComponentEvent e){
        System.out.println("moved");
        
    }
    
    @Override
    public void componentShown(ComponentEvent e){
        
    }

    @Override
    public void componentHidden(ComponentEvent e){
        ;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==playPauseBtn){
            if (playPauseBtn.isSelected()){
                playPauseBtn.setIcon(pauseIc);
                System.out.println("Bouton Play");
            }
            else{
                playPauseBtn.setIcon(playIc);
                System.out.println("Bouton Stop");
            }
        }
        if (e.getSource()==play){
            System.out.println("Bouton Play");
        }
        if (e.getSource()==pause){
            System.out.println("Bouton Pause");
        }
        if (e.getSource()==next || e.getSource()==nextBtn){
            System.out.println("Bouton Next");
        }
        if (e.getSource()==reset || e.getSource()==resetBtn){
            System.out.println("Bouton Reset");
        }
        if (e.getSource()==clear || e.getSource()==clearBtn){
            System.out.println("Bouton Clear");
        }
        if (e.getSource()==photo || e.getSource()==photoBtn){
            System.out.println("Bouton Photo");
        }
        if (e.getSource()==video || e.getSource()==videoBtn){
            System.out.println("Bouton Video");
        }
        if (e.getSource()==load){
            System.out.println("Bouton Load");
            // faire une fenetre qui affiches tous les profiles disponibles
            this.loadProfiles();
        }
        if (e.getSource()==save){
            System.out.println("Bouton Save");
        }
        if (e.getSource()==settingsMenu){
            System.out.println("Bouton Setting");
            setting = new JFrame();
            settingDialog = new JDialog(setting, "Setting");
            settingDialog.setLayout(new GridLayout(5, 1));
            
            iterationP = new JPanel();
            iteration = new JLabel("Type of iteration :");
            infinite = new JCheckBox("Infinite");
            infinite.addActionListener(this);
            finite = new JCheckBox("Finite");
            finite.addActionListener(this);
            iterationP.add(iteration);
            iterationP.add(infinite);
            iterationP.add(finite);
            
            timeItP = new JPanel();
            timeIt = new JLabel("Time between iteration :");
            timeItT = new JTextArea(1,4);
            timeItP.add(timeIt);
            timeItP.add(timeItT);

            numberItP = new JPanel();
            numberIt = new JLabel("Number of iteration :");
            numberItT = new JTextArea(1,4);
            numberItP.add(numberIt);
            numberItP.add(numberItT);
            
            startItP = new JPanel();
            startIt = new JLabel("Start to iteration :");
            startItT = new JTextArea(1,4);
            startItP.add(startIt);
            startItP.add(startItT);

            confimSetting = new JButton("Confirm");
            confimSetting.addActionListener(this);
            
            settingDialog.add(iterationP);
            settingDialog.add(timeItP);
            settingDialog.add(numberItP);
            settingDialog.add(startItP);
            settingDialog.add(confimSetting);
            
            settingDialog.setSize(300,200);
            settingDialog.setVisible(true);
        }
        if (e.getSource()==cellMenu){
            System.out.println("Bouton Cell");
            cell = new JFrame();
            cellDialog = new JDialog(cell, "Cell");
            cellDialog.setLayout(new GridLayout(4, 1));
            
            cellMaxP = new JPanel();
            cellMax = new JLabel("Neighboring cell max :");
            cellMaxT = new JTextArea(1,4);
            cellMaxP.add(cellMax);
            cellMaxP.add(cellMaxT);
            
            cellMinP = new JPanel();
            cellMin = new JLabel("Neighboring cell min :");
            cellMinT = new JTextArea(1,4);            
            cellMinP.add(cellMin);
            cellMinP.add(cellMinT);
            
            radiusP = new JPanel();
            radius = new JLabel("Radius :");
            radiusT = new JTextArea(1,4);            
            radiusP.add(radius);
            radiusP.add(radiusT);

            confirmCell = new JButton("Confirm");
            confirmCell.addActionListener(this);
            
            cellDialog.add(cellMaxP);
            cellDialog.add(cellMinP);
            cellDialog.add(radiusP);
            cellDialog.add(confirmCell);

            cellDialog.setSize(250,150);
            cellDialog.setVisible(true);
        }
        if (e.getSource() == finite){
            System.out.println("Bouton Finite");
            infinite.setSelected(false);
        }
        if (e.getSource() == infinite){
            System.out.println("Bouton Infinite");
            finite.setSelected(false);
        }
        if (e.getSource()==confimSetting){
            System.out.println("Bouton Confirm Set");
            setting.dispatchEvent(new WindowEvent(setting, WindowEvent.WINDOW_CLOSING));        
        }
        if (e.getSource()==confirmCell){
            System.out.println("Bouton Confirm Cell");
            cell.dispatchEvent(new WindowEvent(cell, WindowEvent.WINDOW_CLOSING));        
        }
    }
    @Override
    public void keyTyped(KeyEvent e){
        this.grid.nextGen();
    }
    @Override
    public void keyPressed(KeyEvent e){
        ;
    }
    @Override
    public void keyReleased(KeyEvent e){
    }

    void loadProfiles(){
        loadDialog = new JDialog(new JFrame(), "Load Profile");
    
    }


}
