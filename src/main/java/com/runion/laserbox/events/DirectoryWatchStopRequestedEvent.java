package com.runion.laserbox.events;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class DirectoryWatchStopRequestedEvent extends ApplicationEvent {
    public DirectoryWatchStopRequestedEvent(Object source) {
        super(source);
    }
}
