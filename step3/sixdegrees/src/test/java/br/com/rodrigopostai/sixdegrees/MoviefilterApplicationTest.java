package br.com.rodrigopostai.sixdegrees;

import br.com.rodrigopostai.sixdegrees.domain.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationArguments;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MoviefilterApplicationTest {

    @Test
    void shouldReadFilterAndGenerateNewMovieFilesFromMainMethod() throws Exception {
        SixDegreesApplication.main(new String[] {"--file=src/test/resources/movies.json", "--exactYear=1981", "--cast=George Hamilton"});
        String moviesOf81 = Files.readAllLines(Paths.get("1981-George Hamilton-movies.json")).stream().collect(Collectors.joining());
        Movie[] movies = new ObjectMapper().readValue(moviesOf81, Movie[].class);
        assertTrue(Arrays.stream(movies).allMatch(movie -> movie.getYear() == 1981));
        assertTrue(Arrays.stream(movies).allMatch(movie -> movie.getCast().contains("George Hamilton")));
    }

    @Test
    void shouldReadFilterAndGenerateNewMovieFiles() throws Exception {
        SixDegreesApplication app = new SixDegreesApplication();
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("file", Collections.singletonList("src/test/resources/movies.json"));
        parameters.put("decade", Collections.singletonList("1980"));
        parameters.put("genres", Collections.singletonList("Comedy"));
        MovieFilterApplicationArguments args = new MovieFilterApplicationArguments(parameters);
        app.run(args);
        String comediesFrom80s = Files.readAllLines(Paths.get("1980-Comedy-movies.json")).stream().collect(Collectors.joining());
        Movie[] movies = new ObjectMapper().readValue(comediesFrom80s, Movie[].class);
        assertTrue(Arrays.stream(movies).allMatch(movie -> movie.getYear() >= 1980 && movie.getYear() <= 1989));
    }

    @AfterEach
    public void tearDown() throws Throwable {
        Files.deleteIfExists(Paths.get("1980-Comedy-movies.json"));
        Files.deleteIfExists(Paths.get("1981-George Hamilton-movies.json"));
    }

    private class MovieFilterApplicationArguments implements ApplicationArguments {

        private Map<String, List<String>> parameters;

        public MovieFilterApplicationArguments(Map<String, List<String>> parameters) {
            this.parameters = parameters;
        }

        @Override
        public String[] getSourceArgs() {
            return new String[0];
        }

        @Override
        public Set<String> getOptionNames() {
            return parameters.keySet();
        }

        @Override
        public boolean containsOption(String name) {
            return parameters.containsKey(name);
        }

        @Override
        public List<String> getOptionValues(String name) {
            return parameters.get(name);
        }

        @Override
        public List<String> getNonOptionArgs() {
            return null;
        }
    }

}
