package io.automation;

import com.google.gson.reflect.TypeToken;
import io.automation.entities.Movie;
import io.automation.service.MoviesManager;

import java.util.List;

public class MoviesFilter {
    private String jsonFileName;
    private static final Environment ENV = Environment.getInstance();
    private final MoviesManager manager;

    public MoviesFilter(String jsonFileName) {

        String pathFile = PathHelper.getPathFile(jsonFileName);
        manager = new MoviesManager(JsonHelper.getObjectsFromFile(pathFile, new TypeToken<List<Movie>>() {
        }.getType()));
    }

    public void buildFileMoviesByDecade(int decade, String jsonFileName) {
        List<Movie> movies = manager.getMoviesByDecade(decade);
        JsonHelper.writeJson(movies, PathHelper.getPathFile(jsonFileName));
    }
}
