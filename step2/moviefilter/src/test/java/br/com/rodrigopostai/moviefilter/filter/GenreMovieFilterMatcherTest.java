package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.GenreMovieFilterMatcher;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenreMovieFilterMatcherTest {

    @Test
    void shouldReturnTrueWhenGenreIsFoundInTheMovie() {
        Movie movie = Movie.newBuilder().year(1963).genre("Documentary").build();
        MovieFilter filter = new MovieFilter().genre("Documentary");
        assertTrue(new GenreMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenSeveralGenresAreFoundInTheMovie() {
        Movie movie = Movie.newBuilder().year(1963).genre("Documentary").genre("Short").genre("Comedy").build();
        MovieFilter filter = new MovieFilter().genre("Short").genre("Comedy");
        assertTrue(new GenreMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnTrueWhenNoFilterIsSet() {
        Movie movie = Movie.newBuilder().year(1955).build();
        MovieFilter filter = new MovieFilter();
        assertTrue(new GenreMovieFilterMatcher().matches(movie, filter));
    }

    @Test
    void shouldReturnFilterAppliedAsString() {
        MovieFilter filter = new MovieFilter();
        filter.genre("Comedy").genre("Drama");
        assertEquals("Comedy-Drama", new GenreMovieFilterMatcher().getFilterApplied(filter).get());

        filter = new MovieFilter();
        assertEquals("", new GenreMovieFilterMatcher().getFilterApplied(filter).orElse(""));
    }
}
