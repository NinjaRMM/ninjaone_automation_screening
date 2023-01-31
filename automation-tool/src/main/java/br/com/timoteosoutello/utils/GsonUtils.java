package br.com.timoteosoutello.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import br.com.timoteosoutello.domain.movie.Movie;

@Component
public class GsonUtils {
	private final Gson defaultGson = new Gson();
	private final Gson prettyDisabledEscapingGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
			.create();

	public List<Movie> loadJsonFromPathAndTyped(Path path, Type type)
			throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		return defaultGson.fromJson(new JsonReader(new FileReader(path.toFile())), type);
	}

	public void writesTo(Object object, FileWriter fileWriter, Type type) throws IOException {
		try (Writer writer = fileWriter) {
			prettyDisabledEscapingGson.toJson(object, type, writer);
		}
	}
}
