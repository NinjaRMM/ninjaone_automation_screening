package io.automation.service;

import io.automation.entities.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MoviesManager {
    private Map<Integer, Integer> hashMap;


    private List<Movie> movies;

    public MoviesManager(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMoviesByDecade(int decade) {
        List<Movie> filteredMovies = movies
                .stream()
                .filter(movie ->
                        movie.getYear() >= getMinYearToFilter(decade) && movie.getYear() <= getMaxYearToFilter(decade))
                .collect(Collectors.toList());
        return filteredMovies;

    }

    private int getMinYearToFilter(int decade) {
        int maxDecade = 90;
        int duplicateDecade = 10;
        int nineteenHundreds = 1900;
        int twentyOhs = 2000;

        if (decade > maxDecade) {
            return decade;
        }
        if (decade <= duplicateDecade) {
            return twentyOhs + decade;
        }
        return nineteenHundreds + decade;

    }

    private int getMaxYearToFilter(int decade) {
        int maxYearOfDecade = 9;
        return getMinYearToFilter(decade) + maxYearOfDecade;
    }
}
