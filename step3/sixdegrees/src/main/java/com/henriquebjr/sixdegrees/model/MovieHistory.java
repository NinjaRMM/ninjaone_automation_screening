package com.henriquebjr.sixdegrees.model;

import java.util.ArrayList;
import java.util.List;

public class MovieHistory {

    private List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
