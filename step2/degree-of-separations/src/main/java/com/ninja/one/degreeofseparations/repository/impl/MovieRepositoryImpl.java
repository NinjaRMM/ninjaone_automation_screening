package com.ninja.one.degreeofseparations.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja.one.degreeofseparations.model.Movie;
import com.ninja.one.degreeofseparations.model.exception.ApiException;
import com.ninja.one.degreeofseparations.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MovieRepositoryImpl implements MovieRepository {

    private final ObjectMapper mapper;

    @Override
    public List<Movie> getMovieFromDecade(Integer decade) throws ApiException {
        Integer startYear = 1900 + decade;
        Integer finishYear = 1909 + decade;
        Path currentRelativePath = Paths.get("");
        String dataPath = currentRelativePath.toAbsolutePath().getParent().getParent().toString()
                + File.separator + "data" + File.separator;
        try {
            FileInputStream in = new FileInputStream(dataPath + "movies.json");
            JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
            List<Movie> movieList = extractMovieList(startYear, finishYear, jsonNode);
            if (movieList.isEmpty()) {
                String message = String.format("Movies not found from %d until %d%n", startYear, finishYear);
                log.error(message);
                throw ApiException.builder()
                        .message(message)
                        .statusCode(400)
                        .build();
            }
            mapper.writeValue(new File(dataPath + decade + "s-movies.json"), movieList);
            return movieList;
        } catch (FileNotFoundException fileNotFoundException) {
            throw ApiException.builder()
                    .message("Be sure the file movies.json exist in data directory.")
                    .statusCode(500)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movie> getMovies80Decade() throws ApiException {
        Path currentRelativePath = Paths.get("");
        String dataPath = currentRelativePath.toAbsolutePath().getParent().getParent().toString()
                + File.separator + "data" + File.separator;
        try {
            FileInputStream in = new FileInputStream(dataPath + "80s-movies.json");
            JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
            List<Movie> movieList = new ArrayList<>();
            for (int x = 0; x < jsonNode.size(); x++) {
                String stringMovie = mapper.writeValueAsString(jsonNode.get(x));
                Movie movie = mapper.readValue(stringMovie, Movie.class);
                movieList.add(movie);
            }
            return movieList;
        } catch (FileNotFoundException fileNotFoundException) {
            throw ApiException.builder()
                    .message("Be sure the file 80s-movies.json exist in data directory.")
                    .statusCode(500)
                    .build();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Movie> extractMovieList(Integer startYear, Integer finishYear, JsonNode jsonNode) throws JsonProcessingException {
        List<Movie> movieList = new ArrayList<>();
        for (int x = 0; x < jsonNode.size(); x++) {
            String stringMovie = mapper.writeValueAsString(jsonNode.get(x));
            Movie movie = mapper.readValue(stringMovie, Movie.class);
            if (movie.getYear() >= startYear && movie.getYear() <= finishYear
                    && !movie.getTitle().contains("NinjaOne Easter Egg")) {
                movieList.add(movie);
            }
        }
        return movieList;
    }
}
