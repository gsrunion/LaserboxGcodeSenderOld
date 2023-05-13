package com.runion.laserbox.transcoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class GCodeParser {
    private static final String patternString = "^(((?<command>[G,M]\\d+)|X(?<x>-?\\d+\\.?\\d*)|S(?<s>-?\\d+\\.?\\d*)|P(?<p>-?\\d+\\.?\\d*)|Y(?<y>-?\\d+\\.?\\d*)|Z(?<z>-?\\d+\\.?\\d*)|F(?<f>-?\\d+\\.?\\d*))\\s*)+";
    private static final Pattern pattern = Pattern.compile(patternString);

    public static List<GCodeLine> parseFile(final String s) {
        return Arrays
                .stream(s.split("\n"))
                .map(GCodeParser::parseLine)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public static Optional<GCodeLine> parseLine(final String raw) {
        String tidy = raw.strip();
        tidy = tidy.replace("G00 G17 G40 G21 G54", "G0");

        if(tidy.startsWith(";")) {
            return Optional.of(new GCodeLine().comment(tidy.substring(1)));
        }

        if(tidy.isEmpty()) {
            return Optional.of(new GCodeLine());
        }

        Matcher matcher = pattern.matcher(tidy.strip());
        if(matcher.find()) {
            String comment = null;

            if(tidy.contains(";")) {
                comment = tidy.substring(tidy.indexOf(";") + 1);
            }

            GCodeLine line = new GCodeLine()
                    .command(matcher.group("command"))
                    .x(safeParseDouble(matcher.group("x")))
                    .y(safeParseDouble(matcher.group("y")))
                    .z(safeParseDouble(matcher.group("z")))
                    .f(safeParseInteger(matcher.group("f")))
                    .s(safeParseInteger(matcher.group("s")))
                    .p(safeParseInteger(matcher.group("p")))
                    .comment(comment);

            return Optional.of(line);
        }

        return Optional.empty();
    }

    private static Double safeParseDouble(String in) {
        if(in == null) {
            return null;
        }
        return Double.parseDouble(in);
    }

    private static Integer safeParseInteger(String in) {
        if(in == null) {
            return null;
        }
        return Integer.parseInt(in);
    }
}
