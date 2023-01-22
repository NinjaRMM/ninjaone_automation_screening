package com.ninjaone.step3.model.graph;

import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A vertex in the graph class {@link CastGraph}, encapsulating exactly an actor and all movies in which s/he starred.
 */
@Getter
@Setter
@AllArgsConstructor
public class ActorVertex {

    private String actor;
    private Set<String> movieTitles;

    public ActorVertex(String actor) {
        this.actor = actor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ActorVertex other = (ActorVertex) obj;
        return Objects.equals(actor, other.actor);
    }
}
