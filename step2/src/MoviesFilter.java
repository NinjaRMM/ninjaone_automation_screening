package step2.src;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MoviesFilter {
    private static final String moviesFile = "../../data/movies.json";
    private static final String filteredMoviesFilePath = "data/moviesFiltered.json";

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {
            JSONArray movies = (JSONArray) parser.parse(new FileReader(moviesFile));
            String commandParameter = getCommandParameter(args[0].toLowerCase());
            String commandValue = getCommandValue(args[0].toLowerCase());
            JSONArray filteredMovies = filterMovies(movies, commandParameter, commandValue);
            createFilteredMoviesJsonFile(filteredMovies);

            System.out.println("Filtered movies added to the file: moviesFiltered.json, inside the data folder");

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONArray filterMovies(JSONArray movies, String commandParameter, String commandValue) {
        JSONArray filteredMovies = new JSONArray();

        for (Object o : movies) {
            JSONObject movie = (JSONObject) o;

            if (commandParameter.equals("year")) {
                Long resultFromCommand = (Long) movie.get(commandParameter);
                Long intValue = Long.parseLong(commandValue);

                if (resultFromCommand.equals(intValue)) {
                    filteredMovies.add(movie);
                }
            } else if (commandParameter.equals("title")) {
                String resultFromCommand = ((String) movie.get(commandParameter)).toLowerCase().replaceAll(" ", "");

                if (resultFromCommand.equals(commandValue)) {
                    filteredMovies.add(movie);
                }
            } else {
                JSONArray arrayResultFromCommand = (JSONArray) movie.get(commandParameter);

                for (Object value : arrayResultFromCommand) {
                    String s = value.toString().toLowerCase().replaceAll(" ", "");

                    if (s.equals(commandValue)) {
                        filteredMovies.add(movie);
                    }
                }
            }

        }

        return filteredMovies;
    }

    private static void createFilteredMoviesJsonFile(JSONArray result) throws IOException {
        FileWriter fileWriter = new FileWriter(filteredMoviesFilePath);

        try {
            fileWriter.write(result.toJSONString());
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private static String getCommandParameter(String arg) {
        return splitParameterAndValue(arg, 0);
    }

    private static String getCommandValue(String arg) {
        return splitParameterAndValue(arg, 1);
    }

    private static String splitParameterAndValue(String arg, int pos) {
        String[] splitedString = arg.split("-");
        return splitedString[pos];
    }
}
