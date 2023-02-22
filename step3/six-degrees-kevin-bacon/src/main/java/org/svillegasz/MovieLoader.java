package org.svillegasz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MovieLoader {
    public static List<Movie> loadMovies(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(filePath);
            return mapper.readValue(file, new TypeReference<List<Movie>>() {});
        } catch (IOException e) {
            System.err.println("Failed to load movies from file: " + e.getMessage());
            return null;
        }
    }
}
