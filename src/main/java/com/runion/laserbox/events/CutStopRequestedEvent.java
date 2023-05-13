package com.runion.laserbox.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class CutStopRequestedEvent extends ApplicationEvent {
    public CutStopRequestedEvent(Object source) {
        super(source);
    }
}
