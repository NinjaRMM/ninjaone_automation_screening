//print(__FILE__, __LINE__, __DIR__);
//print('hello');

var JStringBuilder = Java.type('java.lang.StringBuilder');
var JHashMap = Java.type('java.util.HashMap');
var JHashSet = Java.type('java.util.HashSet');
var JFileReader = Java.type('java.io.FileReader');
var JBufferedReader = Java.type('java.io.BufferedReader');

var rawdata = new JStringBuilder();
try {
	var f = new JFileReader(__DIR__+"../../step2/src/80s-movies.json");
	var br = new JBufferedReader(f);
	var line = '';
	while((line = br.readLine()) != null){
		rawdata.append(line).append('\n');
	}
} catch(err) {
	print('Error reading requirement json file. Was step2 completed successfully?');
	print(err);
}

var movies = JSON.parse(rawdata);

var parameter = arguments[0] || null;
var parameter2 = arguments[1] || "Kevin Bacon";

if(parameter == null) {
	throw new Error("Actor name was not provided, Provide one or two actor names, each name wrapped in double quotes \"\".");
}

var actors = {};
var actornames = [];
var actorindex = 0;

var edgemovie = new JHashMap();
var edges = [];
var depth = [];

function calcKeyForActorPair(u,v) {
	if(u < v) {
		return u*100000+v;
	} else {
		return v*100000+u;
	}
}
function connectActorsViaMovie(u,v,title) {
	edgemovie.put(calcKeyForActorPair(u,v),title);
}

for each(var movie in movies) {
	var nodesInMovie = [];
	for each(var actor in movie.cast) {
		var u;
		if(actor in actors) {
			u = actors[actor];
		} else {
			u = actors[actor] = actorindex;
			actornames[u] = actor;
			edges[u] = new JHashSet();
			depth[u] = -1;
			actorindex++;
		}
		
		for each(var v in nodesInMovie) {
			connectActorsViaMovie(u,v,movie.title);
			edges[u].add(v);
			edges[v].add(u);
		}
		
		nodesInMovie.push(u);
	}
}


p1index = actors[parameter] || null;
p2index = actors[parameter2] || null;
if(p1index == null) {
	print(parameter + " did not star in a movie in the data provided.");
}
if(p2index == null) {
	print(parameter2 + " did not star in a movie in the data provided.");
}

if(p1index == null || p2index == null) {
	exit;
}
else {

//bfs
var parent = [];

var buffer = [];
buffer.push(p1index);
depth[p1index] = 0;
parent[p1index] = -1;
while(buffer.length > 0) {
	var node = buffer.shift();
	var nodedepth = depth[node];

	for each(var child in edges[node]){
		if(depth[child] != -1) continue;
		depth[child] = nodedepth+1;
		parent[child] = node;
		buffer.push(child);
	}
}

print('There are '+depth[p2index]+' degrees of separation between '+actornames[p1index]+' and '+actornames[p2index]);
var node = p2index;
var p;
var results = [];
while(true) {
	p = parent[node];
	if(p == -1) break;
//print(node +'-> '+p);
	results.push(actornames[node] + ' starred with '+actornames[p]+' in '+edgemovie.get(calcKeyForActorPair(p,node)));
	node = p;
}

for(var i = results.length-1; i >= 0; i--) {
	print(results[i]);
}

}