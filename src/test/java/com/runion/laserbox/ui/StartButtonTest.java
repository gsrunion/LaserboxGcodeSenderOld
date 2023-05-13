package com.runion.laserbox.ui;

import com.runion.laserbox.events.DirectorySelectRequested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StartButtonTest {
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Captor
    ArgumentCaptor<DirectorySelectRequested> captor;

    @Test
    public void startButton_hasLabelThatSays_startMonitoring() {
        StartButton stopButton = new StartButton(null);

        assertEquals("Start Monitoring", stopButton.getText());
    }

    @Test
    public void startButton_whenPressed_emitsDirectorySelectRequestedEvent() {
        StartButton button = new StartButton(eventPublisher);

        button.doClick();

        verify(eventPublisher, times(1)).publishEvent(captor.capture());

        assertNotNull(captor.getValue());
    }

}