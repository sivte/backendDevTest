package com.backendtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application entry point.
 * Scans all modules under com.backendtest package.
 */
@SpringBootApplication
public class BackendTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendTestApplication.class, args);
	}

}
