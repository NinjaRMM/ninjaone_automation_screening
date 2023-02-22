const fs = require('fs');
const path = require('path');

const VALID_DECADES = [1900, 1910, 1920, 1930, 1940, 1950, 1960, 1970, 1980, 1990, 2000, 2010];

// get command line arguments
const args = process.argv.slice(2);
const decade = parseInt(args[0]);
const inputFile = args[1] || 'movies.json';
const outputFile = args[2] || `${decade}s-movies.json`;


// Validate decade parameter
if (Number.isNaN(decade) || !VALID_DECADES.includes(parseInt(decade))) {
    console.error('Please provide a valid decade (e.g. 1900, 2010)');
    process.exit(1);
}

try {
    // read input file
    const moviesFilePath = path.join(__dirname, inputFile);
    const movies = JSON.parse(fs.readFileSync(moviesFilePath, 'utf8'));

    // filter movies by decade
    const decadeEndYear = decade + 9;
    const decadeMovies = movies.filter(movie => movie.year >= decade && movie.year <= decadeEndYear);
    
    // write output file
    const outputFilePath = path.join(__dirname, outputFile);
    fs.writeFileSync(outputFilePath, JSON.stringify(decadeMovies, null, 2));
    console.log(`Created file ${outputFilePath}`);
} catch (error) {
    console.error(error);
}
