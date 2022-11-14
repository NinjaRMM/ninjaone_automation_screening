package com.henriquebjr.sixdegrees.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ActorNode {

    private String actor;
    private List<ActorNode> path = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;

    private Map<ActorNode, Integer> nodes = new HashMap<>();
    private Map<String, Movie> costars = new HashMap<>();

    public void add(ActorNode costar, int distance) {
        nodes.put(costar, distance);
    }

    public void add(ActorNode costar, int distance, Movie movie) {
        nodes.put(costar, distance);
        costars.put(costar.getActor(), movie);
    }

    public ActorNode() {
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public List<ActorNode> getPath() {
        return path;
    }

    public void setPath(List<ActorNode> path) {
        this.path = path;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<ActorNode, Integer> getNodes() {
        return nodes;
    }

    public void setNodes(Map<ActorNode, Integer> nodes) {
        this.nodes = nodes;
    }

    public Map<String, Movie> getCostars() {
        return costars;
    }

    public void setCostars(Map<String, Movie> costars) {
        this.costars = costars;
    }

    public static final class Builder {
        private ActorNode actorNode;

        private Builder() {
            actorNode = new ActorNode();
        }

        public static Builder of() {
            return new Builder();
        }

        public Builder actor(String actor) {
            actorNode.setActor(actor);
            return this;
        }

        public Builder path(List<ActorNode> path) {
            actorNode.setPath(path);
            return this;
        }

        public Builder distance(Integer distance) {
            actorNode.setDistance(distance);
            return this;
        }

        public Builder nodes(Map<ActorNode, Integer> nodes) {
            actorNode.setNodes(nodes);
            return this;
        }

        public ActorNode build() {
            return actorNode;
        }
    }
}
