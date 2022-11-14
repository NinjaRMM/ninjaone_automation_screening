package com.henriquebjr.movieshistory.service;

import com.henriquebjr.movieshistory.model.MovieHistory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;

public class MovieHistoryServiceTest {

    @InjectMocks
    private MovieHistoryService movieHistoryService;
    @Mock
    private MovieHistoryFileService movieHistoryFileService;
    @Mock
    private MovieHistoryQueryService movieHistoryQueryService;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    public void testFilterByDecade() {
        ArgumentCaptor<MovieHistory> movieHistoryArgumentCaptor1 = ArgumentCaptor.forClass(MovieHistory.class);
        ArgumentCaptor<MovieHistory> movieHistoryArgumentCaptor2 = ArgumentCaptor.forClass(MovieHistory.class);
        MovieHistory movieHistory = new MovieHistory();
        MovieHistory movieHistoryFiltered = new MovieHistory();
        doReturn(movieHistory).when(movieHistoryFileService).loadFromFile(any(File.class));
        doReturn(movieHistoryFiltered).when(movieHistoryQueryService).filterByDecade(any(MovieHistory.class), anyInt());

        File file = new File("anything.json");
        movieHistoryService.filterByDecade(file, 70);

        verify(movieHistoryFileService).loadFromFile(file);
        verify(movieHistoryQueryService).filterByDecade(movieHistoryArgumentCaptor1.capture(), eq(70));
        verify(movieHistoryFileService).storeInFile(movieHistoryArgumentCaptor2.capture(), eq("70s-movies.json"));
        verifyNoMoreInteractions(movieHistoryFileService);
        Assertions.assertThat(movieHistoryArgumentCaptor1.getValue()).isEqualTo(movieHistory);
        Assertions.assertThat(movieHistoryArgumentCaptor2.getValue()).isEqualTo(movieHistoryFiltered);
    }
}
