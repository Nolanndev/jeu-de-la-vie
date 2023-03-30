package main.gui;

import main.core.Grid;
import main.core.HashLife;
import main.core.Quadtree;
import main.core.Cell;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Window implements ActionListener,KeyListener, ComponentListener {

    JFrame window, setting, cell;
    VueGrid vueGrid;
    JMenuBar menu;
    JMenu commandsMenu, profileMenu;
    JMenuItem play, pause, reset, clear, photo, video, load, save, settingsMenu, cellMenu;
    JDialog settingDialog, cellDialog;
    JPanel iterationP, timeItP, numberItP, startItP, cellMaxP, cellMinP, radiusP;
    JLabel iteration, timeIt, numberIt, startIt, cellMax, cellMin, radius;
    JTextArea timeItT, numberItT, startItT, cellMaxT, cellMinT, radiusT;
    JCheckBox infinite, finite;
    JButton confimSetting, confirmCell;
    Grid grid;

    public Window(String title) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.window = new JFrame();

        this.window.addComponentListener(this); //resize Event listener

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.window.setTitle(title);
        this.window.setSize(new Dimension( (int)(screenSize.getWidth()*0.8) ,(int)(screenSize.getHeight()*0.8))); // 80% of screen size
        this.window.setLocation((int)(screenSize.getWidth()-this.window.getWidth())/2, (int)(screenSize.getHeight()-this.window.getHeight())/2); // Center on screen
        this.window.setResizable(true);
        
        Grid grid = new Grid(new Dimension(200, 150)); // grille de 20 x 20 = 400 cases
        grid.setCell(0, 0, new Cell(true));
        grid.setCell(4, 2, new Cell(true));
        grid.setCell(5, 1, new Cell(true));
        grid.setCell(4, 4, new Cell(true));
        grid.setCell(5, 4, new Cell(true));
        grid.setCell(6, 3, new Cell(true));
        grid.setCell(6, 5, new Cell(true));
        grid.setCell(1, 149, new Cell(true));
        grid.setCell(199, 149, new Cell(true));
        
        Dimension dimGrid = new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.vueGrid = new VueGrid(grid, dimGrid,true);
        
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
        
        menu.add(commandsMenu);
        menu.add(profileMenu);
        menu.add(settingsMenu);
        settingsMenu.addActionListener(this);
        menu.add(cellMenu);
        cellMenu.addActionListener(this);
        
        this.window.add(this.vueGrid, BorderLayout.CENTER);
        this.window.setJMenuBar(menu);
        this.window.setVisible(true);
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        if(this.vueGrid != null){
            this.vueGrid.setDimension(new Dimension((int) (this.window.getSize().getWidth()), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
        }
    }
    
    @Override
    public void componentMoved(ComponentEvent e) {
        System.out.println("moved");
        
    }
    
    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==play){
            System.out.println("Bouton Play");
        }
        if (e.getSource()==pause){
            System.out.println("Bouton Pause");
        }
        if (e.getSource()==reset){
            System.out.println("Bouton Reset");
        }
        if (e.getSource()==clear){
            System.out.println("Bouton Clear");
        }
        if (e.getSource()==photo){
            System.out.println("Bouton Photo");
        }
        if (e.getSource()==video){
            System.out.println("Bouton Video");
        }
        if (e.getSource()==load){
            System.out.println("Bouton Load");
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
    public void keyTyped(KeyEvent e) {
        this.grid.nextGen();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }


}
