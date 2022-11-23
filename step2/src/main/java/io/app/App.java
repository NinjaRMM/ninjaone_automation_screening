package io.app;

import io.automation.MoviesFilter;

public class App {
    public static void main(String[] args) {
        MoviesFilter moviesFilter = new MoviesFilter("movies.json");
        moviesFilter.buildFileMoviesByDecade(Integer.parseInt(args[0]), args[1]);
    }

}
