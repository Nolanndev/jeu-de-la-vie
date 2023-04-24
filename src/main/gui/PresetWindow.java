package main.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import main.core.Cell;
import main.core.Grid;
import main.utils.PresetManager;
/**
 * Class which extend JDialog, which allow to interact between users and PresetManager.
 * @author David Matthias
 * @author Parcheminer Nolann
 * 
 * @see PresetManager
 * @see Window
 */
public class PresetWindow extends JDialog{
    
    public enum Action{
        Save,
        Load,
        Delete
    };

    private JFrame frame;
    private Action action;
    private Cell cell;

    private Grid grid;

    public PresetWindow(JFrame frame, Action action, Cell cell, Grid grid) {
        this.action = action;
        this.frame = frame;
        this.cell = cell;
        this.grid = grid;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setTitle("Preset - " + this.action);
        this.setLocationRelativeTo(this.frame);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(200, HEIGHT));

        
            if(this.action == Action.Load){
                this.load();
            }
    
            if(this.action == Action.Delete){
                this.delete();
            }
    
            if(this.action == Action.Save){
                this.save();
            }

            this.pack();
            this.setVisible(true);
        
    }
    
    public Grid getGrid() {
        return grid;
    }

    public Cell getCell() {
        return cell;
    }

    private void loadDataPreset(String name){
        HashMap<String,Object> dataPreset = PresetManager.getPreset(name);

        Dimension dimGrid = (Dimension)dataPreset.get("SIZE");

        this.grid = new Grid(dimGrid, getCell());
        
        ArrayList<Dimension> dimCells = (ArrayList<Dimension>)dataPreset.get("CELLS");
        for (Dimension coord : dimCells) {
            this.grid.getCell(coord).setState(true);
        }
    }

    private void load(){
        this.setLayout(new GridLayout(0,1));
        ArrayList<String> presetNames = PresetManager.getNames();

        for (String name : presetNames) {
            JButton preset = new JButton(name);
            preset.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == preset) {
                        loadDataPreset(name);
                        setVisible(false);
                        dispose();
                    }
                }
            });
            this.add(preset);
        }
    };


    private void saveDataPreset(String name){
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
        map.put(name, map2);
        PresetManager.save(map);
    }
    
    private void save(){
        JPanel panel = new JPanel();
        JLabel error = new JLabel();
        JTextField inputField = new JTextField(10);
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == confirmButton){
                    if(PresetManager.isValidName(inputField.getText())){
                        saveDataPreset(inputField.getText());
                        setVisible(false);
                        dispose();
                    }
                    else{
                        error.setText("Invalid name (possibly already used)");
                    }
                }
            }
        });

        panel.add(error);
        panel.add(new JLabel("Preset name : "));
        panel.add(inputField);
        panel.add(confirmButton);

        this.add(panel);

    };


    private void deleteDataPreset(String name){
        PresetManager.delete(name);
    }

    private void delete(){
        this.setLayout(new GridLayout(0, 1));
        ArrayList<String> presetNames = PresetManager.getNames();

        for (String name : presetNames) {
            if(!name.equals("default")){ //on ne peut pas supprimer la configuration par défaut donc on ne l'affiche pas
                JButton preset = new JButton(name);
                preset.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == preset) {
                            deleteDataPreset(name);
                            setVisible(false);
                            dispose();
                        }
                    }

                });
                this.add(preset);
            }
        }
    };



}