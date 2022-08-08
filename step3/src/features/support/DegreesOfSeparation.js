import { setWorldConstructor } from "@cucumber/cucumber";
import { createRequire } from "module";

const require = createRequire(import.meta.url);
const movies = require("../../../../data/movies.json");

class Graph {
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
    this.startNode = this.graph[actor];
  }
  setEnd(actor) {
    this.endNode = this.graph[actor];
  }
}

class Node {
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

const graph = new Graph();

class DegreesOfSeparation {
  constructor() {
    this.numOfDegreesText = "";
    this.listOfMoviesText = [];
    this.startActor = null;
    this.endActor = null;
  }

  existsFile() {
    return movies.length > 0;
  }

  setupData() {
    for (let i = 0; i < movies.length; i++) {
      const movie = movies[i].title;
      const cast = movies[i].cast;
      const movieNode = new Node(movie, false);
      graph.addNode(movieNode);

      for (let j = 0; j < cast.length; j++) {
        const actor = cast[j];

        let actorNode = graph.getNode(actor);

        if (actorNode === undefined) {
          actorNode = new Node(actor, true);
        }
        graph.addNode(actorNode);
        movieNode.addEdge(actorNode);
      }
    }
  }

  setStartNode(startActor) {
    graph.setStart(startActor);
    this.startActor = startActor;
  }
  setEndNode(endActor) {
    graph.setEnd(endActor);
    this.endActor = endActor;
  }

  bfs() {
    // Breadth-First Search
    const start = graph.startNode;
    const end = graph.endNodeNode;
    const actorsPath = [];
    const moviesPath = [];
    let message;

    if (!start || typeof start === "string" || typeof end === "string") {
      !start &&
        (message = `${this.startActor} did not star in a movie in the data provided.`);
    } else {
      const queue = [];
      start.searched = true;
      queue.push(start);

      while (queue.length > 0) {
        const current = queue.shift();
        if (current === end) {
          break;
        }
        const edges = current.edges;
        for (let i = 0; i < edges.length; i++) {
          const neighbor = edges[i];
          if (!neighbor.searched) {
            neighbor.searched = true;
            neighbor.parent = current;
            queue.push(neighbor);
          }
        }
      }
      // Get the path starting from the end, then going into the parent
      const path = [];
      path.push(graph.endNode);
      let next = graph.endNode.parent;
      while (next !== null) {
        path.push(next);
        next = next.parent;
      }
      // Loop thru path backwards since the last element added to the path is the beginning
      for (let i = path.length - 1; i >= 0; i--) {
        const node = path[i];
        if (node.isActor) {
          actorsPath.push(node.value);
        } else {
          moviesPath.push(node.value);
        }
      }
    }
    this.setText({ actorsPath, moviesPath, message });
  }

  setNumOfDegreesText(text) {
    this.numOfDegreesText = text;
  }

  getNumOfDegreesText() {
    return this.numOfDegreesText;
  }

  setListOfMoviesText(text = []) {
    this.listOfMoviesText = text;
  }

  getListOfMoviesText() {
    return this.listOfMoviesText;
  }

  setText({ actorsPath, moviesPath, message }) {
    let textsList = "";
    const mainText =
      message ||
      `There ${actorsPath.length < 3 ? "is" : "are"} ${
        actorsPath.length - 1
      } degree${actorsPath.length < 3 ? "" : "s"} of separation between ${
        actorsPath[0]
      } and ${actorsPath[actorsPath.length - 1]}`;
    this.setNumOfDegreesText(mainText);
    for (let i = 0; i < moviesPath.length; i++) {
      textsList += `${actorsPath[i]} starred with ${actorsPath[i + 1]} in ${
        moviesPath[i]
      }\n`;
    }
    this.setListOfMoviesText(textsList);
  }
}

setWorldConstructor(DegreesOfSeparation);
