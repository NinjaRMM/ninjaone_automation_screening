package com.ninjaone.sixdegree.service.impl;

import com.ninjaone.sixdegree.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SixDegreesServiceTest {

    private SixDegreesService service = new SixDegreesService();

    @Test
    void calculateDegree_withNonExistingPerson1_shouldReturnNull() {
        List<Movie> movies = List.of(
                Movie.builder().title("Movie 1").cast(List.of("Actor 1", "Actress 2")).build(),
                Movie.builder().title("Movie 2").cast(List.of("Actor 2", "Actress 3")).build(),
                Movie.builder().title("Movie 3").cast(List.of("Actor 5", "Actress 4")).build()
        );

        List<String> result = service.calculateDegree("Person 1", "Actor 1", movies);
        Assertions.assertNull(result);
    }

    @Test
    void calculateDegree_withNonExistingPerson2_shouldReturnNull() {
        List<Movie> movies = List.of(
                Movie.builder().title("Movie 1").cast(List.of("Actor 1", "Actress 2")).build(),
                Movie.builder().title("Movie 2").cast(List.of("Actor 2", "Actress 3")).build(),
                Movie.builder().title("Movie 3").cast(List.of("Actor 5", "Actress 4")).build()
        );

        List<String> result = service.calculateDegree("Actor 5", "Actor X", movies);
        Assertions.assertNull(result);
    }

    @Test
    void calculateDegree_withoutPerson2_shouldReplaceToKevinBacon() {
        List<Movie> movies = List.of(
                Movie.builder().title("Movie 1").cast(List.of("Actor 1", "Actor 5")).build(),
                Movie.builder().title("Movie 2").cast(List.of("Actor 2", "Actress 3")).build(),
                Movie.builder().title("Movie 3").cast(List.of("Actor 5", "Kevin Bacon")).build()
        );

        List<String> result = service.calculateDegree("Actor 1", null, movies);
        assertThat(result).hasSize(3).containsExactlyInAnyOrder("Actor 1", "Actor 5", "Kevin Bacon");
    }

    @Test
    void calculateDegree_withAllPeople_shouldReturnExpectedResult() {
        List<Movie> movies = List.of(
                Movie.builder().title("Movie 1").cast(List.of("Actor 1", "Actor 5")).build(),
                Movie.builder().title("Movie 2").cast(List.of("Actor 5", "Actress 6")).build(),
                Movie.builder().title("Movie 2").cast(List.of("Actor 3", "Actress 4")).build(),
                Movie.builder().title("Movie 3").cast(List.of("Actress 6", "Actress X")).build()
        );

        List<String> result = service.calculateDegree("Actor 1", "Actress X", movies);
        assertThat(result).hasSize(4).containsExactlyInAnyOrder("Actor 1", "Actor 5", "Actress 6", "Actress X");
    }
}