package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import br.com.rodrigopostai.moviefilter.filter.StringExactMatchMovieFilterMatcher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringExactMatchMovieFilterMatcherTest {

    @Test
    void shouldReturnTrueWhenTheTitleMachesExactly() {
        Movie movie = Movie.newBuilder().title("Titanic").build();
        MovieFilter filter = new MovieFilter().exactTitle("TITANIC");
        assertTrue(new StringExactMatchMovieFilterMatcher().matches(movie, filter));

        filter = new MovieFilter().exactTitle("titanic");
        assertTrue(new StringExactMatchMovieFilterMatcher().matches(movie, filter));

        filter = new MovieFilter().exactTitle("TiTaNiC");
        assertTrue(new StringExactMatchMovieFilterMatcher().matches(movie, filter));

        filter = new MovieFilter().exactTitle("Titanic");
        assertTrue(new StringExactMatchMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenNoFilterIsSet() {
        Movie movie = Movie.newBuilder().year(1955).build();
        MovieFilter filter = new MovieFilter();
        assertTrue(new StringExactMatchMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFilterAppliedAsString() {
        MovieFilter filter = new MovieFilter();
        filter.exactTitle("Titanic");
        assertEquals("Titanic", new StringExactMatchMovieFilterMatcher().getFilterApplied(filter).get());

        filter = new MovieFilter();
        assertEquals("", new StringExactMatchMovieFilterMatcher().getFilterApplied(filter).orElse(""));
    }
}
