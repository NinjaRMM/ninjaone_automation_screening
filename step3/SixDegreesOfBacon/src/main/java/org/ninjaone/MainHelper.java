package org.ninjaone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.commons.cli.*;
import org.ninjaone.data_structure.ActorEdge;
import org.ninjaone.data_structure.ActorNode;
import org.ninjaone.data_structure.Movie;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MainHelper {

    public static final String SOURCE_STAR_KEY = "sourceStar";
    public static final String DEST_STAR_KEY = "destStar";
    public static final String PATH_KEY = "path";

    public static final String BACON_DEGREE_MESSAGE = "There are %d degrees of separation between %s and %s";
    public static final String STARRED_WITH_MESSAGE = "%s starred with %s in %s";
    public static final String DID_NOT_STAR_MESSAGE = "%s did not star in a movie in the data provided";

    static void validateActors(Map<String, String> params, Map<String, ActorNode> actorNodeMap) {
        validateActor(actorNodeMap, params, SOURCE_STAR_KEY);
        validateActor(actorNodeMap, params, DEST_STAR_KEY);
    }

    private static void validateActor(Map<String, ActorNode> actorNodeMap, Map<String, String> params, String paramKey) {
        if (!actorNodeMap.containsKey(params.get(paramKey))) {
            System.out.println(String.format(DID_NOT_STAR_MESSAGE, params.get(paramKey)));
            System.exit(1);
        }
    }

    static void buildMaps(List<Movie> movies, Map<String, ActorNode> actorNodeMap, Map<ActorNode, List<ActorEdge>> edges) {
        for (Movie movie : movies) {
            for(int i = 0; i < movie.getCast().size(); i++) {
                for(int j = i+1; j < movie.getCast().size(); j++) {
                    String actorNameA = movie.getCast().get(i);
                    String actorNameB = movie.getCast().get(j);

                    ActorNode actorA = new ActorNode(actorNameA);
                    ActorNode actorB = new ActorNode(actorNameB);

                    actorNodeMap.putIfAbsent(actorNameA, actorA);
                    actorNodeMap.putIfAbsent(actorNameB, actorB);

                    edges.putIfAbsent(actorNodeMap.get(actorNameA), new LinkedList<>());
                    edges.putIfAbsent(actorNodeMap.get(actorNameB), new LinkedList<>());

                    edges.get(actorNodeMap.get(actorNameA)).add(new ActorEdge(movie.getTitle(), actorNodeMap.get(actorNameA), actorNodeMap.get(actorNameB)));
                    edges.get(actorNodeMap.get(actorNameB)).add(new ActorEdge(movie.getTitle(), actorNodeMap.get(actorNameB), actorNodeMap.get(actorNameA)));
                }
            }
        }
    }

    static List<Movie> readMoviesJson(String path) throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader(path));
        Gson gson = new Gson();

        List<Movie> movies = gson.fromJson(jsonReader, new TypeToken<List<Movie>>(){}.getType());
        movies = movies.stream().filter(p -> p.getCast().size() > 0).collect(Collectors.toList());
        return movies;
    }

    static Map<String, String> parseArgs(String[] args) {
        Options options = new Options();

        Option filePathOption = new Option("p", "path", true, "File path");
        filePathOption.setRequired(true);
        options.addOption(filePathOption);

        Option starOption = new Option("s", "star", true, "Star names. Example: \"Kevin Bacon\" or \"Kevin Bacon;Tom Cruise\"");
        starOption.setRequired(true);
        options.addOption(starOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);

            Map<String, String> stringStringMap = new HashMap<>();

            stringStringMap.put(PATH_KEY, cmd.getOptionValue(PATH_KEY));

            String[] starNames = cmd.getOptionValue("star").split(";");

            if (starNames.length > 2) {
                throw new ParseException("Max size 2 for star names");
            }

            if (starNames.length == 1) {
                stringStringMap.put(SOURCE_STAR_KEY, "Kevin Bacon");
                stringStringMap.put(DEST_STAR_KEY, starNames[0]);
            } else {
                stringStringMap.put(SOURCE_STAR_KEY, starNames[0]);
                stringStringMap.put(DEST_STAR_KEY, starNames[1]);
            }

            return stringStringMap;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        return null;
    }
}
