package com.ninjaone;

import com.ninjaone.model.Movies;
import com.ninjaone.service.MoviesService;

import java.util.List;

/**
 *
 */
public class Main {

    public static void main (String[] args) {
        MoviesService moviesService = new MoviesService();
        moviesService.setSourceFilename("movies.json");
        moviesService.setDecade(Integer.parseInt(args[0]));
        List<Movies> moviesByDecade = moviesService.getMoviesByDecade(moviesService.getDecade(),
                moviesService.getMoviesByFilename(moviesService.getSourceFilename()));
        moviesService.generateMoviesFile(moviesByDecade, args[1]);
    }
}
