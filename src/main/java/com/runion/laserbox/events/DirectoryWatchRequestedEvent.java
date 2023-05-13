package com.runion.laserbox.events;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.nio.file.Path;

@Getter
public class DirectoryWatchRequestedEvent extends ApplicationEvent {
    private final Path file;

    public DirectoryWatchRequestedEvent(Object source, Path file) {
        super(source);
        this.file = file;
    }
}
