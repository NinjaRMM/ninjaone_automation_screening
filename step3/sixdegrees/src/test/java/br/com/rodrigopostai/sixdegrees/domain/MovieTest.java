package br.com.rodrigopostai.sixdegrees.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest {

    private List<Movie> movies;

    @BeforeEach
    public void setup() {
        movies = new ArrayList<>();
    }

    @Test
    void shouldReturn3DegreesBetweenTomCruiseAndKevinBacon() {
        Movie m1 = Movie.newBuilder().title("The Outsiders")
                .cast("Ralph Macchio")
                .cast("Tom Cruise").build();
        Movie m2 = Movie.newBuilder().title("Distant Thunders")
                .cast("John Lithgow")
                .cast("Ralph Macchio")
                .build();
        Movie m3 = Movie.newBuilder().title("No name")
                .cast("John Lithgow")
                .build();
        Movie m4 = Movie.newBuilder().title("Footloose")
                .cast("Kevin Bacon")
                .cast("John Lithgow").build();
        movies.add(m1);
        movies.add(m2);
        movies.add(m3);
        movies.add(m4);

        List<MovieGraph> relations
                = Movie.getRelations(movies, "Tom Cruise", "Kevin Bacon");
        assertEquals(1, relations.size());
        MovieGraph movieGraph = relations.get(0);
        List<Movie> relatedMovies = movieGraph.getMovies();
        assertEquals(3, relatedMovies.size());
        assertEquals("The Outsiders", relatedMovies.get(0).getTitle());
        assertEquals("Distant Thunders", relatedMovies.get(1).getTitle());
        assertEquals("Footloose", relatedMovies.get(2).getTitle());
    }

//    # There are 2 degrees of separation between Tom Cruise and Sylvester Stallone
//    # Tom Cruise starred with Tom Skerrit in Top Gun.
//    # Tom Skerrit starred with Dolly Parton in Steel Magnolias
//    # Dolly Parton starred with Sylvester Stallone in Rhinestone
    @Test
    void shouldReturn2DegreesBetweenTomCruiseAndSylvesterStallone() {

        Movie m1 = Movie.newBuilder().title("Top Gun")
                .cast("Tom Skerrit")
                .cast("Tom Cruise").build();
        Movie m2 = Movie.newBuilder().title("Steel Magnolias")
                .cast("Tom Skerrit")
                .cast("Dolly Parton")
                .build();
        Movie m3 = Movie.newBuilder().title("Rhinestone")
                .cast("Dolly Parton")
                .cast("Sylvester Stallone").build();
        movies.add(m1);
        movies.add(m2);
        movies.add(m3);

        List<MovieGraph> relations = Movie.getRelations(movies, "Tom Cruise", "Sylvester Stallone");
        assertEquals(1, relations.size());
        MovieGraph movieGraph = relations.get(0);
        List<Movie> relatedMovies = movieGraph.getMovies();
        assertEquals(3, relatedMovies.size());
        assertEquals("Top Gun", relatedMovies.get(0).getTitle());
        assertEquals("Steel Magnolias", relatedMovies.get(1).getTitle());
        assertEquals("Rhinestone", relatedMovies.get(2).getTitle());
    }

    // # Buzz Lightyear did not star in a movie in the data provided.
    @Test
    void shouldReturnNoMoviesBecauseTheActorDoesNotStarAnyMovie() {
        Movie m1 = Movie.newBuilder().title("Top Gun")
                .cast("Tom Skerrit")
                .cast("Tom Cruise").build();
        Movie m2 = Movie.newBuilder().title("Steel Magnolias")
                .cast("Tom Skerrit")
                .cast("Dolly Parton")
                .build();
        movies.add(m1);
        movies.add(m2);
        List<MovieGraph> relations = Movie.getRelations(movies, "Buzz Lightyear", "Kevin Bacon");
        assertEquals(0, relations.size());
    }

    // # Buzz Lightyear did not star in a movie in the data provided.
    @Test
    void shouldReturnNoMoviesBecauseTheSecondActorDoesNotStarAnyMovie() {
        Movie m1 = Movie.newBuilder().title("Top Gun")
                .cast("Tom Skerrit")
                .cast("Tom Cruise").build();
        Movie m2 = Movie.newBuilder().title("Steel Magnolias")
                .cast("Tom Skerrit")
                .cast("Dolly Parton")
                .build();
        movies.add(m1);
        movies.add(m2);
        List<MovieGraph> movieGraph = Movie.getRelations(movies, "Tom Skerrit", "Buzz Lightyear");
        assertEquals(0, movieGraph.size());
    }
}
