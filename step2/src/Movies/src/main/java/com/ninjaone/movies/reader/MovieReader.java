package com.ninjaone.movies.reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninjaone.movies.domain.Movie;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MovieReader extends AbstractReader {
    public static Movie readOneFrom(String path) throws IOException {
        String jsonText = readJson(path);
        Type collectionType = new TypeToken<Movie>(){}.getType();
        return new Gson().fromJson(jsonText,collectionType);
    }

    public static List<Movie> readListFrom(String path) throws IOException {
        String jsonText = readJson(path);
        Type collectionType = new TypeToken<List<Movie>>(){}.getType();
        return new Gson().fromJson(jsonText, collectionType);

    }
}
