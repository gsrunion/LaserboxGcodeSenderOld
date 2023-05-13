package com.runion.laserbox.monitors;

import com.runion.laserbox.sender.GCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
class UploadFileMonitor extends GcodeFileAddedMonitor {
    public static final String OUTPUT_DIR = "uploaded";
    public static final String INPUT_DIR = "/processed";

    private final GCodeSender GCodeSender;

    public UploadFileMonitor(GCodeSender GCodeSender) {
        this.GCodeSender = GCodeSender;
    }

    @Override
    protected void handleAddedFile(Path file) throws Exception {
        GCodeSender.uploadFile(file);
        FileUtils.copyToDirectory(file.toFile(), Paths.get(file.getParent().getParent().toString(), OUTPUT_DIR).toFile());
    }

    @Override
    protected String getInputDir() {
        return INPUT_DIR;
    }

    @Override
    protected String getOutputDir() {
        return OUTPUT_DIR;
    }
}
