package com.ninja.one.degreeofseparations.service.impl;

import com.ninja.one.degreeofseparations.model.Graph;
import com.ninja.one.degreeofseparations.model.Movie;
import com.ninja.one.degreeofseparations.model.exception.ApiException;
import com.ninja.one.degreeofseparations.repository.MovieRepository;
import com.ninja.one.degreeofseparations.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private int degree;
    private List<Graph> graphList;
    private StringBuilder track;

    @Override
    public List<Movie> extractMovieFromDecade(Integer decade) throws ApiException {
        return movieRepository.getMovieFromDecade(decade);
    }

    @Override
    public String calculateDegreeOfSeparation(String actor1, String actor2) throws ApiException {
        degree = -1;
        track = new StringBuilder();
        graphList = new ArrayList<>();
        if (!StringUtils.hasLength(actor2)) {
            actor2 = "Kevin Bacon";
        }
        List<Movie> movieList = movieRepository.getMovies80Decade();
        findRecursivelyDegreeOfSeparation(movieList, actor1, actor2);
        track.insert(0, "Degree of Separation: " + degree);
        if (degree == 7) {
            track.append("\nThe Degree of separation is bigger than 6");
        }
        return track.toString();
    }

    private int findRecursivelyDegreeOfSeparation(List<Movie> allMovies, String actor1, String actor2) {
        if (degree == -1) {
            if (!validations(allMovies, actor1, actor2)) {
                return -1;
            }
            List<Movie> moviesWithActor1 = allMovies.stream().filter(movie -> movie.getCast().contains(actor1)).collect(Collectors.toList());
            Graph graph = Graph.builder()
                    .actorName(actor1)
                    .build();
            boolean hasCorrelation = existCorrelation(moviesWithActor1, graph, actor2);
            if (hasCorrelation) {
                return ++degree;
            } else {
                degree++;
                return findRecursivelyDegreeOfSeparation(allMovies, actor1, actor2);
            }
        } else {
            List<Graph> previousGraph = graphList.stream().filter(graph -> graph.getDegree() == degree).collect(Collectors.toList());
            for (Graph graph : previousGraph) {
                List<Movie> moviesWithActorLastDegree = allMovies.stream().filter(movie -> movie.getCast().contains(graph.getActorName())).collect(Collectors.toList());
                boolean hasCorrelation = existCorrelation(moviesWithActorLastDegree, graph, actor2);
                if (hasCorrelation) {
                    return ++degree;
                }
            }
            degree++;
            if (degree == 7) {
                return -1;
            }
            return findRecursivelyDegreeOfSeparation(allMovies, actor1, actor2);
        }
    }

    private boolean validations(List<Movie> allMovies, String actor1, String actor2) {
        if (allMovies.parallelStream().noneMatch(movie -> movie.getCast().contains(actor1))) {
            track.insert(0, "\n" + actor1 + " did not star in a movie in the data provided.");
            return false;
        }
        if (allMovies.parallelStream().noneMatch(movie -> movie.getCast().contains(actor2))) {
            track.insert(0, "\n" + actor2 + " did not star in a movie in the data provided.");
            return false;
        }
        if (actor1.equals(actor2)) {
            track.insert(0, "\nActor1 and Actor2 are the same.");
            return false;
        }
        return true;
    }

    private boolean existCorrelation(List<Movie> moviesWithActorLastDegree, Graph graph, String actor2) {
        boolean hasCorrelation = moviesWithActorLastDegree.parallelStream().anyMatch(movie -> movie.getCast().contains(actor2));
        if (hasCorrelation) {
            Movie movieStarred = moviesWithActorLastDegree.stream().filter(movie -> movie.getCast().contains(actor2)).findFirst().orElse(null);
            assert movieStarred != null;
            track.insert(0, "\n" + graph.getActorName() + " starred with " + actor2 + " in " + movieStarred.getTitle());
            if (degree != -1) {
                trackMovie(graph);
            }
            return true;
        } else {
            moviesWithActorLastDegree.forEach(movie -> movie.getCast().remove(graph.getActorName()));
            moviesWithActorLastDegree.forEach(movie -> movie.getCast()
                    .forEach(actorCurrentDegree -> graphList.add(
                            Graph.builder()
                                    .degree(degree+1)
                                    .movieName(movie.getTitle())
                                    .actorName(actorCurrentDegree)
                                    .previousContact(graph.getActorName())
                                    .build()
                    )));
            return false;
        }
    }

    private void trackMovie(Graph graph) {
        if (graph.getDegree() > 0) {
            Graph previous = graphList.stream().filter(previousGraph -> graph.getPreviousContact().equals(previousGraph.getActorName())).findFirst().get();
            track.insert(0, "\n" + previous.getActorName() + " starred with " + graph.getActorName() + " in " + graph.getMovieName());
            trackMovie(previous);
        } else {
            track.insert(0, "\n" + graph.getPreviousContact() + " starred with " + graph.getActorName() + " in " + graph.getMovieName());
        }
    }
}
