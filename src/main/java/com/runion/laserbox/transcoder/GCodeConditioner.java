package com.runion.laserbox.transcoder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@Slf4j
public class GCodeConditioner {
    public void prepareFile(Path inputFile, Path outputFile) throws IOException {
        String input = Files.readString(inputFile);
        List<GCodeLine> parsed = GCodeParser.parseFile(input);
        List<GCodeLine> prepared = new GCodeTranscoder().condition(parsed);
        Files.writeString(outputFile, GCodeSerializer.serialize(prepared));
        log.debug("Saved prepared gcode file to {}", outputFile);
    }
}
