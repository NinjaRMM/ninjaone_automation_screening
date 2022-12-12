package StepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.query.json.app.Main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Steps {

    private Integer dateInit;
    private Integer dateEnd;
    private String sourceJsonNameFile;
    private Integer decade;
    @Given("a json file of all movies between {int}-{int} {string}")
    public void a_json_file_of_all_movies_between(Integer int1, Integer int2, String string) {
        // Write code here that turns the phrase above into concrete actions
        this.dateInit=int1;
        this.dateEnd=int2;
        this.sourceJsonNameFile=string;
    }

    @And("I run the application")
    public void iRunTheApplication() {
        String[] args = {dateInit.toString(),dateEnd.toString(), sourceJsonNameFile,"80s-movies.json"};
        Main.main(args);
    }

    @When("I provide a decade like {int} as a parameter")
    public void i_provide_a_decade_like_as_a_parameter(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        this.decade=int1;
    }

    @And("it contains a JSON array of movies from {string} from the years {int}{int}")
    public void itContainsAJSONArrayOfMoviesFromFromTheYears(String arg0, int arg1, int arg2) {

    }

    @Then("a file is created called {string} in the data folder")
    public void aFileIsCreatedCalledInTheDataFolder(String pathIn) {
        Path path = Paths.get(pathIn);
        assertTrue(Files.exists(path));
    }

    @Given("{string} exists from step one")
    public void exists_from_step_one(String pathIn) {
        Path path = Paths.get(pathIn);
        assertTrue(Files.exists(path));
    }

    @When("I provide one actor's name as a parameter")
    public void i_provide_one_actor_s_name_as_a_parameter() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should see the number of degrees of separation from Kevin Bacon")
    public void i_should_see_the_number_of_degrees_of_separation_from_kevin_bacon() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I see a list of movies describing the degree steps")
    public void i_see_a_list_of_movies_describing_the_degree_steps() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }



}
