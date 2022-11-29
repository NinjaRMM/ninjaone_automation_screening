package com.ninja.one.degreeofseparations.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja.one.degreeofseparations.model.Movie;
import com.ninja.one.degreeofseparations.model.exception.ApiException;
import com.ninja.one.degreeofseparations.repository.impl.MovieRepositoryImpl;
import com.ninja.one.degreeofseparations.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieServiceImpl(new MovieRepositoryImpl(new ObjectMapper()));
    }

    @Test
    @DisplayName("Test extract movies from decade 300 throws exception")
    void extractMovieFromDecade300() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> movieService.extractMovieFromDecade(300));
        assertTrue(runtimeException.getMessage().contains("Movies not found from 2200 until 2209"));
    }

    @Test
    @DisplayName("Test extract movies from decade 80s")
    void testExtractMoviesDecade80() throws ApiException {
        List<Movie> result = movieService.extractMovieFromDecade(80);
        assertEquals(1986, result.size());
        assertTrue(result.stream().noneMatch(movie -> movie.getTitle().contains("NinjaOne Easter Egg")));
        assertTrue(result.stream().anyMatch(movie -> movie.getCast().contains("Tom Cruise")));
        assertTrue(result.stream().anyMatch(movie -> movie.getCast().contains("Sylvester Stallone")));
        assertTrue(result.stream().anyMatch(movie -> movie.getCast().contains("Kevin Bacon")));
        assertTrue(result.stream().noneMatch(movie -> movie.getYear() < 1980));
        assertTrue(result.stream().noneMatch(movie -> movie.getYear() > 1989));

        Path currentRelativePath = Paths.get("");
        String pathSaved = currentRelativePath.toAbsolutePath().getParent().getParent().toString()
                + File.separator + "data" + File.separator + "80s-movies.json";

        File movies80DecadeJson = new File(pathSaved);
        assertTrue(movies80DecadeJson.exists() && !movies80DecadeJson.isDirectory());
    }

    @Test
    @DisplayName("Test extract movies from decade 60s")
    void testExtractMoviesDecade60() throws ApiException {
        List<Movie> result = movieService.extractMovieFromDecade(60);
        assertEquals(1414, result.size());
        assertTrue(result.stream().noneMatch(movie -> movie.getTitle().contains("NinjaOne Easter Egg")));
        assertTrue(result.stream().anyMatch(movie -> movie.getCast().contains("Elvis Presley")));
        assertTrue(result.stream().anyMatch(movie -> movie.getCast().contains("Susan Hayward")));
        assertTrue(result.stream().anyMatch(movie -> movie.getCast().contains("Alexandra Stewart")));
        assertTrue(result.stream().noneMatch(movie -> movie.getYear() < 1960));
        assertTrue(result.stream().noneMatch(movie -> movie.getYear() > 1969));

        Path currentRelativePath = Paths.get("");
        String pathSaved = currentRelativePath.toAbsolutePath().getParent().getParent().toString()
                + File.separator + "data" + File.separator + "60s-movies.json";

        File movies80DecadeJson = new File(pathSaved);
        assertTrue(movies80DecadeJson.exists() && !movies80DecadeJson.isDirectory());
    }

    @Test
    @DisplayName("Test degree of separation between Tom Cruise and default actor Kevin Bacon")
    void testDegreeOfSeparationBetweenTomCruiseAndDefaultActor() throws ApiException {
        String result = movieService.calculateDegreeOfSeparation("Tom Cruise", null);
        assertNotNull(result);
        assertTrue(result.contains("Degree of Separation: 1"));
        assertTrue(result.contains("Tom Cruise starred with Chris Penn in All the Right Moves"));
        assertTrue(result.contains("Chris Penn starred with Kevin Bacon in Footloose"));
    }

    @Test
    @DisplayName("Test degree of separation between Tom Cruise and Diane Lane")
    void testDegreeOfSeparationBetweenTomCruiseAndDianeLane() throws ApiException {
        String result = movieService.calculateDegreeOfSeparation("Tom Cruise", "Diane Lane");
        assertNotNull(result);
        assertTrue(result.contains("Degree of Separation: 0"));
        assertTrue(result.contains("Tom Cruise starred with Diane Lane in The Outsiders"));
    }

    @Test
    @DisplayName("Test degree of separation between Tom Cruise and James Garner")
    void testDegreeOfSeparationBetweenTomCruiseAndJamesGarner() throws ApiException {
        String result = movieService.calculateDegreeOfSeparation("Tom Cruise", "James Garner");
        assertNotNull(result);
        assertTrue(result.contains("Degree of Separation: 1"));
        assertTrue(result.contains("Tom Cruise starred with C. Thomas Howell in The Outsiders"));
        assertTrue(result.contains("C. Thomas Howell starred with James Garner in Tank"));
    }

    @Test
    @DisplayName("Test degree of separation between Tom Cruise and Julie Andrews")
    void testDegreeOfSeparationBetweenTomCruiseAndJulieAndrews() throws ApiException {
        String result = movieService.calculateDegreeOfSeparation("Tom Cruise", "Julie Andrews");
        assertNotNull(result);
        assertTrue(result.contains("Degree of Separation: 2"));
        assertTrue(result.contains("Tom Cruise starred with Shelley Long in Losin' It"));
        assertTrue(result.contains("Shelley Long starred with Sela Ward in Hello Again"));
        assertTrue(result.contains("Sela Ward starred with Julie Andrews in The Man Who Loved Women"));
    }

    @Test
    @DisplayName("Test degree of separation between Tom Cruise and Barrie Ingham")
    void testDegreeOfSeparationBetweenTomCruiseAndBarrieIngham() throws ApiException {
        String result = movieService.calculateDegreeOfSeparation("Tom Cruise", "Barrie Ingham");
        assertNotNull(result);
        assertTrue(result.contains("Degree of Separation: 5"));
        assertTrue(result.contains("Tom Cruise starred with Craig T. Nelson in All the Right Moves"));
        assertTrue(result.contains("Craig T. Nelson starred with Meryl Streep in Silkwood"));
        assertTrue(result.contains("Meryl Streep starred with Carroll Baker in Ironweed"));
        assertTrue(result.contains("Carroll Baker starred with Bette Davis in The Watcher in the Woods"));
        assertTrue(result.contains("Bette Davis starred with Vincent Price in The Whales of August"));
        assertTrue(result.contains("Vincent Price starred with Barrie Ingham in The Great Mouse Detective"));
    }

    @Test
    @DisplayName("Test degree of separation the actor 1 and actor 2 are the same")
    void testDegreeOfSeparationTheActorsAreTheSame() throws ApiException {
        String result = movieService.calculateDegreeOfSeparation("Tom Cruise", "Tom Cruise");
        assertNotNull(result);
        assertTrue(result.contains("Degree of Separation: -1"));
        assertTrue(result.contains("Actor1 and Actor2 are the same."));
    }

    @Test
    @DisplayName("Test degree of separation the actor 1 doesn't exist")
    void testDegreeOfSeparationTheActorOneNotExist() throws ApiException {
        String result = movieService.calculateDegreeOfSeparation("Buzz Lightyear", null);
        assertNotNull(result);
        assertTrue(result.contains("Degree of Separation: -1"));
        assertTrue(result.contains("Buzz Lightyear did not star in a movie in the data provided."));
    }

    @Test
    @DisplayName("Test degree of separation the actor 2 doesn't exist")
    void testDegreeOfSeparationTheActorTwoNotExist() throws ApiException {
        String result = movieService.calculateDegreeOfSeparation("Tom Cruise", "Buzz Lightyear");
        assertNotNull(result);
        assertTrue(result.contains("Degree of Separation: -1"));
        assertTrue(result.contains("Buzz Lightyear did not star in a movie in the data provided."));
    }

    @Test
    @DisplayName("Test degree of separation more than 6 degrees")
    void testDegreeOfSeparationMoreThanSixDegrees() throws ApiException {
        String result = movieService.calculateDegreeOfSeparation("Tom Cruise", "Buster Crabbe");
        assertNotNull(result);
        assertTrue(result.contains("Degree of Separation: 7"));
        assertTrue(result.contains("The Degree of separation is bigger than 6"));
        assertFalse(result.contains("Tom Cruise"));
        assertFalse(result.contains("Buster Crabbe"));
    }

}