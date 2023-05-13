package com.runion.laserbox.monitors;

import com.runion.laserbox.events.DirectoryWatchRequestedEvent;
import com.runion.laserbox.events.DirectoryWatchStopRequestedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

@Slf4j
abstract class GcodeFileAddedMonitor {
    private FileAlterationMonitor monitor;
    private Path watchedDir;
    FileAlterationObserver observer;

    protected abstract void handleAddedFile(Path file) throws Exception;
    protected abstract String getInputDir();
    protected abstract String getOutputDir();

    protected Path getWatchedDir() {
        return watchedDir;
    }

    private final FileAlterationListener listener = new FileAlterationListenerAdaptor() {
        @Override
        public void onFileCreate(File file) {
            if(!FilenameUtils.getExtension(file.getName()).equals("gcode")) {
                log.trace("Found non gcode file {}. Skipping", file);
                return;
            }

            if(!file.getParent().equals(watchedDir.toString())) {
                log.trace("Skipping file from subdirectory {}", file);
                return;
            }

            log.debug("Handling added file {}", file);

            try {
                handleAddedFile(file.toPath());
            } catch (Exception ex) {
                log.error(format("Error while handling file %s", file), ex);
            }
        }
    };

    @EventListener
    public void onWatchRequested(DirectoryWatchRequestedEvent event) throws Exception {
        if(monitor != null) {
            log.trace("Existing monitor found. Stopping");
            onWatchStopped(null);
        }

        watchedDir = Paths.get(event.getFile().toString(), getInputDir());
        watchedDir.toFile().mkdirs();

        Paths.get(event.getFile().toString(), getOutputDir()).toFile().mkdirs();

        observer = new FileAlterationObserver(watchedDir.toFile());
        monitor = new FileAlterationMonitor(500);
        observer.addListener(listener);
        monitor.addObserver(observer);
        log.debug("Watching directory {} for file additions", watchedDir);
        CompletableFuture.runAsync(() -> {
        try {
            monitor.start();
        } catch (Exception e) {
            log.error("", e);
        }});
    }

    @EventListener
    public void onWatchStopped(DirectoryWatchStopRequestedEvent event) throws Exception {
        CompletableFuture.runAsync(() -> {
            try {
                monitor.stop();
            } catch (Exception e) {
                log.error("", e);
            }
        });
        log.trace("Stopped watching directory {} for file additions", watchedDir);
    }
}
