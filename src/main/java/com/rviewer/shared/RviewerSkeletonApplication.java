package com.rviewer.shared;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.rviewer")
public class RviewerSkeletonApplication {

	public static void main(String[] args) {
		SpringApplication.run(RviewerSkeletonApplication.class, args);
	}

}
