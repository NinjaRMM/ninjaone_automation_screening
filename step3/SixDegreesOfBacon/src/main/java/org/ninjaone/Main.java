package org.ninjaone;

import org.ninjaone.data_handler.Dijkstra;
import org.ninjaone.data_structure.ActorEdge;
import org.ninjaone.data_structure.ActorNode;
import org.ninjaone.data_structure.Movie;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Map<String, String> params = MainHelper.parseArgs(args);

        List<Movie> movies = MainHelper.readMoviesJson(params.get(MainHelper.PATH_KEY));

        Map<String, ActorNode> actorNodeMap = new HashMap<>();
        Map<ActorNode, List<ActorEdge>> edges = new HashMap<>();

        MainHelper.buildMaps(movies, actorNodeMap, edges);
        MainHelper.validateActors(params, actorNodeMap);

        ActorNode sourceActor = actorNodeMap.get(params.get(MainHelper.SOURCE_STAR_KEY));
        ActorNode destActor = actorNodeMap.get(params.get(MainHelper.DEST_STAR_KEY));

        Dijkstra dijkstra = new Dijkstra(edges);
        dijkstra.execute(sourceActor);
        List<ActorEdge> path = dijkstra.getPath(destActor);


        System.out.println(String.format(
                MainHelper.BACON_DEGREE_MESSAGE,
                path.size()-1,
                params.get(MainHelper.SOURCE_STAR_KEY),
                params.get(MainHelper.DEST_STAR_KEY)
        ));

        for (ActorEdge step : path) {
            System.out.println(String.format(
                    MainHelper.STARRED_WITH_MESSAGE,
                    step.getDestination().getName(),
                    step.getSource().getName(),
                    step.getMovie()
            ));
        }
    }
}