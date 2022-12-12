package org.dijkstra.sample;

import org.dijkstra.sample.model.Movie;
import org.dijkstra.sample.util.LoadData;

import java.util.List;
import java.util.Objects;

public class Main {


    public static void main(String[] args) {
      String source_actor = args[0];
      String final_actor = args[1];
      String path_file = args[2];
      if (args.length >= 3) {
          List<Movie> movies = Objects.requireNonNull(LoadData.getObservable(path_file)).toList().blockingGet();
          System.out.println("Total movies: " + movies.size());
          GraphDijkstra graphDijkstra = new GraphDijkstra(source_actor, final_actor, movies);
          int degree = graphDijkstra.calculateDijkstra();
          args[2] = String.valueOf(degree);
      }else{
          System.out.println("Invalid parameters number");
      }

    }




}