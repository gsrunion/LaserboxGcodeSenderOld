package com.runion.laserbox.ui;

import com.runion.laserbox.events.DirectoryWatchStopRequestedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;


@Component
class StopButton extends JButton {
    public StopButton(ApplicationEventPublisher publisher) {
        setLabel("Stop Monitoring");
        addActionListener(event -> publisher.publishEvent(new DirectoryWatchStopRequestedEvent(this)));
    }
}
