package com.ninjaone.sixdegree;

import com.google.gson.Gson;
import com.ninjaone.sixdegree.domain.Movie;
import com.ninjaone.sixdegree.reader.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class SixDegreeApplication implements CommandLineRunner {
    String ActorOne;
    String ActorTwo;
    List<Movie> AllMovies = new ArrayList<>();
    int Degrees = 0;

    public static void main(String[] args) {
        SpringApplication.run(SixDegreeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length < 1) {
            instructions();
            return;
        } else if (args[0].trim().isEmpty()) {
            instructions();
            return;
        }

        ActorOne = args[0].trim();
        if (args.length >= 2) {
            ActorTwo = args[1].trim();
        }
        countDegreesOfSeparation();
    }

    public void instructions() {
        System.out.println("Wrong parameter. You must provide the name of at least one actor.");
        System.out.println("Example:");
        System.out.println("<system> " + '"' + "ActorName1" + '"' + " " + '"' + "[ActorName2]" + '"');
        System.out.println();
    }

    public void countDegreesOfSeparation() {
        var targetActor = "";
        var nextActor = "";
        var path = "../../data/80s-movies.json";
        List<Movie> filteredMovies = new ArrayList<>();


        if (ActorTwo.isEmpty()) {
            targetActor = "Kevin Bacon";
        } else {
            targetActor = ActorTwo;
        }

        // Data Read
        try {
            AllMovies = MovieReader.readListFrom(path);
        } catch (NoSuchFileException e) {
            System.err.println("Unable to find json in folder " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reach out
        try  {
            //degrees ++;
            filteredMovies = getMoviesByActor(ActorOne);
            if (filteredMovies == null || filteredMovies.isEmpty()) {
                System.out.println(nextActor + " did not star in a movie in the data provided");
                return;
            }

            findTarget(filteredMovies, targetActor);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Degrees >= 1) System.out.println("Degree of Separation: " + Degrees);
    }

    public List<Movie> getMoviesByActor(String name) {
        return AllMovies
                .stream()
                .filter(x -> Arrays.stream(x.getCast()).anyMatch(i -> i.contains(name)) )
                .toList();
    }

    public boolean findTarget(List<Movie> filteredMovies, String targetActor) {
        int innerDegree = 0;
        var nextActor = "";

        for (Movie movie: filteredMovies ) {
            for (int i = 0; i < movie.getCast().length; i++) {
                nextActor = movie.getCast()[i];
                if (!Objects.equals(nextActor, ActorOne)) {
                    if (!Objects.equals(nextActor, targetActor)) {
                        var peaceOfMovie = getMoviesByActor(nextActor);
                        if (peaceOfMovie != null && peaceOfMovie.size() > 0) {
                            innerDegree++;
                            if (findTarget(peaceOfMovie, targetActor)) {
                                Degrees += innerDegree;
                                return true;
                            }
                        }
                    } else {
                        Degrees += innerDegree;
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
