package br.com.rodrigopostai.sixdegrees.reader;

import br.com.rodrigopostai.sixdegrees.domain.Movie;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class MovieReader implements Spliterator<Movie> {

    private final Queue<Movie> movies = new ConcurrentLinkedQueue<>();
    private boolean completed = false;

    public MovieReader(Path file) {
        read(file);
    }

    private void read(Path file) {
        completed = false;
        new Thread(() -> {
            JsonFactory factory = JsonFactory.builder().build();
            try (BufferedReader br = Files.newBufferedReader(file)) {
                JsonParser parser = factory.createParser(br);
                parseMovies(parser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void parseMovies(JsonParser parser) throws IOException {
        Movie.MovieBuilder builder = Movie.newBuilder();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            String fieldName = parser.getCurrentName();
            if (Movie.TITLE.equals(fieldName)) {
                parser.nextToken();
                builder.title(parser.getText());
            } else if (Movie.YEAR.equals(fieldName)) {
                parser.nextToken();
                builder.year(parser.getValueAsInt());
            } else if (Movie.CAST.equals(fieldName)) {
                parser.nextToken();
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    builder.cast(parser.getText());
                }
            } else if (Movie.GENRES.equals(fieldName)) {
                parser.nextToken();
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    builder.genre(parser.getText());
                }
                movies.add(builder.build());
                builder = Movie.newBuilder();
            }
        }
        completed = true;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Movie> action) {
        if (!movies.isEmpty()) {
            action.accept(movies.poll());
            return true;
        }
        if (!completed) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("Error reading movies", e);
            }
            return tryAdvance(action);
        }
        return false;
    }

    @Override
    public Spliterator<Movie> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return Spliterator.SUBSIZED;
    }
}
