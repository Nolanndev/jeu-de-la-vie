package main.gui;

import main.core.*;
import main.utils.PresetManager;
import main.utils.ProfileManager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Window implements ActionListener, ComponentListener {

    JFrame window, setting, cell, loadProfileFrame, saveProfileFrame, loadPresetFrame, savePresetFrame;
    VueGrid vueGrid;
    JMenuBar menu;
    JMenu commandsMenu, profileMenu, presetMenu;
    JMenuItem play, pause, next, reset, clear, photo, video, icon, loadProfile, saveProfile, deleteProfile, loadPreset,
            savePreset, deletePreset, settingsMenu, cellMenu;
    JDialog settingDialog, cellDialog, loadProfileDialog, saveProfileDialog, loadPresetDialog, savePresetDialog;
    JPanel saveProfileP, savePresetP, iterationP, timeItP, numberItP, cellBornP, cellMaxBP, cellMinBP, cellDieP,
            cellMaxDP, cellMinDP, radiusP, iconP;
    JLabel iteration, timeIt, numberIt, cellBorn, cellMaxB, cellMinB, cellDie, cellMaxD, cellMinD, radius;
    JTextArea saveProfileT, savePresetT, timeItT, numberItT, cellMaxBT, cellMinBT, cellMaxDT, cellMinDT, radiusT;
    JCheckBox infinite, finite;
    JButton confirmProfileSave, confirmPresetSave, confimSetting, confirmCell, nextBtn, resetBtn, clearBtn, photoBtn, videoBtn, closeBtn;
    JToggleButton playPauseBtn;
    Icon playIc, pauseIc, nextIc, resetIc, clearIc, photoIc, videoIc, closeIc;
    JLayeredPane iconMenu;
    Grid grid;
    Action code;
    int timeItVal = 1000, numberItVal = 10, maxBornVal = 3, minBornVal = 2, maxDieVal = 3, minDieVal = 2, radiusVal = 1;

    public Window(String title) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.window = new JFrame();

        this.window.addComponentListener(this); // resize Event listener

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setTitle(title);
        this.window.setSize(new Dimension((int) (screenSize.getWidth() * 0.8), (int) (screenSize.getHeight() * 0.8))); // 80% of screen size
        this.window.setLocation((int) (screenSize.getWidth() - this.window.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.window.getHeight()) / 2); // Center on screen
        this.window.setResizable(true);
        this.grid = new Grid(new Dimension(300,300));

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
        this.photo.addActionListener(this);
        this.video = new JMenuItem("Video");
        this.video.addActionListener(this);
        this.icon = new JMenuItem("Icons");
        this.icon.setToolTipText("Afficher la barre de raccourci d'actions");
        this.icon.addActionListener(this);

        this.commandsMenu.add(this.play);
        this.commandsMenu.add(this.pause);
        this.commandsMenu.add(this.next);
        this.commandsMenu.add(this.reset);
        this.commandsMenu.add(this.clear);
        this.commandsMenu.add(this.photo);
        this.commandsMenu.add(this.video);
        this.commandsMenu.add(this.icon);

        this.loadProfile = new JMenuItem("Load profile");
        this.loadProfile.addActionListener(this);
        this.saveProfile = new JMenuItem("Save current profile");
        this.saveProfile.addActionListener(this);
        this.deleteProfile = new JMenuItem("Delete profile");
        this.deleteProfile.addActionListener(this);

        this.loadPreset = new JMenuItem("Load preset");
        this.loadPreset.addActionListener(this);
        this.savePreset = new JMenuItem("Save current Preset");
        this.savePreset.addActionListener(this);
        this.deletePreset = new JMenuItem("Delete preset");
        this.deletePreset.addActionListener(this);

        this.profileMenu.add(this.loadProfile);
        this.profileMenu.add(this.saveProfile);
        this.profileMenu.add(this.deleteProfile);

        this.presetMenu.add(this.loadPreset);
        this.presetMenu.add(this.savePreset);
        this.presetMenu.add(this.deletePreset);

        this.menu.add(this.commandsMenu);
        this.menu.add(this.profileMenu);
        this.menu.add(this.presetMenu);
        this.menu.add(this.settingsMenu);
        this.settingsMenu.addActionListener(this);
        this.menu.add(this.cellMenu);
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

        this.videoIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/video.png");
        this.iconP.add(this.videoBtn = new JButton(this.videoIc));
        this.videoBtn.addActionListener(this);

        this.closeIc = new ImageIcon(System.getProperty("user.dir") + "/src/main/assets/button/close.png");
        this.iconP.add(this.closeBtn = new JButton(this.closeIc));
        this.closeBtn.addActionListener(this);

        this.iconP.setLayout(new GridLayout(1, 7));

        this.window.add(this.vueGrid, BorderLayout.CENTER);
        this.window.setJMenuBar(this.menu);
        this.window.setVisible(true);
        this.iconMenu.add(this.iconP);
        this.iconP.setVisible(true);
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
    public void componentMoved(ComponentEvent e) {
        ;
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public int stringToInt(JTextArea txt) {
        return Integer.valueOf(txt.getText());
    }

    public void actionPlay() {
        this.playPauseBtn.setIcon(this.pauseIc);
        if (this.infinite != null) {
            if (this.infinite.isSelected()) {
                this.code = new Action(this.grid, stringToInt(this.timeItT), 0);
            } else {
                this.code = new Action(this.grid, stringToInt(this.timeItT), stringToInt(this.numberItT));
            }
            Thread t = new Thread(this.code);
            t.start();
            this.playPauseBtn.setIcon(this.pauseIc);
        } else {
            JOptionPane.showMessageDialog(this.window, "Comfirm setting information");
        }
    }

    public void actionIcon() {
        if (this.iconP.isVisible() == false) {
            iconP.setVisible(true);
        } else {
            iconP.setVisible(false);
        }
    }

    public void actionProfileLoad() {
        this.loadProfileFrame = new JFrame();
        this.loadProfileDialog = new JDialog(this.loadProfileFrame, "Profile - Load");
        this.loadProfileDialog.setLayout(new GridLayout(2, 1));
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
        this.loadProfileDialog.setSize(200, 200);
        this.loadProfileDialog.setVisible(true);
    }

    public void dataProfiles(String name) {
        HashMap<String, String> dataProfile = ProfileManager.getProfile(name);
        this.timeItVal = Integer.parseInt(dataProfile.get("DELAY"));
        this.numberItVal = Integer.parseInt(dataProfile.get("NUMBER-OF-ITERATION"));
        this.maxBornVal = Integer.parseInt(dataProfile.get("NEIGHBORS-BIRTH-MAX"));
        this.minBornVal = Integer.parseInt(dataProfile.get("NEIGHBORS-BIRTH-MIN"));
        this.maxDieVal = Integer.parseInt(dataProfile.get("NEIGHBORS-DEATH-MAX"));
        this.minDieVal = Integer.parseInt(dataProfile.get("NEIGHBORS-DEATH-MIN"));
        this.radiusVal = Integer.parseInt(dataProfile.get("RADIUS"));
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
        this.loadProfileDialog.setLayout(new GridLayout(2, 1));
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
        this.loadProfileDialog.setSize(200, 200);
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
            profileSettings.put("RADIUS", Integer.toString(this.radiusVal));
            profileSettings.put("NUMBER-OF-ITERATION", Integer.toString(this.numberItVal));
            profileSettings.put("NEIGHBORS-BIRTH-MIN", Integer.toString(this.minBornVal));
            profileSettings.put("NEIGHBORS-DEATH-MIN", Integer.toString(this.minDieVal));
            profileSettings.put("DELAY", Integer.toString(this.timeItVal));
            profileSettings.put("NAME", this.saveProfileT.getText());
            profileSettings.put("NEIGHBORS-BIRTH-MAX", Integer.toString(this.maxBornVal));
            profileSettings.put("NEIGHBORS-DEATH-MAX", Integer.toString(this.maxDieVal));
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
        this.settingDialog.setLayout(new GridLayout(3, 1));

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
        this.cellMaxBT = new JTextArea(Integer.toString(this.maxBornVal), 1, 4);
        this.cellMaxBP.add(this.cellMaxB);
        this.cellMaxBP.add(this.cellMaxBT);

        this.cellMinBP = new JPanel();
        this.cellMinB = new JLabel("Min :");
        this.cellMinBT = new JTextArea(Integer.toString(this.minBornVal), 1, 4);
        this.cellMinBP.add(this.cellMinB);
        this.cellMinBP.add(this.cellMinBT);

        this.cellBornP.add(cellMaxBP);
        this.cellBornP.add(cellMinBP);

        this.cellDie = new JLabel("Neighboring cell dead :");
        this.cellDieP = new JPanel();

        this.cellMaxDP = new JPanel();
        this.cellMaxD = new JLabel("Max :");
        this.cellMaxDT = new JTextArea(Integer.toString(this.maxDieVal), 1, 4);
        this.cellMaxDP.add(this.cellMaxD);
        this.cellMaxDP.add(this.cellMaxDT);

        this.cellMinDP = new JPanel();
        this.cellMinD = new JLabel("Min :");
        this.cellMinDT = new JTextArea(Integer.toString(this.minDieVal), 1, 4);
        this.cellMinDP.add(this.cellMinD);
        this.cellMinDP.add(this.cellMinDT);

        this.cellDieP.add(cellMaxDP);
        this.cellDieP.add(cellMinDP);

        this.radiusP = new JPanel();
        this.radius = new JLabel("Radius :");
        this.radiusT = new JTextArea(Integer.toString(this.radiusVal), 1, 4);
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

    public void actionComfirmCell() {
        this.maxBornVal = stringToInt(this.cellMaxBT);
        this.minBornVal = stringToInt(this.cellMinBT);
        this.maxDieVal = stringToInt(this.cellMaxDT);
        this.minDieVal = stringToInt(this.cellMinDT);
        this.radiusVal = stringToInt(this.radiusT);
        if (this.maxBornVal == 0 || this.minBornVal == 0 || this.maxDieVal == 0 || this.minDieVal == 0
                || this.radiusVal == 0) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.playPauseBtn) {
            if (this.playPauseBtn.isSelected()) {
                actionPlay();
            } else {
                this.playPauseBtn.setIcon(playIc);
            }
        }
        if (e.getSource() == play) {
            actionPlay();
        }
        if (e.getSource() == this.pause) {
            this.playPauseBtn.setIcon(playIc);
        }
        if (e.getSource() == this.next || e.getSource() == this.nextBtn) {
            this.grid.nextGen();
        }
        if (e.getSource() == this.reset || e.getSource() == this.resetBtn) {
            // this.grid.removeListener(this.vueGrid);
            ;
        }
        if (e.getSource() == this.clear || e.getSource() == this.clearBtn) {
            this.grid.clearGrid();
        }
        if (e.getSource() == this.photo || e.getSource() == this.photoBtn) {
            System.out.println("Bouton Photo");
        }
        if (e.getSource() == this.video || e.getSource() == this.videoBtn) {
            System.out.println("Bouton Video");
        }
        if (e.getSource() == this.icon || e.getSource() == this.closeBtn) {
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
            this.settingDialog.setLayout(new GridLayout(4, 1));
            this.settingDialog.pack();
            this.infinite.setSelected(false);
        }
        if (e.getSource() == this.infinite) {
            this.settingDialog.remove(this.numberItP);
            this.settingDialog.setLayout(new GridLayout(3, 1));
            this.settingDialog.pack();
            this.finite.setSelected(false);
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
        if (e.getSource() == this.confirmProfileSave) {
            saveNewProfile();
        }
        if (e.getSource() == this.confirmPresetSave) {
            saveNewPreset();
        }
    }
}
