package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import br.com.rodrigopostai.moviefilter.filter.StringContainsMovieFilterMatcher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringContainsMatchMovieFilterMatcherTest {

    @Test
    void shouldReturnTrueWhenTheTitleContainsTheFilter() {
        Movie movie = Movie.newBuilder().title("Horror stories").build();
        MovieFilter filter = new MovieFilter().titleContains("stories");
        assertTrue(new StringContainsMovieFilterMatcher().matches(movie, filter));

        filter = new MovieFilter().titleContains("horror");
        assertTrue(new StringContainsMovieFilterMatcher().matches(movie, filter));

        filter = new MovieFilter().titleContains("stori");
        assertTrue(new StringContainsMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenNoFilterIsSet() {
        Movie movie = Movie.newBuilder().year(1955).build();
        MovieFilter filter = new MovieFilter();
        assertTrue(new StringContainsMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFilterAppliedAsString() {
        MovieFilter filter = new MovieFilter();
        filter.titleContains("Bar");
        assertEquals("Bar", new StringContainsMovieFilterMatcher().getFilterApplied(filter).get());

        filter = new MovieFilter();
        assertEquals("", new StringContainsMovieFilterMatcher().getFilterApplied(filter).orElse(""));
    }
}
