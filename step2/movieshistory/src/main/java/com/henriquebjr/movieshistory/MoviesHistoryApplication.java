package com.henriquebjr.movieshistory;

import com.henriquebjr.movieshistory.service.MovieHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class MoviesHistoryApplication implements CommandLineRunner {

	public static final String FILE_PATH = "data/movies.json";
	public static final String DEFAULT_EMBEDDED_DATA_MOVIES_JSON = "src/main/resources/movies.json";

	@Autowired
	private MovieHistoryService movieHistoryService;

	private static Logger LOG = LoggerFactory.getLogger(MoviesHistoryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MoviesHistoryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(args.length < 1) {
			System.out.println("Please inform decade.");
			return;
		}


		File moviesFile = new File(FILE_PATH);

		if(!moviesFile.exists()) {
			moviesFile = new File(DEFAULT_EMBEDDED_DATA_MOVIES_JSON);
		}

		movieHistoryService.filterByDecade(moviesFile, Integer.parseInt(args[0]));
	}
}
