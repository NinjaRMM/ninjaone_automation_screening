package com.ninja.one.degreeofseparations.repository;

import com.ninja.one.degreeofseparations.model.Movie;
import com.ninja.one.degreeofseparations.model.exception.ApiException;

import java.util.List;

public interface MovieRepository {
    List<Movie> getMovieFromDecade(Integer decade) throws ApiException;
    List<Movie> getMovies80Decade() throws ApiException;
}
