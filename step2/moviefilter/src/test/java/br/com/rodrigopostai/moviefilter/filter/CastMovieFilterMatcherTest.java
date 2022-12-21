package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.CastMovieFilterMatcher;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CastMovieFilterMatcherTest {

    @Test
    void shouldReturnTrueWhenCastIsFoundInTheMovie() {
        Movie movie = Movie.newBuilder().year(1963).cast("Rodrigo Postai").cast("Fernanda da Silva").build();
        MovieFilter filter = new MovieFilter().cast("Rodrigo Postai");
        assertTrue(new CastMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenSeveralCastsAreFoundInTheMovie() {
        Movie movie = Movie.newBuilder().year(1963).cast("Rodrigo Postai").cast("Fernanda da Silva")
                .cast("Maria da Silva")
                .build();
        MovieFilter filter = new MovieFilter().cast("Rodrigo Postai").cast("Fernanda da Silva");
        assertTrue(new CastMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFalseWhenCastIsNotFoundInTheMovie() {
        Movie movie = Movie.newBuilder().year(1963).cast("Rodrigo Postai").cast("Fernanda da Silva")
                .cast("Maria da Silva")
                .build();
        MovieFilter filter = new MovieFilter().cast("Jose da Silva");
        assertFalse(new CastMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenNoFilter() {
        Movie movie = Movie.newBuilder().year(1963).cast("Rodrigo Postai").cast("Fernanda da Silva")
                .cast("Maria da Silva")
                .build();
        MovieFilter filter = new MovieFilter().cast("Jose da Silva");
        assertFalse(new CastMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenNoFilterIsSet() {
        Movie movie = Movie.newBuilder().year(1955).build();
        MovieFilter filter = new MovieFilter();
        assertTrue(new CastMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFilterAppliedAsString() {
        MovieFilter filter = new MovieFilter();
        filter.cast("Rodrigo").cast("Fernanda");
        assertEquals("Fernanda-Rodrigo", new CastMovieFilterMatcher().getFilterApplied(filter).get());

        filter = new MovieFilter();
        assertEquals("", new CastMovieFilterMatcher().getFilterApplied(filter).orElse(""));
    }
}
