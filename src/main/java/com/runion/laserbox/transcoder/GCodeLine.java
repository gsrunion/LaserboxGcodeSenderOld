package com.runion.laserbox.transcoder;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
class GCodeLine {
    private String command;
    private Double x;
    private Double y;
    private Double z;
    private Integer f;
    private Integer s;
    private Integer p;
    private String comment;
}
