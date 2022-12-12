package org.dijkstra.sample;

import org.dijkstra.sample.model.Movie;

import java.util.*;

public class GraphDijkstra {
    private final HashMap<String, List<Movie>> moviesByActor;

    private final String start_actor;
    private final String finish_actor;

    private final List<Movie> movies;


    public GraphDijkstra(String start_actor, String finish_actor, List<Movie> movies) {

        this.start_actor=start_actor;
        this.finish_actor=finish_actor;
        this.movies=movies;
        this.moviesByActor = new HashMap<>();

    }

    private int createMoviesByActor(String person )  {

        for (Movie movie : movies) {
            for (int j = 0; j < movie.getAdditionalProperties().getCast().size(); j++) {
                if (person.equals(movie.getAdditionalProperties().getCast().get(j))) {
                    if (moviesByActor.containsKey(person)) {
                        moviesByActor.get(person).add(movie);
                    } else {
                        moviesByActor.put(person, new ArrayList<>());
                    }
                }
            }
        }
        if (!moviesByActor.containsKey(person)){
            System.out.println(person + " did not star in a movie in the data provided.");
            return 0;

        }
        return 1;
    }


    private Set<String> getCastByActor(String person) {
        Set<String> castsByActor = new HashSet<>();
        moviesByActor.get(person).forEach(m -> processCastByActor(castsByActor,m));
        return castsByActor;
    }

    public void processCastByActor(Set<String> castsByActor, Movie movie){
        castsByActor.addAll(movie.getAdditionalProperties().getCast());
    }


    public int calculateDijkstra()  {
        PriorityQueue<Node> pqueue = new PriorityQueue<>();
        Set<String> certain = new HashSet<>();
        Map<String, List<String>> dijkstra_table = new HashMap<>();
        Node node = new Node(0, start_actor);
        Node current;

        pqueue.add(node);
        dijkstra_table.put(start_actor, new ArrayList<>());
        while (pqueue.size() > 0) {
            current = pqueue.poll();

            if(current.person==null)
                return 0;

            if (current.person.equals(finish_actor)) {
                return showPath(dijkstra_table);

            }

            if (certain.contains(current.person))
                continue;
            certain.add(current.person);

            if (!moviesByActor.containsKey(current.person)){
               int resp = createMoviesByActor(current.person);
               if(resp==0)
                   return 0;
            }


            Set<String> castsByActor;
            castsByActor = getCastByActor(current.person);
            castsByActor.removeAll(certain);

            ArrayList<String> path;
            path = new ArrayList<>(dijkstra_table.get(current.person));
            path.add(current.person);


            for (String neighbor : castsByActor) {
                if (dijkstra_table.get(current.person).size() <= path.size()) {
                    if (dijkstra_table.containsKey(neighbor)) {
                        continue;
                    }
                }
                dijkstra_table.put(neighbor, path);
                pqueue.add(new Node(path.size(), neighbor));
            }

        }

        return 0;

    }

     private int showPath(Map<String, List<String>> shortest_path){
         List<String>path = shortest_path.get(finish_actor);

         String result = "There are " + shortest_path.get(finish_actor).size() + " degrees of separation between " +
                 start_actor + " and " + finish_actor + ".";
         System.out.println(result);
         path.add(finish_actor);


         for (int i=0; i < path.size() - 1; i++){
             for (int j=0; j < moviesByActor.get(path.get(i)).size(); j++) {

                 if (moviesByActor.get(path.get(i)).get(j).getAdditionalProperties().getCast().contains(path.get(i + 1))){
                    Movie movie=  moviesByActor.get(path.get(i)).get(j);
                     result = path.get(i) + " starred with "+ path.get(i + 1) + " in " + movie.getTitle();
                     System.out.println(result);
                 }
             }
         }

         return shortest_path.get(finish_actor).size();
     }

}
