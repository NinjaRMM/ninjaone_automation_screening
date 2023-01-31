package br.com.timoteosoutello.cucumber.stepdefinitions.filtermovies;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonIOException;

import br.com.timoteosoutello.cucumber.stepdefinitions.common.CommonStepDefinitions;
import br.com.timoteosoutello.domain.movie.Movie;
import br.com.timoteosoutello.dto.movie.FilterMoviesDTO;
import br.com.timoteosoutello.exception.movie.MoviesFilePathNotFoundException;
import br.com.timoteosoutello.usecase.movie.MovieUsecase;
import br.com.timoteosoutello.utils.movie.MovieUtils;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FilterMoviesStepDefinitions extends CommonStepDefinitions {

	@Autowired
	private MovieUsecase movieUsecase;
	@Autowired
	private MovieUtils movieUtils;
	private FilterMoviesDTO filterMoviesDTO = new FilterMoviesDTO();

	@Before
	public void beforeStep(Scenario scenario) {
		super.beforeStep(scenario);
	}

	@Given("a json file of all movies between {int}-{int} {string}")
	public void given_json_file_by_parameter(Integer from, Integer to, String fileName)
			throws MoviesFilePathNotFoundException {
		setFile(fileName);
		assertTrue(Files.exists(filterMoviesDTO.getFilePath()));
	}

	@And("I run the application")
	public void and_run_the_application() throws FileNotFoundException {
		filterMoviesDTO.setMoviesList(movieUtils.loadJsonMoviesFromfile(filterMoviesDTO.getFilePath()));
		assertTrue(!filterMoviesDTO.getMoviesList().isEmpty());
	}

	@When("I provide a decade like {int} as a parameter")
	public void when_i_provide_a_de_decade_as_a_parameter(int yearDecade) {
		assertNotNull(yearDecade);
	}

	@Then("a file is created called {string} in the data folder")
	public void then_a_file_is_created_called_in_data_folder(String generatedFileName) {
		assertNotNull(generatedFileName);
		filterMoviesDTO.setGeneratedFileName(generatedFileName);
	}

	@And("it contains a JSON array of movies from {string} from the years {int}-{int}")
	public void and_contains_json_array_of_movies_from_years(String filename, Integer from, Integer to)
			throws JsonIOException, IOException, MoviesFilePathNotFoundException {
		assertEquals(filterMoviesDTO.getFileName(), filename);
		filterMoviesDTO
				.setFilteredMovieList(movieUsecase.filterMoviesByRangedate(filterMoviesDTO.getMoviesList(), from, to));
		movieUtils.writesMoviesListIntoFile(filterMoviesDTO.getFilteredMovieList(),
				filterMoviesDTO.getGeneratedFileName());
		assertTrue(Files.exists(movieUsecase.getMoviesFile(filterMoviesDTO.getGeneratedFileName())));
		List<Movie> moviesListAux = movieUsecase.filterMoviesByRangedate(
				movieUtils.loadJsonMoviesFromfile(movieUsecase.getMoviesFile(filterMoviesDTO.getGeneratedFileName())),
				from, to);
		assertTrue(moviesListAux.size() == filterMoviesDTO.getFilteredMovieList().size());
	}

	private void setFile(String fileName) throws MoviesFilePathNotFoundException {
		filterMoviesDTO.setFileName(fileName);
		filterMoviesDTO.setFilePath(movieUsecase.getMoviesFile(fileName));
	}
}
