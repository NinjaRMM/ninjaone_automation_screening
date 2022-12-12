'use strict';

const fs = require('fs');
const readline = require('readline');

let rawdata = null;
try {
	rawdata = fs.readFileSync('../../data/movies.json');
} catch(err) {
	throw new Error("movies.json not found where expected, is the working directory pointing to step2.js?");
}
let movies = JSON.parse(rawdata);

var isNumber = (str) => {
	return /^\d+$/.test(str);
}

let parameter = (process.argv.length > 2 ? process.argv[2] : 'A');
if(!isNumber(parameter)) {
	parameter = -1;
} else {
	parameter = parseInt(parameter);
}
if(parameter < 0 || parameter%10 != 0 || (parameter > 90 && parameter != 2000 && parameter != 2010)) {
	throw new Error(`ERROR - Requires valid parameter.
0, 10, 20, 30, 40, 50, 60, 70, 80 or 90 (decade of the 1900s),
2000 (decade of the 2000s),
2010 (decade of the 2010s)

ex. node step2.js 80`);
}


let decade = (parameter < 2000 ? parameter + 1900 : parameter);

let result = [];
for(var mi=0; mi < movies.length; mi++) {
	let movie = movies[mi];
	if(movie.year >= decade && movie.year < decade+10) {
		result.push(movie);
	}
}

if(parameter == 0) parameter = '00';
fs.writeFileSync(`./${parameter}s-movies.json`, JSON.stringify(result));