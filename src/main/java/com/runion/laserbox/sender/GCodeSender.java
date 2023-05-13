package com.runion.laserbox.sender;

import com.runion.laserbox.events.CutStopRequestedEvent;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Slf4j
public class GCodeSender {
    @EventListener
    public void onCutStop(CutStopRequestedEvent event) {
        Unirest.post("http://201.234.3.1:8080/cnc/data?action=stop").asString();
    }


    public void uploadFile(Path file) {
        Unirest.post("http://201.234.3.1:8080/cnc/data?action=upload&zip=false&id=-2")
                .header("Content-Type", "application/octet-stream")
                .field("file", file.toFile())
                .asString();
        log.debug("Sent file {} to Laserbox", file);
    }
}
