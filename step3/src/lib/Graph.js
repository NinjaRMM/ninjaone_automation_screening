import { capitalize } from "../utils/utils.js";

export default class Graph {
  constructor() {
    this.nodes = [];
    this.graph = {};
    this.startNode = null;
    this.endNode = null;
  }
  addNode(node) {
    this.nodes.push(node);
    const title = node?.value;
    this.graph[title] = node;
  }

  getNode(actor) {
    const node = this.graph[actor];
    return node;
  }
  setStart(actor) {
    this.startNode = this.graph[capitalize(actor)];
  }
  setEnd(actor) {
    this.endNode = this.graph[capitalize(actor)];
  }
}
