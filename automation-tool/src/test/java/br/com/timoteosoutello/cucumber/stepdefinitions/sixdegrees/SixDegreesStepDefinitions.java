package br.com.timoteosoutello.cucumber.stepdefinitions.sixdegrees;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import br.com.timoteosoutello.cucumber.stepdefinitions.common.CommonStepDefinitions;
import br.com.timoteosoutello.dto.movie.FilterMoviesDTO;
import br.com.timoteosoutello.dto.movie.SixDegreeActorMovieDTO;
import br.com.timoteosoutello.exception.movie.ActorNotFoundInMoviesException;
import br.com.timoteosoutello.exception.movie.MoviesFilePathNotFoundException;
import br.com.timoteosoutello.usecase.movie.MovieUsecase;
import br.com.timoteosoutello.usecase.movie.sixdegree.SixDegreeActorUsecase;
import br.com.timoteosoutello.utils.movie.MovieUtils;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SixDegreesStepDefinitions extends CommonStepDefinitions {

	@Autowired
	private MovieUsecase movieUsecase;
	@Autowired
	private SixDegreeActorUsecase sixDegreeActorUsecase;
	@Autowired
	private MovieUtils movieUtils;
	private FilterMoviesDTO filterMoviesDTO = new FilterMoviesDTO();
	private SixDegreeActorMovieDTO sixDegreeActorMovieDTO = new SixDegreeActorMovieDTO();

	@Before
	public void beforeStep(Scenario scenario) {
		super.beforeStep(scenario);
	}
	
	@Given("{string} exists from step one")
	public void and_file_exists_from_step_one(String filename)
			throws JsonIOException, JsonSyntaxException, FileNotFoundException, MoviesFilePathNotFoundException {
		filterMoviesDTO.setFilteredMovieList(movieUtils.loadJsonMoviesFromfile(movieUsecase.getMoviesFile(filename)));
	}

	@When("I provide actor's name {string}")
	public void when_i_provide_one_actor_name_as_a_parameter(String actorName) {
		sixDegreeActorMovieDTO.setActorName1(actorName);
		assertNotNull(sixDegreeActorMovieDTO.getActorName1());
		sixDegreeActorMovieDTO.setActorName2(null);
	}

	@Then("I should see the number of degrees of separation from {string}")
	public void then_i_should_see_number_of_degrees_of_separation_from(String name)
			throws ActorNotFoundInMoviesException {
		sixDegreeActorMovieDTO.setActorName2(name);
		assertNotNull(sixDegreeActorMovieDTO.getActorName2());
		checkDistanceBetweenTwoActors();
	}

	@Then("I should see the number of degrees of separation between the two actors")
	public void then_i_should_see_number_of_degrees_of_separation_between_two_actors()
			throws ActorNotFoundInMoviesException {
		assertNotNull(sixDegreeActorMovieDTO.getActorName2());
		checkDistanceBetweenTwoActors();
	}

	@Then("I should see a message stating that name did not star in a movie")
	public void then_i_should_see_that_actor_did_not_start_in_any_movie() {
		try {
			calculateDegreeBetweenActors();
			fail("ActorNotFoundInMoviesException exception was not raised");
		} catch (ActorNotFoundInMoviesException e) {
			assertTrue(String.format(ActorNotFoundInMoviesException.MESSAGE, sixDegreeActorMovieDTO.getActorName1())
					.equals(e.getMessage())
					|| String.format(ActorNotFoundInMoviesException.MESSAGE, sixDegreeActorMovieDTO.getActorName2())
							.equals(e.getMessage()));
			System.out.println(e.getMessage());
		}
	}

	@And("I see a list of movies describing the degree steps")
	public void and_i_see_a_list_of_movies_describing_degree_steps() throws ActorNotFoundInMoviesException {
		String movieListBetweenTwoActorsLog = String.format(
				"One of the movie list'scenario between the two actors is: %s",
				movieUtils.getMovieTitlesStringFromActorMovieDTOList(sixDegreeActorMovieDTO.getActorMoviesDTOs()));
		System.out.println(movieListBetweenTwoActorsLog);
		super.scenario.log(movieListBetweenTwoActorsLog);
	}

	@Then("I provide actor name {string} and {string}")
	public void then_i_should_see_number_of_degrees_of_separation_from(String actorName1, String actorName2)
			throws ActorNotFoundInMoviesException {
		sixDegreeActorMovieDTO.setActorName1(actorName1);
		assertNotNull(sixDegreeActorMovieDTO.getActorName1());
		sixDegreeActorMovieDTO.setActorName2(actorName2);
		assertNotNull(sixDegreeActorMovieDTO.getActorName2());
	}

	private void calculateDegreeBetweenActors() throws ActorNotFoundInMoviesException {
		sixDegreeActorMovieDTO = sixDegreeActorUsecase.calculateDegreeBetweenActors(
				filterMoviesDTO.getFilteredMovieList(), sixDegreeActorMovieDTO.getActorName1(),
				sixDegreeActorMovieDTO.getActorName2());
	}

	private void checkDistanceBetweenTwoActors() throws ActorNotFoundInMoviesException {
		calculateDegreeBetweenActors();
		assertNotNull(sixDegreeActorMovieDTO.getActorMoviesDTOs());
		assertNotNull(sixDegreeActorMovieDTO.getNumberOfDegrees());
		String numberOfDegreesLogMessage = String.format("Number of degrees between actor %s and actor %s is %s",
				sixDegreeActorMovieDTO.getActorName1(), sixDegreeActorMovieDTO.getActorName2(),
				sixDegreeActorMovieDTO.getNumberOfDegrees());
		System.out.println(numberOfDegreesLogMessage);
		super.scenario.log(numberOfDegreesLogMessage);
	}
}
