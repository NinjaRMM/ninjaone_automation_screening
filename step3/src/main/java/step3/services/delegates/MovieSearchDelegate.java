package step3.services.delegates;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import step3.models.Movie;

/**
 * Semi-Delegate used to house the more complex searching algorithm.
 */
public class MovieSearchDelegate {
  HashMap<String, List<Movie>> usersMetaData = new HashMap<>();
  final String originator;
  final String otherPerson;
  public MovieSearchDelegate(String originator, String otherPerson, Collection<Movie> movies_) {
    this.originator = originator;
    this.otherPerson = otherPerson;
    for(var movie : movies_) {
      // Build up a map of users to their movies.
      for(String cast : movie.cast()) {
        var movieList = usersMetaData.getOrDefault(cast, new ArrayList<>());
        movieList.add(movie);
        usersMetaData.put(cast, movieList);
      }
    }

    if(!usersMetaData.containsKey(originator)) {
      throw new IllegalArgumentException(originator + " did not star in a movie in the data provided.");
    }

    if(!usersMetaData.containsKey(otherPerson)) {
      throw new IllegalArgumentException(otherPerson + " did not star in a movie in the data provided.");
    }
  }

  public List<SearchEntry> search() {
    return search(originator, usersMetaData);
  }

  private List<SearchEntry> search(String currentStar, HashMap<String, List<Movie>> usersMetaData) {
    Queue<String> usersToSearch = new ArrayDeque<>();
    Set<String> usersSearched = new HashSet<>();
    usersToSearch.add(currentStar);
    usersSearched.add(currentStar);
    Map<String, SearchEntry> parentNodes = new HashMap<>();

    while(!usersToSearch.isEmpty()) {
      var cast = usersToSearch.poll();
      // Add all of that users' co-stars.
      var movieList = usersMetaData.get(cast);

      var nextUserList = movieList.stream().flatMap(movie ->
              movie.cast().stream().map(costar -> new SearchEntry(cast, costar, movie.title())))
          .filter(searchEntry -> !usersSearched.contains(searchEntry.other)).toList();

      for(var nextUser : nextUserList) {
          usersToSearch.add(nextUser.other);
          usersSearched.add(nextUser.other);
          parentNodes.put(nextUser.other, nextUser);
          if(nextUser.other.equals(otherPerson)) {
            List<SearchEntry> shortestPath = new ArrayList<>();
            var match = nextUser;
            while(match != null) {
              shortestPath.add(match);
              match = parentNodes.get(match.originator);
            }
            Collections.reverse(shortestPath);
            return shortestPath;
          }
        }
    }

    // Would normally remove this commented out code,
    // But thought I'd let you see one of my earlier failed attempts at trying to do a BFS algorithm.

//    if (!metaData.userSearched) {
//      System.out.println("Searching, current depth: " + currentDepth + " Looking for: " + otherPerson + " using " + currentStar);
//      metaData.userSearched = true;
//      // If we haven't looked through this user's metadata before, look through it.
//
//      for (var movie : metaData.movies) {
//        for (String cast : movie.cast()) {
//          if (cast.equals(otherPerson)) {
//            // Found it, set the shortest depth possible to the current depth and go back.
//            shortestDepth = currentDepth;
//            SearchResults searchResults = new SearchResults();
//            searchResults.movies.add(new SearchEntry(currentStar, cast, movie.title()));
//            return searchResults;
//          }
//        }
//      }
//      System.out.println("No matches for " + currentStar);
//      var newMetaData = new HashMap<>(usersMetaData);
//      // No match, let's search deeper.
//      SearchResults currentBestResults = null;
//
//      Queue<String> users = metaData.movies.stream().map(Movie::cast).flatMap(Collection::stream)
//          .distinct().collect(Collectors.toCollection(ArrayDeque::new));
//
//      while(!users.isEmpty()) {
//        var cast = users.remove();
//
//        i
//      }
//
//      for ( var movie : metaData.movies) {
//        for (String cast : movie.cast()) {
//
//          var results = search(cast, ++currentDepth, newMetaData);
//          if(results != null) {
//            System.out.println("Potential match at depth: " + results.movies.size());
//
//            if(currentBestResults == null || results.movies.size() < currentBestResults.movies.size() - 1) {
//              // Found a shorter path, use this one instead.
//              results.movies.add(new SearchEntry(currentStar, cast, movie.title()));
//              currentBestResults = results;
//              System.out.println("Better than our current: " + currentBestResults.movies.size());
//
//              if(currentBestResults.movies.size() == 2) {
//                // Cannot get better than one movie below our own.  Just bail out now.
//                return currentBestResults;
//              }
//            }
//          }
//        }
//      }
//
//      return currentBestResults;
//    }

    return null;
  }

  public record SearchEntry(String originator, String other, String title) {
  }
}
