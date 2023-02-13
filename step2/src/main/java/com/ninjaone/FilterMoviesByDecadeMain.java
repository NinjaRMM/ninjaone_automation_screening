package com.ninjaone;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FilterMoviesByDecadeMain {


    private static final String MOVIES_JSON_PATH = "../data/movies.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        if (args.length != 1)
            throw new RuntimeException("Please add 1 parameter only.");
        String decade = args[0];

        filterMoviesByDecade(decade);
    }

    private static void filterMoviesByDecade(String decade) {
        int decadeStart = Integer.parseInt("19" + decade);
        int decadeEnd = decadeStart + 9;
        List<Movie> movieList = readMovieListFromJsonFile();
        List<Movie> decadeFilteredMovies = movieList.stream()
                .filter(movie -> movie.getYear() >= decadeStart && movie.getYear() < decadeEnd)
                .collect(Collectors.toList());
        produceFilteredMoviesFile(decade, decadeFilteredMovies);
    }

    private static List<Movie> readMovieListFromJsonFile() {
        try {
            return objectMapper.readValue(new File(MOVIES_JSON_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Movie.class));
        } catch (IOException e) {
            System.out.println("Error when trying to read the movies.json file: " + e.getMessage());
            throw new RuntimeException("Error when trying to read the movies.json file: " + e.getMessage());
        }
    }

    private static void produceFilteredMoviesFile(String decade, List<Movie> decadeFilteredMovies) {
        try {
            objectMapper.writeValue(new File("../data/" + decade + "s-movies.json"), decadeFilteredMovies);
        } catch (IOException e) {
            System.out.println("Error when writing the " + decade + "s-movies.json file: " + e.getMessage());
        }
    }
}

