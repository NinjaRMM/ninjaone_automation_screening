package com.ninjaone.step2.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.step2.model.Movie;
import com.ninjaone.step2.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

    private final ObjectMapper mapper;

    public MovieServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Movie> extractMoviesFromFile(File pathName) throws IOException {
        return mapper.readValue(pathName, mapper.getTypeFactory().constructCollectionType(List.class, Movie.class));
    }

    @Override
    public Collection<Movie> filterMoviesByTimePeriod(List<Movie> allMovies, Integer startYear, Integer endYear) {

        if (startYear > endYear) {
            throw new IllegalArgumentException("The start year can't be greater than the end year.");
        }

        List<Movie> filteredMovies = allMovies.stream()
                                              .filter(movie -> movie.getYear() >= startYear && movie.getYear() <= endYear)
                                              .collect(Collectors.toList());

        return filteredMovies;
    }

    @Override
    public void writeMoviesToFile(Collection<Movie> movies, File pathName) throws StreamWriteException, DatabindException, IOException {
        mapper.writeValue(pathName, movies);
    }
}
