package stepsDefinition;

import com.testTask.ninja.Movie;
import com.testTask.ninja.ReadWriteHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StepsDefinition {
    private static List<Movie> result = new ArrayList<>();
    private static List<Movie> movies;
    private static final String RESULT_PATH = "../data/%s";
    private static final String SOURCE_PATH = "../data/movies.json";

    @Given("a json file of all movies between 1900-2018 \"movies.json\"")
    public void givenFile() {
        movies = ReadWriteHelper.readFileToList(SOURCE_PATH);
    }

    @And("I run the application")
    public void andRunApp() {
        // TODO: 10/13/22 what should be here?
    }

    @When("I provide a decade like {int} as a parameter")
    public void whenSetParam(int decade) {
        result = ReadWriteHelper.getMoviesByDecade(movies, decade);
    }

    @Then("a file is created called {string} in the data folder")
    public void thenCheckFileWasCreated(String fileName) {
        ReadWriteHelper.saveToFile(result, RESULT_PATH, fileName);
    }

    @And("it contains a JSON array of movies from \"movies.json\" from the years {int}-{int}")
    public void andCheckFileContent(int from, int to) {
        ReadWriteHelper.checkFilter(RESULT_PATH, from, to);
    }
}
