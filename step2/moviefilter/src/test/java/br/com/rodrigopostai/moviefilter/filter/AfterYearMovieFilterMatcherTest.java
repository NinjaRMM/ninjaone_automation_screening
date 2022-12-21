package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.AfterYearMovieFilterMatcher;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AfterYearMovieFilterMatcherTest {

    @Test
    void shouldReturnTrueWhenYearOfMovieIsAfterTheFilter() {
        Movie movie = Movie.newBuilder().year(1963).build();
        MovieFilter filter = new MovieFilter().afterYear(1955);
        assertTrue(new AfterYearMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFalseWhenYearOfMovieIsBeforeTheFilter() {
        Movie movie = Movie.newBuilder().year(1955).build();
        MovieFilter filter = new MovieFilter().afterYear(1985);
        assertFalse(new AfterYearMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenNoFilterIsSet() {
        Movie movie = Movie.newBuilder().year(1955).build();
        MovieFilter filter = new MovieFilter();
        assertTrue(new AfterYearMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFilterAppliedAsString() {
        MovieFilter filter = new MovieFilter();
        filter.afterYear(1955);
        assertEquals("1955", new AfterYearMovieFilterMatcher().getFilterApplied(filter).get());

        filter = new MovieFilter();
        assertEquals("", new AfterYearMovieFilterMatcher().getFilterApplied(filter).orElse(""));
    }

}
