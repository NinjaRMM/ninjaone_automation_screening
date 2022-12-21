package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.ExactYearMovieFilterMatcher;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExactYearMovieFilterMatcherTest {

    @Test
    void shouldReturnTrueWhenYearOfMovieMatchesTheFilter() {
        Movie movie = Movie.newBuilder().year(1963).build();
        MovieFilter filter = new MovieFilter().exactYear(1963);
        assertTrue(new ExactYearMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFalseWhenYearOfMovieDoesntMatchesFilter() {
        Movie movie = Movie.newBuilder().year(2012).build();
        MovieFilter filter = new MovieFilter().exactYear(2013);
        assertFalse(new ExactYearMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenNoFilterIsSet() {
        Movie movie = Movie.newBuilder().year(1955).build();
        MovieFilter filter = new MovieFilter();
        assertTrue(new ExactYearMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFilterAppliedAsString() {
        MovieFilter filter = new MovieFilter();
        filter.exactYear(1920);
        assertEquals("1920", new ExactYearMovieFilterMatcher().getFilterApplied(filter).get());

        filter = new MovieFilter();
        assertEquals("", new ExactYearMovieFilterMatcher().getFilterApplied(filter).orElse(""));
    }

}
