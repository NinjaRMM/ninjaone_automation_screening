package com.ninjaone.step3.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.ninjaone.step3.model.Movie;

public interface MovieService {

    public Collection<Movie> extractMoviesFromFile(File pathName) throws IOException;

    public Collection<Movie> filterMoviesByTimePeriod(List<Movie> allMovies, Integer startYear, Integer endYear);

    public void writeMoviesToFile(Collection<Movie> movies, File pathName) throws StreamWriteException, DatabindException, IOException;

}
