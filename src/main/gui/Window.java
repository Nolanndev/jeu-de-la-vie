package main.gui;

import main.core.*;
import main.utils.PresetManager;
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

    JFrame window, setting, cellFrame, loadProfileFrame, saveProfileFrame, loadPresetFrame, savePresetFrame;
    VueGrid vueGrid;
    JMenuBar menu;
    JMenu commandsMenu, profileMenu, presetMenu;
    JMenuItem play, pause, next, reset, clear, photo, video, icon, loadProfile, saveProfile, deleteProfile, loadPreset,
            savePreset, deletePreset, settingsMenu, cellMenu;
    JDialog settingDialog, cellDialog, loadProfileDialog, saveProfileDialog, loadPresetDialog, savePresetDialog;
    JPanel startItP, saveProfileP, savePresetP, iterationP, timeItP, numberItP, cellBornP, cellMaxBP, cellMinBP, cellDieP,
            cellMaxDP, cellMinDP, radiusP, iconP;
    JLabel startIt, iteration, timeIt, numberIt, cellBorn, cellMaxB, cellMinB, cellDie, cellMaxD, cellMinD, radius;
    JTextArea startItT, saveProfileT, savePresetT, timeItT, numberItT, cellMaxBT, cellMinBT, cellMaxDT, cellMinDT, radiusT;
    JCheckBox infinite, finite;
    JButton confirmProfileSave, confirmPresetSave, confimSetting, confirmCell, nextBtn, resetBtn, clearBtn, photoBtn, videoBtn, closeBtn;
    JToggleButton playPauseBtn;
    Icon playIc, pauseIc, nextIc, resetIc, clearIc, photoIc, closeIc;
    JLayeredPane iconMenu;
    Grid grid;
    Cell cell;
    int timeItVal = 1000,  startItVal = 1, numberItVal = 10;
    String activePreset = "default";
    
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


        Dimension dimGrid = new Dimension((int) (this.window.getSize().getWidth() * 0.75),(int) this.window.getSize().getHeight() - this.window.getInsets().top);
        this.vueGrid = new VueGrid(this.grid, dimGrid, true);

        // Bar Menu
        this.menu = new JMenuBar();

        this.menu.setLayout(new GridLayout(1, 4));

        this.commandsMenu = new JMenu("Commands");
        this.profileMenu = new JMenu("Profiles");
        this.presetMenu = new JMenu("Presets");
        this.settingsMenu = new JMenuItem("Settings");
        this.cellMenu = new JMenuItem("Cell");

        this.play = new JMenuItem("Play");
        this.play.setToolTipText("Lancer la simulation");
        this.play.addActionListener(this);
        this.pause = new JMenuItem("Pause");
        this.pause.setToolTipText("Mettre la simulation en pause");
        this.pause.addActionListener(this);
        this.next = new JMenuItem("Next");
        this.next.setToolTipText("Prochaine géneration");
        this.next.addActionListener(this);
        this.reset = new JMenuItem("Reset");
        this.reset.setToolTipText("Réinitialiser la simulation");
        this.reset.addActionListener(this);
        this.clear = new JMenuItem("Clear");
        this.clear.setToolTipText("Effacer la grille");
        this.clear.addActionListener(this);
        this.photo = new JMenuItem("Photo");
        this.photo.setToolTipText("Enregistrer la grille actuelle en format jpg");
        this.photo.addActionListener(this);
        this.icon = new JMenuItem("Icons");
        this.icon.addActionListener(this);
        this.icon.setToolTipText("Afficher la barre de raccourci d'actions");


        this.commandsMenu.add(this.play);
        this.commandsMenu.add(this.pause);
        this.commandsMenu.add(this.next);
        this.commandsMenu.add(this.reset);
        this.commandsMenu.add(this.clear);
        this.commandsMenu.add(this.photo);
        this.commandsMenu.add(this.icon);

        this.loadProfile = new JMenuItem("Load profile");
        this.loadProfile.addActionListener(this);
        this.loadProfile.setToolTipText("Afficher les profiles disponibles");
        this.saveProfile = new JMenuItem("Save current profile");
        this.saveProfile.addActionListener(this);
        this.saveProfile.setToolTipText("Enregistrer les paramètres de la simulation dans un nouveau profil");
        this.deleteProfile = new JMenuItem("Delete profile");
        this.deleteProfile.addActionListener(this);
        this.deleteProfile.setToolTipText("Supprimer les profils (sauf default)");

        this.loadPreset = new JMenuItem("Load preset");
        this.loadPreset.addActionListener(this);
        this.loadPreset.setToolTipText("Affichers les presets disponibles");
        this.savePreset = new JMenuItem("Save current Preset");
        this.savePreset.addActionListener(this);
        this.savePreset.setToolTipText("Enregistrer l'état actuel de la grille");
        this.deletePreset = new JMenuItem("Delete preset");
        this.deletePreset.addActionListener(this);
        this.deletePreset.setToolTipText("Affiche les presets pour les supprimer");

        this.profileMenu.add(this.loadProfile);
        this.profileMenu.add(this.saveProfile);
        this.profileMenu.add(this.deleteProfile);

        this.presetMenu.add(this.loadPreset);
        this.presetMenu.add(this.savePreset);
        this.presetMenu.add(this.deletePreset);

        this.menu.add(this.commandsMenu);
        this.menu.setToolTipText("Afficher les actions de simulation");
        this.menu.add(this.profileMenu);
        this.menu.setToolTipText("Afficher les options des profiles");
        this.menu.add(this.presetMenu);
        this.menu.setToolTipText("Afficher les options des presets");
        this.menu.add(this.settingsMenu);
        this.menu.setToolTipText("Afficher les paramètres de simulation");
        this.settingsMenu.addActionListener(this);
        this.menu.add(this.cellMenu);
        this.menu.setToolTipText("Afficher les paramètres des cellules");
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

    public int stringToInt(JTextArea txt) {
        return Integer.valueOf(txt.getText());
    }

    public void actionIcon() {
        if (this.iconP.isVisible() == false) {
            iconP.setVisible(true);
            this.icon.setToolTipText("Cacher la barre de raccourci d'actions");
        } else {
            iconP.setVisible(false);
            this.icon.setToolTipText("Afficher la barre de raccourci d'actions");
        }
    }

    public void actionProfileLoad() {
        this.loadProfileFrame = new JFrame();
        this.loadProfileDialog = new JDialog(this.loadProfileFrame, "Profile - Load");
        this.loadProfileDialog.setLayout(new GridLayout(0, 1));
        ArrayList<String> profilesNames = ProfileManager.getNames();

        for (String name : profilesNames) {
            JButton profil = new JButton(name);
            profil.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == profil) {
                        dataProfiles(name);
                    }
                }

            });
            this.loadProfileDialog.add(profil);
        }
        this.loadProfileDialog.pack();
        this.loadProfileDialog.setVisible(true);
    }

    public void dataProfiles(String name) {
        HashMap<String, String> dataProfile = ProfileManager.getProfile(name);
        this.timeItVal = Integer.parseInt(dataProfile.get("DELAY"));
        this.numberItVal = Integer.parseInt(dataProfile.get("NUMBER-OF-ITERATION"));
        this.cell.setBornMaxNeighbors(Integer.parseInt(dataProfile.get("NEIGHBORS-BIRTH-MAX")));
        this.cell.setBornMinNeighbors(Integer.parseInt(dataProfile.get("NEIGHBORS-BIRTH-MIN")));
        this.cell.setDieMaxNeighbors(Integer.parseInt(dataProfile.get("NEIGHBORS-DEATH-MAX")));
        this.cell.setDieMinNeighbors(Integer.parseInt(dataProfile.get("NEIGHBORS-DEATH-MIN")));
        this.cell.setRadius(Integer.parseInt(dataProfile.get("RADIUS")));
        this.loadProfileFrame.dispatchEvent(new WindowEvent(this.loadProfileFrame, WindowEvent.WINDOW_CLOSING));
    }

    public void actionProfileSave() {
        this.saveProfileFrame = new JFrame();
        this.saveProfileDialog = new JDialog(this.saveProfileFrame, "Profile - Save");
        this.saveProfileP = new JPanel();
        this.saveProfileT = new JTextArea(1, 10);
        this.confirmProfileSave = new JButton("Confirm");
        this.confirmProfileSave.addActionListener(this);
        this.saveProfileP.add(new JLabel("Profile name "));
        this.saveProfileP.add(this.saveProfileT);
        this.saveProfileP.add(this.confirmProfileSave);

        this.saveProfileDialog.add(this.saveProfileP);

        this.saveProfileDialog.pack();
        this.saveProfileDialog.setVisible(true);
    }

    public void deleteProfile() {
        this.loadProfileFrame = new JFrame();
        this.loadProfileDialog = new JDialog(this.loadProfileFrame, "Profile - Delete");
        this.loadProfileDialog.setLayout(new GridLayout(0, 1));
        ArrayList<String> profilesNames = ProfileManager.getNames();

        for (String name : profilesNames) {
            JButton profil = new JButton(name);
            profil.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == profil) {
                        delPro(name);
                    }
                }

            });
            this.loadProfileDialog.add(profil);
        }
        this.loadProfileDialog.pack();
        this.loadProfileDialog.setVisible(true);
    }

    public void delPro(String name) {
        // we ensure not to delete the default profile
        if (!name.equals("default")) {
            HashMap<String, HashMap<String, String>> map = ProfileManager.load();
            map.remove(ProfileManager.getId(name));
            ProfileManager.save(map);
            this.loadProfileFrame.dispatchEvent(new WindowEvent(this.loadProfileFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void saveNewProfile() {
        if (ProfileManager.isValidName(this.saveProfileT.getText())) {
            HashMap<String, HashMap<String, String>> map = ProfileManager.load();
            HashMap<String, String> profileSettings = new HashMap<>();
            profileSettings.put("RADIUS", Integer.toString(this.cell.getRadius()));
            profileSettings.put("NUMBER-OF-ITERATION", Integer.toString(this.numberItVal));
            profileSettings.put("NEIGHBORS-BIRTH-MIN", Integer.toString(this.cell.getBornMinNeighbors()));
            profileSettings.put("NEIGHBORS-DEATH-MIN", Integer.toString(this.cell.getDieMinNeighbors()));
            profileSettings.put("DELAY", Integer.toString(this.timeItVal));
            profileSettings.put("NAME", this.saveProfileT.getText());
            profileSettings.put("NEIGHBORS-BIRTH-MAX", Integer.toString(this.cell.getBornMaxNeighbors()));
            profileSettings.put("NEIGHBORS-DEATH-MAX", Integer.toString(this.cell.getDieMaxNeighbors()));
            map.put(UUID.randomUUID().toString(), profileSettings);
            ProfileManager.save(map);
            this.saveProfileFrame.dispatchEvent(new WindowEvent(this.saveProfileFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void actionPresetLoad() {
        this.loadProfileFrame = new JFrame();
        this.loadPresetDialog = new JDialog(this.loadProfileFrame, "Preset - Load");
        this.loadPresetDialog.setLayout(new GridLayout(2, 1));
        ArrayList<String> presetNames = PresetManager.getNames();
        if (presetNames == null) {
            this.loadPresetDialog.add(new JLabel("you don't have any preset"));
        } else {
            for (String presetName : presetNames) {
                JButton profil = new JButton(presetName);
                profil.addActionListener(new ActionListener() {
    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == profil) {
                            dataPreset(presetName);
                        }
                    }
                    
                });
                this.loadPresetDialog.add(profil);
            }
        }
        this.loadPresetDialog.setSize(200, 200);
        this.loadPresetDialog.setVisible(true);
    }
    
    public void dataPreset(String name) {
        this.grid.clearGrid();
        System.lineSeparator();
        for (Dimension dim : PresetManager.getPreset(name)) {
            System.out.println("taille grille : " + this.grid.getSize());
            System.out.println("dim : " + dim);
            // this.grid.setCell(dim, new Cell(this.minBornVal, this.maxBornVal, this.minDieVal, this.maxDieVal, this.radiusVal, true));
            // this.grid.setCell(dim, new Cell(true));
            this.grid.getCell(dim);
        }
    }

    public void actionPresetSave() {
        this.savePresetFrame = new JFrame();
        this.savePresetDialog = new JDialog(this.savePresetFrame, "Preset - Save");
        this.savePresetP = new JPanel();
        this.savePresetT = new JTextArea(1, 10);
        this.confirmPresetSave = new JButton("Confirm");
        this.confirmPresetSave.addActionListener(this);
        this.savePresetP.add(new JLabel("Profile name "));
        this.savePresetP.add(this.savePresetT);
        this.savePresetP.add(this.confirmPresetSave);

        this.savePresetDialog.add(this.savePresetP);

        this.savePresetDialog.pack();
        this.savePresetDialog.setVisible(true);
    }
    
    public void deletePreset() {
        this.loadProfileFrame = new JFrame();
        this.loadPresetDialog = new JDialog(this.loadProfileFrame, "Preset - Delete");
        this.loadPresetDialog.setLayout(new GridLayout(2, 1));
        ArrayList<String> presetNames = PresetManager.getNames();
        if (presetNames == null) {
            this.loadPresetDialog.add(new JLabel("you don't have any preset"));
        } else {
            for (String presetName : presetNames) {
                JButton profil = new JButton(presetName);
                profil.addActionListener(new ActionListener() {
    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == profil) {
                            delPre(presetName);
                        }
                    }
                    
                });
                this.loadPresetDialog.add(profil);
            }
        }
        this.loadPresetDialog.setSize(200, 200);
        this.loadPresetDialog.setVisible(true);
    }
    
    public void delPre(String name) {
        HashMap<String, ArrayList<Dimension>> map = PresetManager.load();
        map.remove(name);
        PresetManager.save(map);
        this.loadProfileFrame.dispatchEvent(new WindowEvent(this.loadProfileFrame, WindowEvent.WINDOW_CLOSING));
    }
    
    public void saveNewPreset() {
        if (PresetManager.isValidName(this.savePresetT.getText())) {
            HashMap<String, ArrayList<Dimension>> map = PresetManager.load();
            ArrayList<Dimension> dims = new ArrayList<>();
            for (int i=0; i<this.grid.getWidth(); i++) {
                for (int j=0; j<this.grid.getHeight(); j++) {
                    if (this.grid.getCell(i,j).isAlive()) {
                        dims.add(new Dimension(i,j));
                    }
                }
            }
            map.put(this.savePresetT.getText(), dims);
            PresetManager.save(map);
            this.savePresetFrame.dispatchEvent(new WindowEvent(this.savePresetFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void actionSetting() {
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
        this.timeIt = new JLabel("Time between iteration (ms) :");
        this.timeItT = new JTextArea(Integer.toString(timeItVal), 1, 4);
        this.timeItP.add(this.timeIt);
        this.timeItP.add(this.timeItT);

        this.startItP = new JPanel();
        this.startIt = new JLabel("Start to iteration :");
        this.startItT = new JTextArea(Integer.toString(startItVal),1,4);
        this.startItP.add(this.startIt);
        this.startItP.add(this.startItT);

        this.numberItP = new JPanel();
        this.numberIt = new JLabel("Number of iteration :");
        this.numberItT = new JTextArea(Integer.toString(numberItVal), 1, 4);
        this.numberItP.add(this.numberIt);
        this.numberItP.add(this.numberItT);

        this.confimSetting = new JButton("Confirm");
        this.confimSetting.addActionListener(this);

        this.settingDialog.add(this.confimSetting);
        this.settingDialog.add(this.iterationP);
        this.settingDialog.add(this.timeItP);
        this.settingDialog.add(this.startItP);
        this.settingDialog.add(this.confimSetting);
        
        this.settingDialog.pack();
        this.settingDialog.setVisible(true);
    }

    public void actionComfirmSetting() {
        this.timeItVal = stringToInt(this.timeItT);
        this.numberItVal = stringToInt(this.numberItT);
        if (this.timeItVal == 0 || this.numberItVal == 0) {
            JOptionPane.showMessageDialog(this.setting, "Missing information");
        } else {
            this.setting.dispatchEvent(new WindowEvent(this.setting, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void actionCell() {
        this.cellFrame = new JFrame();
        this.cellDialog = new JDialog(this.cellFrame, "Cell");
        this.cellDialog.setLayout(new GridLayout(6, 1));

        this.cellBorn = new JLabel("Neighboring cell born :");
        this.cellBornP = new JPanel();

        this.cellMaxBP = new JPanel();
        this.cellMaxB = new JLabel("Max :");
        this.cellMaxBT = new JTextArea(Integer.toString(this.cell.getBornMaxNeighbors()), 1, 4);
        this.cellMaxBP.add(this.cellMaxB);
        this.cellMaxBP.add(this.cellMaxBT);

        this.cellMinBP = new JPanel();
        this.cellMinB = new JLabel("Min :");
        this.cellMinBT = new JTextArea(Integer.toString(this.cell.getBornMinNeighbors()), 1, 4);
        this.cellMinBP.add(this.cellMinB);
        this.cellMinBP.add(this.cellMinBT);

        this.cellBornP.add(cellMaxBP);
        this.cellBornP.add(cellMinBP);

        this.cellDie = new JLabel("Neighboring cell dead :");
        this.cellDieP = new JPanel();

        this.cellMaxDP = new JPanel();
        this.cellMaxD = new JLabel("Max :");
        this.cellMaxDT = new JTextArea(Integer.toString(this.cell.getDieMaxNeighbors()), 1, 4);
        this.cellMaxDP.add(this.cellMaxD);
        this.cellMaxDP.add(this.cellMaxDT);

        this.cellMinDP = new JPanel();
        this.cellMinD = new JLabel("Min :");
        this.cellMinDT = new JTextArea(Integer.toString(this.cell.getDieMinNeighbors()), 1, 4);
        this.cellMinDP.add(this.cellMinD);
        this.cellMinDP.add(this.cellMinDT);

        this.cellDieP.add(cellMaxDP);
        this.cellDieP.add(cellMinDP);

        this.radiusP = new JPanel();
        this.radius = new JLabel("Radius :");
        this.radiusT = new JTextArea(Integer.toString(this.cell.getRadius()), 1, 4);
        this.radiusP.add(this.radius);
        this.radiusP.add(this.radiusT);

        this.confirmCell = new JButton("Confirm");
        this.confirmCell.addActionListener(this);

        this.cellDialog.add(this.cellBorn);
        this.cellDialog.add(this.cellBornP);
        this.cellDialog.add(this.cellDie);
        this.cellDialog.add(this.cellDieP);
        this.cellDialog.add(this.radiusP);
        this.cellDialog.add(this.confirmCell);

        this.cellDialog.pack();
        this.cellDialog.setVisible(true);
    }

    public void actionComfirmCell() {
        if (stringToInt(this.cellMaxBT) < stringToInt(this.cellMinBT) || stringToInt(this.cellMaxDT) < stringToInt(this.cellMinDT)) {
            JOptionPane.showMessageDialog(this.cellFrame, "Minimum values need to be superior or equal than maximum values");
        }
        else if(stringToInt(this.radiusT)<=0){
            JOptionPane.showMessageDialog(this.cellFrame, "Radius must be superior to 0");
        }
        else {
            this.cell.setBornMaxNeighbors(stringToInt(this.cellMaxBT));
            this.cell.setBornMinNeighbors(stringToInt(this.cellMinBT));
            this.cell.setDieMaxNeighbors(stringToInt(this.cellMaxDT));
            this.cell.setDieMinNeighbors(stringToInt(this.cellMinDT));
            this.cell.setRadius(stringToInt(this.radiusT));

            for (Cell[] row : this.grid.getBoard()) {
                for (Cell cell : row) {
                    cell.setBornMaxNeighbors(this.cell.getBornMaxNeighbors());
                    cell.setBornMinNeighbors(this.cell.getBornMinNeighbors());
                    cell.setDieMaxNeighbors(this.cell.getDieMaxNeighbors());
                    cell.setDieMinNeighbors(this.cell.getDieMinNeighbors());
                    cell.setRadius(this.cell.getRadius());
                }
            }
            this.cellFrame.dispatchEvent(new WindowEvent(this.cellFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void action(){
        this.grid.nextGen();
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
                if (this.infinite != null){
                    if (!go) {
                        new Thread(this).start();
                    }
                    this.playPauseBtn.setIcon(this.pauseIc);
                    btnEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this.window, "Comfirm setting information");
                }
            }
            else{
                this.playPauseBtn.setIcon(this.playIc);
                go = false;
                btnEnabled(true);
            }
        }
        if (e.getSource()==play){
            if (this.infinite != null){
                if (!go) {
                    new Thread(this).start();
                }
            } else {
                JOptionPane.showMessageDialog(this.window, "Comfirm setting information");
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
            dataPreset(this.activePreset);
        }
        if (e.getSource() == this.clear || e.getSource() == this.clearBtn) {
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
            deleteProfile();
        }
        if (e.getSource() == this.loadPreset) {
            actionPresetLoad();
        }
        if (e.getSource() == this.savePreset) {
            actionPresetSave();
        }
        if (e.getSource() == this.deletePreset) {
            deletePreset();
        }
        if (e.getSource() == this.settingsMenu) {
            actionSetting();
        }
        if (e.getSource() == this.finite) {
            this.settingDialog.add(this.numberItP);
            this.settingDialog.setLayout(new GridLayout(5, 1));
            this.infinite.setSelected(false);
            this.settingDialog.add(this.confimSetting);
            this.settingDialog.pack();
        }
        if (e.getSource() == this.infinite) {
            this.settingDialog.remove(this.numberItP);
            this.settingDialog.setLayout(new GridLayout(4, 1));
            this.finite.setSelected(false);
            this.settingDialog.add(this.confimSetting);
            this.settingDialog.pack();
        }
        if (e.getSource() == this.confimSetting) {
            actionComfirmSetting();
        }
        if (e.getSource() == this.cellMenu) {
            actionCell();
        }
        if (e.getSource() == this.confirmCell) {
            actionComfirmCell();
        }
        if (e.getSource() == this.confirmPresetSave) {
            saveNewPreset();
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
