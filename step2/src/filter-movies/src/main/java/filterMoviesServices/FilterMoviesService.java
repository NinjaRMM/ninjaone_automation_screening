package filterMoviesServices;

import DTO.MovieDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.FileController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FilterMoviesService {

    private static final String INVALID_DECADE = "Invalid decade";
    private FileController fileController = new FileController();

    /**
     * Filter movies by decade
     * @param decade to be analyzed
     * @param isStrictMode if is true accept exact decade times, false accept more flexible times
     */
    public void filterMovies(int decade, Boolean isStrictMode){

        int fixedDecade = validateDecade(decade, isStrictMode);
        Gson gson = new Gson();

        if(fixedDecade < 0){
            System.out.println(INVALID_DECADE);
            return;
        }

        //Using a static path, but this could be parameterized
        try(JsonReader reader = fileController.openFile("./movies.json")) {
            List<MovieDTO> movies = gson.fromJson(reader, new TypeToken<List<MovieDTO>>(){}.getType());

            List<MovieDTO> decadeMovies  = movies.stream().filter(movie -> movie.getYear() >= fixedDecade
                    && movie.getYear() < fixedDecade + 10).collect(Collectors.toList());

            fileController.createDecadeFile(decadeMovies, "./", fixedDecade);

        }catch (FileNotFoundException e){
            System.out.println("Cant not find movies file, should be in same folder");
        }catch (IOException e){
            System.out.println("Error reading or writing file");
        }


    }

    /**
     * Validate if decade input is correct
     * @param decade to be analyzed
     * @param isStrictMode if is true accept exact decade times, false accept more flexible times
     * @return decade in 4 digits
     */
    private int validateDecade(int decade, Boolean isStrictMode){
        int resultDecade = -1;

        if(decade > 0){
            if(decade < 100 || decade >= 1900) {
                resultDecade = validateStrictMode(decade, isStrictMode);
            }
            if(decade < 100){
                resultDecade += 1900;
            }
        }
        return resultDecade;

    }

    /**
     * Validate if strict mode is on, if is true accept exact decade times, false accept more flexible times
     * @param decade to be analyzed
     * @param isStrictMode
     * @return
     */
    private int validateStrictMode(int decade, Boolean isStrictMode){
        StringBuilder outputMessage = new StringBuilder();
        if(isStrictMode){
            if (decade % 10 != 0) return -1;
        }

        if (decade % 10 != 0) {
            decade = decade - decade % 10;
            outputMessage.append("Oh we see that you use and incorrect decade")
                    .append(System.lineSeparator())
                    .append("Dont worry we are on flex mode, so we change your decade to: ")
                    .append(decade)
                    .append(System.lineSeparator())
                    .append("You can active strictMode sending true in second parameter");
            System.out.println(outputMessage.toString());
        }

        return decade;
    }
}
