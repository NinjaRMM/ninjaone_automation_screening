package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.DataInput;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Excercise02 {
    public static void main(String[] args) throws Exception{
        try {
            String path = args[0];
            String nombreArchivoEntrada = args[1];
            String nombreArchivoSalida = args[2];
            String inityear = args[3];
            String finalyear = args[3];
            if(path == null || nombreArchivoEntrada == null
            || nombreArchivoSalida == null || inityear == null || finalyear == null) {
                System.out.println("ingrese todos los parametros");
                return;
            }
            Reader reader = Files.newBufferedReader(Paths.get("/home/will/NINJA/ninjaone_automation_screening/data/movies.json"));
            Type collectionType = new TypeToken<Collection<Movie>>(){}.getType();
            List<Movie> movies = new Gson().fromJson(reader, collectionType);
            List<Movie> movies80 = movies.stream()
                    .filter(el -> el.getYear() >= 1980 && el.getYear() <= 1989 )
                    .collect(Collectors.toList());
            reader.close();
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(movies80);
            System.out.println(jsonArray);
            try (FileWriter file = new FileWriter("/home/will/NINJA/ninjaone_automation_screening/data/80s-movies.json")) {
                file.write(jsonArray.toJSONString());
                file.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

