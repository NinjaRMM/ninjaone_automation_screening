package com.ninjaone.filtermovies.service.impl;

import com.ninjaone.filtermovies.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class MovieFilterServiceImplTest {

    private static final String DECADE_EXCEPTION_MSG = "The value of the decade must range from 0 to 90 and must be a multiple of 10";
    private MovieFilterServiceImpl filterService = new MovieFilterServiceImpl();

    @Test
    void filter_withNullList_shouldReturnNull() {
        List<Movie> movies = filterService.filter(10, null);
        Assertions.assertNull(movies);
    }

    @Test
    void filter_withInvalidDecadeValues_shouldReturnException() {
        // Null decade value
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> filterService.filter(null, List.of()));
        Assertions.assertTrue(exception.getMessage().contentEquals(DECADE_EXCEPTION_MSG));

        // Non-multiple of 10 value
        exception = Assertions.assertThrows(RuntimeException.class, () -> filterService.filter(18, List.of()));
        Assertions.assertTrue(exception.getMessage().contentEquals(DECADE_EXCEPTION_MSG));

        // Greater than 90 value
        exception = Assertions.assertThrows(RuntimeException.class, () -> filterService.filter(100, List.of()));
        Assertions.assertTrue(exception.getMessage().contentEquals(DECADE_EXCEPTION_MSG));

        // Less than 0 value
        exception = Assertions.assertThrows(RuntimeException.class, () -> filterService.filter(-10, List.of()));
        Assertions.assertTrue(exception.getMessage().contentEquals(DECADE_EXCEPTION_MSG));
    }

    @Test
    void filter_withDecadeValueIncludindTwoCenturies_shouldReturnDataFromBothCenturies() {
        List<Movie> movies = List.of(
                Movie.builder().title("Movie 1").year(1918).build(),
                Movie.builder().title("Movie 2").year(1990).build(),
                Movie.builder().title("Movie 3").year(1915).build(),
                Movie.builder().title("Movie 4").year(2000).build(),
                Movie.builder().title("Movie 5").year(2018).build(),
                Movie.builder().title("Movie 6").year(2013).build()
        );

        List<Integer> filteredYears = filterService.filter(10, movies)
                                                .stream()
                                                .map(m -> m.getYear())
                                                .collect(Collectors.toList());


        assertThat(filteredYears)
            .hasSize(4)
            .containsExactlyInAnyOrder(1918, 1915, 2018, 2013);
    }

    @Test
    void filter_withDecadeValue_shouldReturnMoviesBelongingOnlyToThatDecade() {
        List<Movie> movies = List.of(
                Movie.builder().title("Movie 1").year(1989).build(),
                Movie.builder().title("Movie 2").year(1990).build(),
                Movie.builder().title("Movie 3").year(1991).build(),
                Movie.builder().title("Movie 4").year(1992).build(),
                Movie.builder().title("Movie 5").year(1993).build(),
                Movie.builder().title("Movie 6").year(1994).build(),
                Movie.builder().title("Movie 7").year(1995).build(),
                Movie.builder().title("Movie 8").year(1996).build(),
                Movie.builder().title("Movie 9").year(1997).build(),
                Movie.builder().title("Movie 10").year(1998).build(),
                Movie.builder().title("Movie 11").year(1999).build(),
                Movie.builder().title("Movie 12").year(2000).build()
        );

        List<Integer> filteredYears = filterService.filter(90, movies)
                                                    .stream()
                                                    .map(m -> m.getYear())
                                                    .collect(Collectors.toList());


        assertThat(filteredYears)
                .hasSize(10)
                .containsExactlyInAnyOrder(1990, 1991, 1992, 1993, 1994,
                                            1995, 1996, 1997, 1998, 1999);
    }
}