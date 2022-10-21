package com.ninjaone.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ninjaone.model.Movies;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoviesService {

    private static final int MIN_CENTURY_20 = 1900;
    private static final int MIN_CENTURY_21 = 2000;
    private static final int DECADE_YEARS = 10;
    private final Logger log = Logger.getLogger(MoviesService.class.getName());
    private List<Movies> movies;
    private List<Movies> filteredMovies;
    private int firstYear;
    private int lastYear;
    private int decade;
    private String sourceFilename;

    public List<Movies> getMovies() {
        return movies;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
    }

    public int getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(int firstYear) {
        this.firstYear = firstYear;
    }

    public int getLastYear() {
        return lastYear;
    }

    public void setLastYear(int lastYear) {
        this.lastYear = lastYear;
    }

    public List<Movies> getFilteredMovies() {
        return filteredMovies;
    }

    public void setFilteredMovies(List<Movies> filteredMovies) {
        this.filteredMovies = filteredMovies;
    }

    public int getDecade() {
        return decade;
    }

    public void setDecade(int decade) {
        this.decade = decade;
    }

    public String getSourceFilename() {
        return sourceFilename;
    }

    public void setSourceFilename(String sourceFilename) {
        this.sourceFilename = sourceFilename;
    }

    /**
     * Get the first year of a decade in the 20th century
     * @param year
     * @return the first year of a decade
     */
    private static int getStartYearOfDecadeCentury20(int year){
        return ((MIN_CENTURY_20+year) / DECADE_YEARS) * DECADE_YEARS;
    }

    private static int getLastYearOfDecadeCentury(int year){
        return year + DECADE_YEARS;
    }

    private static int getStartYearOfDecadeCentury21(int year){
        return ((MIN_CENTURY_21+year) / DECADE_YEARS) * DECADE_YEARS;
    }

    public List<Movies> getMoviesByFilename(String filename){
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try {
            return mapper.readValue(classloader.getResource(filename), new TypeReference<List<Movies>>(){});
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error reading file :", e);
        }

        return Collections.emptyList();
    }

    public List<Movies> getMoviesByDecade(int decade, List<Movies> movies){
        log.log(Level.INFO, "Getting movies for decade ", decade);
        List<Movies> moviesDecade = new ArrayList<>();
        int minYear20 = getStartYearOfDecadeCentury20(decade);
        int maxYear20 = getLastYearOfDecadeCentury(minYear20);
        //int minYear21 = getStartYearOfDecadeCentury21(decade);
        //int maxYear21 = getLastYearOfDecadeCentury(minYear21);

        for(Movies movie: movies){

            if (movie.getYear() >= minYear20 && movie.getYear() < maxYear20){
                moviesDecade.add(movie);
            }
        }
        setFirstYear(minYear20);
        setLastYear(maxYear20-1);
        return moviesDecade;
    }

    public void generateMoviesFile(List<Movies> movies, String filename) {
        log.log(Level.INFO, "Writing file ", filename);
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File("../../../data/"+ filename), movies);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error generating file:", e);
        }
    }
}
