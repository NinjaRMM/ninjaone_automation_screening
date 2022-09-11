package step3.services;


import java.util.Collection;
import step3.models.Movie;
import step3.services.delegates.MovieSearchDelegate;

/**
 * This class provides relations between multiple users.
 */
public class RelationsService {

  public void getDegreesOfKevinBacon(Collection<Movie> movies, String person) {
    getDegrees(movies, "Kevin Bacon", person);
  }


  /**
   * Filters the list of movies into the decade specified.
   *
   * @param movies The list of movies to search through.
   * @param originator The person to look for.
   * @param otherPerson The other person to look for.
   */
  public void getDegrees(Collection<Movie> movies, String originator, String otherPerson) {
    MovieSearchDelegate delegate = new MovieSearchDelegate(originator, otherPerson, movies);

    var results = delegate.search();
    if(results != null) {
      System.out.println("There are " + (results.size() - 1) + " degrees of separation between " + otherPerson + " and " + originator );

      for(var movie : results) {
        System.out.println(movie.originator() + " starred with " + movie.other() + " in " + movie.title());
      }
    }
    else {
      System.out.println("Could not find mapping between users.");
    }

  }
}
