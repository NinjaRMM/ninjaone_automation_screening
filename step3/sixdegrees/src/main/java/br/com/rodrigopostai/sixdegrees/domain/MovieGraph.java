package br.com.rodrigopostai.sixdegrees.domain;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieGraph {

    private String actor;
    private Movie movie;
    private List<MovieGraph> relatedMovies;

    private MovieGraph() {

    }

    private MovieGraph(String actor, Movie movie, List<MovieGraph> relatedMovies) {
        this.actor = actor;
        this.movie = movie;
        this.relatedMovies = relatedMovies;
    }

    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        List<Movie> relatedMovies = getRelatedMovies(this.relatedMovies);
        if (CollectionUtils.isNotEmpty(relatedMovies)) {
            movies.addAll(relatedMovies);
        }
        return movies;
    }

    private List<Movie> getRelatedMovies(List<MovieGraph> relatedMovies) {
        List<Movie> movies = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(relatedMovies)) {
            for (MovieGraph relatedMovie : relatedMovies) {
                movies.add(relatedMovie.movie);
                List<Movie> children = getRelatedMovies(relatedMovie.relatedMovies);
                if (CollectionUtils.isNotEmpty(children)) {
                    movies.addAll(children);
                }
            }
        }
        return movies;
    }

    public static MovieGraphBuilder newBuilder() {
        return new MovieGraphBuilder();
    }

    public static class MovieGraphBuilder {
        private String actor;
        private Movie movie;
        private List<MovieGraph> relatedMovies = new ArrayList<>();
        private boolean foundActor;

        private MovieGraphBuilder() {

        }

        public MovieGraphBuilder actor(String actor) {
            this.actor = actor;
            return this;
        }

        public MovieGraphBuilder movie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public Movie getMovie() {
            return this.movie;
        }

        public MovieGraphBuilder relatedMovie(MovieGraph movie) {
            this.relatedMovies.add(movie);
            return this;
        }

        public MovieGraphBuilder relatedMovies(List<MovieGraph> movies) {
            this.relatedMovies.addAll(movies);
            return this;
        }

        public MovieGraph build() {
            Objects.requireNonNull(actor, "Actor is mandatory");
            Objects.requireNonNull(movie, "Movie is mandatory");
            return new MovieGraph(actor,movie,relatedMovies);
        }

        public void setFoundActor(boolean b) {
            this.foundActor = b;
        }
    }
}
