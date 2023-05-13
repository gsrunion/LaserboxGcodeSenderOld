package com.runion.laserbox.ui;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
class Ui extends JFrame {
    public Ui(TopPanel topPanel, LowerPanel lowerPanel) {
        setTitle("Laserbox Gcode Sender");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(topPanel);
        add(lowerPanel);
        setVisible(true);
    }
}
