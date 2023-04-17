package main.gui;

import main.core.Grid;
import main.core.HashLife;
import main.core.Quadtree;
import main.core.Cell;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Window implements ActionListener, KeyListener, ComponentListener {

    JFrame window, setting, cell;
    VueGrid vueGrid;
    JMenuBar menu;
    JMenu commandsMenu, profileMenu;
    JMenuItem play, pause, next, reset, clear, photo, video, icon, load, save, settingsMenu, cellMenu;
    JDialog settingDialog, cellDialog;
    JPanel iterationP, timeItP, numberItP, startItP, cellMaxP, cellMinP, radiusP, iconP;
    JLabel iteration, timeIt, numberIt, startIt, cellMax, cellMin, radius;
    JTextArea timeItT, numberItT, startItT, cellMaxT, cellMinT, radiusT;
    JCheckBox infinite, finite;
    JButton confimSetting, confirmCell, nextBtn, resetBtn, clearBtn, photoBtn, videoBtn, closeBtn;
    JToggleButton playPauseBtn;
    Icon playIc, pauseIc, nextIc, resetIc, clearIc, photoIc, videoIc, closeIc;
    JLayeredPane iconMenu;
    Grid grid;

    public Window(String title) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.window = new JFrame();

        this.window.addComponentListener(this); //resize Event listener
        this.window.addKeyListener(this);

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.window.setTitle(title);
        this.window.setSize(new Dimension( (int)(screenSize.getWidth()*0.8) ,(int)(screenSize.getHeight()*0.8))); // 80% of screen size
        this.window.setLocation((int)(screenSize.getWidth()-this.window.getWidth())/2, (int)(screenSize.getHeight()-this.window.getHeight())/2); // Center on screen
        this.window.setResizable(true);
        
        this.grid = new Grid(new Dimension(200, 150)); // grille de 20 x 20 = 400 cases
        this.grid.setCell(0, 0, new Cell(true));
        this.grid.setCell(4, 2, new Cell(true));
        this.grid.setCell(5, 1, new Cell(true));
        this.grid.setCell(4, 4, new Cell(true));
        this.grid.setCell(5, 4, new Cell(true));
        this.grid.setCell(6, 3, new Cell(true));
        this.grid.setCell(6, 5, new Cell(true));
        this.grid.setCell(1, 149, new Cell(true));
        this.grid.setCell(199, 149, new Cell(true));
        
        Dimension dimGrid = new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.vueGrid = new VueGrid(this.grid, dimGrid,true);
        
        //Bar Menu
        this.menu = new JMenuBar();

        this.menu.setLayout(new GridLayout(1,4));
        
        this.commandsMenu = new JMenu("Commands");
        this.profileMenu = new JMenu("Profile");
        this.settingsMenu = new JMenuItem("Settings");
        this.cellMenu = new JMenuItem("Cell");
        
        this.play = new JMenuItem("Play");
        this.play.addActionListener(this);
        this.pause = new JMenuItem("Pause");
        this.pause.addActionListener(this);
        this.next = new JMenuItem("Next");
        this.next.addActionListener(this);
        this.reset = new JMenuItem("Reset");
        this.reset.addActionListener(this);
        this.clear = new JMenuItem("Clear");
        this.clear.addActionListener(this);
        this.photo = new JMenuItem("Photo");
        this.photo.addActionListener(this);
        this.video = new JMenuItem("Video");
        this.video.addActionListener(this);
        this.icon = new JMenuItem("Icons");
        this.icon.addActionListener(this);
        
        this.commandsMenu.add(this.play);
        this.commandsMenu.add(this.pause);
        this.commandsMenu.add(this.next);
        this.commandsMenu.add(this.reset);
        this.commandsMenu.add(this.clear);
        this.commandsMenu.add(this.photo);
        this.commandsMenu.add(this.video);
        this.commandsMenu.add(this.icon);
        
        this.load = new JMenuItem("Load file");
        this.load.addActionListener(this);
        this.save = new JMenuItem("Save file");
        this.save.addActionListener(this);
        
        this.profileMenu.add(this.load);
        this.profileMenu.add(this.save);
        
        this.menu.add(this.commandsMenu);
        this.menu.add(this.profileMenu);
        this.menu.add(this.settingsMenu);
        this.settingsMenu.addActionListener(this);
        this.menu.add(this.cellMenu);
        this.cellMenu.addActionListener(this);

        //Icon Menu

        this.iconMenu = this.window.getLayeredPane();
        this.iconP = new JPanel();
        
        this.playIc = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\play.png");
        this.pauseIc = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\stop.png");
        this.iconP.add(this.playPauseBtn = new JToggleButton(this.playIc));
        this.playPauseBtn.addActionListener(this);

        this.nextIc = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\next.png");
        this.iconP.add(this.nextBtn = new JButton(this.nextIc));
        this.nextBtn.addActionListener(this);

        this.resetIc = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\reset.png");
        this.iconP.add(this.resetBtn = new JButton(this.resetIc));
        this.resetBtn.addActionListener(this);

        this.clearIc = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\clear.png");
        this.iconP.add(this.clearBtn = new JButton(this.clearIc));
        this.clearBtn.addActionListener(this);

        this.photoIc = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\photo.png");
        this.iconP.add(this.photoBtn = new JButton(this.photoIc));
        this.photoBtn.addActionListener(this);

        this.videoIc = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\video.png");
        this.iconP.add(this.videoBtn = new JButton(this.videoIc));
        this.videoBtn.addActionListener(this);

        this.closeIc = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\assets\\button\\close.png");
        this.iconP.add(this.closeBtn = new JButton(this.closeIc));
        this.closeBtn.addActionListener(this);

        this.iconP.setLayout(new GridLayout(1, 7));
        this.iconMenu.add(this.iconP);
        this.iconP.setVisible(false);
        

        this.window.add(this.vueGrid, BorderLayout.CENTER);
        this.window.setJMenuBar(this.menu);
        this.window.setVisible(true);
        System.out.println(Thread.currentThread().getName());
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        if(this.vueGrid != null){
            this.vueGrid.setDimension(new Dimension((int) (this.window.getSize().getWidth()), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
            this.iconP.setBounds((this.window.getWidth()-300)/2, (this.window.getHeight()-100), 300, 50);
        }
    }
    
    @Override
    public void componentMoved(ComponentEvent e) {
        // System.out.println("moved");
        
    }
    
    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.playPauseBtn){
            if (this.playPauseBtn.isSelected()){
                if (this.infinite != null){
                    Thread t = new Thread(code);
                    if(this.infinite.isSelected()){
                        while(e.getSource()!=pause){
                            try {
                                t.start();
                                Thread.sleep(Integer.valueOf(this.timeItT.getText())*1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    else {
                        for(int i=0; i< Integer.valueOf(this.numberItT.getText());i++){
                            try {
                                t.start();
                                Thread.sleep(Integer.valueOf(this.timeItT.getText())*1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    this.playPauseBtn.setIcon(this.pauseIc);
                } else {
                    JOptionPane.showMessageDialog(this.window, "Comfirm setting information");
                }
                System.out.println("Bouton Play");
            }
            else{
                this.playPauseBtn.setIcon(playIc);
                System.out.println("Bouton Pause");
            }
        }
        if (e.getSource()==play){
            this.playPauseBtn.setIcon(this.pauseIc);
            // if (this.infinite != null){
            //     if(this.infinite.isSelected()){
            //         while(e.getSource()!=pause){
            //             try {
            //                 this.grid.nextGen();
            //                 Thread.sleep(Integer.valueOf(this.timeItT.getText())*1000);
            //             } catch (InterruptedException e1) {
            //                 e1.printStackTrace();
            //             }
            //         }
            //     }
            //     else {
            //         for(int i=0; i< Integer.valueOf(this.numberItT.getText());i++){
            //             try {
            //                 this.grid.nextGen();
            //                 Thread.sleep(Integer.valueOf(this.timeItT.getText())*1000);
            //             } catch (InterruptedException e1) {
            //                 e1.printStackTrace();
            //             }
            //         }
            //     }
            // } else {
            //     JOptionPane.showMessageDialog(this.window, "Comfirm information in setting");
            // }
            System.out.println("Bouton Play");
        }
        if (e.getSource()==this.pause){
            this.playPauseBtn.setIcon(playIc);
            System.out.println("Bouton Pause");
        }
        if (e.getSource()==this.next || e.getSource()==this.nextBtn){
            this.grid.nextGen();
            System.out.println("Bouton Next");
        }
        if (e.getSource()==this.reset || e.getSource()==this.resetBtn){
            // this.grid.removeListener(this.vueGrid);
            System.out.println("Bouton Reset");
        }
        if (e.getSource()==this.clear || e.getSource()==this.clearBtn){
            this.grid.clearGrid();
            System.out.println("Bouton Clear");
        }
        if (e.getSource()==this.photo || e.getSource()==this.photoBtn){
            System.out.println("Bouton Photo");
        }
        if (e.getSource()==this.video || e.getSource()==this.videoBtn){
            System.out.println("Bouton Video");
        }
        if (e.getSource()==this.icon || e.getSource()==this.closeBtn){
            if(this.iconP.isVisible() == false){
                System.out.println("Bouton Icon on");
                iconP.setVisible(true);
            } else {
                System.out.println("Bouton Icon off");
                iconP.setVisible(false);
            }
        }
        if (e.getSource()==this.load){
            System.out.println("Bouton Load");
        }
        if (e.getSource()==this.save){
            System.out.println("Bouton Save");
        }
        if (e.getSource()==this.settingsMenu){
            System.out.println("Bouton Setting");
            this.setting = new JFrame();
            this.settingDialog = new JDialog(this.setting, "Setting");
            this.settingDialog.setLayout(new GridLayout(4, 1));
            
            this.iterationP = new JPanel();
            this.iteration = new JLabel("Type of iteration :");
            this.infinite = new JCheckBox("Infinite");
            this.infinite.addActionListener(this);
            this.finite = new JCheckBox("Finite");
            this.finite.addActionListener(this);
            this.infinite.setSelected(true);
            this.iterationP.add(this.iteration);
            this.iterationP.add(this.infinite);
            this.iterationP.add(this.finite);
            
            this.timeItP = new JPanel();
            this.timeIt = new JLabel("Time between iteration :");
            this.timeItT = new JTextArea("1",1,4);
            this.timeItP.add(this.timeIt);
            this.timeItP.add(this.timeItT);

            this.numberItP = new JPanel();
            this.numberIt = new JLabel("Number of iteration :");
            this.numberItT = new JTextArea("10",1,4);
            this.numberItP.add(this.numberIt);
            this.numberItP.add(this.numberItT);
            
            this.startItP = new JPanel();
            this.startIt = new JLabel("Start to iteration :");
            this.startItT = new JTextArea("1",1,4);
            this.startItP.add(this.startIt);
            this.startItP.add(this.startItT);

            this.confimSetting = new JButton("Confirm");
            this.confimSetting.addActionListener(this);
            
            this.settingDialog.add(this.confimSetting);
            this.settingDialog.add(this.iterationP);
            this.settingDialog.add(this.timeItP);
            this.settingDialog.add(this.startItP);
            
            this.settingDialog.setSize(300,200);
            this.settingDialog.setVisible(true);
        }
        if (e.getSource() == this.finite){
            System.out.println("Bouton Finite");
            this.settingDialog.add(this.numberItP);
            this.settingDialog.setLayout(new GridLayout(5, 1));
            this.settingDialog.pack();
            this.infinite.setSelected(false);
        }
        if (e.getSource() == this.infinite){
            System.out.println("Bouton Infinite");
            this.settingDialog.remove(this.numberItP);
            this.settingDialog.setLayout(new GridLayout(4, 1));
            this.settingDialog.pack();
            this.finite.setSelected(false);
        }
        if (e.getSource()==this.confimSetting){
            System.out.println("Bouton Confirm Set");
            if (this.timeItT.getText() == null || this.numberItT.getText() == null || this.startItT.getText() == null){
                JOptionPane.showMessageDialog(this.setting, "Missing information");
            } else {
                this.setting.dispatchEvent(new WindowEvent(this.setting, WindowEvent.WINDOW_CLOSING));        
            }
        }
        if (e.getSource()==this.cellMenu){
            System.out.println("Bouton Cell");
            this.cell = new JFrame();
            this.cellDialog = new JDialog(cell, "Cell");
            this.cellDialog.setLayout(new GridLayout(4, 1));
            
            this.cellMaxP = new JPanel();
            this.cellMax = new JLabel("Neighboring cell max :");
            this.cellMaxT = new JTextArea("1",1,4);
            this.cellMaxP.add(this.cellMax);
            this.cellMaxP.add(this.cellMaxT);
            
            this.cellMinP = new JPanel();
            this.cellMin = new JLabel("Neighboring cell min :");
            this.cellMinT = new JTextArea("1",1,4);            
            this.cellMinP.add(this.cellMin);
            this.cellMinP.add(this.cellMinT);
            
            this.radiusP = new JPanel();
            this.radius = new JLabel("Radius :");
            this.radiusT = new JTextArea("1",1,4);            
            this.radiusP.add(this.radius);
            this.radiusP.add(this.radiusT);

            this.confirmCell = new JButton("Confirm");
            this.confirmCell.addActionListener(this);
            
            this.cellDialog.add(this.confirmCell);
            this.cellDialog.add(this.cellMaxP);
            this.cellDialog.add(this.cellMinP);
            this.cellDialog.add(this.radiusP);

            this.cellDialog.setSize(250,150);
            this.cellDialog.setVisible(true);
        }
        if (e.getSource()==this.confirmCell){
            System.out.println("Bouton Confirm Cell");
            if (this.cellMaxT.getText() == null || this.cellMinT.getText() == null || this.radiusT.getText() == null){
                JOptionPane.showMessageDialog(this.cell, "Missing information");
            } else {
                this.cell.dispatchEvent(new WindowEvent(this.cell, WindowEvent.WINDOW_CLOSING));        
            }       
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        this.grid.nextGen();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    Runnable code = new Runnable() {
        

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    };
   

}
