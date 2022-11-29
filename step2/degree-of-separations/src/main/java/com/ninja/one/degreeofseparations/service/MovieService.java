package com.ninja.one.degreeofseparations.service;

import com.ninja.one.degreeofseparations.model.Movie;
import com.ninja.one.degreeofseparations.model.exception.ApiException;

import java.util.List;

public interface MovieService {
    List<Movie> extractMovieFromDecade(Integer decade) throws ApiException;
    String calculateDegreeOfSeparation(String actor1, String actor2) throws ApiException;
}
