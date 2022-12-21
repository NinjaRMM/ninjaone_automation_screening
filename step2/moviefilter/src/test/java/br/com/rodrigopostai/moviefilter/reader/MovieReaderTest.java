package br.com.rodrigopostai.moviefilter.reader;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import br.com.rodrigopostai.moviefilter.filter.MovieFilter;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieReaderTest {

    @Test
    void shouldReadSmallJsonFromClasspath() {
        Path path = Paths.get(this.getClass().getClassLoader().getResource("small-movies-subset.json").getFile());
        MovieReader reader = new MovieReader(path);
        List<Movie> movies = StreamSupport.stream(reader, false).collect(Collectors.toList());
        assertEquals(3,movies.size());
    }

    @Test
    void shouldReadLargeJsonFromClasspath() {
        Path path = Paths.get(this.getClass().getClassLoader().getResource("movies.json").getFile());
        MovieReader reader = new MovieReader(path);
        List<Movie> movies = StreamSupport.stream(reader, false).collect(Collectors.toList());
        assertEquals(28796, movies.size());

    }

    @Test
    void shouldReadLargeJsonFromClassPathAndFilterByYear() {
        Path path = Paths.get(this.getClass().getClassLoader().getResource("movies.json").getFile());
        MovieReader reader = new MovieReader(path);
        MovieFilter filter = new MovieFilter();
        filter.afterYear(1980);
        List<Movie> moviesBefore1980 = StreamSupport.stream(reader, false).filter(filter::matches).collect(Collectors.toList());
        assertTrue(moviesBefore1980.stream().allMatch(m -> m.getYear() >= 1980));

        filter = new MovieFilter();
        filter.beforeYear(1980);
        List<Movie> moviesAfter1980 = StreamSupport.stream(reader, false).filter(filter::matches).collect(Collectors.toList());
        assertTrue(moviesAfter1980.stream().allMatch(m -> m.getYear() <= 1980));

        filter = new MovieFilter();
        filter.exactYear(1980);
        List<Movie> moviesIn1980 = StreamSupport.stream(reader, false).filter(filter::matches).collect(Collectors.toList());
        assertTrue(moviesAfter1980.stream().allMatch(m -> m.getYear() == 1980));
    }

    @Test
    void shouldReadLargeJsonFromClassPathAndFilterByDecade() {
        Path path = Paths.get(this.getClass().getClassLoader().getResource("movies.json").getFile());
        MovieReader reader = new MovieReader(path);
        MovieFilter filter = new MovieFilter();
        filter.decade(1980);
        List<Movie> moviesFrom80s = StreamSupport.stream(reader, false).filter(filter::matches).collect(Collectors.toList());
        assertTrue(moviesFrom80s.stream().allMatch(m -> m.getYear() >= 1980 && m.getYear() <= 1989));
    }
}
