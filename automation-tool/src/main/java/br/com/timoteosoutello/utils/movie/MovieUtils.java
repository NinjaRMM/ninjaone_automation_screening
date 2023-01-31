package br.com.timoteosoutello.utils.movie;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import br.com.timoteosoutello.domain.movie.Movie;
import br.com.timoteosoutello.dto.movie.ActorMovieDTO;
import br.com.timoteosoutello.utils.GsonUtils;

@Component
public class MovieUtils {

	@Autowired
	private GsonUtils gsonUtils;

	public List<Movie> loadJsonMoviesFromfile(Path path)
			throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return gsonUtils.loadJsonFromPathAndTyped(path, MovieConstants.MOVIE_LIST_TYPE_CONSTANT);
	}

	public void writesMoviesListIntoFile(List<Movie> movies, String fileName) throws IOException {
		gsonUtils.writesTo(movies, getMovieFileWriter(fileName), MovieConstants.MOVIE_LIST_TYPE_CONSTANT);
	}

	public FileWriter getMovieFileWriter(String fileName) throws IOException {
		return new FileWriter(MovieConstants.DATA_MOVIES_DIR.concat(fileName));
	}

	public String getMovieTitlesStringFromActorMovieDTOList(List<ActorMovieDTO> actorMovieDTOList) {
		StringBuilder stringBuilder = new StringBuilder();
		if (Optional.ofNullable(actorMovieDTOList).isEmpty() || actorMovieDTOList.isEmpty())
			return StringUtils.EMPTY;
		for (ActorMovieDTO actorMovieDTO : actorMovieDTOList) {
			stringBuilder.append(actorMovieDTO.getMovieTitle()).append(", ");
		}
		String commaSeparatedList = stringBuilder.toString();
		// Condition check to remove the last comma
		if (commaSeparatedList.length() > 0)
			commaSeparatedList = commaSeparatedList.substring(0, commaSeparatedList.length() - 2);
		return commaSeparatedList;
	}
}
