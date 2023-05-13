package com.runion.laserbox.monitors;

import com.runion.laserbox.transcoder.GCodeConditioner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
class InputFileMonitor extends GcodeFileAddedMonitor {
    public static final String OUTPUT_DIR = "processed";
    public static final String INPUT_DIR = "/";

    private final GCodeConditioner codeConditioner;

    public InputFileMonitor(GCodeConditioner codeConditioner) {
        this.codeConditioner = codeConditioner;
    }

    @Override
    protected void handleAddedFile(Path file) throws Exception {
        codeConditioner.prepareFile(file, Paths.get(getWatchedDir().toString(), OUTPUT_DIR, file.getFileName().toString()));
    }

    @Override
    protected String getOutputDir() {
        return OUTPUT_DIR;
    }

    @Override
    protected String getInputDir() {
        return INPUT_DIR;
    }
}
