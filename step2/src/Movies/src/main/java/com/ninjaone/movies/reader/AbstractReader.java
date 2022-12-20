package com.ninjaone.movies.reader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractReader {
    public static String readJson(String path) throws IOException {
        String json = String.join(" ", Files.readAllLines( Paths.get(path), StandardCharsets.UTF_8) );
        return json;
    }
}
