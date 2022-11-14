package com.henriquebjr.sixdegrees.service;

import com.henriquebjr.sixdegrees.model.ActorNode;
import org.springframework.stereotype.Service;

@Service
public class SixDegreesReportService {

    public void report(ActorNode actorNode) {
        var degrees = actorNode.getDistance() - 1;
        System.out.println(String.format("There are %d degrees of separation between %s and %s", degrees, actorNode.getPath().get(0).getActor(), actorNode.getActor()));

        var pathSize = actorNode.getPath().size();

        for(int i = 0; i < pathSize; i++) {
            boolean lastIteration = (i == (pathSize - 1));
            var step = actorNode.getPath().get(i);
            var nextActor = lastIteration ? actorNode.getActor() : actorNode.getPath().get(i + 1).getActor();
            System.out.println(String.format("%s starred with %s in %s", step.getActor(), nextActor, step.getCostars().get(nextActor).getTitle()));
        }
    }
}
