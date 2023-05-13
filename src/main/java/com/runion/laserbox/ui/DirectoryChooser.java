package com.runion.laserbox.ui;

import com.runion.laserbox.events.DirectorySelectRequested;
import com.runion.laserbox.events.DirectoryWatchRequestedEvent;
import com.runion.laserbox.events.DirectoryWatchStopRequestedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Optional;

@Component
class DirectoryChooser {
    private final ApplicationEventPublisher publisher;

    DirectoryChooser(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void onApplicationEvent(DirectorySelectRequested event) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            publisher.publishEvent(new DirectoryWatchRequestedEvent(this, fileChooser.getSelectedFile().toPath()));
        } else {
            publisher.publishEvent(new DirectoryWatchStopRequestedEvent(this));
        }
    }
}
