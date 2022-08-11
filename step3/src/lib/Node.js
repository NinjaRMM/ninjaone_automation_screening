export default class Node {
  constructor(value, isActor) {
    this.value = value;
    this.edges = [];
    this.searched = false;
    this.parent = null;
    this.isActor = isActor;
  }
  addEdge(neighbor) {
    this.edges.push(neighbor);
    neighbor?.edges?.push(this);
  }
}
