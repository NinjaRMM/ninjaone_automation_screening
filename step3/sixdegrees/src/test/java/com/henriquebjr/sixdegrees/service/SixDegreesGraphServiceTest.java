package com.henriquebjr.sixdegrees.service;

import com.henriquebjr.sixdegrees.model.Movie;
import com.henriquebjr.sixdegrees.model.MovieHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.io.File;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

public class SixDegreesGraphServiceTest {

    public static final String DEFAULT_FILE_NAME = "movies.json";

    @InjectMocks
    private SixDegreesGraphService sixDegreesGraphService;

    private File defaultFile;

    @BeforeEach
    public void setup() {
        openMocks(this);
        configureDefaultFile();
    }

    private void configureDefaultFile() {
        String filePath = MovieHistoryFileServiceTest.class.getClassLoader().getResource(DEFAULT_FILE_NAME).getFile();
        defaultFile = new File(filePath);
    }

    @Test
    public void testCalculateSimpleScenario() throws Exception {
        var movieHistory = new MovieHistory();
        var movie1 = Movie.Builder.of()
                .title("Movie 1")
                .cast(asList("A", "B", "C"))
                .build();
        movieHistory.getMovies().addAll(asList(movie1));

        var result = sixDegreesGraphService.calculate(movieHistory, "A", "C");
        assertThat(result.getDistance()).isEqualTo(1);
    }

    @Test
    public void testCalculate() throws Exception {
        var movieHistory = new MovieHistory();
        var movie1 = Movie.Builder.of()
                .title("Movie 1")
                .cast(asList("A", "B", "C"))
                .build();
        var movie2 = Movie.Builder.of()
                .title("Movie 2")
                .cast(asList("C", "D", "E"))
                .build();
        var movie3 = Movie.Builder.of()
                .title("Movie 3")
                .cast(asList("E", "F", "G"))
                .build();
        var movie4 = Movie.Builder.of()
                .title("Movie 4")
                .cast(asList("G", "H", "I"))
                .build();
        movieHistory.getMovies().addAll(asList(movie1, movie2, movie3, movie4));

        var result = sixDegreesGraphService.calculate(movieHistory, "A", "I");
        assertThat(result.getDistance()).isEqualTo(4);
    }

    @Test
    public void testCalculateWithOtherMovies() throws Exception {
        var movieHistory = new MovieHistory();
        var movie1 = Movie.Builder.of()
                .title("Movie 1")
                .cast(asList("A", "B", "C"))
                .build();
        var movie2 = Movie.Builder.of()
                .title("Movie 2")
                .cast(asList("C", "D", "E"))
                .build();
        var movie3 = Movie.Builder.of()
                .title("Movie 3")
                .cast(asList("E", "F", "G"))
                .build();
        var movie4 = Movie.Builder.of()
                .title("Movie 4")
                .cast(asList("G", "H", "I"))
                .build();
        var movie5 = Movie.Builder.of()
                .title("Movie 5")
                .cast(asList("I", "J", "K"))
                .build();
        var movie6 = Movie.Builder.of()
                .title("Movie 6")
                .cast(asList("L", "M", "N"))
                .build();
        var movie7 = Movie.Builder.of()
                .title("Movie 7")
                .cast(asList("A", "B", "Z"))
                .build();
        var movie8 = Movie.Builder.of()
                .title("Movie 8")
                .cast(asList("Z", "X", "Y"))
                .build();
        movieHistory.getMovies().addAll(asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8));

        var result = sixDegreesGraphService.calculate(movieHistory, "A", "I");
        assertThat(result.getDistance()).isEqualTo(4);
    }
}
