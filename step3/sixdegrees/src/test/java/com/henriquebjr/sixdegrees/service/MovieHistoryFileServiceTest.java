package com.henriquebjr.sixdegrees.service;

import com.henriquebjr.sixdegrees.model.Movie;
import com.henriquebjr.sixdegrees.model.MovieHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

public class MovieHistoryFileServiceTest {

    public static final String DEFAULT_FILE_NAME = "movies.json";

    @InjectMocks
    private MovieHistoryFileService movieHistoryFileService;

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
    public void validateDefaultFile() {
        var result = defaultFile.exists();
        assertThat(result).isTrue();
    }

    @Test
    public void testLoadFromFile() {
        MovieHistory result = movieHistoryFileService.loadFromFile(defaultFile);
        assertThat(result).isNotNull();
        assertThat(result.getMovies()).isNotNull();
        assertThat(result.getMovies().size()).isEqualTo(28795);
        assertThat(result.getMovies().get(0).getTitle()).isEqualTo("After Dark in Central Park");
        assertThat(result.getMovies().get(0).getYear()).isEqualTo(1900);
        assertThat(result.getMovies().get(0).getCast()).isEmpty();
        assertThat(result.getMovies().get(0).getGenres()).isEmpty();
        assertThat(result.getMovies().get(28794).getTitle()).isEqualTo("Destroyer");
        assertThat(result.getMovies().get(28794).getYear()).isEqualTo(2018);
        assertThat(result.getMovies().get(28794).getCast()).isNotEmpty();
        assertThat(result.getMovies().get(28794).getCast()).contains("Nicole Kidman", "Tatiana Maslany", "Sebastian Stan", "Toby Kebbell", "Scoot McNairy");
        assertThat(result.getMovies().get(28794).getGenres()).isNotEmpty();
        assertThat(result.getMovies().get(28794).getGenres()).contains("Crime", "Thriller");
    }

    @Test
    public void testStoreInFile() {
        MovieHistory movieHistory = new MovieHistory();
        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setYear(1931);
        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setYear(1932);
        movieHistory.setMovies(Arrays.asList(movie1, movie2));

        String fileName = "teste_" + new Date().getTime() + ".json";

        File result = movieHistoryFileService.storeInFile(movieHistory, fileName);
        assertThat(result).isNotNull();
        assertThat(result).exists();
        // Poderia melhorar estes testes para comprarar json como arquivo texto

        result.delete();
    }
}
