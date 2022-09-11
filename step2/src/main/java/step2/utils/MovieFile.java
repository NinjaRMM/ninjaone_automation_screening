package step2.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import step2.models.Movie;

/**
 * Utility class for reading and writing a list of movies from and to a file.
 */
public class MovieFile {

  /**
   * Read a list of movies from a JSON file.
   * @param path_ The path of the file to read.
   * @return The list of movies from the file.
   * @throws IOException If unable to read the file for some reason.
   */
  public static Collection<Movie> read(String path_) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return Arrays.asList(objectMapper.readValue(new File(path_), Movie[].class));
  }

  /**
   * Write a list of movies to a JSON file.
   * @param path_ The path to write the files to.
   * @param movies The list of movies to write.
   * @throws IOException If unable to write to the file for some reason.
   */
  public static void write(String path_, Collection<Movie> movies) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(new File("../data/" + path_), movies);
  }
}
