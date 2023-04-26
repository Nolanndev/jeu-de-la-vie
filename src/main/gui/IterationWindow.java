package main.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IterationWindow extends JDialog implements ActionListener{

    private JFrame frame;
    private int timeItVal, startItVal, numberItVal;
    private JLabel startIt, iteration, timeIt, numberIt;
    private JPanel startItP, iterationP, timeItP, numberItP;
    private JTextField startItT, numberItT, timeItT;
    private JCheckBox infinite, finite;
    private JButton confimIteration;
    private boolean type, confirm = false;
    
    public IterationWindow(JFrame frame, int timeItVal, int startItVal, int numberItVal){
        this.frame = frame;
        this.timeItVal = timeItVal;
        this.startItVal =startItVal;
        this.numberItVal = numberItVal;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setTitle("Iteration");
        this.setLocationRelativeTo(this.frame);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setting();
        this.pack();
        this.setVisible(true);
    }

    public int stringToInt(JTextField txt) {
        return Integer.valueOf(txt.getText());
    }

    public void setting() {
        this.setLayout(new GridLayout(4, 1));
        
        this.iterationP = new JPanel();
        this.iteration = new JLabel("Type of iteration :");
        this.infinite = new JCheckBox("Infinite");
        this.infinite.addActionListener(this);
        this.finite = new JCheckBox("Finite");
        this.finite.addActionListener(this);
        this.infinite.setSelected(true);
        this.type = false;
        this.iterationP.add(this.iteration);
        this.iterationP.add(this.infinite);
        this.iterationP.add(this.finite);

        this.timeItP = new JPanel();
        this.timeIt = new JLabel("Time between iteration (ms) :");
        this.timeItT = new JTextField(Integer.toString(timeItVal), 4);
        this.timeItP.add(this.timeIt);
        this.timeItP.add(this.timeItT);

        this.startItP = new JPanel();
        this.startIt = new JLabel("Start to iteration :");
        this.startItT = new JTextField(Integer.toString(startItVal),4);
        this.startItP.add(this.startIt);
        this.startItP.add(this.startItT);

        this.numberItP = new JPanel();
        this.numberIt = new JLabel("Number of iteration :");
        this.numberItT = new JTextField(Integer.toString(numberItVal), 4);
        this.numberItP.add(this.numberIt);
        this.numberItP.add(this.numberItT);

        this.confimIteration = new JButton("Confirm");
        this.confimIteration.addActionListener(this);

        this.add(this.iterationP);
        this.add(this.timeItP);
        this.add(this.startItP);
        this.add(this.confimIteration);
    }

    public void actionComfirmIteration() {
        this.timeItVal = stringToInt(this.timeItT);
        this.numberItVal = stringToInt(this.numberItT);
        this.startItVal = stringToInt(this.startItT);
        if (this.timeItVal <= 0 || this.numberItVal <= 0 || this.startItVal < 0) {
            JOptionPane.showMessageDialog(this.frame, "Missing information");
        } else {
            this.confirm = true;
            this.setVisible(false);
            this.dispose();
        }
    }

    public boolean getIteration(){
        return this.type;
    }

    public boolean getConf(){
        return this.confirm;
    }

    public int getTime(){
        return this.timeItVal;
    } 

    public int getNumber(){
        return this.numberItVal;
    } 

    public int getStart(){
        return this.startItVal;
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.finite) {
            this.type = true;
            this.add(this.numberItP);
            this.setLayout(new GridLayout(5, 1));
            this.infinite.setSelected(false);
            this.add(this.confimIteration);
            this.pack();
        }
        if (e.getSource() == this.infinite) {
            this.type = false;
            this.remove(this.numberItP);
            this.setLayout(new GridLayout(4, 1));
            this.finite.setSelected(false);
            this.add(this.confimIteration);
            this.pack();
        }
        if (e.getSource() == this.confimIteration) {
            this.actionComfirmIteration();
        }
    }

}
