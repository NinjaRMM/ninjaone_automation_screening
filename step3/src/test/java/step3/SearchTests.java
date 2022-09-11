/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package step3;

import java.io.IOException;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import step3.models.Movie;
import step3.services.RelationsService;
import step3.services.delegates.MovieSearchDelegate;
import step3.utils.MovieFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchTests {

    private Collection<Movie> movies;

    @BeforeEach
    public void readMovies() throws Exception {
        movies = MovieFile.read("../data/80s-movies.json");
        assertEquals(1986, movies.size());
    }

    @Test
    public void filterTest() {
        MovieSearchDelegate fs = new MovieSearchDelegate("Tom Cruise", "Kevin Bacon", movies);
        assertEquals(2, fs.search().size());
    }

    @Test
    public void filterTestReversed() {
        MovieSearchDelegate fs = new MovieSearchDelegate("Kevin Bacon", "Tom Cruise", movies);
        assertEquals(2, fs.search().size());
    }

    @Test()
    public void testBadInputs() {
        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new MovieSearchDelegate("Kevin Hamburger", "Tom Cruise", movies));
        assertEquals("Kevin Hamburger did not star in a movie in the data provided.", exception.getMessage());
    }
}