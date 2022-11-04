package com.testTask.ninja;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {
    private static final String VALIDATION_MESSAGE = "Invalid input value. Decade should be one of '20, 30, 40, 50, 60, 70, 80, 90'";
    private static final String RESULT_PATH = "data/%s";
    private static final String SOURCE_FILE_NAME = "movies.json";
    private static final String SOURCE_PATH = String.format(RESULT_PATH, SOURCE_FILE_NAME);
    private static String RESULT_FILE_NAME;

    public static String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Movie> readFileToList() {
        return getMovies(readFile(SOURCE_PATH));
    }

    public static List<Movie> readFileToList(String path) {
        return getMovies(readFile(path));
    }

    public static List<Movie> getMovies(String s) {
        Type listType = new TypeToken<ArrayList<Movie>>() {
        }.getType();
        return new Gson().fromJson(s, listType);
    }

    public static void writeToFile(String path, String fileContent) {
        try {
            FileUtils.writeStringToFile(new File(path), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Movie> getMoviesByDecade(List<Movie> all, int decade) {
        isInputValid(decade);

        List<Movie> result = new ArrayList<>();
        int from = 1900 + decade;
        int to = 1909 + decade;
        for (Movie movie : all) {
            if (movie.getYear() >= from & movie.getYear() <= to)
                result.add(movie);
        }
        return result;
    }

    private static void isInputValid(Integer decade) {
        List<Integer> validValues = List.of(20, 30, 40, 50, 60, 70, 80, 90);
        boolean contains = validValues.contains(decade);
        if (!contains)
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
    }

    public static void saveToFile(List<Movie> movies, String path, String fileName) {
        RESULT_FILE_NAME = fileName;
        String fileContent = new Gson().toJson(movies);
        writeToFile(String.format(path, RESULT_FILE_NAME), fileContent);
    }

    public static void saveToFile(List<Movie> movies, String fileName) {
        saveToFile(movies, RESULT_PATH, fileName);
    }

    public static void checkFilter(String path, int yearFrom, int yearTo) {
        List<Movie> moviesFromSource = readFileToList(String.format(path, RESULT_FILE_NAME));
        List<Movie> moviesFromResult = readFileToList(String.format(path, SOURCE_FILE_NAME));

        for (int i = yearFrom; i <= yearTo; i++) {
            int year = i;
            List<Movie> fromSource = moviesFromSource.stream().filter(movie -> movie.getYear() == year).collect(Collectors.toList());
            List<Movie> fromResult = moviesFromResult.stream().filter(movie -> movie.getYear() == year).collect(Collectors.toList());

            Assertions.assertEquals(fromSource.size(), fromResult.size());
            Assertions.assertEquals(fromSource, fromResult);
        }
    }
}