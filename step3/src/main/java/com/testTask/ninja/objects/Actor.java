package com.testTask.ninja.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Actor {
    private final String name;
    private List<Pair> pairs = new ArrayList<>();

    public Actor(String name) {
        this.name = name;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void addPair(Pair pair) {
        this.pairs.add(pair);
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
