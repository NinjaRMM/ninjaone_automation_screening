package com.henriquebjr.movieshistory.service;

import com.henriquebjr.movieshistory.model.Movie;
import com.henriquebjr.movieshistory.model.MovieHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

public class MovieHistoryQueryServiceTest {

    @InjectMocks
    private MovieHistoryQueryService movieHistoryQueryService;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    public void testFilterByDecade() {
        MovieHistory movieHistory = new MovieHistory();

        Movie movie1 = Movie.Builder.of()
                .title("Movie 1909")
                .year(1909)
                .build();
        Movie movie2 = Movie.Builder.of()
                .title("Movie 1919")
                .year(1919)
                .build();
        Movie movie3 = Movie.Builder.of()
                .title("Movie 1920")
                .year(1920)
                .build();
        Movie movie4 = Movie.Builder.of()
                .title("Movie 1921")
                .year(1921)
                .build();
        Movie movie5 = Movie.Builder.of()
                .title("Movie 1928")
                .year(1928)
                .build();
        Movie movie6 = Movie.Builder.of()
                .title("Movie 1929")
                .year(1929)
                .build();
        Movie movie7 = Movie.Builder.of()
                .title("Movie 1930")
                .year(1930)
                .build();
        Movie movie8 = Movie.Builder.of()
                .title("Movie 1931")
                .year(1931)
                .build();
        movieHistory.getMovies().addAll(asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8));

        MovieHistory result = movieHistoryQueryService.filterByDecade(movieHistory, 20);

        assertThat(result.getMovies().size()).isEqualTo(4);
    }
}
