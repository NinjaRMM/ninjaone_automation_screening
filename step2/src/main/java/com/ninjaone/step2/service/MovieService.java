package com.ninjaone.step2.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.ninjaone.step2.model.Movie;

public interface MovieService {

    public Collection<Movie> extractMoviesFromFile(File pathName) throws IOException;

    public Collection<Movie> filterMoviesByTimePeriod(List<Movie> allMovies, Integer startYear, Integer endYear);

    public void writeMoviesToFile(Collection<Movie> movies, File pathName) throws StreamWriteException, DatabindException, IOException;

    public default void validateDecade(Integer decade) {
        if (decade == null) {
            throw new IllegalArgumentException("Please provide the 'decade' parameter.");
        } else if (decade % 10 != 0) {
            throw new IllegalArgumentException("The parameter 'decade' must end in '0' - e.g. '80', '1910'.");
        } else if ((decade < 1900 || decade > 2010) && (decade < 0 || decade > 90)) {
            throw new IllegalArgumentException("The decade must be between '0' and '90' or '1900' and '2010'.");            
        } else if (decade == 0 || decade == 10) {
            throw new IllegalArgumentException("Ambiguous decade, as it could be either " + (1900 + decade) + " or " + (2000 + decade) + ". Please insert full year.");
        }
    }

}
