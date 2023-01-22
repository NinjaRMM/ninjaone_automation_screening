package com.ninjaone.step2.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.ninjaone.step2.model.Movie;

/**
 * Service for filtering and de/serializing {@link Movie} collections.
 */
public interface MovieService {

    /**
     * Extracts objects from a JSON file and converts them into a {@link Collection} of {@link Movie}(s).
     */
    public Collection<Movie> extractMoviesFromFile(File pathName) throws IOException;

    /**
     * From <b>allMovies</b>, returns only the {@link Movie}(s) which year is within the interval of <b>startYear</b>
     * and <b>endYear</b> (inclusive for both).
     */
    public Collection<Movie> filterMoviesByTimePeriod(List<Movie> allMovies, Integer startYear, Integer endYear);

    /**
     * Converts a {@link Collection} of {@link Movie}(s) into a JSON file in the directory specified by <b>pathName</b>.
     */
    public void writeMoviesToFile(Collection<Movie> movies, File pathName) throws StreamWriteException, DatabindException, IOException;

    /**
     * Checks whether <b>decade</b> ends in "0" and is within the period from 1900 and 2010, which are the decades
     * comprised by "movies.json". It also accepts values like 30, 80, as they represent 1930, 1980, respectively.
     * <br>
     * It doesn't accept "0" or "10", as they could be decades either from the 1900s and from the 2000s, so for such
     * decades, please write the full-year decade - e.g. 1910, 2000.
     * <br>
     * Throws {@link IllegalArgumentException} should <b>decade</b> be non-compliant.
     */
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
