package com.ninjaone.movies;

import com.ninjaone.movies.domain.Movie;
import com.ninjaone.movies.reader.MovieReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

@SpringBootApplication
public class MoviesApplication implements CommandLineRunner {
    String decade;
    String outputFile;

    public static void main(String[] args) {
        SpringApplication.run(MoviesApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length < 4) {
            System.out.println("Wrong parameter. Example:");
            System.out.println("<system> -decade 80 -output 80s-movies.json");
            System.out.println();
            return;
        }

        for (int i = 0; i < args.length; ++i) {
            if (args[i].contains("decade")) {
                decade = args[i + 1].trim();
            }
            if (args[i].contains("output")) {
                outputFile = args[i + 1].trim();
            }
        }

        if (decade != null && !decade.trim().isEmpty() && outputFile != null && outputFile.trim().isEmpty()) {
            doFilter();
        } else {
            System.out.println("Wrong parameter. Example:");
            System.out.println("<system> -decade 80 -output 80s-movies.json");
            System.out.println();
            return;
        }
    }

    public void doFilter() {
        var startYear = Integer.parseInt("19" + decade);
        var endYear = Integer.parseInt("19" + String.valueOf((Integer.parseInt(decade) + 9)));
        var path = "../../data/movies.json";
        List<Movie> allMovies = new ArrayList<>();
        List<Movie> filteredMovies = new ArrayList<>();

        try {
            allMovies = MovieReader.readListFrom(path);
            filteredMovies = allMovies
                    .stream()
                    .filter(x -> Integer.parseInt(x.getYear()) >= startYear && Integer.parseInt(x.getYear()) <= endYear)
                    .toList();


        } catch (NoSuchFileException e) {
            System.err.println("Unable to find json in folder " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(filteredMovies);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
