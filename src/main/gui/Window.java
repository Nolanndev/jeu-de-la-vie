package main.gui;

import main.core.*;
import main.utils.ProfileManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;


public class Window implements ActionListener, ComponentListener, Runnable {

    JFrame window, setting, cell, loadFrame, dataProfilesFrame, saveFrame;
    VueGrid vueGrid;
    JMenuBar menu;
    JMenu commandsMenu, profileMenu, presetMenu;
    JMenuItem play, pause, next, reset, clear, photo, icon, loadProfile, saveProfile, loadPreset, savePreset, settingsMenu, cellMenu;
    JDialog settingDialog, cellDialog, loadDialog, dataProfilesDialog, saveDialog;
    JPanel dataProfilesP, saveP, iterationP, timeItP, startItP, numberItP, cellBornP, cellMaxBP, cellMinBP, cellDieP, cellMaxDP, cellMinDP, radiusP, iconP;
    JLabel iteration, timeIt, startIt, numberIt, cellBorn, cellMaxB, cellMinB, cellDie, cellMaxD, cellMinD, radius;
    JTextArea saveT, timeItT, startItT, numberItT, cellMaxBT, cellMinBT, cellMaxDT, cellMinDT, radiusT;
    JCheckBox infinite, finite;
    JButton profileApli, profileDel, confirmSave, confimSetting, confirmCell, nextBtn, resetBtn, clearBtn, photoBtn, closeBtn;
    JToggleButton playPauseBtn;
    Icon playIc, pauseIc, nextIc, resetIc, clearIc, photoIc, closeIc;
    JLayeredPane iconMenu;
    Grid grid;

    int timeItVal = 1000,  startItVal = 1, numberItVal = 10, maxBornVal = 3, minBornVal = 2, maxDieVal = 3, minDieVal = 2, radiusVal = 1;
    
    public Window(String title){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.window = new JFrame();

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

        
        new Grid(q).displayGrid();
        Quadtree advance = hash.advance(q, 479);

        this.grid = new Grid(advance);
        
        Dimension dimGrid = new Dimension((int) (this.window.getSize().getWidth()*0.75), (int)this.window.getSize().getHeight()-this.window.getInsets().top);
        this.vueGrid = new VueGrid(this.grid, dimGrid,true);
        
        //Bar Menu

        this.menu = new JMenuBar();

        this.menu.setLayout(new GridLayout(1,4));
        
        this.commandsMenu = new JMenu("Commands");
        this.profileMenu = new JMenu("Profiles");
        this.presetMenu = new JMenu("Presets");
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
        this.icon = new JMenuItem("Icons");
        this.icon.addActionListener(this);
        
        this.commandsMenu.add(this.play);
        this.commandsMenu.add(this.pause);
        this.commandsMenu.add(this.next);
        this.commandsMenu.add(this.reset);
        this.commandsMenu.add(this.clear);
        this.commandsMenu.add(this.photo);
        this.commandsMenu.add(this.icon);
        
        this.loadProfile = new JMenuItem("Load profile");
        this.loadProfile.addActionListener(this);
        this.saveProfile = new JMenuItem("Save current profile");
        this.saveProfile.addActionListener(this);

        this.loadPreset = new JMenuItem("Load Preset");
        this.loadPreset.addActionListener(this);
        this.savePreset = new JMenuItem("Save current Preset");
        this.savePreset.addActionListener(this);
        
        this.profileMenu.add(this.loadProfile);
        this.profileMenu.add(this.saveProfile);

        this.presetMenu.add(this.loadPreset);
        this.presetMenu.add(this.savePreset);
        
        this.menu.add(this.commandsMenu);
        this.menu.add(this.profileMenu);
        this.menu.add(this.presetMenu);
        this.menu.add(this.settingsMenu);
        this.settingsMenu.addActionListener(this);
        this.menu.add(this.cellMenu);
        this.cellMenu.addActionListener(this);

        //Icon Menu

        this.iconMenu = this.window.getLayeredPane();
        this.iconP = new JPanel();
        
        this.playIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/play.png");
        this.pauseIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/stop.png");
        this.iconP.add(this.playPauseBtn = new JToggleButton(this.playIc));
        this.playPauseBtn.addActionListener(this);

        this.nextIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/next.png");
        this.iconP.add(this.nextBtn = new JButton(this.nextIc));
        this.nextBtn.addActionListener(this);

        this.resetIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/reset.png");
        this.iconP.add(this.resetBtn = new JButton(this.resetIc));
        this.resetBtn.addActionListener(this);

        this.clearIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/clear.png");
        this.iconP.add(this.clearBtn = new JButton(this.clearIc));
        this.clearBtn.addActionListener(this);

        this.photoIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/photo.png");
        this.iconP.add(this.photoBtn = new JButton(this.photoIc));
        this.photoBtn.addActionListener(this);

        this.closeIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/close.png");
        this.iconP.add(this.closeBtn = new JButton(this.closeIc));
        this.closeBtn.addActionListener(this);

        this.iconP.setLayout(new GridLayout(1, 6));
        
        
        this.window.add(this.vueGrid, BorderLayout.CENTER);
        this.window.setJMenuBar(this.menu);
        this.window.pack();
        this.window.setVisible(true);

        this.iconMenu.add(this.iconP);
        this.iconP.setVisible(true);
    }
    
