package com.testTask.ninja;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteHelper {
    public static String readFile(String path) {
        try {
            return Files.readString(Path.of(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Movie> readFileToList(String path) {
        String s = readFile(path);
        return getMovies(s);
    }

    public static List<Movie> getMovies(String s) {
        Type listType = new TypeToken<ArrayList<Movie>>() {}.getType();
        return new Gson().fromJson(s, listType);
    }

    public static void writeToFile(String path, String fileContent) {
        try {
            FileUtils.writeStringToFile(new File(path), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}