package common;

import DTO.MovieDTO;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.List;

public class FileController {

    private static final String OUTPUT_FILENAME = "%ss-movies.json";
    private Gson gson = new Gson();

    public JsonReader openFile(String folder) throws FileNotFoundException {
        return new JsonReader(new FileReader(folder));
    }

    public void createDecadeFile(List<MovieDTO> movieDTOList, String outputFolder, int decade) throws IOException {
        String filename = getOutputDecadeFileName(decade);
        Writer writer = new FileWriter(outputFolder + filename);
        gson.toJson(movieDTOList, writer);
        writer.close();
    }

    public String getOutputDecadeFileName(int decade){
        StringBuilder filenameOutput = new StringBuilder();
        if(decade < 2000){
            decade += -1900;
        }
        filenameOutput.append(String.format(OUTPUT_FILENAME, decade));
        return filenameOutput.toString();
    }

}
