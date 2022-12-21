package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.DecadeMovieFilterMatcher;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DecadeMovieFilterMatcherTest {

    @Test
    void shouldReturnTrueWhenTheMoviesMatchesTheDecadeOnFilter() {
        Movie movie = Movie.newBuilder().year(1963).build();
        MovieFilter filter = new MovieFilter().decade(1960);
        assertTrue(new DecadeMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFalseWhenTheMoviesDoesNotMatchTheDecadeOnFilter() {
        Movie movie = Movie.newBuilder().year(1963).build();
        MovieFilter filter = new MovieFilter().decade(1970);
        assertFalse(new DecadeMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenNoFilterIsSet() {
        Movie movie = Movie.newBuilder().year(1955).build();
        MovieFilter filter = new MovieFilter();
        assertTrue(new DecadeMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFilterAppliedAsString() {
        MovieFilter filter = new MovieFilter();
        filter.decade(1920);
        assertEquals("1920", new DecadeMovieFilterMatcher().getFilterApplied(filter).get());

        filter = new MovieFilter();
        assertEquals("", new DecadeMovieFilterMatcher().getFilterApplied(filter).orElse(""));
    }
}
