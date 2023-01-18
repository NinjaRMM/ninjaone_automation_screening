package com.ninjaone.step3.model.graph;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ninjaone.step3.model.Movie;

@SpringBootTest
public class CastGraphTest {

    private CastGraph castGraph;

    private Movie theOutsiders = Movie.builder()
                                      .title("The Outsiders")
                                      .year(1983)
                                      .cast(new HashSet<String>(Arrays.asList(
                                          "C. Thomas Howell",
                                          "Matt Dillon",
                                          "Diane Lane",
                                          "Patrick Swayze",
                                          "Ralph Macchio",
                                          "Rob Lowe",
                                          "Tom Cruise")))
                                      .genres(new HashSet<String>(Arrays.asList("Drama")))
                                      .build();

    private Movie distantThunder = Movie.builder()
                                         .title("Distant Thunder")
                                        .year(1988)
                                        .cast(new HashSet<String>(Arrays.asList(
                                            "John Lithgow",
                                            "Ralph Macchio")))
                                        .genres(new HashSet<String>(Arrays.asList("Drama")))
                                        .build();

    private Movie footloose = Movie.builder()
                                   .title("Footloose")
                                   .year(1984)
                                   .cast(new HashSet<String>(Arrays.asList(
                                       "Kevin Bacon",
                                       "John Lithgow",
                                       "Lori Singer",
                                       "Dianne Wiest",
                                       "Chris Penn")))
                                   .genres(new HashSet<String>(Arrays.asList(
                                       "Drama",
                                       "Musical")))
                                   .build();

    @Test
    void givenActorsWith2DegreesOfSeparation_whenGetDegreesOfSeparation_shouldReturnListWith4ElementsCorrectlyLinked() {

        // given: theOutsiders, distantThunder, footloose - when
        castGraph = new CastGraph(Arrays.asList(theOutsiders, distantThunder, footloose));

        List<ActorVertex> result = castGraph.getDegreesOfSeparation("Tom Cruise", "Kevin Bacon");

        // should
        assertEquals(result.size(), 4);

        assertEquals(result.get(0).getActor(), "Tom Cruise");
        assertEquals(result.get(1).getActor(), "Ralph Macchio");
        assertEquals(result.get(2).getActor(), "John Lithgow");
        assertEquals(result.get(3).getActor(), "Kevin Bacon");
    }

    @Test
    void givenActorThatDidNotStar_whenGetDegreesOfSeparation_shouldThrowException() {

        // given
        castGraph = new CastGraph(Collections.emptyList());

        // when - should
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> castGraph.getDegreesOfSeparation("Buzz Lightyear", "Kevin Bacon"))
                .withMessage("Buzz Lightyear did not star in a movie in the data provided.");
    }

    @Test
    void given2IsolatedActors_whenGetDegreesOfSeparation_shouldReturnEmptyList() {

        // given: theOutsiders, footloose - when
        castGraph = new CastGraph(Arrays.asList(theOutsiders, footloose));

        List<ActorVertex> result = castGraph.getDegreesOfSeparation("Tom Cruise", "Kevin Bacon");

        // should
        assertEquals(result.size(), 0);
    }
}
