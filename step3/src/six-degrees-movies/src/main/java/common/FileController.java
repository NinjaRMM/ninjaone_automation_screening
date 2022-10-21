package common;

import DTO.MovieDTO;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.List;

public class FileController {

    private Gson gson = new Gson();

    public JsonReader openFile(String folder) throws FileNotFoundException {
        return new JsonReader(new FileReader(folder));
    }

    public void createDecadeFile(List<MovieDTO> movieDTOList, String outputFolder, int decade) throws IOException {
        String filename = getOutputDecadeFileName(decade);
        Writer writer = new FileWriter(outputFolder + filename);
        gson.toJson(movieDTOList, writer);
        writer.close();

        System.out.println("File created successfully ");
    }

    public String getOutputDecadeFileName(int decade){
        StringBuilder filenameOutput = new StringBuilder();
        filenameOutput.append("output-from-")
                .append(decade)
                .append("-to-")
                .append(decade+9)
                .append(".json");
        return filenameOutput.toString();
    }

}
