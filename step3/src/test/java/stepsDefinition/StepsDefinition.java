package stepsDefinition;

import com.testTask.ninja.Graph;
import com.testTask.ninja.objects.Movie;
import com.testTask.ninja.objects.TraverseResult;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static com.testTask.ninja.MovieService.getMovies;
import static com.testTask.ninja.MovieService.readFile;

public class StepsDefinition {
    private static final String DEFAULT_ACTOR = "Kevin Bacon";
    private static final String RESULT_PATH = "../data/%s";
    private static final String DEGREES_MESSAGE = "Number of degrees of separation is: %s";
    private String fileContent;
    private TraverseResult result;

    @Given("I run the application")
    public void iRunTheApplication() {

    }

    @And("{string} exists from step one")
    public void existsFromStepOne(String fileName) {
        fileContent = readFile(String.format(RESULT_PATH, fileName));
        Assertions.assertNotNull(fileContent);
    }

    @When("I provide one actor's name as a parameter {string}")
    public void iProvideOneActorSNameAsAParameter(String name) {
        iProvideTwoActorsNamesAsAParameters(name, DEFAULT_ACTOR);
    }

    @When("I provide two actors' names as a parameters {string}, {string}")
    public void iProvideTwoActorsNamesAsAParameters(String actor1, String actor2) {
        List<Movie> movies = getMovies(fileContent);
        Graph graph = new Graph(movies);
        result = graph.traverse(actor1, actor2);
    }

    @Then("I should see the number of degrees of separation from Kevin Bacon")
    public void iShouldSeeTheNumberOfDegreesOfSeparationFromKevinBacon() {
        System.out.println(result.getDegree());
    }

    @And("I see a list of movies describing the degree steps")
    public void iSeeAListOfMoviesDescribingTheDegreeSteps() {
        result.getPairs().forEach(System.out::println);
    }

    @Then("I should see the number of degrees of separation between the two actors")
    public void iShouldSeeTheNumberOfDegreesOfSeparationBetweenTheTwoActors() {
        iShouldSeeTheNumberOfDegreesOfSeparationFromKevinBacon();
    }

    @When("I provide a name not in the data as a parameter {string}")
    public void iProvideANameNotInTheDataAsAParameter(String name) {
        iProvideOneActorSNameAsAParameter(name);
    }

    @Then("I should see a message stating that name did not star in a movie")
    public void iShouldSeeAMessageStatingThatNameDidNotStarInAMovie() {
        System.out.println(result.getDegree());
    }

    @When("I provide two actors' names one known, and one not in the data as a parameters {string}, {string}")
    public void iProvideTwoActorsNamesOneKnownAndOneNotInTheDataAsAParameters(String name1, String name2) {
        iProvideTwoActorsNamesAsAParameters(name1, name2);
    }
}
