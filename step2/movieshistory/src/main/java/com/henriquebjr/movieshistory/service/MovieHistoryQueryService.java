package com.henriquebjr.movieshistory.service;

import com.henriquebjr.movieshistory.model.Movie;
import com.henriquebjr.movieshistory.model.MovieHistory;
import org.springframework.stereotype.Service;

@Service
public class MovieHistoryQueryService {

    public MovieHistory filterByDecade(MovieHistory movieHistory, int decade) {
        int initialPeriod = 1900 + decade;
        int finalPeriod = 1909 + decade;

        MovieHistory newMovieHistory = new MovieHistory();

        for(Movie movie : movieHistory.getMovies()) {
            if(movie.getYear() >= initialPeriod && movie.getYear() <= finalPeriod) {
                newMovieHistory.getMovies().add(movie);
            }
        }

        return newMovieHistory;
    }

}
