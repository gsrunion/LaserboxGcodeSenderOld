package com.runion.laserbox.ui;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
class LowerPanel extends JPanel {
    public LowerPanel(StartButton startButton, StopButton stopButton, EndButton endButton) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(startButton);
        add(stopButton);
        add(endButton);
    }
}
