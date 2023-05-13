package com.runion.laserbox.transcoder;

import java.util.List;

import static java.lang.String.format;

class GCodeSerializer {
    public static String serialize(final List<GCodeLine> lines) {
        List<String> output = lines
                .stream()
                .map(GCodeSerializer::serialize)
                .toList();

        return String.join("\n", output);
    }

    public static String serialize(final GCodeLine line) {
        StringBuilder stringBuilder = new StringBuilder();

        if(line.command() != null) {
            stringBuilder.append(line.command());
            stringBuilder.append(" ");
        }

        if(line.x() != null) {
            stringBuilder.append("X");
            stringBuilder.append(format("%.3f", line.x()));
            stringBuilder.append(" ");
        }

        if(line.y() != null) {
            stringBuilder.append("Y");
            stringBuilder.append(format("%.3f", line.y()));
            stringBuilder.append(" ");
        }

        if(line.z() != null) {
            stringBuilder.append("Z");
            stringBuilder.append(format("%.3f", line.z()));
            stringBuilder.append(" ");
        }

        if(line.f() != null) {
            stringBuilder.append("F");
            stringBuilder.append(line.f());
            stringBuilder.append(" ");
        }

        if(line.s() != null) {
            stringBuilder.append("S");
            stringBuilder.append(line.s());
            stringBuilder.append(" ");
        }

        if(line.p() != null) {
            stringBuilder.append("P");
            stringBuilder.append(line.p());
            stringBuilder.append(" ");
        }

        if(line.comment() != null) {
            stringBuilder.append(";");
            stringBuilder.append(line.comment());
        }

        return stringBuilder.toString().strip();
    }
}
