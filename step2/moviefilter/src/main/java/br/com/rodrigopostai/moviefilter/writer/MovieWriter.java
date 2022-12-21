package br.com.rodrigopostai.moviefilter.writer;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.collections4.CollectionUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class MovieWriter implements AutoCloseable {

    private Path path;
    private FileOutputStream fos;
    private JsonFactory jsonFactory;
    private JsonGenerator generator;

    public MovieWriter(Path destination) {
        this.path = destination;
        try {
            fos = new FileOutputStream(destination.toFile());
            jsonFactory = new JsonFactory();
            generator = jsonFactory.createGenerator(fos);
            generator.writeStartArray();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found to write",e);
        } catch (IOException e) {
            throw new RuntimeException("Error to write destination file",e);
        }
    }

    public void writeMovie(Movie movie) {
        try {
            generator.writeStartObject();
            try {
                generator.writeStringField(Movie.TITLE, movie.getTitle());
                generator.writeNumberField(Movie.YEAR, movie.getYear());

                try {
                    generator.writeArrayFieldStart(Movie.CAST);
                    if (CollectionUtils.isNotEmpty(movie.getCast())) {
                        for (String cast : movie.getCast()) {
                            generator.writeString(cast);
                        }
                    }
                } finally {
                    generator.writeEndArray();
                }
                try {
                    generator.writeArrayFieldStart(Movie.GENRES);
                    if (CollectionUtils.isNotEmpty(movie.getGenres())) {
                        for (String genre : movie.getGenres()) {
                            generator.writeString(genre);
                        }
                    }
                } finally {
                    generator.writeEndArray();
                }
            } finally {
                generator.writeEndObject();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error to write movie",e);
        }
    }

    @Override
    public void close() throws Exception {
        generator.writeEndArray();
        generator.close();
        fos.flush();
        fos.close();
    }
}
