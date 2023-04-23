package main.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.core.Cell;
import main.utils.ProfileManager;

public class ProfileWindow extends JDialog{
    
    public enum Action{
        Save,
        Load,
        Delete
    };

    private JFrame frame;
    private Action action;

    //use to load profile
    private Cell cell;
    private int delay;
    private int numberIteration;

    public ProfileWindow(JFrame frame, Action action) {
        this.action = action;
        this.frame = frame;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setTitle("Profile - " + this.action);
        this.setLocationRelativeTo(this.frame);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(200, HEIGHT));

        if(this.action != Action.Save){
            if(this.action == Action.Load){
                this.load();
            }
    
            if(this.action == Action.Delete){
                this.delete();
            }
    
            this.pack();
            this.setVisible(true);
        }
    }
    
    public ProfileWindow(JFrame frame, Cell cell, int delay, int numberIteration){
        this(frame, Action.Save);
        this.cell = cell;
        this.delay = delay;
        this.numberIteration = numberIteration;
        this.save();
        this.pack();
        this.setVisible(true);
    }

    public Cell getCell() {
        return cell;
    }

    public int getDelay() {
        return delay;
    }

    public int getNumberIteration() {
        return numberIteration;
    }


    private void loadDataProfiles(String name){
        HashMap<String, String> dataProfile = ProfileManager.getProfile(name);
        
        this.delay = Integer.parseInt(dataProfile.get("DELAY"));
        this.numberIteration = Integer.parseInt(dataProfile.get("NUMBER-OF-ITERATION"));
        
        int bornMaxNeighbors = Integer.parseInt(dataProfile.get("NEIGHBORS-BIRTH-MAX"));
        int bornMinNeighbors = Integer.parseInt(dataProfile.get("NEIGHBORS-BIRTH-MIN"));
        int dieMaxNeighbors = Integer.parseInt(dataProfile.get("NEIGHBORS-DEATH-MAX"));
        int dieMinNeighbors = Integer.parseInt(dataProfile.get("NEIGHBORS-DEATH-MIN"));
        int radius = Integer.parseInt(dataProfile.get("RADIUS"));
        this.cell = new Cell(bornMinNeighbors, bornMaxNeighbors, dieMinNeighbors, dieMaxNeighbors, radius, false);
    }

    private void load(){
        this.setLayout(new GridLayout(0,1));
        ArrayList<String> profilesNames = ProfileManager.getNames();

        for (String name : profilesNames) {
            JButton profil = new JButton(name);
            profil.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == profil) {
                        loadDataProfiles(name);
                        setVisible(false);
                        dispose();
                    }
                }

            });
            this.add(profil);
        }
    };


    private void saveDataProfile(String name){
        HashMap<String, HashMap<String, String>> map = ProfileManager.load();
        HashMap<String, String> profileSettings = new HashMap<>();
        profileSettings.put("RADIUS", Integer.toString(this.getCell().getRadius()));
        profileSettings.put("NUMBER-OF-ITERATION", Integer.toString(this.getNumberIteration()));
        profileSettings.put("NEIGHBORS-BIRTH-MIN", Integer.toString(this.getCell().getBornMinNeighbors()));
        profileSettings.put("NEIGHBORS-DEATH-MIN", Integer.toString(this.getCell().getDieMinNeighbors()));
        profileSettings.put("DELAY", Integer.toString(this.getDelay()));
        profileSettings.put("NAME", name);
        profileSettings.put("NEIGHBORS-BIRTH-MAX", Integer.toString(this.getCell().getBornMaxNeighbors()));
        profileSettings.put("NEIGHBORS-DEATH-MAX", Integer.toString(this.getCell().getDieMaxNeighbors()));
        map.put(UUID.randomUUID().toString(), profileSettings);
        ProfileManager.save(map);
    }
    
    private void save(){
        JPanel panel = new JPanel();
        JLabel error = new JLabel("");
        JTextField inputField = new JTextField(10);
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == confirmButton){
                    if(ProfileManager.isValidName(inputField.getText())){
                        saveDataProfile(inputField.getText());
                        setVisible(false);
                        dispose();
                    }
                    else{
                        error.setText("Invalid name (possibly already used)");
                    }
                }
            }
        });

        panel.add(new JLabel("Profile name : "));
        panel.add(inputField);
        panel.add(confirmButton);

        this.add(panel);

    };


    private void deleteDataProfile(String name){
        // we ensure not to delete the default profile
        HashMap<String, HashMap<String, String>> map = ProfileManager.load();
        map.remove(ProfileManager.getId(name));
        ProfileManager.save(map);
    }

    private void delete(){
        this.setLayout(new GridLayout(0, 1));
        ArrayList<String> profilesNames = ProfileManager.getNames();

        for (String name : profilesNames) {
            if(!name.equals("default")){ //on ne peut pas supprimer la configuration par défaut donc on ne l'affiche pas
                JButton profil = new JButton(name);
                profil.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == profil) {
                            deleteDataProfile(name);
                            setVisible(false);
                            dispose();
                        }
                    }

                });
                this.add(profil);
            }
        }
    };



}