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
        Movie m1 = Movie.newBuilder().title("The Outsiders")
                        .cast("Ralph Macchio")
                                .cast("Tom Cruise").build();
         Movie m2 = Movie.newBuilder().title("Distant Thunders")
                         .cast("John Lithgow")
                                 .cast("Ralph Macchio")
                                         .build();
         Movie m3 = Movie.newBuilder().title("Footloose")
                         .cast("Kevin Bacon")
                                 .cast("John Lithgow").build();
        movies.add(m1);
        movies.add(m2);
        movies.add(m3);

    }

    @Test
    void shouldReturn() {
        assertEquals(2,Movie.getNumberOfDegreesBetween(movies, "Tom Cruise", "Kevin Bacon"));
    }
}
