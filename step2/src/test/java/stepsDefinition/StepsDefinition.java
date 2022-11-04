package stepsDefinition;

import com.google.gson.Gson;
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
    private static final List<Movie> result = new ArrayList<>();
    private static final String SOURCE_PATH = "../data/movies.json";
    private static final String RESULT_PATH = "../data/%s";
    private static final String VALIDATION_MESSAGE = "Invalid input value. Decade should be one of '20, 30, 40, 50, 60, 70, 80, 90'";
    private static String resultFileName;
    private static List<Movie> movies;

    @Given("a json file of all movies between 1900-2018 \"movies.json\"")
    public void givenFile() {
        String fileContent = ReadWriteHelper.readFile(SOURCE_PATH);
        movies = ReadWriteHelper.getMovies(fileContent);
    }

    @And("I run the application")
    public void andRunApp() {
        // TODO: 10/13/22 what should be here?
        System.out.println("I run APP");
    }

    @When("I provide a decade like {int} as a parameter")
    public void whenSetParam(int decade) {
        isInputValid(decade);
        for (int x = 0; x < 10; x++) {
            int year = 1900 + decade + x;
            result.addAll(getMovies(year));
        }
    }

    @Then("a file is created called {string} in the data folder")
    public void thenCheckFileWasCreated(String fileName) {
        resultFileName = fileName;
        String fileContent = new Gson().toJson(result);
        ReadWriteHelper.writeToFile(String.format(RESULT_PATH, fileName), fileContent);
    }

    @And("it contains a JSON array of movies from \"movies.json\" from the years {int}-{int}")
    public void andCheckFileContent(Integer from, Integer to) {
        List<Movie> moviesFromSource = ReadWriteHelper.readFileToList(String.format(RESULT_PATH, resultFileName));
        List<Movie> moviesFromResult = ReadWriteHelper.readFileToList(SOURCE_PATH);

        for (int x = from; x <= to; x++) {
            int year = x;
            List<Movie> fromSource = moviesFromSource.stream().filter(movie -> movie.getYear() == year).collect(Collectors.toList());
            List<Movie> fromResult = moviesFromResult.stream().filter(movie -> movie.getYear() == year).collect(Collectors.toList());

            Assertions.assertEquals(fromSource.size(), fromResult.size());
            Assertions.assertEquals(fromSource, fromResult);
        }
    }

    private void isInputValid(Integer decade) {
        List<Integer> validValues = List.of(20, 30, 40, 50, 60, 70, 80, 90);
        boolean contains = validValues.contains(decade);
        if (!contains)
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
    }

    //todo one cicle
    private List<Movie> getMovies(Integer yearFilter) {
        return movies.stream()
                .filter(movie -> movie.getYear().intValue() == yearFilter)
                .collect(Collectors.toList());
    }
}
