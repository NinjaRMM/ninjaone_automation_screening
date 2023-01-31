package br.com.timoteosoutello.dto.movie;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import br.com.timoteosoutello.domain.movie.Movie;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FilterMoviesDTO {
	
	private List<Movie> moviesList;
	private List<Movie> filteredMovieList;
	private Path filePath;
	private String fileName;
	private String generatedFileName;

	public FilterMoviesDTO() {
		this.moviesList = new ArrayList<>();
	}
}
