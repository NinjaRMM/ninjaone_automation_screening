package br.com.timoteosoutello.usecase.movie;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.timoteosoutello.domain.movie.Movie;
import br.com.timoteosoutello.exception.movie.MoviesFilePathNotFoundException;
import br.com.timoteosoutello.utils.FileUtils;
import br.com.timoteosoutello.utils.movie.MovieConstants;

@Component
public class MovieUsecase {

	public Path getMoviesFile(String fileName) throws MoviesFilePathNotFoundException {
		Path path;
		try {
			path = FileUtils.getFilePath(MovieConstants.DATA_MOVIES_DIR.concat(fileName));
		} catch (FileNotFoundException e) {
			throw new MoviesFilePathNotFoundException();
		}
		return path;
	}

	public List<Movie> filterMoviesByRangedate(List<Movie> moviesList, int from, int to) {
		return moviesList.stream().filter(movie -> movie.getYear() >= from && movie.getYear() <= to).toList();
	}

}
