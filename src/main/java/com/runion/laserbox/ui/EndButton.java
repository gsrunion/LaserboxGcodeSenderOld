package com.runion.laserbox.ui;

import com.runion.laserbox.events.CutStopRequestedEvent;
import com.runion.laserbox.events.DirectoryWatchStopRequestedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;


@Component
class EndButton extends JButton {
    public EndButton(ApplicationEventPublisher publisher) {
        setLabel("Stop Cut");
        addActionListener(event -> publisher.publishEvent(new CutStopRequestedEvent(this)));
    }
}
