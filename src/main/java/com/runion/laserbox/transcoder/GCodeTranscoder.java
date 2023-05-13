package com.runion.laserbox.transcoder;

import java.util.List;


class GCodeTranscoder {
    double x = 0.0;
    double y = 0.0;
    boolean absoluteMode = true;

    public GCodeLine condition(GCodeLine line) {
        if(line.command() == null) {
            return line;
        }

        if(line.command().startsWith("G90")) {
            absoluteMode = true;
            return new GCodeLine();
        }

        if(line.command().startsWith("G91")) {
            absoluteMode = false;
            return new GCodeLine();
        }

        if(line.x() != null) {
            x = line.x() + (absoluteMode ? 0 : x);
            line.x(negateIfNecessary(x));
        }

        if(line.y() != null) {
            y = line.y() + (absoluteMode ? 0 : y);
            line.y(negateIfNecessary(y));
        }

        return line;
    }


    public List<GCodeLine> condition(final List<GCodeLine> lines) {
        return lines
                .stream()
                .map(this::condition)
                .toList();
    }

    private static Double negateIfNecessary(Double input) {
        if(input == 0.00) {
            return input;
        }
        return -input;
    }
}
