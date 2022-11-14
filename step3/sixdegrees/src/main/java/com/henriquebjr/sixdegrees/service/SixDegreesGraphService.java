package com.henriquebjr.sixdegrees.service;

import com.henriquebjr.sixdegrees.model.ActorNode;
import com.henriquebjr.sixdegrees.model.ActorsGraph;
import com.henriquebjr.sixdegrees.model.Movie;
import com.henriquebjr.sixdegrees.model.MovieHistory;
import com.henriquebjr.sixdegrees.service.exception.ActorDestinyNotFoundException;
import com.henriquebjr.sixdegrees.service.exception.ActorOriginNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@Service
public class SixDegreesGraphService {

    public ActorNode calculate(MovieHistory movieHistory, String originActor, String destinyActor) throws ActorOriginNotFoundException, ActorDestinyNotFoundException {
        Map<String, ActorNode> actorsNodes = new HashMap<>();

        for(Movie movie : movieHistory.getMovies()) {
            for(String actor : movie.getCast()) {
                ActorNode actorNode = getActorNode(actorsNodes, actor);

                for(String otherActor : movie.getCast()) {
                    if(!otherActor.equals(actor)) {
                        ActorNode otherActorNoder = getActorNode(actorsNodes, otherActor);
                        actorNode.add(otherActorNoder, 1, movie);
                    }
                }
            }
        }

        ActorsGraph graph = new ActorsGraph();
        for(Map.Entry<String, ActorNode> node : actorsNodes.entrySet()) {
            graph.add(node.getValue());
        }

        var originActorNode = actorsNodes.get(originActor);
        if(originActorNode == null) {
            throw new ActorOriginNotFoundException();
        }

        calculateShortestPathFromSource(graph, originActorNode);

        var destinyNode = actorsNodes.get(destinyActor);
        if(destinyNode == null) {
            throw new ActorDestinyNotFoundException();
        }

        return destinyNode;
    }

    private ActorNode getActorNode(Map<String, ActorNode> actorsNodes, String actorName) {
        if(actorsNodes.containsKey(actorName)) {
            return actorsNodes.get(actorName);
        }

        ActorNode actorNode = ActorNode.Builder.of()
                .actor(actorName)
                .build();
        actorsNodes.put(actorName, actorNode);

        return actorNode;
    }

    public ActorsGraph calculateShortestPathFromSource(ActorsGraph graph, ActorNode source) {
        source.setDistance(0);

        Set<ActorNode> settledNodes = new HashSet<>();
        Set<ActorNode> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            ActorNode currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<ActorNode, Integer> adjacencyPair:
                    currentNode.getNodes().entrySet()) {
                ActorNode adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private ActorNode getLowestDistanceNode(Set<ActorNode> unsettledNodes) {
        ActorNode lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (ActorNode node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(ActorNode evaluationNode, Integer weight, ActorNode sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + weight < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + weight);
            LinkedList<ActorNode> shortestPath = new LinkedList<>(sourceNode.getPath());
            shortestPath.add(sourceNode);
            evaluationNode.setPath(shortestPath);
        }
    }
}
