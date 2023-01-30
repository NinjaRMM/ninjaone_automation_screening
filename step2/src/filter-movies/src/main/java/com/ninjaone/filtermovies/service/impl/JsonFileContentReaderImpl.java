package com.ninjaone.filtermovies.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ninjaone.filtermovies.model.Movie;
import com.ninjaone.filtermovies.service.FileContentReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class JsonFileContentReaderImpl implements FileContentReader<Movie> {
    @Override
    public List<Movie> readAll(String path) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(path));
            List<Movie> data = gson.fromJson(reader, new TypeToken<List<Movie>>(){}.getType());
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
