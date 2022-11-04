package com.testTask.ninja;

import com.testTask.ninja.objects.Movie;
import com.testTask.ninja.objects.TraverseResult;

import java.util.List;

import static com.testTask.ninja.MovieService.readFile;

public class Main {
    private static final String PATH = "data/80s-movies.json";
    private static final String DEFAULT_ACTOR = "Kevin Bacon";

    public static void main(String[] args) {
        TraverseResult result;
        String fileContent = readFile(PATH);
        List<Movie> movies = MovieService.getMovies(fileContent);
        Graph graph = new Graph(movies);

        switch (args.length) {
            case 1:
                result = graph.traverse(args[0], DEFAULT_ACTOR);
                break;
            case 2:
                result = graph.traverse(args[0], args[1]);
                break;
            default:
                throw new IllegalArgumentException("Unsupported number of actors, please write 1 or 2 actors name");
        }
        System.out.println(result.getDegree());
    }
}
