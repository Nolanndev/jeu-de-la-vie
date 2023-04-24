package main.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.core.*;

public class CellWindow extends JDialog {

    private Frame frame;
    private Cell cell;
    private JTextField cellMaxBT, cellMinBT, cellMaxDT, cellMinDT, radiusT;

    public CellWindow(Frame frame, Cell cell){
        this.frame = frame;
        this.cell = cell;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setTitle("Cell");
        this.setLocationRelativeTo(this.frame);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.displayUI();
        this.pack();
        this.setVisible(true);
    }

    public int stringToInt(JTextField txt) {
        return Integer.valueOf(txt.getText());
    }

    private void displayUI() {
        this.setLayout(new GridLayout(6, 1));

        JLabel cellBorn = new JLabel("Neighboring cell born :");
        JPanel cellBornP = new JPanel();

        JPanel cellMaxBP = new JPanel();
        JLabel cellMaxB = new JLabel("Max :");
        this.cellMaxBT = new JTextField(Integer.toString(this.cell.getBornMaxNeighbors()), 4);
        cellMaxBP.add(cellMaxB);
        cellMaxBP.add(this.cellMaxBT);

        JPanel cellMinBP = new JPanel();
        JLabel cellMinB = new JLabel("Min :");
        this.cellMinBT = new JTextField(Integer.toString(this.cell.getBornMinNeighbors()), 4);
        cellMinBP.add(cellMinB);
        cellMinBP.add(this.cellMinBT);
    
        cellBornP.add(cellMaxBP);
        cellBornP.add(cellMinBP);

        JLabel cellDie = new JLabel("Neighboring cell dead :");
        JPanel cellDieP = new JPanel();

        JPanel cellMaxDP = new JPanel();
        JLabel cellMaxD = new JLabel("Max :");
        this.cellMaxDT = new JTextField(Integer.toString(this.cell.getDieMaxNeighbors()),4);
        cellMaxDP.add(cellMaxD);
        cellMaxDP.add(this.cellMaxDT);

        JPanel cellMinDP = new JPanel();
        JLabel cellMinD = new JLabel("Min :");
        this.cellMinDT = new JTextField(Integer.toString(this.cell.getDieMinNeighbors()),4);
        cellMinDP.add(cellMinD);
        cellMinDP.add(this.cellMinDT);

        cellDieP.add(cellMaxDP);
        cellDieP.add(cellMinDP);

        JPanel radiusP = new JPanel();
        JLabel radius = new JLabel("Radius :");
        this.radiusT = new JTextField(Integer.toString(this.cell.getRadius()), 4);
        radiusP.add(radius);
        radiusP.add(this.radiusT);
        
        this.add(cellBorn);
        this.add(cellBornP);
        this.add(cellDie);
        this.add(cellDieP);
        this.add(radiusP);
        
        JButton confirmCell = new JButton("Confirm");
        add(confirmCell);
        confirmCell.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == confirmCell){
                    actionConfirmCell();
                    
                }
            }
        });
    }

    public void actionConfirmCell() {
        int cellMinBVal = stringToInt(this.cellMinBT);
        int cellMaxBVal = stringToInt(this.cellMaxBT);
        int cellMinDVal = stringToInt(this.cellMinDT);
        int cellMaxDVal = stringToInt(this.cellMaxDT);
        int radiusVal =stringToInt(this.radiusT);

        if (cellMaxBVal < cellMinBVal || cellMaxDVal < cellMinDVal) {
            JOptionPane.showMessageDialog(this.frame, "Maximum values need to be superior or equal than minimum values");
        }
        else if(radiusVal <= 0){
            JOptionPane.showMessageDialog(this.frame, "Radius must be superior to 0");
        }
        else {
            this.cell = new Cell(cellMinBVal, cellMaxBVal, cellMinDVal, cellMaxDVal, radiusVal, false);
            setVisible(false);
            dispose();
        }
    }

    public Cell getCell(){
        return this.cell;
    }
}