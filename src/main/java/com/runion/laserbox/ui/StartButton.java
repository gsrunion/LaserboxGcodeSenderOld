package com.runion.laserbox.ui;

import com.runion.laserbox.events.DirectorySelectRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;


@Component
class StartButton extends JButton {

    public StartButton(ApplicationEventPublisher publisher) {
        setLabel("Start Monitoring");
        addActionListener(event -> publisher.publishEvent(new DirectorySelectRequested(this)));
    }
}
