package com.henriquebjr.movieshistory.service;

import com.henriquebjr.movieshistory.model.MovieHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MovieHistoryService {

    @Autowired
    private MovieHistoryFileService movieHistoryFileService;
    @Autowired
    private MovieHistoryQueryService movieHistoryQueryService;

    public void filterByDecade(File file, Integer decade) {
        if(decade > 100) {
            // invalid decade
            // Poderia permitir decadas como 1900, 1910, 1920, etc.
            // Poderia também tratar para os anos 2000, como 2000, 2010, etc.
            return;
        }

        MovieHistory movieHistory = movieHistoryFileService.loadFromFile(file);
        MovieHistory newMovieHistory = movieHistoryQueryService.filterByDecade(movieHistory, decade);

        String newFileName = decade + "s-movies.json";
        movieHistoryFileService.storeInFile(newMovieHistory, newFileName);
    }

}
