package main.gui;

import main.utils.Tuple;
import javax.swing.*;

public class Window {

    JFrame window;

    public Window(String title, Tuple windowSize) {

        this.window = new JFrame();
        this.window.setTitle(title);
        this.window.setSize(windowSize.getValue1(),windowSize.getValue2());
        this.window.setVisible(true);
        this.window.setResizable(false);

    }

}
