package main.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.core.*;

public class CellWindow extends JDialog {

    private Frame frame;
    private Cell cell;
    private JPanel cellBornP, cellMaxBP, cellMinBP, cellDieP, cellMaxDP, cellMinDP, radiusP;
    private JLabel cellBorn, cellMaxB, cellMinB, cellDie, cellMaxD, cellMinD, radius;
    private JTextField cellMaxBT, cellMinBT, cellMaxDT, cellMinDT, radiusT;

    public CellWindow(Frame frame, Cell cell){
        this.frame = frame;
        this.cell = cell;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setTitle("Cell");
        this.setLocationRelativeTo(this.frame);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.actionCell();
        this.pack();
        this.setVisible(true);
    }

    public int stringToInt(JTextField txt) {
        return Integer.valueOf(txt.getText());
    }

    public void actionCell() {
        this.setLayout(new GridLayout(6, 1));

        this.cellBorn = new JLabel("Neighboring cell born :");
        this.cellBornP = new JPanel();

        this.cellMaxBP = new JPanel();
        this.cellMaxB = new JLabel("Max :");
        this.cellMaxBT = new JTextField(Integer.toString(this.cell.getBornMaxNeighbors()), 4);
        this.cellMaxBP.add(this.cellMaxB);
        this.cellMaxBP.add(this.cellMaxBT);

        this.cellMinBP = new JPanel();
        this.cellMinB = new JLabel("Min :");
        this.cellMinBT = new JTextField(Integer.toString(this.cell.getBornMinNeighbors()), 4);
        this.cellMinBP.add(this.cellMinB);
        this.cellMinBP.add(this.cellMinBT);

        this.cellBornP.add(cellMaxBP);
        this.cellBornP.add(cellMinBP);

        this.cellDie = new JLabel("Neighboring cell dead :");
        this.cellDieP = new JPanel();

        this.cellMaxDP = new JPanel();
        this.cellMaxD = new JLabel("Max :");
        this.cellMaxDT = new JTextField(Integer.toString(this.cell.getDieMaxNeighbors()),4);
        this.cellMaxDP.add(this.cellMaxD);
        this.cellMaxDP.add(this.cellMaxDT);

        this.cellMinDP = new JPanel();
        this.cellMinD = new JLabel("Min :");
        this.cellMinDT = new JTextField(Integer.toString(this.cell.getDieMinNeighbors()),4);
        this.cellMinDP.add(this.cellMinD);
        this.cellMinDP.add(this.cellMinDT);

        this.cellDieP.add(cellMaxDP);
        this.cellDieP.add(cellMinDP);

        this.radiusP = new JPanel();
        this.radius = new JLabel("Radius :");
        this.radiusT = new JTextField(Integer.toString(this.cell.getRadius()), 4);
        this.radiusP.add(this.radius);
        this.radiusP.add(this.radiusT);
        
        this.add(this.cellBorn);
        this.add(this.cellBornP);
        this.add(this.cellDie);
        this.add(this.cellDieP);
        this.add(this.radiusP);
        
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
        int cellMinBVal = stringToInt(this.cellMinBT), cellMaxBVal = stringToInt(this.cellMaxBT), cellMinDVal = stringToInt(this.cellMinDT), cellMaxDVal = stringToInt(this.cellMaxDT), radiusVal =stringToInt(this.radiusT);
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

    public Cell getConfCell(){
        return this.cell;
    }
}