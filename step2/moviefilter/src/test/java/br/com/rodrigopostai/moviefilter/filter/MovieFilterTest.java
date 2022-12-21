package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieFilterTest {

    @Test
    void shouldFailIfMoreThanOneYearIsLessThan1900() {
        int currentYear = LocalDateTime.now().getYear();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MovieFilter filter = new MovieFilter();
            filter.exactYear(1899);
        });
        assertEquals("Year is invalid. It must be greater than 1900 and fewer than " + currentYear,exception.getMessage());
    }

    @Test
    void shouldFailIfMoreThanOneYearIsGreaterThanCurrentYear() {
        int currentYear = LocalDateTime.now().getYear();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MovieFilter filter = new MovieFilter();
            filter.exactYear(currentYear+1);
        });
        assertEquals("Year is invalid. It must be greater than 1900 and fewer than " + currentYear,exception.getMessage());
    }

    @Test
    void shouldFailIfMoreThanOneYearIsSet() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MovieFilter filter = new MovieFilter();
            filter.beforeYear(1980);
            filter.afterYear(1980);
            filter.matches(Movie.newBuilder().build());
        });
        assertEquals("Set either beforeYear, afterYear or exactYear",exception.getMessage());
    }

    @Test
    void shouldFailIfMoreThanOneTitleIsSet() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MovieFilter filter = new MovieFilter();
            filter.titleContains("test");
            filter.exactTitle("te");
            filter.matches(Movie.newBuilder().build());
        });
        assertEquals("Set either titleContains or exactTitle",exception.getMessage());
    }

    @Test
    void shouldCreateFilePathBasedOnFilters() {
        MovieFilter filter = new MovieFilter();
        filter.titleContains("test");
        filter.decade(1910);
        filter.genre("drama");
        Path fileName = filter.createFileName();
        assertEquals("1910-drama-test-movies.json", fileName.toString());
    }

}
