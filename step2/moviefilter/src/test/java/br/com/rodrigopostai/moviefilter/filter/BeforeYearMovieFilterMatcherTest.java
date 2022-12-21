package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.BeforeYearMovieFilterMatcher;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeforeYearMovieFilterMatcherTest {

    @Test
    void shouldReturnTrueWhenYearOfMovieIsBeforeTheFilter() {
        Movie movie = Movie.newBuilder().year(1963).build();
        MovieFilter filter = new MovieFilter().beforeYear(1980);
        assertTrue(new BeforeYearMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenYearOfMovieMatchesFilter() {
        Movie movie = Movie.newBuilder().year(2012).build();
        MovieFilter filter = new MovieFilter().beforeYear(2012);
        assertTrue(new BeforeYearMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenNoFilterIsSet() {
        Movie movie = Movie.newBuilder().year(1955).build();
        MovieFilter filter = new MovieFilter();
        assertTrue(new BeforeYearMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFilterAppliedAsString() {
        MovieFilter filter = new MovieFilter();
        filter.beforeYear(1955);
        assertEquals("1955", new BeforeYearMovieFilterMatcher().getFilterApplied(filter).get());

        filter = new MovieFilter();
        assertEquals("", new BeforeYearMovieFilterMatcher().getFilterApplied(filter).orElse(""));
    }
}
