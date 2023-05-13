package com.runion.laserbox.ui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
class TopPanel extends JPanel {

    private static final String aboutText = "This application monitors a provided directory for saved gcode files as " +
            "output by LightBurn, prepares those files for use with the XTool Laserbox ('P1') C02 laser cutters, and " +
            "uploads the gcode to the machine at a provided IP address.";

    private static final String howToText = "For directions on how to configure LightBurn for use the Laserbox visit " +
            "https://community.xtool.com/#/making/detail/tips/662";



    public TopPanel(DirectoryLabel directoryLabel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(createTextPane(aboutText + "\n\n\n" + howToText));
        add(directoryLabel);
    }

    private JTextPane createTextPane(String text) {
        JTextPane pane = new JTextPane();
        pane.setContentType("text/plain");
        pane.setText(text);
        pane.setMargin(new Insets(20, 20, 20, 20));
        pane.setOpaque(false);
        pane.setEditable(false);
        return pane;
    }
}
