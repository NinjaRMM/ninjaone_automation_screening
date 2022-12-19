package rubens.step2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieFilter {
	
	private static final String MISSING_REQUIRED_ARGUMENTS_DECADE_AND_OUTPUT = "Missing required arguments: --decade and --output";
	private static final String MOVIES_JSON = "movies.json";
	
	public void run(String[] args) throws IOException {
		FilterInput filterInput = getInputs(args);
		validateFilters(filterInput);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory jsonFactory = new JsonFactory(mapper);
		JsonParser jsonParser = getJsonParser(jsonFactory);
		
        try (OutputStream outputStream = getFileOutputStream(filterInput.getOutputPath())) {
        	try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream)) {
        		jsonGenerator.writeStartArray();
        		processJsonContent(filterInput, jsonParser, jsonGenerator);
        		jsonGenerator.writeEndArray();
        	}
        }
	}
	
	private void processJsonContent(FilterInput filterInput, JsonParser jsonParser, JsonGenerator jsonGenerator) throws IOException {
		while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
			JsonToken token = jsonParser.getCurrentToken();
			if (token == JsonToken.START_OBJECT) {
				processJsonNode(filterInput, jsonParser, jsonGenerator);
			}
		}
	}
	
	private void processJsonNode(FilterInput filterInput, JsonParser jsonParser, JsonGenerator jsonGenerator) throws IOException {
		JsonNode elementNode = jsonParser.readValueAsTree();
		Integer year = elementNode.get("year").asInt();
		int movieDecade = year % 100;
		// I was not sure how to proceed with decade=10. I'm considering 1910-1919 and 2010-2019. I hope that's ok
		if (movieDecade >= filterInput.getDecade() && movieDecade <= filterInput.getDecade() + 9) {
			jsonGenerator.writeTree(elementNode);
		}
	}
	
	private FileOutputStream getFileOutputStream(String outputPath) throws IOException {
		File outputFile = new File(outputPath);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        return new FileOutputStream(outputFile);
	}
	
	private JsonParser getJsonParser(JsonFactory jsonFactory) throws IOException {
		InputStream is = MovieFilter.class.getClassLoader().getResourceAsStream(MOVIES_JSON);
		return jsonFactory.createParser(is);
	}

	private FilterInput getInputs(String[] args) {
		if (args == null) {
			throw new IllegalArgumentException(MISSING_REQUIRED_ARGUMENTS_DECADE_AND_OUTPUT);
		}
		
		Integer decade = null;
		String outputPath = null;
		for (String arg : args) {
			String[] parts = arg.split("=");
			if (parts[0].equals("--decade")) {
				decade = Integer.parseInt(parts[1]);
			} else if (parts[0].equals("--output")) {
				outputPath = parts[1];
			}
		}

		return new FilterInput(decade, outputPath);
	}
	
	private void validateFilters(FilterInput filterInput) {
		if (filterInput.getDecade() == null || filterInput.getOutputPath() == null) {
			throw new IllegalArgumentException(MISSING_REQUIRED_ARGUMENTS_DECADE_AND_OUTPUT);
		}

		if (filterInput.getDecade() % 10 > 0 || filterInput.getDecade() < 0) {
			throw new IllegalArgumentException("Decade must be divisible by 10 and bigger than 0, example: 80");
		}
	}
	
	public static void main(String[] args) throws IOException {
		MovieFilter jsonFilter = new MovieFilter();
		jsonFilter.run(args);
	}

}
