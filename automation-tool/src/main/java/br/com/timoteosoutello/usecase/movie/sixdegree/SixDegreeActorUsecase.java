package br.com.timoteosoutello.usecase.movie.sixdegree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.com.timoteosoutello.domain.movie.Movie;
import br.com.timoteosoutello.dto.movie.ActorMovieDTO;
import br.com.timoteosoutello.dto.movie.SixDegreeActorMovieDTO;
import br.com.timoteosoutello.exception.movie.ActorNotFoundInMoviesException;
import br.com.timoteosoutello.utils.movie.MovieConstants;

@Component
public class SixDegreeActorUsecase {

	public SixDegreeActorMovieDTO calculateDegreeBetweenActors(List<Movie> movies, String actorName1,
			String actorName2Aux) throws ActorNotFoundInMoviesException {
		final String actorName2 = Optional.ofNullable(actorName2Aux).isEmpty() ? MovieConstants.DEFAULT_ACTOR_NAME
				: actorName2Aux;
		final List<ActorMovieDTO> actorMovieDTOList = calculateMoviesDegreeLevel(movies, actorName1, actorName2);
		return new SixDegreeActorMovieDTO(actorName1, actorName2, actorMovieDTOList);
	}

	private void validateActorInCastMovies(final Map<String, Set<String>> actorMovies, final String actorName1,
			final String actorName2) throws ActorNotFoundInMoviesException {
		boolean notFound = true;
		notFound = actorMovies.get(actorName1) == null;
		if (notFound)
			throw new ActorNotFoundInMoviesException(actorName1);

		notFound = actorMovies.get(actorName2) == null;
		if (notFound)
			throw new ActorNotFoundInMoviesException(actorName2);
	}

	private List<String> getCastActorsPerMovieTitle(String movieTitle, List<Movie> movies) {
		for (Movie movie : movies) {
			if (movie.getTitle().equals(movieTitle)) {
				return movie.getCast();
			}
		}
		return new ArrayList<>();
	}

	/**
	 * Using breadth-first search by searching a tree data structure approach to
	 * search for degree between two references (actor1 to actor2) based on movie
	 * references
	 * 
	 * @param movies
	 * @param actorName1
	 * @param actorName2
	 * @return List<ActorMovieDTO>
	 * @throws ActorNotFoundInMoviesException
	 */
	private List<ActorMovieDTO> calculateMoviesDegreeLevel(final List<Movie> movies, final String actorName1,
			final String actorName2) throws ActorNotFoundInMoviesException {
		// Variables to be used
		Map<String, Set<String>> actorMovies = new HashMap<>();
		Queue<String> queue = new LinkedList<>();
		Set<String> visited = new HashSet<>();
		Map<String, ActorMovieDTO> predecessorActionMoviesDTOs = new HashMap<>();
		// Start from actorname1 as header reference
		queue.add(actorName1);
		visited.add(actorName1);
		buildMoviesListFromCastActors(movies, actorMovies);
		validateActorInCastMovies(actorMovies, actorName1, actorName2);
		// Continuous build of graph until actorName1-actorName2 is completed found.
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				// actor is being removed from queue in aux to no search again
				String currentActorNameFromQueue = queue.poll();
				if (currentActorNameFromQueue.equals(actorName2))
					return assembleActorPathFound(currentActorNameFromQueue, predecessorActionMoviesDTOs);
				// Not found, keep constructing node pairs.
				constructActorMoviesPair(actorMovies, currentActorNameFromQueue, movies, visited,
						predecessorActionMoviesDTOs, queue);
			}
		}
		return Collections.emptyList();
	}

	private void constructActorMoviesPair(final Map<String, Set<String>> actorMovies,
			final String currentActorNameFromQueue, final List<Movie> movies, final Set<String> visited,
			final Map<String, ActorMovieDTO> predecessorActionMoviesDTOs, Queue<String> queue) {
		Optional<Set<String>> auxMovies = Optional.ofNullable(actorMovies.get(currentActorNameFromQueue));
		// If there are movies for the current actor, the graph for actor is continued
		// to be built.
		if (auxMovies.isPresent()) {
			for (String movieName : auxMovies.get()) {
				for (String actor : getCastActorsPerMovieTitle(movieName, movies)) {
					if (!visited.contains(actor)) {
						queue.add(actor);
						visited.add(actor);
						predecessorActionMoviesDTOs.put(actor, new ActorMovieDTO(movieName, currentActorNameFromQueue));
					}
				}
			}
		}
	}

	private void buildMoviesListFromCastActors(final List<Movie> movies, final Map<String, Set<String>> actorMovies) {
		for (Movie movie : movies) {
			for (String actor : movie.getCast()) {
				Set<String> auxMovies = actorMovies.get(actor);
				if (Optional.ofNullable(auxMovies).isEmpty()) {
					auxMovies = new HashSet<>();
					actorMovies.put(actor, auxMovies);
				}
				auxMovies.add(movie.getTitle());
			}
		}
	}

	private List<ActorMovieDTO> assembleActorPathFound(final String currentActorNameFromQueue,
			final Map<String, ActorMovieDTO> predecessorActionMoviesDTOs) {
		List<ActorMovieDTO> auxMovies = new LinkedList<>();
		String currentActorName = currentActorNameFromQueue;
		while (Optional.ofNullable(currentActorName).isPresent()) {
			if (Optional.ofNullable(predecessorActionMoviesDTOs.get(currentActorName)).isPresent()) {
				auxMovies.add(new ActorMovieDTO(predecessorActionMoviesDTOs.get(currentActorName).getMovieTitle(),
						currentActorName));
			}
			currentActorName = Optional.ofNullable(predecessorActionMoviesDTOs.get(currentActorName)).isEmpty() ? null
					: predecessorActionMoviesDTOs.get(currentActorName).getActorName();
		}
		// In order to build a proper actor from-to path.
		Collections.reverse(auxMovies);
		return auxMovies;
	}

}
