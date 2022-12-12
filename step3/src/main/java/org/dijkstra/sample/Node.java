package org.dijkstra.sample;

class Node implements Comparable<Node> {
        Integer distance;
        String person;
        Node(Integer distance, String person){
            this.distance = distance;
            this.person = person;
        }

        @Override
        public int compareTo(Node o) {
            return o.distance < this.distance ? 1 : -1;
        }

        @Override
        public String toString() {
            return "[" + this.distance + "," + this.person + "]";
        }
    }