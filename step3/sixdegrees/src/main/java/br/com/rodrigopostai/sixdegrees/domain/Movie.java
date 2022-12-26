package br.com.rodrigopostai.sixdegrees.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Movie implements Serializable {

    public static final String TITLE = "title";
    public static final String YEAR = "year";
    public static final String CAST = "cast";
    public static final String GENRES = "genres";

    private String title;
    private int year;
    private Set<String> cast;
    private Set<String> genres;

    private Movie(String title,
                  int year,
                  Set<String> cast,
                  Set<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    @JsonCreator
    public Movie(@JsonProperty("title") String title,
                 @JsonProperty("year") int year,
                 @JsonProperty("cast") List<String> cast,
                 @JsonProperty("genres") List<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = CollectionUtils.isNotEmpty(cast) ? cast.stream().collect(Collectors.toSet()) : Collections.emptySet();
        this.genres = CollectionUtils.isNotEmpty(genres) ?  genres.stream().collect(Collectors.toSet()) : Collections.emptySet();
    }

    public static MovieBuilder newBuilder() {
        return new MovieBuilder();
    }

    public String getTitle() {
        return StringUtils.defaultIfBlank(title,"");
    }

    public int getYear() {
        return year;
    }

    public Set<String> getCast() {
        if (cast != null) {
            return Collections.unmodifiableSet(cast);
        }
        return Collections.emptySet();
    }

    public Set<String> getGenres() {
        if (genres != null) {
            return Collections.unmodifiableSet(genres);
        }
        return Collections.emptySet();
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", cast=" + cast +
                ", genre=" + genres +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Movie movie = (Movie) o;

        return new EqualsBuilder().append(year, movie.year).append(title, movie.title).append(cast, movie.cast)
                .append(genres, movie.genres).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(title).append(year).append(cast).append(genres).toHashCode();
    }

    public static class MovieBuilder {
        private String title;
        private int year;
        private Set<String> cast;
        private Set<String> genre;

        private MovieBuilder() {
        }

        public MovieBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MovieBuilder year(int year) {
            this.year = year;
            return this;
        }

        public MovieBuilder cast(String cast) {
            if (this.cast == null) {
                this.cast = new HashSet<>();
            }
            this.cast.add(cast);
            return this;
        }

        public MovieBuilder genre(String genre) {
            if (this.genre == null) {
                this.genre = new HashSet<>();
            }
            this.genre.add(genre);
            return this;
        }

        public Movie build() {
            return new Movie(title, year, cast, genre);
        }
    }

    public static List<MovieGraph> getRelations(List<Movie> movies, String actor1, String actor2) {
        if (CollectionUtils.isNotEmpty(movies)) {
            return createMovieGraph(movies, actor1, actor2);
        }
        return null;
    }

    private static List<MovieGraph> createMovieGraph(List<Movie> movies, String actor1, String actor2) {
        List<Movie> moviesStarredByActor1 = movies.parallelStream().filter(movie -> movie.isStarredBy(actor1)).collect(Collectors.toList());
        List<MovieGraph> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moviesStarredByActor1)) {
            for (Movie movie : moviesStarredByActor1) {
                MovieGraph.MovieGraphBuilder builder = MovieGraph.newBuilder();
                builder.movie(movie);
                if (movie.isStarredBy(actor2)) {
                    builder.movie(movie);
                    builder.actor(actor1);
                    builder.setFoundActor(true);
                    result.add(builder.build());
                } else {
                    Set<String> cast = movie.getCastExcept(actor1);
                    List<Movie> moviesNotStarredByActor1 = movies.parallelStream().filter(m -> !m.isStarredBy(actor1)).collect(Collectors.toList());
                    for (String actor: cast) {
                        List<MovieGraph> movieGraph = createMovieGraph(moviesNotStarredByActor1, actor, actor2);
                        if (!movieGraph.isEmpty()) {
                            builder.actor(actor);
                            builder.relatedMovies(movieGraph);
                            result.add(builder.build());
                        }
                    }
                }
            }
        }
        return result;
    }

    private boolean isStarredBy(String actor) {
        if (CollectionUtils.isNotEmpty(cast)) {
            return cast.stream().anyMatch(c -> c.equals(actor));
        }
        return false;
    }

    private Set<String> getCastExcept(String actor) {
        if (CollectionUtils.isNotEmpty(cast)) {
            return cast.stream().filter(c -> !c.equalsIgnoreCase(actor)).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }


}
