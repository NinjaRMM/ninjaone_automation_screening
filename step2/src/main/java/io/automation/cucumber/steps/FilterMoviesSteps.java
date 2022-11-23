package io.automation.cucumber.steps;

import com.google.gson.reflect.TypeToken;
import io.automation.JsonHelper;
import io.automation.MoviesFilter;
import io.automation.PathHelper;
import io.automation.ScenarioContext;
import io.automation.entities.Movie;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class FilterMoviesSteps {
    private static final String INPUT_FILE = "INPUT_FILE";
    private static final String OUTPUT_FILE = "OUTPUT_FILE";
    private static final String DECADE = "DECADE";
    private final ScenarioContext context;
    private MoviesFilter moviesFilter;

    public FilterMoviesSteps(final ScenarioContext context) {
        this.context = context;
    }

    @Given("a json file of all movies between 1900-2018 {string}")
    public void AJsonFileOfAllMoviesBetweenADate(final String jsonFile) {
        context.set(INPUT_FILE, jsonFile);
    }

    @And("I run the application")
    public void iRunTheApplication() {
        moviesFilter = new MoviesFilter((String) context.get(INPUT_FILE));
    }

    @When("I provide a decade like {int} as a parameter")
    public void iProvideADecadeLikeIntAsAParameter(int expectedDecade) {
        context.set(DECADE, expectedDecade);
    }

    @Then("a file is created called {string} in the data folder")
    public void aFileIsCreatedCalledFileNameInTheDataFolder(final String jsonFileOut) {
        context.set(OUTPUT_FILE,jsonFileOut);
        moviesFilter.buildFileMoviesByDecade((int) context.get(DECADE), jsonFileOut);
    }

    @And("it contains a JSON array of movies from {string} from the years {int}-{int}")
    public void ItContainsAJsonArrayOfMoviesFromStringFromTheYears(
            final String jsonFileIn,
            int expectedLowerYear,
            int expectedGreaterYear) {
        List<Movie> inMovies = JsonHelper.getObjectsFromFile(
                PathHelper.getPathFile(jsonFileIn),
                new TypeToken<List<Movie>>() {
                }.getType()
        );
        List<Movie> outMovies = JsonHelper.getObjectsFromFile(
                PathHelper.getPathFile((String)context.get(OUTPUT_FILE)),
                new TypeToken<List<Movie>>() {
                }.getType()
        );

        int index = Collections.indexOfSubList(inMovies, outMovies);
        assertNotEquals(index, -1);

        List<Movie> filteredMovies = outMovies.stream()
                .filter(movie -> movie.getYear() >= expectedLowerYear && movie.getYear() <= expectedGreaterYear)
                .collect(Collectors.toList());
        assertEquals(outMovies.size(), filteredMovies.size());
    }
}
