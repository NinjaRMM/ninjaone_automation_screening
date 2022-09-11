package step2.services;


import java.util.Collection;
import step2.models.Movie;

/**
 * This class provides filtering abilities for movies.
 */
public class FilterService {


  /**
   * Filters the list of movies into the decade specified.
   *
   * @param movies The list of movies to filter.
   * @param decade The decade to filter by. I.e. "80"
   * @return A filtered list of movies by the decade provided.
   */
  public Collection<Movie> getMoviesByDecade(Collection<Movie> movies, int decade) {
    var decadePrefix = (decade % 100) / 10;
    return movies.stream().filter(movie -> ( (movie.year() % 100) / 10 == decadePrefix)).toList();
  }
}
