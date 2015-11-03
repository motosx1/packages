package gui;

import javax.swing.*;

/**
 * Created by Bartosz on 2014-12-11.
 */
public class MainForm extends JFrame {
    private DrawPanel drawPanel;

    public MainForm() {
        initUI();
    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }

    public final void initUI() {


        drawPanel = new DrawPanel();


        add(drawPanel);

        setSize(600, 600);
        setTitle("Zarządzanie paczkami");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);


    }


}