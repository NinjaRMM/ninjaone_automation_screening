package org.ninjaone.data_handler;

import lombok.Getter;
import org.ninjaone.data_structure.ActorEdge;
import org.ninjaone.data_structure.ActorNode;

import java.util.*;

public class Dijkstra {
    private final Map<ActorNode, List<ActorEdge>> edgeMap;
    private Set<ActorNode> visitedNodes;
    private Set<ActorNode> unvisitedNodes;
    private Map<ActorNode, ActorNode> predecessors;
    private Map<ActorNode, Integer> distance;

    public Dijkstra(Map<ActorNode, List<ActorEdge>> actorGraph) {
        this.edgeMap = new HashMap<>(actorGraph);
    }

    public void execute(ActorNode source) {
        visitedNodes = new HashSet<>();
        unvisitedNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();

        distance.put(source, 0);
        unvisitedNodes.add(source);

        while(unvisitedNodes.size() > 0) {
            ActorNode node = getMinimum(unvisitedNodes);
            visitedNodes.add(node);
            unvisitedNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(ActorNode node) {
        List<ActorNode> adjacentNodes = getNeighbors(node);
        for (ActorNode target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unvisitedNodes.add(target);
            }
        }
    }

    private int getDistance(ActorNode node, ActorNode target) {
        for (ActorEdge edge : edgeMap.get(node)) {
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
                return edge.getCost();
            }
        }
        return -1;
    }

    private List<ActorNode> getNeighbors(ActorNode node) {
        List<ActorNode> neighbors = new ArrayList<>();
        for (ActorEdge edge : edgeMap.get(node)) {
            if (edge.getSource().equals(node) && !isVisited(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private ActorNode getMinimum(Set<ActorNode> actorNodes) {
        ActorNode minimum = null;
        for ( ActorNode actorNode : actorNodes) {
            if (minimum == null) {
                minimum = actorNode;
            } else {
                if (getShortestDistance(actorNode) < getShortestDistance(minimum)) {
                    minimum = actorNode;
                }
            }
        }
        return minimum;
    }

    private boolean isVisited(ActorNode actorNode) {
        return visitedNodes.contains(actorNode);
    }

    private int getShortestDistance(ActorNode destination) {
        Integer dist = distance.get(destination);
        if (dist == null) {
            return Integer.MAX_VALUE;
        } else {
            return dist;
        }
    }

    public List<ActorEdge> getPath(ActorNode target) {
        LinkedList<ActorEdge> path = new LinkedList<>();
        ActorNode acActorNode = target;

        if(predecessors.get(acActorNode) == null) {
            return null;
        }
        while (predecessors.get(acActorNode) != null) {
            ActorNode oldActorNode = acActorNode;
            acActorNode = predecessors.get(acActorNode);
            for (ActorEdge edge: edgeMap.get(oldActorNode)) {
                if(edge.getSource().equals(oldActorNode) && edge.getDestination().equals(acActorNode))
                    path.add(edge);
            }
        }
        Collections.reverse(path);
        return path;
    }
}
