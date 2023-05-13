package com.runion.laserbox.ui;

import com.runion.laserbox.events.CutStopRequestedEvent;
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
class EndButtonTest {
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Captor
    ArgumentCaptor<CutStopRequestedEvent> captor;

    @Test
    public void endButton_hasLabelThatSays_stopCut() {
        EndButton button = new EndButton(null);

        assertEquals("Stop Cut", button.getText());
    }

    @Test
    public void endButton_whenPressed_emitsDirectorySelectRequestedEvent() {
        EndButton button = new EndButton(eventPublisher);

        button.doClick();

        verify(eventPublisher, times(1)).publishEvent(captor.capture());

        assertNotNull(captor.getValue());
    }

}