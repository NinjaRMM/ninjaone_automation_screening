package org.ninjaone.data_structure;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class ActorNode {
    @Getter @Setter
    private String name;

    public ActorNode(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ActorNode)) {
            return false;
        }

        ActorNode actorNode = (ActorNode) obj;
        return name == actorNode.name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
