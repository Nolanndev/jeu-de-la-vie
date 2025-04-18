package main.gui;

import main.core.*;
import main.utils.PresetManager;

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

import javax.imageio.ImageIO;


public class Window implements ActionListener, ComponentListener, Runnable {

    JFrame window;
    VueGrid vueGrid;
    JMenuBar menu;
    JMenu commandsMenu, profileMenu, presetMenu;
    JMenuItem play, pause, next, reset, clear, photo, icon, loadProfile, saveProfile, deleteProfile, loadPreset,
            savePreset, deletePreset, iterationMenu, cellMenu;
    JPanel iconP;
    JButton nextBtn, resetBtn, clearBtn, photoBtn, videoBtn, closeBtn;
    JToggleButton playPauseBtn;
    Icon playIc, pauseIc, nextIc, resetIc, clearIc, photoIc, closeIc;
    JLayeredPane iconMenu;


    Grid grid;
    Cell cell;

    int timeItVal = 1000,  startItVal = 0, numberItVal = 10;
    boolean presetSave = false;
    boolean go=false, iteration, confirm;
    
    public Window(String title){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.window = new JFrame();

        this.window.addComponentListener(this); // resize Event listener

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setTitle(title);
        this.window.setSize(new Dimension((int) (screenSize.getWidth() * 0.8), (int) (screenSize.getHeight() * 0.8))); // 80% of screen size
        this.window.setLocation((int) (screenSize.getWidth() - this.window.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.window.getHeight()) / 2); // Center on screen
        this.window.setResizable(true);
        
        this.cell = new Cell(3,3,2,3,1,false);
        this.grid = new Grid(new Dimension(500,500));
        
        Dimension dimGrid = new Dimension((int) (this.window.getSize().getWidth()),(int) this.window.getSize().getHeight() - this.window.getInsets().top);
        this.vueGrid = new VueGrid(this.grid, dimGrid, true);

        // Bar Menu

        this.menu = new JMenuBar();

        this.menu.setLayout(new GridLayout(1, 4));

        this.commandsMenu = new JMenu("Commands");
        this.profileMenu = new JMenu("Profiles");
        this.presetMenu = new JMenu("Presets");
        this.iterationMenu = new JMenuItem("Iteration");
        this.cellMenu = new JMenuItem("Cell");

        this.play = new JMenuItem("Play");
        this.play.setToolTipText("Run the simulation");
        this.play.addActionListener(this);
        this.pause = new JMenuItem("Pause");
        this.pause.setToolTipText("Pause the simulation");
        this.pause.addActionListener(this);
        this.next = new JMenuItem("Next");
        this.next.setToolTipText("Next generation");
        this.next.addActionListener(this);
        this.reset = new JMenuItem("Reset");
        this.reset.setToolTipText("Reset Simulation");
        this.reset.addActionListener(this);
        this.clear = new JMenuItem("Clear");
        this.clear.setToolTipText("Clear grid");
        this.clear.addActionListener(this);
        this.photo = new JMenuItem("Photo");
        this.photo.setToolTipText("Save current grid in jpg format");
        this.photo.addActionListener(this);
        this.icon = new JMenuItem("Icons");
        this.icon.addActionListener(this);
        this.icon.setToolTipText("Show or hide the action shortcut bar");


        this.commandsMenu.add(this.play);
        this.commandsMenu.add(this.pause);
        this.commandsMenu.add(this.next);
        this.commandsMenu.add(this.reset);
        this.commandsMenu.add(this.clear);
        this.commandsMenu.add(this.photo);
        this.commandsMenu.add(this.icon);

        this.loadProfile = new JMenuItem("Load profile");
        this.loadProfile.addActionListener(this);
        this.loadProfile.setToolTipText("Show available profiles");
        this.saveProfile = new JMenuItem("Save current profile");
        this.saveProfile.addActionListener(this);
        this.saveProfile.setToolTipText("Save simulation settings to a new profile");
        this.deleteProfile = new JMenuItem("Delete profile");
        this.deleteProfile.addActionListener(this);
        this.deleteProfile.setToolTipText("Delete profiles (except default)");

        this.loadPreset = new JMenuItem("Load preset");
        this.loadPreset.addActionListener(this);
        this.loadPreset.setToolTipText("Show available presets");
        this.savePreset = new JMenuItem("Save current Preset");
        this.savePreset.addActionListener(this);
        this.savePreset.setToolTipText("Save current grid state");
        this.deletePreset = new JMenuItem("Delete preset");
        this.deletePreset.addActionListener(this);
        this.deletePreset.setToolTipText("Display presets to delete them");

        this.profileMenu.add(this.loadProfile);
        this.profileMenu.add(this.saveProfile);
        this.profileMenu.add(this.deleteProfile);

        this.presetMenu.add(this.loadPreset);
        this.presetMenu.add(this.savePreset);
        this.presetMenu.add(this.deletePreset);

        this.menu.add(this.commandsMenu);
        this.menu.setToolTipText("Show simulation actions");
        this.menu.add(this.profileMenu);
        this.menu.setToolTipText("Show profile options");
        this.menu.add(this.presetMenu);
        this.menu.setToolTipText("Show preset options");
        this.menu.add(this.iterationMenu);
        this.menu.setToolTipText("Show iteration settings");
        this.iterationMenu.addActionListener(this);
        this.menu.add(this.cellMenu);
        this.menu.setToolTipText("Show cell settings");
        this.cellMenu.addActionListener(this);

        // Icon Menu

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
        this.iconMenu.add(this.iconP);

        this.iconP.setVisible(true);

        this.window.pack();
        this.window.setVisible(true);

    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (this.vueGrid != null) {
            this.vueGrid.setVueDimension(new Dimension((int) (this.window.getSize().getWidth()),
                    (int) this.window.getSize().getHeight() - this.window.getInsets().top));
            this.iconP.setBounds((this.window.getWidth() - 300) / 2, (this.window.getHeight() - 100), 300, 50);
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}

    
    public void actionIcon() {
        if (this.iconP.isVisible() == false) {
            iconP.setVisible(true);
            this.icon.setToolTipText("Hide action shortcut bar");
        } else {
            iconP.setVisible(false);
            this.icon.setToolTipText("Show action shortcut bar");
        }
    }

    public void actionProfileLoad() {
        ProfileWindow loadDialog = new ProfileWindow(this.window, ProfileWindow.Action.Load);
        if(loadDialog.getCell()!=null){ // We check that we have loaded a cell
            this.cell = loadDialog.getCell().copyCell();
            this.numberItVal = loadDialog.getNumberIteration();
            this.timeItVal = loadDialog.getDelay();
        }
    }

    public void actionProfileSave() {
        new ProfileWindow(this.window, this.cell, this.timeItVal, this.numberItVal);
    }

    public void actionDeleteProfile() {
        new ProfileWindow(this.window, ProfileWindow.Action.Delete);
    }


    public void actionPresetLoad() {
        PresetWindow loadDialog = new PresetWindow(this.window, PresetWindow.Action.Load, this.cell, null);
        if(loadDialog.getGrid() != null){
            this.grid.setBoard(loadDialog.getGrid().getBoard());
        }
    }

    public void actionPresetDelete() {
        new PresetWindow(this.window, PresetWindow.Action.Delete,null, null);
    }

    public void actionPresetSave(){
        new PresetWindow(this.window, PresetWindow.Action.Save, null, grid);
    }

    public void actionIteration() {
        IterationWindow iterationSetting = new IterationWindow(this.window, this.timeItVal, this.startItVal, this.numberItVal);
        this.iteration = iterationSetting.getIteration();
        this.confirm = iterationSetting.getConf();
        this.timeItVal = iterationSetting.getTime();
        this.numberItVal = iterationSetting.getNumber();
        this.startItVal = iterationSetting.getStart();
    }

    public void actionCell(){
        CellWindow cellSetting = new CellWindow(this.window, this.cell);

        this.cell = cellSetting.getCell().copyCell();
        for (Cell[] row : this.grid.getBoard()) {
            for (Cell cell : row) {
                Boolean alive = cell.isAlive();
                cell = this.cell.copyCell();
                cell.setState(alive);
            }
        }
    }

    public void action(){
        if(this.presetSave == false){
            this.presetSave = true; //on passe a true afin que le contenue de ce if ne s'execute que la premiere fois qu'on appel action()

            PresetManager.delete("default"); //on supprime le preset par default

            //on sauvegarde la grille actuelle dans le fichier de preset avec le nom default
            HashMap<String,HashMap<String,Object>> map = PresetManager.load();
            ArrayList<Dimension> dims = new ArrayList<>();
            for (int i = 0; i < this.grid.getWidth(); i++) {
                for (int j = 0; j < this.grid.getHeight(); j++) {
                    if (this.grid.getCell(i, j).isAlive()) {
                        dims.add(new Dimension(i, j));
                    }
                }
            }

            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("SIZE", this.grid.getSize());
            map2.put("CELLS", dims);
            map.put("default", map2);
            PresetManager.save(map);
        }
        this.grid.advance(1);
    }

    public void actionScreen() throws Exception{ //take screenshot
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

    public void run() {
        this.grid.removeListener(vueGrid); //On cache le calcul des n génération
        this.grid.advance(this.startItVal);
        this.grid.addListener(vueGrid);
        this.vueGrid.changeOccured();

        go=true;
        while(go){
            this.iconMenu.setVisible(true);
            if(this.iteration == true){
                try {
                    for(int i=0; i<this.numberItVal; i++){
                        if(go){
                            action();
                            Thread.sleep(this.timeItVal);
                        }
                    }
                    go=false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    while(go){
                        action();
                        Thread.sleep(this.timeItVal);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void btnEnabled(Boolean touch){
        if(touch){
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
                if (this.confirm == true){
                    if (!go) {
                        new Thread(this).start();
                    }
                    this.playPauseBtn.setIcon(this.pauseIc);
                    btnEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this.window, "Comfirm itertion information");
                }
            }
            else{
                this.playPauseBtn.setIcon(this.playIc);
                go = false;
                btnEnabled(true);
            }
        }
        if (e.getSource()==play){
            if (this.confirm == true){
                if (!go) {
                    new Thread(this).start();
                }
            } else {
                JOptionPane.showMessageDialog(this.window, "Comfirm itertion information");
            }
            this.playPauseBtn.setIcon(this.pauseIc);
            btnEnabled(false);
        }
        if (e.getSource()==this.pause){
            go = false;
            this.playPauseBtn.setIcon(playIc);
            btnEnabled(true);
        }
        if (e.getSource()==this.next || e.getSource()==this.nextBtn){
            action();
        }
        if (e.getSource() == this.reset || e.getSource() == this.resetBtn) {
            //on charge le preset "default"
            if(this.presetSave == true){
                HashMap<String,Object> dataPreset = PresetManager.getPreset("default");

                Dimension dimGrid = (Dimension)dataPreset.get("SIZE");

                Grid loadGrid = new Grid(dimGrid, this.cell);
                
                ArrayList<Dimension> dimCells = (ArrayList<Dimension>)dataPreset.get("CELLS");
                for (Dimension coord : dimCells) {
                    loadGrid.getCell(coord).setState(true);
                }
                this.grid.setBoard(loadGrid.getBoard());
            }
        }
        if (e.getSource() == this.clear || e.getSource() == this.clearBtn) {
            this.presetSave = false; //on reinitialise le preset par défaut
            this.grid.clearGrid();
        }
        if (e.getSource()==this.photo || e.getSource()==this.photoBtn){
            try {
                actionScreen();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        
        if (e.getSource()==this.icon || e.getSource()==this.closeBtn){
            actionIcon();
        }
        if (e.getSource() == this.loadProfile) {
            actionProfileLoad();
        }
        if (e.getSource() == this.saveProfile) {
            actionProfileSave();
        }
        if (e.getSource() == this.deleteProfile) {
            actionDeleteProfile();
        }
        if (e.getSource() == this.loadPreset) {
            actionPresetLoad();
        }
        if (e.getSource() == this.savePreset) {
            actionPresetSave();
        }
        if (e.getSource() == this.deletePreset) {
            actionPresetDelete();
        }
        if (e.getSource() == this.iterationMenu) {
            actionIteration();
        }
        if (e.getSource() == this.cellMenu) {
            actionCell();
        }
    }

}
