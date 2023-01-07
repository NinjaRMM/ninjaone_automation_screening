package com.gbvbahia.ninjarmm;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class StepThreeSixDegreesOfSeparationApplication {

	public static void main(String[] args) {
	    log.info("Applications Step 3 Starts with args: {}", Arrays.toString(args));
	  
		SpringApplication.run(StepThreeSixDegreesOfSeparationApplication.class, args);
		
		log.info("Applications Step 3 Ends.");
	}

}