    @Override
    public void componentResized(ComponentEvent e){
        if(this.vueGrid != null){
            this.vueGrid.setVueDimension(new Dimension((int) (this.window.getSize().getWidth()), (int)this.window.getSize().getHeight()-this.window.getInsets().top));
            this.iconP.setBounds((this.window.getWidth()-300)/2, (this.window.getHeight()-100), 300, 50);
        }
    }
    
    @Override
    public void componentMoved(ComponentEvent e) {
        // System.out.println("moved");
    }
    
    @Override
    public void componentShown(ComponentEvent e){
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }

    public int stringToInt(JTextArea txt){
        return Integer.valueOf(txt.getText());
    }

    public void actionIcon(){
        if(this.iconP.isVisible() == false){
            System.out.println("Bouton Icon on");
            iconP.setVisible(true);
        } else {
            System.out.println("Bouton Icon off");
            iconP.setVisible(false);
        }
    }

    public void actionLoad(){
        System.out.println("Bouton Load");
        this.loadFrame = new JFrame();
        this.loadDialog = new JDialog(this.loadFrame, "Load file");
        this.loadDialog.setLayout(new GridLayout(2, 1));
        ArrayList<String> profilesNames = ProfileManager.getNames();
        System.out.println(profilesNames);

        for (String name : profilesNames) {
            JButton profil = new JButton(name);
            profil.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource()==profil){
                        System.out.println(name);
                        dataProfiles(name);
                    }
                }
            });
            this.loadDialog.add(profil);
        }
        this.loadDialog.pack();
        this.loadDialog.setVisible(true);
    }

    public void dataProfiles(String name){
        HashMap<String,String> dataProfile = ProfileManager.getProfile(name);
        System.out.println(dataProfile);
        this.timeItVal = Integer.parseInt(dataProfile.get("DELAY"));
        this.numberItVal = Integer.parseInt(dataProfile.get("NUMBER-OF-ITERATION"));
        this.maxBornVal = Integer.parseInt(dataProfile.get("NEIGHBORS-BIRTH-MAX"));
        this.minBornVal = Integer.parseInt(dataProfile.get("NEIGHBORS-BIRTH-MIN"));
        this.maxDieVal = Integer.parseInt(dataProfile.get("NEIGHBORS-DEATH-MAX"));
        this.minDieVal = Integer.parseInt(dataProfile.get("NEIGHBORS-DEATH-MIN"));
        this.radiusVal = Integer.parseInt(dataProfile.get("RADIUS"));
        this.loadFrame.dispatchEvent(new WindowEvent(this.loadFrame, WindowEvent.WINDOW_CLOSING));
        this.radiusVal = Integer.parseInt(dataProfile.get("RADIUS"));
        this.loadFrame.dispatchEvent(new WindowEvent(this.loadFrame, WindowEvent.WINDOW_CLOSING));
    }

    public void actionSave(){
        System.out.println("Save file");
        this.saveFrame = new JFrame();
        this.saveDialog = new JDialog(this.saveFrame, "Load file");
        this.saveP = new JPanel();
        this.saveT = new JTextArea( 1, 10);
        this.confirmSave = new JButton("Confirm");
        this.confirmSave.addActionListener(this);
        this.saveP.add(new JLabel("Profile name "));
        this.saveP.add(this.saveT);
        this.saveP.add(this.confirmSave);
    
        this.saveDialog.add(this.saveP);
        
        this.saveDialog.pack();
        this.saveDialog.setVisible(true);
    }

    public void saveNewProfile() {
        if(ProfileManager.isValidName(this.saveT.getText())) {
            HashMap<String,HashMap<String,String>> map = ProfileManager.load();
            HashMap<String,String> profileSettings = new HashMap<>();
            profileSettings.put("RADIUS", Integer.toString(this.radiusVal));
            profileSettings.put("NUMBER-OF-ITERATION", Integer.toString(this.numberItVal));
            profileSettings.put("NEIGHBORS-BIRTH-MIN", Integer.toString(this.minBornVal));
            profileSettings.put("NEIGHBORS-DEATH-MIN", Integer.toString(this.minDieVal));
            profileSettings.put("DELAY", Integer.toString(this.timeItVal));
            profileSettings.put("NAME",this.saveT.getText());
            profileSettings.put("NEIGHBORS-BIRTH-MAX", Integer.toString(this.maxBornVal));
            profileSettings.put("NEIGHBORS-DEATH-MAX", Integer.toString(this.maxDieVal));
            map.put(UUID.randomUUID().toString(), profileSettings);
            ProfileManager.save(map);
            this.saveFrame.dispatchEvent(new WindowEvent(this.saveFrame, WindowEvent.WINDOW_CLOSING));
        }

    }

    public void actionSetting(){
        System.out.println("Bouton Setting");
        this.setting = new JFrame();
        this.settingDialog = new JDialog(this.setting, "Setting");
        this.settingDialog.setLayout(new GridLayout(5, 1));
        
        this.iterationP = new JPanel();
        this.iteration = new JLabel("Type of iteration :");
        this.infinite = new JCheckBox("Infinite");
        this.infinite.addActionListener(this);
        this.finite = new JCheckBox("Finite");
        this.finite.addActionListener(this);
        this.finite.setSelected(true);
        this.iterationP.add(this.iteration);
        this.iterationP.add(this.infinite);
        this.iterationP.add(this.finite);
        
        this.timeItP = new JPanel();
        this.timeIt = new JLabel("Time between iteration (ms) :");
        this.timeItT = new JTextArea(Integer.toString(timeItVal),1,4);
        this.timeItP.add(this.timeIt);
        this.timeItP.add(this.timeItT);

        this.startItP = new JPanel();
        this.startIt = new JLabel("Start to iteration :");
        this.startItT = new JTextArea(Integer.toString(startItVal),1,4);
        this.startItP.add(this.startIt);
        this.startItP.add(this.startItT);

        this.numberItP = new JPanel();
        this.numberIt = new JLabel("Number of iteration :");
        this.numberItT = new JTextArea(Integer.toString(numberItVal),1,4);
        this.numberItP.add(this.numberIt);
        this.numberItP.add(this.numberItT);

        this.confimSetting = new JButton("Confirm");
        this.confimSetting.addActionListener(this);
        
        this.settingDialog.add(this.iterationP);
        this.settingDialog.add(this.timeItP);
        this.settingDialog.add(this.startItP);
        this.settingDialog.add(this.numberItP);
        this.settingDialog.add(this.confimSetting);
        
        this.settingDialog.pack();
        this.settingDialog.setVisible(true);
    }

    public void actionComfirmSetting(){
        System.out.println("Bouton Confirm Set");
        this.timeItVal = stringToInt(this.timeItT);
        this.numberItVal = stringToInt(this.numberItT);
        if (this.timeItVal == 0 || this.numberItVal == 0){
            JOptionPane.showMessageDialog(this.setting, "Missing information");
        } else {
            this.setting.dispatchEvent(new WindowEvent(this.setting, WindowEvent.WINDOW_CLOSING));        
        }
    }

    public void actionCell(){
        System.out.println("Bouton Cell");
        this.cell = new JFrame();
        this.cellDialog = new JDialog(cell, "Cell");
        this.cellDialog.setLayout(new GridLayout(6, 1));
                    
        for (Cell[] row : this.grid.getBoard()) {
            for (Cell cell : row) {
                this.maxBornVal = cell.getBornMaxNeighbors();
                this.minBornVal = cell.getBornMinNeighbors();
                this.maxDieVal = cell.getDieMaxNeighbors();
                this.minDieVal = cell.getDieMinNeighbors();
                this.radiusVal = cell.getRadius();
            }
        }
        
        this.cellBorn = new JLabel("Neighboring cell born :");
        this.cellBornP = new JPanel();
        
        this.cellMaxBP = new JPanel();
        this.cellMaxB = new JLabel("Max :");
        this.cellMaxBT = new JTextArea(Integer.toString(this.maxBornVal),1,4);
        this.cellMaxBP.add(this.cellMaxB);
        this.cellMaxBP.add(this.cellMaxBT);
        
        this.cellMinBP = new JPanel();
        this.cellMinB = new JLabel("Min :");
        this.cellMinBT = new JTextArea(Integer.toString(this.minBornVal),1,4);            
        this.cellMinBP.add(this.cellMinB);
        this.cellMinBP.add(this.cellMinBT);

        this.cellBornP.add(cellMaxBP);
        this.cellBornP.add(cellMinBP);

        this.cellDie = new JLabel("Neighboring cell dead :");
        this.cellDieP = new JPanel();

        this.cellMaxDP = new JPanel();
        this.cellMaxD = new JLabel("Max :");
        this.cellMaxDT = new JTextArea(Integer.toString(this.maxDieVal),1,4);
        this.cellMaxDP.add(this.cellMaxD);
        this.cellMaxDP.add(this.cellMaxDT);
        
        this.cellMinDP = new JPanel();
        this.cellMinD = new JLabel("Min :");
        this.cellMinDT = new JTextArea(Integer.toString(this.minDieVal),1,4);            
        this.cellMinDP.add(this.cellMinD);
        this.cellMinDP.add(this.cellMinDT);

        this.cellDieP.add(cellMaxDP);
        this.cellDieP.add(cellMinDP);
        
        this.radiusP = new JPanel();
        this.radius = new JLabel("Radius :");
        this.radiusT = new JTextArea(Integer.toString(this.radiusVal),1,4);            
        this.radiusP.add(this.radius);
        this.radiusP.add(this.radiusT);

        this.confirmCell = new JButton("Confirm");
        this.confirmCell.addActionListener(this);
        
        this.cellDialog.add(this.confirmCell);
        this.cellDialog.add(this.cellBorn);
        this.cellDialog.add(this.cellBornP);
        this.cellDialog.add(this.cellDie);
        this.cellDialog.add(this.cellDieP);
        this.cellDialog.add(this.radiusP);

        this.cellDialog.pack();
        this.cellDialog.setVisible(true);
    }

    public void actionComfirmCell(){
        System.out.println("Bouton Confirm Cell");
        this.maxBornVal = stringToInt(this.cellMaxBT);
        this.minBornVal = stringToInt(this.cellMinBT);
        this.maxDieVal = stringToInt(this.cellMaxDT);
        this.minDieVal = stringToInt(this.cellMinDT);
        this.radiusVal = stringToInt(this.radiusT);
        if (this.maxBornVal == 0 || this.minBornVal == 0 || this.maxDieVal == 0 || this.minDieVal == 0 || this.radiusVal == 0){
            JOptionPane.showMessageDialog(this.cell, "Missing information");
        } else {
            for (Cell[] row : this.grid.getBoard()) {
                for (Cell cell : row) {
                    cell.setBornMaxNeighbors(this.maxBornVal);
                    cell.setBornMinNeighbors(this.minBornVal);
                    cell.setDieMaxNeighbors(this.maxDieVal);
                    cell.setDieMinNeighbors(this.minDieVal);
                    cell.setRadius(this.radiusVal);
                }
            }
            this.cell.dispatchEvent(new WindowEvent(this.cell, WindowEvent.WINDOW_CLOSING));        
        } 
    }

    public void action(){
        this.grid.nextGen();
        System.out.println(Thread.currentThread().getName());
    }

    public void btnEnabled(Boolean a){
        if(a){
            this.next.setEnabled(true);
            this.reset.setEnabled(true);
            this.clear.setEnabled(true);
            this.photo.setEnabled(true);
            this.nextBtn.setEnabled(true);
            this.resetBtn.setEnabled(true);
            this.clearBtn.setEnabled(true);
            this.photoBtn.setEnabled(true);
        } else {
            this.next.setEnabled(false);
            this.reset.setEnabled(false);
            this.clear.setEnabled(false);
            this.photo.setEnabled(false);
            this.nextBtn.setEnabled(false);
            this.resetBtn.setEnabled(false);
            this.clearBtn.setEnabled(false);
            this.photoBtn.setEnabled(false);    
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.playPauseBtn){
            if (this.playPauseBtn.isSelected()){
                if (!go) {
                    new Thread(this).start();
                }
                this.playPauseBtn.setIcon(this.pauseIc);
                btnEnabled(false);
            }
            else{
                this.playPauseBtn.setIcon(this.playIc);
                go = false;
                btnEnabled(true);
                System.out.println("Bouton Pause");
            }
        }
        if (e.getSource()==play){
            if ( !go ) {
                new Thread(this).start();
            }
            this.playPauseBtn.setIcon(this.pauseIc);
            btnEnabled(false);
        }
        if (e.getSource()==this.pause){
            go = false;
            this.playPauseBtn.setIcon(playIc);
            btnEnabled(true);
            System.out.println("Bouton Pause");
        }
        if (e.getSource()==this.next || e.getSource()==this.nextBtn){
            action();
            System.out.println("Bouton Next");
        }
        if (e.getSource()==this.reset || e.getSource()==this.resetBtn){
            // this.grid.removeListener(this.vueGrid);
            System.out.println("Bouton Reset");
        }
        if (e.getSource()==this.clear || e.getSource()==this.clearBtn){
            this.grid.clearGrid();
        }
        if (e.getSource()==this.photo || e.getSource()==this.photoBtn){
            try {
                actionScreen();
            } catch (Exception e1) {
                System.out.print(e1);
                e1.printStackTrace();
            }
        }
        
        if (e.getSource()==this.icon || e.getSource()==this.closeBtn){
            actionIcon();
        }
        if (e.getSource()==this.loadProfile){
            actionLoad();       
        }
        if (e.getSource()==this.saveProfile){
            actionSave();
        }
        if (e.getSource()==this.settingsMenu){
            actionSetting();
        }
        if (e.getSource() == this.finite){
            System.out.println("Bouton Finite");
            this.settingDialog.add(this.numberItP);
            this.settingDialog.setLayout(new GridLayout(5, 1));
            this.infinite.setSelected(false);
            this.settingDialog.add(this.confimSetting);
            this.settingDialog.pack();
        }
        if (e.getSource() == this.infinite){
            System.out.println("Bouton Infinite");
            this.settingDialog.remove(this.numberItP);
            this.settingDialog.setLayout(new GridLayout(4, 1));
            this.finite.setSelected(false);
            this.settingDialog.add(this.confimSetting);
            this.settingDialog.pack();
        }
        if (e.getSource()==this.confimSetting){
            actionComfirmSetting();
        }
        if (e.getSource()==this.cellMenu){
            actionCell();
        }
        if (e.getSource()==this.confirmCell){
            actionComfirmCell();
        }
    }

    private boolean go=false;
    public void run() {
        go=true;
        while(go){
            if(this.finite.isSelected()){
                try {
                    for(int i=0; i<this.numberItVal; i++){
                        if(go){
                            this.grid.nextGen();
                            Thread.sleep(this.timeItVal);
                        }
                    }
                    go=false;
                    this.playPauseBtn.setIcon(this.pauseIc);
                    this.playPauseBtn.setEnabled(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    while(go){
                        this.grid.nextGen();
                        Thread.sleep(this.timeItVal);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void actionScreen() throws Exception{
        String path="";
        JFileChooser choose = new JFileChooser(
            FileSystemView
            .getFileSystemView()
            .getHomeDirectory()
        );
        
        choose.setDialogTitle("Enregistrer sous: ");
        choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choose.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");
        choose.addChoosableFileFilter(filter);
        choose.setSelectedFile(new File("screenshot.jpg"));
        int res = choose.showSaveDialog(null);
        if(res == JFileChooser.APPROVE_OPTION) 
        {
            path = choose.getSelectedFile().toString();
        }
        if(path!=""){
            Rectangle rect = this.vueGrid.getBounds();
            BufferedImage captureImage =
                new BufferedImage(rect.width, rect.height,
                                    BufferedImage.TYPE_INT_ARGB);
            this.vueGrid.paint(captureImage.getGraphics());
    
            ImageIO.write(captureImage, "png", new File(path));
        }
    }
}