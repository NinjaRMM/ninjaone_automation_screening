package com.henriquebjr.sixdegrees.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriquebjr.sixdegrees.model.Movie;
import com.henriquebjr.sixdegrees.model.MovieHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MovieHistoryFileService {

    private static Logger LOG = LoggerFactory.getLogger(MovieHistoryFileService.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    public MovieHistory loadFromFile(File file) {
        MovieHistory movieHistory = new MovieHistory();
        List<Movie> movies = null;
        try {
            movies = objectMapper.readValue(file, new TypeReference<>(){});
            movieHistory.setMovies(movies);
        } catch (IOException e) {
            LOG.error("FAIL FILE JSON READING.");
            throw new RuntimeException(e);
        }

        return movieHistory;
    }

    public File storeInFile(MovieHistory movieHistory, String fileName) {
        verifyDataFolder();

        File file = new File("data/" + fileName);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, movieHistory.getMovies());
        } catch (IOException e) {
            LOG.error("FAIL FILE JSON WRITING.");
            throw new RuntimeException(e);
        }
        return file;
    }

    private void verifyDataFolder() {
        File folder = new File("data");
        if(!folder.exists()) {
            folder.mkdirs();
        }
    }
}
