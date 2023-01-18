package com.ninjaone.step2.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ninjaone.step2.model.Movie;
import com.ninjaone.step2.service.MovieService;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(name = "parse", mixinStandardHelpOptions = true)
public class ParseCommand implements Callable<Integer> {

    private static final String CUSTOM_OUTPUT_PATH = "../data/%s.json";
    private static final String DEFAULT_OUTPUT_PATH = "../data/%ds-movies.json";
    private static final String MOVIES_JSON_PATH = "../data/movies.json";

    @Autowired
    private MovieService movieService;

    @Option(names = { "-d", "--decade" }, description = "The decade (or full-year decade) which movies you want to list - e.g. 70, 1910")
    private Integer decade;

    @Option(names = { "-o", "--output" }, description = "The desired name for the output file")
    private String outputFileName;

    @Override
    public Integer call() throws IOException {

        movieService.validateDecade(decade);

        List<Movie> allMovies = (List<Movie>) movieService.extractMoviesFromFile(new File(MOVIES_JSON_PATH));

        Integer fullYearDecade;

        if (decade < 100) {
            fullYearDecade = decade + 1900;
        } else {
            fullYearDecade = decade;
        }

        List<Movie> filteredMovies = (List<Movie>) movieService.filterMoviesByTimePeriod(allMovies, fullYearDecade, fullYearDecade + 9);

        File outputPath;

        if (outputFileName == null) {
            outputPath = new File(String.format(DEFAULT_OUTPUT_PATH, decade));
        } else {
            outputPath = new File(String.format(CUSTOM_OUTPUT_PATH, outputFileName));
        }

        movieService.writeMoviesToFile(filteredMovies, outputPath);

        return 0;
    }

}