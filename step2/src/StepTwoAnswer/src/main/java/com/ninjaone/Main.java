package com.ninjaone;

import com.ninjaone.service.MoviesService;

public class Main {

    public static void main (String[] args) {

        MoviesService moviesService = new MoviesService();

        moviesService.setSourceFilename("movies.json");
        moviesService.setDecade(Integer.parseInt(args[0]));
        moviesService.generateMoviesFile(moviesService.getMoviesByDecade(moviesService.getDecade(),
                moviesService.getMoviesByFilename(moviesService.getSourceFilename())), args[1]);

    }
}