package com.ninjaone.step3.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.step3.model.Movie;

@SpringBootTest
public class MovieServiceImplTest {

    @Mock
    private ObjectMapper mapperMock;

    private Movie movie1 = Movie.builder().title("Title1").build(),
                  movie2 = Movie.builder().title("Title2").build(),
                  movie3 = Movie.builder().title("Title3").build();

    private MovieServiceImpl movieServiceImpl;

    @BeforeEach
    void setup() {
        movieServiceImpl = new MovieServiceImpl(mapperMock);
    }

    @Test
    void given3MoviesFrom80s_whenfilterMoviesByTimePeriod_withPeriod80To89_shouldReturn3Movies() {

        // given
        movie1.setYear(1980);
        movie2.setYear(1985);
        movie3.setYear(1989);

        // when
        Collection<Movie> result = movieServiceImpl.filterMoviesByTimePeriod(Arrays.asList(movie1, movie2, movie3), 1980, 1989);

        // should
        assertEquals(result.size(), 3);
    }

    @Test
    void givenOneMovieFrom70_andOneFrom80_andOneFrom90_whenfilterMoviesByTimePeriod_withPeriod80To89_shouldReturn1Movie() {

        // given
        movie1.setYear(1975);
        movie2.setYear(1985);
        movie3.setYear(1995);

        // when
        Collection<Movie> result = movieServiceImpl.filterMoviesByTimePeriod(Arrays.asList(movie1, movie2, movie3), 1980, 1989);

        // should
        assertEquals(result.size(), 1);
    }

    @Test
    void given3Movies_whenfilterMoviesByTimePeriod_withStartYear89_andEndYear80_shouldThrowException() {

        // given - when - should
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> movieServiceImpl.filterMoviesByTimePeriod(Arrays.asList(movie1, movie2, movie3), 1989, 1980));
    }
}
