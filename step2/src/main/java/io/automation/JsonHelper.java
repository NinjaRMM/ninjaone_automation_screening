package io.automation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class JsonHelper {

    public static final String MSG_NOT_POSSIBLE_TO_READ_JSON =
            "Not possible to read json file";
    public static final String MSG_NOT_POSSIBLE_TO_WRITE_JSON =
            "Not possible to write json file";

    private JsonHelper() {
    }

    public static JSONObject getJsonObject(final String configJsonPath) {
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = new FileInputStream(configJsonPath)) {
            Reader fileReader = new InputStreamReader(inputStream);
            jsonObject = (JSONObject) parser.parse(fileReader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(MSG_NOT_POSSIBLE_TO_READ_JSON);
        }
        return jsonObject;
    }


    public static <T> List<T> getObjectsFromFile(final String filename, Type type) {
        List<T> list;
        try {
            Reader reader = new FileReader(filename);
            Gson gson = new Gson();
            list = gson.fromJson(reader, type);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(MSG_NOT_POSSIBLE_TO_READ_JSON);
        }
        return list;

    }

    public static <T> void writeJson(T json, String path) {
        try {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create();
            Writer writer = Files.newBufferedWriter(Paths.get(path));
            gson.toJson(json, writer);
            writer.close();

        } catch (Exception ex) {
            throw new RuntimeException(MSG_NOT_POSSIBLE_TO_WRITE_JSON);
        }
    }


}
