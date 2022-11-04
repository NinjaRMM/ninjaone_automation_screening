package com.testTask.ninja;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testTask.ninja.objects.Movie;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    public static String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Movie> getMovies(String s) {
        Type listType = new TypeToken<ArrayList<Movie>>() {
        }.getType();
        return new Gson().fromJson(s, listType);
    }
}
