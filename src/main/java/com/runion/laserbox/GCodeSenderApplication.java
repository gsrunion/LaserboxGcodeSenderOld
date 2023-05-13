package com.runion.laserbox;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class GCodeSenderApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(GCodeSenderApplication.class).headless(false).run(args);
	}
}
