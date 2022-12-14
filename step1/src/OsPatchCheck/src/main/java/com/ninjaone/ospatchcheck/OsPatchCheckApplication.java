package com.ninjaone.ospatchcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OsPatchCheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsPatchCheckApplication.class, args);

		SoCheck.run();
	}
}
