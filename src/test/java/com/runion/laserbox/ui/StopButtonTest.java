package com.runion.laserbox.ui;

import com.runion.laserbox.events.DirectoryWatchRequestedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StopButtonTest {
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Captor
    ArgumentCaptor<DirectoryWatchRequestedEvent> captor;

    @Test
    public void stopButton_hasLabelThatSasy_stopMonitoring() {
        StopButton stopButton = new StopButton(null);

        assertEquals("Stop Monitoring", stopButton.getText());
    }

    @Test
    public void stopButton_whenPressed_emitsDirectoryWatchStopRequestedEvent() {
        StopButton stopButton = new StopButton(eventPublisher);

        stopButton.doClick();

        verify(eventPublisher, times(1)).publishEvent(captor.capture());

        assertNotNull(captor.getValue());
    }

}