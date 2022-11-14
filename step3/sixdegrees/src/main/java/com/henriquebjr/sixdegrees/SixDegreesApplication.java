package com.henriquebjr.sixdegrees;

import com.henriquebjr.sixdegrees.model.ActorNode;
import com.henriquebjr.sixdegrees.model.MovieHistory;
import com.henriquebjr.sixdegrees.service.MovieHistoryService;
import com.henriquebjr.sixdegrees.service.SixDegreesGraphService;
import com.henriquebjr.sixdegrees.service.SixDegreesReportService;
import com.henriquebjr.sixdegrees.service.exception.ActorDestinyNotFoundException;
import com.henriquebjr.sixdegrees.service.exception.ActorOriginNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SixDegreesApplication implements CommandLineRunner {

	public static final String FILE_PATH = "data/movies.json";
	public static final String DEFAULT_EMBEDDED_DATA_MOVIES_JSON = "src/main/resources/movies.json";

	@Autowired
	private MovieHistoryService movieHistoryService;
	@Autowired
	private SixDegreesGraphService sixDegreesGraphService;
	@Autowired
	private SixDegreesReportService sixDegreesReportService;

	private static Logger LOG = LoggerFactory.getLogger(SixDegreesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SixDegreesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(args.length < 1) {
			System.out.println("Please inform a decade.");
			System.out.println("OPTIONAL: You may inform a file and two actor to calculate six degrees of separation.");
			System.out.println("Ex.: java -jar sixdegrees.jar 80 movies.json \"Tom Cruise\" \"Sylvester Stallone\" ");
			return;
		}

		File moviesFile = new File(FILE_PATH);
		String actor1 = null, actor2 = null;

		if(args.length > 1 && new File(args[1]).exists()) {
			moviesFile = new File(args[1]);

			if(args.length > 2) {
				actor1 = args[2];
			}

			if(args.length > 3) {
				actor2 = args[3];
			}
		} else if(args.length > 1) {
			actor1 = args[1];

			if(args.length > 2) {
				actor2 = args[2];
			}
		}

		if(!moviesFile.exists()) {
			moviesFile = new File(DEFAULT_EMBEDDED_DATA_MOVIES_JSON);
		}

		MovieHistory history = movieHistoryService.filterByDecade(moviesFile, Integer.parseInt(args[0]));

		if(actor1 != null) {
			if(actor2 == null) {
				actor2 = "Kevin Bacon";
			}

			try {
				ActorNode result = sixDegreesGraphService.calculate(history, actor1, actor2);
				sixDegreesReportService.report(result);
			} catch (ActorOriginNotFoundException e) {
				System.out.println(String.format("%s did not star in a movie in the data provided.", actor1));
			} catch (ActorDestinyNotFoundException e) {
				System.out.println(String.format("%s did not star in a movie in the data provided.", actor2));
			}
		}
	}
}
