package com.testTask.ninja.objects;

import java.util.List;

public class TraverseResult {
    private List<Pair> pairs;
    private String degree;
    private String message = "%s did not star in a movie in the data provided.";

    public TraverseResult(List<Pair> pairs) {
        this.pairs = pairs;
        this.degree = "" + (pairs.size() - 1);
    }

    public TraverseResult(String name) {
        this.message = String.format(message, name);
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public String getDegree() {
        return pairs != null ? degree : message;
    }
}
