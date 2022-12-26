package br.com.rodrigopostai.sixdegrees;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SixDegreesApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(SixDegreesApplication.class, args);
	}

    @Override
    public void run(ApplicationArguments arguments) throws Exception {

    }

}
