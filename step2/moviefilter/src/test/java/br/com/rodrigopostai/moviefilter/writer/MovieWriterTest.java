package br.com.rodrigopostai.moviefilter.writer;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MovieWriterTest {

    private static final int NUMBER_OF_RECORDS = 1000;

    @Test
    void shouldWriteMovies() throws Throwable {
        String file = "moviewriter.json";
        List<Movie> movies = createMovies();
        try(MovieWriter writer = new MovieWriter(Paths.get(file))) {
            movies.stream().forEach(writer::writeMovie);
        }
        String writtenFile = String.join("", Files.readAllLines(Paths.get(file)));
        assertNotNull(writtenFile);
        ObjectMapper mapper = new ObjectMapper();
        Movie[] writtenMovies = mapper.readValue(writtenFile, Movie[].class);
        assertEquals(movies.size(), writtenMovies.length);
        for (int i = 0; i < NUMBER_OF_RECORDS; i++) {
            assertEquals(movies.get(i), writtenMovies[i]);
        }
    }

    private List<Movie> createMovies() {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_RECORDS; i++) {
            movies.add(Movie.newBuilder().title(String.format("Movie %d", i))
                    .year(RandomUtils.nextInt(1900, 2019))
                    .cast(String.format("Actor %d", i))
                    .cast(String.format("Actress %d", i))
                    .genre("Comedy")
                    .genre("Short").build());
        }
        return movies;
    }

}
